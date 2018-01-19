package com.fooock.github.jobs.domain.interactor;

import com.fooock.github.jobs.domain.Repository;
import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class FilterJobs extends ObservableUseCase<List<JobOffer>, String> {

    private Repository mRepository;

    @Inject
    FilterJobs(MainThread mainThread, ThreadExecutor threadExecutor, Repository repo) {
        super(mainThread, threadExecutor);
        mRepository = repo;
    }

    @Override
    protected Observable<List<JobOffer>> build(final String params) {
        return mRepository.filterBy(String.format("%%%s%%", params));
    }
}
