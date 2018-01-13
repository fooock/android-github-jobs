package com.fooock.github.jobs.domain.model;

import java.util.Date;

/**
 *
 */
public class JobOffer {

    private String mId;
    private String mTitle;
    private String mLocation;
    private String mType;
    private String mDescription;
    private String mHowToApply;

    private Company mCompany;
    private Date mCreatedAt;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getHowToApply() {
        return mHowToApply;
    }

    public void setHowToApply(String howToApply) {
        mHowToApply = howToApply;
    }

    public Company getCompany() {
        return mCompany;
    }

    public void setCompany(Company company) {
        mCompany = company;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }
}
