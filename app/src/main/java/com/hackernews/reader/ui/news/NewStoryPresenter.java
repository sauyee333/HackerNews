package com.hackernews.reader.ui.news;

import com.hackernews.reader.rest.HackerRepository;
import com.hackernews.reader.rest.model.Story;
import com.hackernews.reader.ui.base.GenericPresenter;

import java.util.ArrayList;
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

    private void addSingStorySubscription(String storyId, final List<Story> stories, final int total) {
        addSubscription(hackerRepository.getHackerStory(storyId)
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Subscriber<Story>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().hideLoading();
                        getView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onNext(Story story) {
                        stories.add(story);
                        if (stories.size() >= total) {
                            getView().hideLoading();
                            getView().showNewsList(stories);
                        }
                    }
                }));
    }

    private void addTopStoriesSubscription() {
        addSubscription(hackerRepository.getHackerTopStories()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getView().hideLoading();
                        getView().showError(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<String> storyList) {
                        List<Story> stories = new ArrayList<>();
                        for (String storyId : storyList) {
                            addSingStorySubscription(storyId, stories, storyList.size());
                        }
                    }
                }));
    }


    @Override
    public void getTopStories() {
        checkViewAttached();
        getView().showLoading();
        addTopStoriesSubscription();
    }
}