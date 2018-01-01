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

public class NewsStoryPresenter extends GenericPresenter<NewsInterface.NewsView> implements NewsInterface.StoryPresenter {
    private final Scheduler ioScheduler, mainScheduler;
    private HackerRepository hackerRepository;

    public NewsStoryPresenter(HackerRepository hackerRepository, Scheduler ioScheduler, Scheduler mainScheduler) {
        this.hackerRepository = hackerRepository;
        this.ioScheduler = ioScheduler;
        this.mainScheduler = mainScheduler;
    }

    private void addSingleStorySubscription(String storyId, final List<Story> stories, final int total) {
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
                            addSingleStorySubscription(storyId, stories, storyList.size());
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