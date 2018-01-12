package com.fooock.github.jobs.di;

import com.fooock.github.jobs.GithubJobsActivity;

import dagger.Component;

/**
 *
 */
@Component(modules = {AppModule.class, IoModule.class})
public interface AppComponent {

    void inject(GithubJobsActivity activity);
}
