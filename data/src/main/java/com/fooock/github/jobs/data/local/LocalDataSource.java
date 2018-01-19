package com.fooock.github.jobs.data.local;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;
import java.util.concurrent.Callable;

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
        return Observable.fromCallable(new Callable<List<JobData>>() {
            @Override
            public List<JobData> call() throws Exception {
                return mJobDao.getJobs();
            }
        });
    }

    @Override
    public void save(List<JobData> jobs) {
        mJobDao.save(jobs);
    }

    @Override
    public Observable<List<JobData>> filterBy(final String query) {
        return Observable.fromCallable(new Callable<List<JobData>>() {
            @Override
            public List<JobData> call() throws Exception {
                return mJobDao.filterBy(query);
            }
        });
    }
}
