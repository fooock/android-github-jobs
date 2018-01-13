package com.fooock.github.jobs.presenter.view;

import android.support.annotation.StringRes;

import com.fooock.github.jobs.model.JobViewModel;

import java.util.List;

/**
 *
 */
public interface JobsView {

    void onJobsLoaded(List<JobViewModel> jobs);

    void onError(@StringRes int message, Throwable error);

    void loading(boolean isLoading);
}
