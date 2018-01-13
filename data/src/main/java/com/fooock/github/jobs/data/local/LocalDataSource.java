package com.fooock.github.jobs.data.local;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class LocalDataSource implements DataSource {

    private final JobDao mJobDao;

    @Inject
    public LocalDataSource(JobDao jobDao) {
        mJobDao = jobDao;
    }

    @Override
    public Observable<List<JobData>> getJobs(int page) {
        return Observable.empty();
    }

    @Override
    public void save(List<JobData> jobs) {

    }
}
