package com.fooock.github.jobs.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class CompanyViewModel implements Parcelable {

    public static final Creator<CompanyViewModel> CREATOR = new Creator<CompanyViewModel>() {
        @Override
        public CompanyViewModel createFromParcel(Parcel in) {
            return new CompanyViewModel(in);
        }

        @Override
        public CompanyViewModel[] newArray(int size) {
            return new CompanyViewModel[size];
        }
    };

    private String mName;
    private String mUrl;
    private String mLogoUrl;

    public CompanyViewModel(String name, String url, String logoUrl) {
        mName = name;
        mUrl = url;
        mLogoUrl = logoUrl;
    }

    private CompanyViewModel(Parcel in) {
        mName = in.readString();
        mUrl = in.readString();
        mLogoUrl = in.readString();
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getLogoUrl() {
        return mLogoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mUrl);
        dest.writeString(mLogoUrl);
    }
}
