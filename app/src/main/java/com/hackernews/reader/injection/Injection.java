package com.hackernews.reader.injection;

import com.hackernews.reader.rest.HackerNewsRestInterface;
import com.hackernews.reader.rest.HackerRepoImpl;
import com.hackernews.reader.rest.HackerRepository;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sauyee on 29/12/17.
 */

public class Injection {
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0/";
    private static OkHttpClient okHttpClient;
    private static HackerNewsRestInterface hackerNewsRestInterface;
    private static Retrofit retrofitInstance;

    public static HackerRepository provideHackerRepository() {
        return new HackerRepoImpl(provideHackerNewsRestInterface());
    }

    static HackerNewsRestInterface provideHackerNewsRestInterface() {
        if (hackerNewsRestInterface == null) {
            hackerNewsRestInterface = getRetrofitInstance().create(HackerNewsRestInterface.class);
        }
        return hackerNewsRestInterface;
    }

    static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        }
        return okHttpClient;
    }

    static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            Retrofit.Builder retrofit = new Retrofit.Builder().client(Injection.getOkHttpClient()).baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
            retrofitInstance = retrofit.build();

        }
        return retrofitInstance;
    }
}
