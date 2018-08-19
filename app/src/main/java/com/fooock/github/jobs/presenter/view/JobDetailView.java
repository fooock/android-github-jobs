package com.fooock.github.jobs.presenter.view;

/**
 *
 */
public interface JobDetailView {

    void onShowLoading(boolean loading);

    void onShowJobDetail(String description);
}
