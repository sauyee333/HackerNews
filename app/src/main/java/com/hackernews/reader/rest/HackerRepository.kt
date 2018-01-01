package com.hackernews.reader.rest

import com.hackernews.reader.rest.model.Comments
import com.hackernews.reader.rest.model.Story
import rx.Observable

/**
 * Created by sauyee on 26/12/17.
 */
interface HackerRepository {
    fun getHackerTopStories(): Observable<List<String>>
    fun getHackerStory(story: String): Observable<Story>
    fun getHackerComment(commentId: String): Observable<Comments>
}