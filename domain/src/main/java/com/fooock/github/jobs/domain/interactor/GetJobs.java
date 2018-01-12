package com.fooock.github.jobs.domain.interactor;

import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class GetJobs extends ObservableUseCase<List<JobOffer>, Integer> {

    @Inject
    GetJobs(MainThread mainThread, ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
    }

    @Override
    protected Observable<List<JobOffer>> build(Integer params) {
        return Observable.empty();
    }
}
