package com.fooock.github.jobs.data;

import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Base interface for all data sources
 */
public interface DataSource {

    Observable<List<JobData>> getJobs(int page);

    void save(List<JobData> jobs);

    Observable<List<JobData>> filterBy(String query);
}
