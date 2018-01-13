package com.fooock.github.jobs.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 *
 */
@Entity(tableName = "jobs")
public class JobData {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String mId;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String mTitle;

    @ColumnInfo(name = "localization")
    @SerializedName("localization")
    private String mLocation;

    @ColumnInfo(name = "type")
    @SerializedName("type")
    private String mType;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    private String mDescription;

    @ColumnInfo(name = "how_to_apply")
    @SerializedName("how_to_apply")
    private String mHowToApply;

    @ColumnInfo(name = "company")
    @SerializedName("company")
    private String mCompany;

    @ColumnInfo(name = "company_url")
    @SerializedName("company_url")
    private String mCompanyUrl;

    @ColumnInfo(name = "company_logo")
    @SerializedName("company_logo")
    private String mCompanyLogoUrl;

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    private String mCreatedAt;

    public JobData() {
        mId = "";
    }

    @NonNull
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
