package com.fooock.github.jobs.model;

import java.util.Date;

/**
 *
 */
public class JobViewModel {

    private String mId;
    private String mTitle;
    private String mLocation;
    private String mType;

    private CompanyViewModel mCompany;
    private Date mDate;

    public JobViewModel(String id, String title, String location, String type, CompanyViewModel company, Date date) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mType = type;
        mCompany = company;
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getType() {
        return mType;
    }

    public CompanyViewModel getCompany() {
        return mCompany;
    }

    public Date getDate() {
        return mDate;
    }
}
