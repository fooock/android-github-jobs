package com.fooock.github.jobs;

import android.app.FragmentManager;

import com.fooock.github.jobs.fragment.JobsFragment;

/**
 * Provides navigation between screens and services
 */
public class Navigation {

    public void showJobsFragment(FragmentManager manager) {
        manager.beginTransaction()
                .add(R.id.container, new JobsFragment())
                .commit();
    }
}
