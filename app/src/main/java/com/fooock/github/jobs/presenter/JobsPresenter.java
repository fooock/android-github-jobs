package com.fooock.github.jobs.presenter;

import com.fooock.github.jobs.presenter.view.JobsView;

import javax.inject.Inject;

/**
 *
 */
public class JobsPresenter extends Presenter<JobsView> {

    private static final int JOBS_FIRST_PAGE = 1;

    @Inject
    public JobsPresenter() {
    }

    public void loadFirstPage() {
        loadJobs(JOBS_FIRST_PAGE);
    }

    public void loadJobs(int page) {

    }
}
