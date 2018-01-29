package com.fooock.github.jobs.domain.interactor;

import com.fooock.github.jobs.domain.ObserverAdapter;
import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
abstract class ObservableUseCase<T, P> {

    private final MainThread mMainThread;
    private final ThreadExecutor mThreadExecutor;
    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    ObservableUseCase(MainThread mainThread, ThreadExecutor threadExecutor) {
        mMainThread = mainThread;
        mThreadExecutor = threadExecutor;
    }

    protected abstract Observable<T> build(P params);

    public void execute(ObserverAdapter<T> observer, P params) {
        Observable<T> observable = build(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mMainThread.scheduler());
        mCompositeDisposable.add(observable.subscribeWith(observer));
    }

    public void close() {
        if (mCompositeDisposable.isDisposed()) mCompositeDisposable.dispose();
    }
}
