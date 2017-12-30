package com.hackernews.reader.rest

import com.hackernews.reader.rest.model.Story
import rx.Observable

/**
 * Created by sauyee on 26/12/17.
 */
interface HackerRepository {
    fun showHackerStory(): Observable<List<Story>>


    fun getHackerTopStories(): Observable<List<String>>
    fun getHackerStory(story: String): Observable<Story>
}