package com.fooock.github.jobs.domain;

import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 *
 */
public interface Repository {

    Flowable<List<JobOffer>> getJobs(int page);

    Observable<List<JobOffer>> filterBy(String query);
}
