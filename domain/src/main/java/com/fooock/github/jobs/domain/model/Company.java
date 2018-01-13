package com.fooock.github.jobs.domain.model;

/**
 *
 */
public class Company {

    private String mCompany;
    private String mCompanyUrl;
    private String mCompanyLogoUrl;

    public Company(String name, String url, String logoUrl) {
        mCompany = name;
        mCompanyUrl = url;
        mCompanyLogoUrl = logoUrl;
    }

    public String getName() {
        return mCompany;
    }

    public String getUrl() {
        return mCompanyUrl;
    }

    public String getLogoUrl() {
        return mCompanyLogoUrl;
    }
}
