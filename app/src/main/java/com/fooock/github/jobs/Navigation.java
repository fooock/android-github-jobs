package com.fooock.github.jobs;

import android.app.FragmentManager;
import android.os.Bundle;

import com.fooock.github.jobs.fragment.JobDetailFragment;
import com.fooock.github.jobs.fragment.JobsFragment;

/**
 * Provides navigation between screens and services
 */
public class Navigation {

    private static final String JOB_FRAGMENT_TAG = "JobFragmentTag";
    private static final String JOB_DETAIL_FRAGMENT_TAG = "JobDetailFragmentTag";

    public void showJobsFragment(FragmentManager manager) {
        JobsFragment fragment = (JobsFragment) manager.findFragmentByTag(JOB_FRAGMENT_TAG);
        if (fragment == null) {
            manager.beginTransaction()
                    .add(R.id.container, new JobsFragment(), JOB_FRAGMENT_TAG)
                    .commit();
        }
    }

    public void showJobDetailFragment(FragmentManager manager, String id, String jobName) {
        JobDetailFragment fragment = (JobDetailFragment) manager.findFragmentByTag(JOB_DETAIL_FRAGMENT_TAG);
        if (fragment == null) {
            JobDetailFragment jobDetailFragment = new JobDetailFragment();

            Bundle bundle = new Bundle();
            bundle.putString("id", id);
            bundle.putString("jobName", jobName);
            jobDetailFragment.setArguments(bundle);

            manager.beginTransaction()
                    .replace(R.id.container, jobDetailFragment, JOB_DETAIL_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
