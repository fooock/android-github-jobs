package com.fooock.github.jobs.presenter;

import android.support.annotation.CallSuper;

/**
 * Base class for all presenters
 */
abstract class Presenter<T> {

    private T mView;

    @CallSuper
    public void attach(T view) {
        mView = view;
    }

    @CallSuper
    public void detach() {
        mView = null;
    }

    protected final boolean isAttached() {
        return mView != null;
    }

    protected final T getView() {
        return mView;
    }
}
