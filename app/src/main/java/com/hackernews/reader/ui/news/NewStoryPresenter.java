package com.hackernews.reader.ui.news;

import com.hackernews.reader.rest.HackerRepository;
import com.hackernews.reader.rest.model.Story;
import com.hackernews.reader.ui.base.GenericPresenter;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;

/**
 * Created by sauyee on 29/12/17.
 */

public class NewStoryPresenter extends GenericPresenter<NewsInterface.NewsView> implements NewsInterface.Presenter {
    private final Scheduler ioScheduler, mainScheduler;
    private HackerRepository hackerRepository;

    public NewStoryPresenter(HackerRepository hackerRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.hackerRepository = hackerRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    @Override
    public void getTopStories() {
        checkViewAttached();
        getView().showLoading();
        addSubscription(hackerRepository.showHackerStory()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Subscriber<List<Story>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().hideLoading();
                        getView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<Story> storyList) {
                        getView().hideLoading();
                        getView().showNewsList(storyList);
                    }
                }));
    }
}
