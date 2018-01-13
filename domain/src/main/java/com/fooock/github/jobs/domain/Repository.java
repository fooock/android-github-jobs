package com.fooock.github.jobs.domain;

import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */
public interface Repository {

    Observable<List<JobOffer>> getJobs(int page);
}
