package com.fooock.github.jobs.presenter;

import com.fooock.github.jobs.presenter.view.JobDetailView;

import javax.inject.Inject;

import timber.log.Timber;

/**
 *
 */
public class JobDetailPresenter extends Presenter<JobDetailView> {

    @Inject
    public JobDetailPresenter() {
    }

    public void loadJobDetail(String jobId) {
        Timber.d("Load job detail for %s", jobId);
    }
}
