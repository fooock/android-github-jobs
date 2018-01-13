package com.fooock.github.jobs.presenter.mapper;

import com.fooock.github.jobs.domain.mapper.Mapper;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.model.JobViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JobOfferMapper implements Mapper<List<JobOffer>, List<JobViewModel>> {

    @Override
    public List<JobViewModel> map(List<JobOffer> from) {
        ArrayList<JobViewModel> jobs = new ArrayList<>(from.size());
        for (JobOffer offer : from) {
            jobs.add(new JobViewModel());
        }
        return jobs;
    }
}
