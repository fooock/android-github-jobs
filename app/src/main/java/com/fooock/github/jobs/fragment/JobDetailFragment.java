package com.fooock.github.jobs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fooock.github.jobs.R;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class JobDetailFragment extends BaseFragment {
    public static final String KEY_JOB_NAME = "jobName";
    public static final String KEY_JOB_ID = "jobId";

    private String mJobName;
    private String mJobId;

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
    }
}
