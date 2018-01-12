package com.fooock.github.jobs.di;

import com.fooock.github.jobs.GithubJobsApplication;
import com.fooock.github.jobs.Navigation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class AppModule {

    private final GithubJobsApplication mApplication;

    public AppModule(GithubJobsApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    GithubJobsApplication providesApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    Navigation providesNavigation() {
        return new Navigation();
    }
}
