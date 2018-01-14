package com.fooock.github.jobs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.model.JobViewModel;
import com.fooock.github.jobs.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.Holder> {

    private final Context mContext;
    private final List<JobViewModel> mJobs;

    public JobsAdapter(Context context) {
        mJobs = new ArrayList<>();
        mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_jobs, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        JobViewModel viewModel = mJobs.get(position);
        holder.mTxtJobTitle.setText(viewModel.getTitle());
        holder.mTxtJobType.setText(viewModel.getType());
        holder.mTxtCreated.setText(DateUtil.elapsedTime(
                viewModel.getDate(), new Date(System.currentTimeMillis())));
        holder.mTxtCompanyName.setText(viewModel.getCompany().getName());
        holder.mTxtLocation.setText(viewModel.getLocation());
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    public void update(List<JobViewModel> jobs) {
        mJobs.addAll(jobs);
    }

    /**
     *
     */
    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_job_title) TextView mTxtJobTitle;
        @BindView(R.id.txt_job_type) TextView mTxtJobType;
        @BindView(R.id.txt_created_at) TextView mTxtCreated;
        @BindView(R.id.txt_company_name) TextView mTxtCompanyName;
        @BindView(R.id.txt_location) TextView mTxtLocation;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
