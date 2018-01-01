package com.hackernews.reader.ui.news;

import com.hackernews.reader.rest.model.CommentContent;
import com.hackernews.reader.rest.model.Comments;
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

        void showCommentList(List<Comments> commentsList);

        void addSubCommentList(List<Comments> commentsList, CommentContent commentContent);

        void onStorySelected(List<String> commentList);
    }

    public interface NewsCommentListener {
        void onCommentClicked(List<String> commentList, CommentContent commentContent);
    }

    public interface StoryPresenter extends BasePresenter<NewsView> {
        void getTopStories();
    }

    public interface CommentPresenter extends BasePresenter<NewsView> {
        void getStoryComments(List<String> commentList);

        void getSubComments(List<String> commentList, CommentContent commentContent);
    }
}
