package com.fooock.github.jobs.data.repository;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.remote.JobsApiService;
import com.fooock.github.jobs.domain.Repository;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class DefaultRepository implements Repository {

    private final JobsApiService mJobsApiService;
    private final DataSource mLocalDataSource;

    @Inject
    public DefaultRepository(JobsApiService jobsApiService, DataSource localDataSource) {
        mJobsApiService = jobsApiService;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Observable<List<JobOffer>> getJobs(int page) {
        return Observable.empty();
    }
}
