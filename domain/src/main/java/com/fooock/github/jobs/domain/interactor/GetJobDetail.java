package com.fooock.github.jobs.domain.interactor;

import com.fooock.github.jobs.domain.Repository;
import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;
import com.fooock.github.jobs.domain.model.JobOffer;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class GetJobDetail extends ObservableUseCase<JobOffer, String> {

    private Repository mRepository;

    @Inject
    public GetJobDetail(MainThread mainThread, ThreadExecutor threadExecutor, Repository repo) {
        super(mainThread, threadExecutor);
        mRepository = repo;
    }

    @Override
    protected Observable<JobOffer> build(String jobId) {
        return mRepository.getJobDetail(jobId);
    }
}
