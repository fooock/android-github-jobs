package com.fooock.github.jobs.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class JobViewModel implements Parcelable {

    public static final Creator<JobViewModel> CREATOR = new Creator<JobViewModel>() {
        @Override
        public JobViewModel createFromParcel(Parcel in) {
            return new JobViewModel(in);
        }

        @Override
        public JobViewModel[] newArray(int size) {
            return new JobViewModel[size];
        }
    };

    private String mId;
    private String mTitle;
    private String mLocation;
    private String mType;
    private String mDate;

    private CompanyViewModel mCompany;

    public JobViewModel(String id, String title, String location, String type, CompanyViewModel company, String date) {
        mId = id;
        mTitle = title;
        mLocation = location;
        mType = type;
        mCompany = company;
        mDate = date;
    }

    private JobViewModel(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mLocation = in.readString();
        mType = in.readString();
        mCompany = in.readParcelable(CompanyViewModel.class.getClassLoader());
        mDate = in.readString();
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

    public String getDate() {
        return mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mLocation);
        dest.writeString(mType);
        dest.writeParcelable(mCompany, flags);
        dest.writeString(mDate);
    }
}
