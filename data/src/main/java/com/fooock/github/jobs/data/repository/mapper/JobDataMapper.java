package com.fooock.github.jobs.data.repository.mapper;

import com.fooock.github.jobs.data.entity.JobData;
import com.fooock.github.jobs.domain.mapper.Mapper;
import com.fooock.github.jobs.domain.model.Company;
import com.fooock.github.jobs.domain.model.JobOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class JobDataMapper implements Mapper<JobData, JobOffer> {

    @Override
    public JobOffer map(JobData job) {
        JobOffer offer = new JobOffer();
        offer.setId(job.getId());
        offer.setDescription(job.getDescription());
        offer.setTitle(job.getTitle());
        offer.setHowToApply(job.getHowToApply());
        offer.setType(job.getType());
        offer.setLocation(job.getLocation());
        offer.setCreatedAt(new Date(job.getCreatedAt()));

        Company company = new Company(job.getCompany(), job.getCompanyUrl(), job.getCompanyLogoUrl());
        offer.setCompany(company);

        return offer;
    }

    @Override
    public List<JobOffer> map(List<JobData> from) {
        ArrayList<JobOffer> jobs = new ArrayList<>(from.size());
        for (JobData job : from) {
            jobs.add(map(job));
        }
        Collections.sort(jobs);
        return jobs;
    }
}
