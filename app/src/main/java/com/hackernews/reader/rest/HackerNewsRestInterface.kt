package com.hackernews.reader.rest

import com.hackernews.reader.rest.model.Comments
import com.hackernews.reader.rest.model.Story
import rx.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by sauyee on 26/12/17.
 */
interface HackerNewsRestInterface {
    @GET("topstories.json")
    fun getHackerTopStories(): Observable<List<String>>

    @GET("item/{storyId}.json")
    fun getHackerStory(@Path("storyId") storyId: String): Observable<Story>

    @GET("item/{commentId}.json")
    fun getHackerComments(@Path("commentId") commentId: String): Observable<Comments>

}

//https://hacker-news.firebaseio.com/v0/topstories.json
//https://hacker-news.firebaseio.com/v0/item/16000389.json
//https://hacker-news.firebaseio.com/v0/item/16007744.json
//https://hacker-news.firebaseio.com/v0/item/16007791.json
