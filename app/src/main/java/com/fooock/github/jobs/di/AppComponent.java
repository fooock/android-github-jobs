package com.fooock.github.jobs.di;

import com.fooock.github.jobs.GithubJobsActivity;
import com.fooock.github.jobs.JobsFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 *
 */
@Singleton
@Component(modules = {AppModule.class, IoModule.class})
public interface AppComponent {

    void inject(GithubJobsActivity activity);

    void inject(JobsFragment fragment);
}
