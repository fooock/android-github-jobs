package com.fooock.github.jobs.model;

/**
 *
 */
public class CompanyViewModel {

    private String mName;
    private String mUrl;
    private String mLogoUrl;

    public CompanyViewModel(String name, String url, String logoUrl) {
        mName = name;
        mUrl = url;
        mLogoUrl = logoUrl;
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
}
