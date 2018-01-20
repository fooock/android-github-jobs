package com.fooock.github.jobs.domain.interactor;

import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 *
 */
abstract class FlowableUseCase<T, P> {

    private final MainThread mMainThread;
    private final ThreadExecutor mThreadExecutor;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    FlowableUseCase(MainThread mainThread, ThreadExecutor threadExecutor) {
        mMainThread = mainThread;
        mThreadExecutor = threadExecutor;
    }

    protected abstract Flowable<T> build(P params);

    public void execute(final DisposableObserver<T> observer, P params) {
        Flowable<T> observable = build(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mMainThread.scheduler());
        observable.subscribeWith(new DisposableSubscriber<T>() {
            @Override
            public void onNext(T t) {
                observer.onNext(t);
            }

            @Override
            public void onError(Throwable t) {
                observer.onError(t);
            }

            @Override
            public void onComplete() {
                observer.onComplete();
            }
        });
    }

    public void close() {
        if (mCompositeDisposable.isDisposed()) mCompositeDisposable.dispose();
    }
}
