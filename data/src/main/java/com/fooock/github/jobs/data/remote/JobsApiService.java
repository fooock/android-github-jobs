package com.fooock.github.jobs.data.remote;

import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 */
public interface JobsApiService {

    @GET("positions.json")
    Observable<List<JobData>> getJobs(@Query("page") int page);
}
