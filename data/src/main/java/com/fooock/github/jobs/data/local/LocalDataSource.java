package com.fooock.github.jobs.data.local;

import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public class LocalDataSource implements DataSource {

    @Override
    public Observable<List<JobData>> getJobs(int page) {
        return Observable.empty();
    }

    @Override
    public void save(List<JobData> jobs) {

    }
}
