package com.fooock.github.jobs.presenter.mapper;

import com.fooock.github.jobs.domain.mapper.Mapper;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.model.CompanyViewModel;
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

            CompanyViewModel company = new CompanyViewModel(
                    offer.getCompany().getName(), offer.getCompany().getUrl(), offer.getCompany().getLogoUrl());

            jobs.add(new JobViewModel(offer.getId(), offer.getTitle(), offer.getLocation(),
                    offer.getType(), company, offer.getCreatedAt()));
        }
        return jobs;
    }
}
