package com.fooock.github.jobs.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
public class JobData {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("localization")
    private String mLocation;

    @SerializedName("type")
    private String mType;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("how_to_apply")
    private String mHowToApply;

    @SerializedName("company")
    private String mCompany;

    @SerializedName("company_url")
    private String mCompanyUrl;

    @SerializedName("company_logo")
    private String mCompanyLogoUrl;

    @SerializedName("created_at")
    private String mCreatedAt;

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

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getCompanyUrl() {
        return mCompanyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        mCompanyUrl = companyUrl;
    }

    public String getCompanyLogoUrl() {
        return mCompanyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl) {
        mCompanyLogoUrl = companyLogoUrl;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
