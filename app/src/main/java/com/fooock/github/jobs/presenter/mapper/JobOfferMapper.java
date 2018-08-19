package com.fooock.github.jobs.presenter.mapper;

import com.fooock.github.jobs.domain.mapper.Mapper;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.model.CompanyViewModel;
import com.fooock.github.jobs.model.JobViewModel;
import com.fooock.github.jobs.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class JobOfferMapper implements Mapper<JobOffer, JobViewModel> {

    @Override
    public JobViewModel map(JobOffer offer) {
        Date to = new Date(System.currentTimeMillis());

        CompanyViewModel company = new CompanyViewModel(
                offer.getCompany().getName().trim(), offer.getCompany().getUrl(), offer.getCompany().getLogoUrl());

        String date = DateUtil.elapsedTime(offer.getCreatedAt(), to);

        return new JobViewModel(offer.getId(), offer.getTitle().trim(), offer.getLocation().trim(),
                offer.getType().trim(), company, date, offer.getCreatedAt());


    }

    @Override
    public List<JobViewModel> map(List<JobOffer> from) {
        ArrayList<JobViewModel> jobs = new ArrayList<>(from.size());
        for (JobOffer offer : from) {
            jobs.add(map(offer));
        }
        return jobs;
    }
}
