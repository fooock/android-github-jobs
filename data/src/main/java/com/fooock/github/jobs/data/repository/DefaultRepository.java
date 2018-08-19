package com.fooock.github.jobs.data.repository;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;
import com.fooock.github.jobs.data.remote.JobsApiService;
import com.fooock.github.jobs.data.repository.mapper.JobDataMapper;
import com.fooock.github.jobs.domain.Repository;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import javax.inject.Inject;

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
    public Observable<List<JobOffer>> getJobs(int page) {
        return Observable.concat(
                mLocalDataSource.getJobs(page)
                        .filter(new Predicate<List<JobData>>() {
                            @Override
                            public boolean test(List<JobData> jobData) {
                                return jobData.size() > 0;
                            }
                        }),
                mJobsApiService.getJobs(page)
                        .doOnNext(new Consumer<List<JobData>>() {
                            @Override
                            public void accept(List<JobData> jobData) {
                                mLocalDataSource.save(jobData);
                            }
                        }))
                .firstElement()
                .toObservable()
                .map(new Function<List<JobData>, List<JobOffer>>() {
                    @Override
                    public List<JobOffer> apply(List<JobData> jobData) {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }

    @Override
    public Observable<List<JobOffer>> filterBy(final String query) {
        return mLocalDataSource.filterBy(query)
                .map(new Function<List<JobData>, List<JobOffer>>() {
                    @Override
                    public List<JobOffer> apply(List<JobData> jobData) {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }

    @Override
    public Observable<JobOffer> getJobDetail(String jobId) {
        return mLocalDataSource.getJobDetail(jobId)
                .map(new Function<JobData, JobOffer>() {
                    @Override
                    public JobOffer apply(JobData jobData) {
                        return mJobDataMapper.map(jobData);
                    }
                });
    }
}
