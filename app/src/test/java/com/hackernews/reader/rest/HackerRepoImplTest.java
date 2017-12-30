package com.hackernews.reader.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.when;

/**
 * Created by sauyee on 29/12/17.
 */
public class HackerRepoImplTest {

    @Mock
    HackerNewsRestInterface hackerNewsRestInterface;
    private HackerRepository hackerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        hackerRepository = new HackerRepoImpl(hackerNewsRestInterface);
    }

    @Test
    public void showHackerStory_200OkResponse() {
        when(hackerNewsRestInterface.getHackerTopStories()).thenReturn(Observable.just(hackerTopStories()));
    }

    private List<String> hackerTopStories() {
        List<String> topStories = new ArrayList<>();
        topStories.add("16000895");
        topStories.add("16008340");
        topStories.add("16007633");
        return topStories;
    }

}