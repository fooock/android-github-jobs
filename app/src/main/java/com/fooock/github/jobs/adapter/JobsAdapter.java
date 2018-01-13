package com.fooock.github.jobs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.model.JobViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.Holder> {

    private static final int TARGET_WIDTH = 200;
    private static final int TARGET_HEIGHT = 200;

    private final Context mContext;
    private final List<JobViewModel> mJobs;

    public JobsAdapter(List<JobViewModel> jobs, Context context) {
        mJobs = jobs;
        mContext = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_jobs, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        JobViewModel viewModel = mJobs.get(position);
        holder.mTxtJobTitle.setText(viewModel.getTitle());
        holder.mTxtJobType.setText(viewModel.getType());
        holder.mTxtCreated.setText(viewModel.getDate().toString());
        holder.mTxtCompanyName.setText(viewModel.getCompany().getName());
        holder.mTxtLocation.setText(viewModel.getLocation());

        Picasso.with(mContext)
                .load(viewModel.getCompany().getLogoUrl())
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerInside()
                .into(holder.mImgCompanyUrl);
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    /**
     *
     */
    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_company) ImageView mImgCompanyUrl;
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
