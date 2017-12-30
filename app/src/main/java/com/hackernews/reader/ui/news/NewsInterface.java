package com.hackernews.reader.ui.news;

import com.hackernews.reader.rest.model.Story;
import com.hackernews.reader.ui.base.BasePresenter;
import com.hackernews.reader.ui.base.BaseView;

import java.util.List;

/**
 * Created by sauyee on 29/12/17.
 */

public class NewsInterface {
    public interface NewsView extends BaseView {
        void showNewsList(List<Story> storyList);

        void showError(String message);

        void showLoading();

        void hideLoading();
    }

    public interface Presenter extends BasePresenter<NewsView> {
        void getTopStories();
    }
}