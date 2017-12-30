package com.hackernews.reader.ui.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by sauyee on 29/12/17.
 */

public class GenericPresenter<T extends BaseView> implements BasePresenter<T> {
    private T view;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
        compositeSubscription.clear();
    }

    public T getView() {
        return view;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvmViewNotAttachedException();
        }
    }

    public boolean isViewAttached() {
        return view != null;
    }

    public void addSubscription(Subscription subscription) {
        this.compositeSubscription.add(subscription);
    }

    public static class MvmViewNotAttachedException extends RuntimeException {
        public MvmViewNotAttachedException() {
            super("Plsase call Presenter.attachView(BaseView) before requesting data");
        }
    }
}
