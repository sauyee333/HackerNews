package com.hackernews.reader.rest

import com.hackernews.reader.rest.model.Comments
import com.hackernews.reader.rest.model.Story
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by sauyee on 26/12/17.
 */
interface HackerNewsRestInterface {
    @GET("topstories.json")
    fun getHackerTopStories(): Observable<List<String>>

    @GET("item/{storyId}.json")
    fun getHackerStory(@Path("storyId") storyId: String): Observable<Story>

    @GET("item/{commentId}.json")
    fun getHackerComment(@Path("commentId") commentId: String): Observable<Comments>

}

