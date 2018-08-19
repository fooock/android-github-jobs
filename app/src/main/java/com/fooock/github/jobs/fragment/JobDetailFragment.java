package com.fooock.github.jobs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.presenter.JobDetailPresenter;
import com.fooock.github.jobs.presenter.view.JobDetailView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class JobDetailFragment extends BaseFragment implements JobDetailView {
    public static final String KEY_JOB_NAME = "jobName";
    public static final String KEY_JOB_ID = "jobId";

    private String mJobName;
    private String mJobId;

    @Inject JobDetailPresenter mJobDetailPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        mJobName = getArguments().getString(KEY_JOB_NAME);
        mJobId = getArguments().getString(KEY_JOB_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Timber.d("Get job %s", mJobId);
        getActivity().setTitle(mJobName);
        mJobDetailPresenter.attach(this);
    }

    @Override
    public void onDestroyView() {
        mJobDetailPresenter.detach();
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) mJobDetailPresenter.loadJobDetail(mJobId);
    }
}
