package com.fooock.github.jobs.data.local;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import timber.log.Timber;

/**
 *
 */
public class LocalDataSource implements DataSource {

    private final JobDao mJobDao;
    private final CachePolicy mCachePolicy;

    @Inject
    public LocalDataSource(JobDao jobDao, CachePolicy policy) {
        mJobDao = jobDao;
        mCachePolicy = policy;
    }

    @Override
    public Observable<List<JobData>> getJobs(final int page) {
        return Observable.fromCallable(new Callable<List<JobData>>() {
            @Override
            public List<JobData> call() {
                if (mCachePolicy.hasExpired()) deleteAll();
                return mJobDao.getJobs(calculateOffset(page));
            }
        });
    }

    private int calculateOffset(int page) {
        if (page == 0) return 0;
        return (page + 1) * 50;
    }

    @Override
    public void save(List<JobData> jobs) {
        Timber.d("Save %s entities into database", jobs.size());
        mJobDao.save(jobs);
        mCachePolicy.updateExpiration();
    }

    @Override
    public Observable<List<JobData>> filterBy(final String query) {
        return Observable.fromCallable(new Callable<List<JobData>>() {
            @Override
            public List<JobData> call() {
                return mJobDao.filterBy(query);
            }
        });
    }

    @Override
    public void deleteAll() {
        Timber.d("Delete all entities from database");
        mJobDao.removeAll();
        mCachePolicy.clean();
    }
}
