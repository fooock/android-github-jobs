package com.fooock.github.jobs.data.local;

import android.content.SharedPreferences;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 *
 */
public class LocalDataSource implements DataSource {

    private static final int CACHE_TIME = 1000 * 10 * 60;

    private static final String CACHE_TIME_TAG = "timestamp";

    private final JobDao mJobDao;
    private final SharedPreferences mPreferences;

    @Inject
    public LocalDataSource(JobDao jobDao, SharedPreferences preferences) {
        mJobDao = jobDao;
        mPreferences = preferences;
    }

    @Override
    public Observable<List<JobData>> getJobs(final int page) {
        return Observable.fromCallable(new Callable<List<JobData>>() {
            @Override
            public List<JobData> call() throws Exception {
                // check cache
                final long lastSave = mPreferences.getLong(CACHE_TIME_TAG, 0);
                final long currentTime = System.currentTimeMillis();
                if (currentTime - lastSave > CACHE_TIME) return Collections.emptyList();

                return mJobDao.getJobs();
            }
        });
    }

    private int calculateOffset(int page) {
        if (page == 0) return 0;
        return (page + 1) * 50;
    }

    @Override
    public void save(List<JobData> jobs) {
        mJobDao.save(jobs);

        // save timestamp to check data invalidation
        mPreferences.edit().putLong(CACHE_TIME_TAG, System.currentTimeMillis()).apply();
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
