package com.fooock.github.jobs.data.repository;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;
import com.fooock.github.jobs.data.remote.JobsApiService;
import com.fooock.github.jobs.data.repository.mapper.JobDataMapper;
import com.fooock.github.jobs.domain.Repository;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 *
 */
public class DefaultRepository implements Repository {

    private final JobsApiService mJobsApiService;
    private final DataSource mLocalDataSource;

    private final JobDataMapper mJobDataMapper = new JobDataMapper();

    @Inject
    public DefaultRepository(JobsApiService jobsApiService, DataSource localDataSource) {
        mJobsApiService = jobsApiService;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<List<JobOffer>> getJobs(int page) {
        return Observable.concat(getJobsLocalObservable(page), getJobsRemoteObservable(page))
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Observable<List<JobOffer>> filterBy(final String query) {
        return mLocalDataSource.filterBy(query)
                .map(new Function<List<JobData>, List<JobOffer>>() {
                    @Override
                    public List<JobOffer> apply(List<JobData> jobData) throws Exception {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }

    private Observable<List<JobOffer>> getJobsLocalObservable(int page) {
        return mLocalDataSource.getJobs(page)
                .filter(new Predicate<List<JobData>>() {
                    @Override
                    public boolean test(List<JobData> jobData) throws Exception {
                        return !jobData.isEmpty();
                    }
                })
                .map(new Function<List<JobData>, List<JobOffer>>() {
                    @Override
                    public List<JobOffer> apply(List<JobData> jobData) throws Exception {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }

    private Observable<List<JobOffer>> getJobsRemoteObservable(int page) {
        return mJobsApiService.getJobs(page)
                .doOnNext(new Consumer<List<JobData>>() {
                    @Override
                    public void accept(List<JobData> jobData) throws Exception {
                        mLocalDataSource.save(jobData);
                    }
                })
                .map(new Function<List<JobData>, List<JobOffer>>() {
                    @Override
                    public List<JobOffer> apply(List<JobData> jobData) throws Exception {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }
}
