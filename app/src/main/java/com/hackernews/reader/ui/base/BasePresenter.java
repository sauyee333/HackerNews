package com.hackernews.reader.ui.base;

/**
 * Created by sauyee on 29/12/17.
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V mvpView);

    void detachView();
}
