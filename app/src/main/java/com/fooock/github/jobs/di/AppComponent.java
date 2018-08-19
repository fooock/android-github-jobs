package com.fooock.github.jobs.di;

import com.fooock.github.jobs.activity.GithubJobsActivity;
import com.fooock.github.jobs.fragment.JobDetailFragment;
import com.fooock.github.jobs.fragment.JobsFragment;

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

    void inject(JobDetailFragment fragment);
}
