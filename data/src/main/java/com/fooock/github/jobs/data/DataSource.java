package com.fooock.github.jobs.data;

import android.database.Observable;

import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

/**
 * Base interface for all data sources
 */
public interface DataSource {

    Observable<List<JobData>> getJobs();
}
