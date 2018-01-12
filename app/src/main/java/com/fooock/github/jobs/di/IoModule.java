package com.fooock.github.jobs.di;

import dagger.Module;

/**
 *
 */
@Module
public class IoModule {

    private final String mBaseUrl;

    public IoModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }
}
