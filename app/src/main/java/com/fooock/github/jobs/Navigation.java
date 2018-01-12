package com.fooock.github.jobs;

import android.app.FragmentManager;

/**
 * Provides navigation between screens and services
 */
public class Navigation {

    void showJobsFragment(FragmentManager manager) {
        manager.beginTransaction()
                .add(R.id.container, new JobsFragment())
                .commit();
    }
}
