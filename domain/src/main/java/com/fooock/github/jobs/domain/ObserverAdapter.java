package com.fooock.github.jobs.domain;

import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public abstract class ObserverAdapter<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
