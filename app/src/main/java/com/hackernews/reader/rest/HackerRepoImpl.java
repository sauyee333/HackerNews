package com.hackernews.reader.rest;

import com.hackernews.reader.rest.model.Comments;
import com.hackernews.reader.rest.model.Story;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;

/**
 * Created by sauyee on 27/12/17.
 */

public class HackerRepoImpl implements HackerRepository {

    private HackerNewsRestInterface hackerNewsRestInterface;

    public HackerRepoImpl(HackerNewsRestInterface hackerNewsRestInterface) {
        this.hackerNewsRestInterface = hackerNewsRestInterface;
    }

    @NotNull
    @Override
    public Observable<List<String>> getHackerTopStories() {
        return hackerNewsRestInterface.getHackerTopStories();
    }

    @NotNull
    @Override
    public Observable<Story> getHackerStory(String story) {
        return hackerNewsRestInterface.getHackerStory(story);
    }

    @NotNull
    @Override
    public Observable<Comments> getHackerComment(String commentId) {
        return hackerNewsRestInterface.getHackerComment(commentId);
    }
}
