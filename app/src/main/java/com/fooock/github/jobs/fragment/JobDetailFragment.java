package com.fooock.github.jobs.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.presenter.JobDetailPresenter;
import com.fooock.github.jobs.presenter.view.JobDetailView;

import javax.inject.Inject;

import butterknife.BindView;
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

    @BindView(R.id.wv_jod_desc) WebView mJobDescription;
    @BindView(R.id.pb_loader) ProgressBar mProgress;

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

    @Override
    public void onShowLoading(boolean loading) {
        mProgress.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onShowJobDetail(String description) {
        mJobDescription.setVisibility(View.VISIBLE);
        mJobDescription.loadDataWithBaseURL("file:///android_asset/", applyStyle(description), "text/html", "uft-8", null);
    }

    private String applyStyle(String desc) {
        StringBuilder builder = new StringBuilder();
        builder.append("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css\" integrity=\"sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO\" crossorigin=\"anonymous\">");
        builder.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/>");
        builder.append(desc);
        return builder.toString();
    }
}
