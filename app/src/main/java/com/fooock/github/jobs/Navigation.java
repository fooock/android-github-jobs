package com.fooock.github.jobs;

import android.app.FragmentManager;

import com.fooock.github.jobs.fragment.JobsFragment;

/**
 * Provides navigation between screens and services
 */
public class Navigation {

    private static final String JOB_FRAGMENT_TAG = "JobFragmentTag";

    public void showJobsFragment(FragmentManager manager) {
        JobsFragment fragment = (JobsFragment) manager.findFragmentByTag(JOB_FRAGMENT_TAG);
        if (fragment == null) {
            manager.beginTransaction()
                    .add(R.id.container, new JobsFragment(), JOB_FRAGMENT_TAG)
                    .commit();
        }
    }
}
