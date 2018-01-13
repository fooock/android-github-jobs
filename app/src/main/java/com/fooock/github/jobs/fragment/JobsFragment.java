package com.fooock.github.jobs.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.activity.GithubJobsActivity;
import com.fooock.github.jobs.di.AppComponent;
import com.fooock.github.jobs.model.JobViewModel;
import com.fooock.github.jobs.presenter.JobsPresenter;
import com.fooock.github.jobs.presenter.view.JobsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class JobsFragment extends Fragment implements JobsView {

    @BindView(R.id.rv_jobs) RecyclerView mJobList;
    @BindView(R.id.pb_loader) ProgressBar mProgressBar;

    @Inject JobsPresenter mJobsPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle(R.string.title_jobs);
        mJobsPresenter.attach(this);
        mJobsPresenter.loadFirstPage();
    }

    @Override
    public void onDestroy() {
        mJobsPresenter.detach();
        super.onDestroy();
    }

    private AppComponent component() {
        return ((GithubJobsActivity) getActivity()).component();
    }

    @Override
    public void onJobsLoaded(List<JobViewModel> jobs) {
        Timber.d("Found %s jobs", jobs.size());
    }

    @Override
    public void onError(int message, Throwable error) {
        Timber.e(error);
    }

    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mJobList.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }
}
