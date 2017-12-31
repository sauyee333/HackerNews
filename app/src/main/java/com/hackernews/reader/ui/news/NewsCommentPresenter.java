package com.hackernews.reader.ui.news;

import com.hackernews.reader.rest.HackerRepository;
import com.hackernews.reader.rest.model.Comments;
import com.hackernews.reader.ui.base.GenericPresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.Subscriber;

/**
 * Created by sauyee on 29/12/17.
 */

public class NewsCommentPresenter extends GenericPresenter<NewsInterface.NewsView> implements NewsInterface.CommentPresenter {
    private final Scheduler ioScheduler, mainScheduler;
    private HackerRepository hackerRepository;

    public NewsCommentPresenter(HackerRepository hackerRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.hackerRepository = hackerRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    private void addSingleCommentSubscription(String commentId, final List<Comments> commentsList, final int total) {
        addSubscription(hackerRepository.getHackerComment(commentId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Subscriber<Comments>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().hideLoading();
                        getView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Comments comments) {
                        commentsList.add(comments);
                        if (commentsList.size() >= total) {
                            getView().hideLoading();
                            getView().showCommentList(commentsList);
                        }
                    }
                }));
    }

    @Override
    public void getStoryComments(List<String> commentIdList) {
        checkViewAttached();
        getView().showLoading();
        List<Comments> commentList = new ArrayList<>();
        for (String commentId : commentIdList) {
            addSingleCommentSubscription(commentId, commentList, commentList.size());
        }
    }
}