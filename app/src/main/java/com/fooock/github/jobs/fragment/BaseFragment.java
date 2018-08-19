package com.fooock.github.jobs.fragment;

import android.app.Fragment;

import com.fooock.github.jobs.activity.GithubJobsActivity;
import com.fooock.github.jobs.di.AppComponent;

/**
 * Base class for all fragments
 */
public abstract class BaseFragment extends Fragment {

    protected AppComponent component() {
        return getBaseActivity().component();
    }

    private GithubJobsActivity getBaseActivity() {
        return (GithubJobsActivity) getActivity();
    }
}
