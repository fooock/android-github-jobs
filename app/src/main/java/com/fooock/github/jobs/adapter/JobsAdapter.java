package com.fooock.github.jobs.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.model.JobViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.Holder> {

    private final SelectedJobListener mJobListener;
    private final Context mContext;
    private final List<JobViewModel> mJobs;

    private int mLastPosition = -1;

    public JobsAdapter(Context context, SelectedJobListener listener) {
        mJobs = new ArrayList<>();
        mContext = context.getApplicationContext();
        mJobListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_jobs, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final JobViewModel viewModel = mJobs.get(position);

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > mLastPosition)
                ? R.anim.up_from_bottom
                : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        mLastPosition = holder.getAdapterPosition();

        holder.mTxtJobTitle.setText(viewModel.getTitle());
        holder.mTxtJobType.setText(viewModel.getType());
        holder.mTxtCreated.setText(viewModel.getDate());
        holder.mTxtCompanyName.setText(viewModel.getCompany().getName());
        holder.mTxtLocation.setText(viewModel.getLocation());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobListener.onSelectedJob(viewModel.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }

    @Override
    public void onViewDetachedFromWindow(Holder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.mLayout.clearAnimation();
    }

    public void update(List<JobViewModel> jobs) {
        if (mJobs.isEmpty()) {
            mJobs.addAll(jobs);
            notifyItemRangeInserted(mJobs.size(), jobs.size());
            Timber.d("Total jobs %s", mJobs.size());
            return;
        }
        for (JobViewModel job : jobs) {
            if (mJobs.contains(job)) {
                int index = mJobs.indexOf(job);
                mJobs.remove(index);
                mJobs.add(index, job);
            } else {
                mJobs.add(job);
            }
        }
        Timber.d("Total jobs %s", mJobs.size());
    }

    public ArrayList<JobViewModel> getJobs() {
        return (ArrayList<JobViewModel>) mJobs;
    }

    /**
     *
     */
    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.job_base_layout) ConstraintLayout mLayout;
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

    public interface SelectedJobListener {
        void onSelectedJob(String jobId);
    }
}
