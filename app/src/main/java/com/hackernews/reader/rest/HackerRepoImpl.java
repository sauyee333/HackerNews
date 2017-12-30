package com.hackernews.reader.rest;

import com.hackernews.reader.rest.model.Story;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

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
    public Observable<List<Story>> showHackerStory() {
        return Observable.defer(new Func0<Observable<List<Story>>>() {
            @Override
            public Observable<List<Story>> call() {
                hackerNewsRestInterface.getHackerTopStories().concatMap(new Func1<List<String>, Observable<?>>() {
                    @Override
                    public Observable<?> call(List<String> storieslist) {
                        Observable.from(storieslist).concatMap(new Func1<String, Observable<?>>() {
                            @Override
                            public Observable<?> call(String story) {
                                return hackerNewsRestInterface.getHackerStory(story).toList();

                            }
                        });
                        return null;
                    }
                });
                return null;
            }
        }).retryWhen(new Func1<rx.Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public rx.Observable<?> call(rx.Observable<? extends Throwable> observable) {
                observable.flatMap(new Func1<Throwable, Observable<?>>() {
                    @Override
                    public Observable<?> call(Throwable throwable) {
                        if (throwable instanceof IOException) {
                            return Observable.just(null);
                        }
                        return Observable.error(throwable);
                    }
                });
                return Observable.just(null);
            }
        });
    }
}
