package com.fooock.github.jobs.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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

    private boolean mEnableAnimation = true;

    private int mLastPosition = -1;

    private String mSearchFilter;

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

        if (mEnableAnimation) {
            Animation animation = AnimationUtils.loadAnimation(mContext, (position > mLastPosition)
                    ? R.anim.up_from_bottom
                    : R.anim.down_from_top);
            holder.itemView.startAnimation(animation);
            mLastPosition = holder.getAdapterPosition();
        } else {
            holder.mLayout.clearAnimation();
        }

        highlightText(viewModel.getTitle(), holder.mTxtJobTitle);
        highlightText(viewModel.getCompany().getName(), holder.mTxtCompanyName);
        highlightText(viewModel.getLocation(), holder.mTxtLocation);

        holder.mTxtJobType.setText(viewModel.getType());
        holder.mTxtCreated.setText(viewModel.getDate());
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJobListener.onSelectedJob(viewModel.getId());
            }
        });
    }

    private void highlightText(String original, TextView view) {
        if (mSearchFilter == null || mSearchFilter.isEmpty()) {
            view.setText(original);
            return;
        }
        String text = original.toLowerCase();
        if (text.contains(mSearchFilter)) {
            int startPos = text.indexOf(mSearchFilter);
            int endPos = startPos + mSearchFilter.length();

            Spannable spanText = Spannable.Factory.getInstance().newSpannable(original);
            spanText.setSpan(new UnderlineSpan(), startPos, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new ForegroundColorSpan(mContext.getColor(R.color.textColorDark)),
                    startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            view.setText(spanText, TextView.BufferType.SPANNABLE);
        } else {
            view.setText(original);
        }
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

    public void update(@NonNull List<JobViewModel> jobs) {
        Timber.d("Total jobs %s, update with %s", mJobs.size(), jobs.size());
        if (mJobs.isEmpty()) {
            mJobs.addAll(jobs);
            notifyItemRangeInserted(mJobs.size(), jobs.size());
            return;
        }
        for (JobViewModel job : jobs) {
            if (mJobs.contains(job)) {
                int index = mJobs.indexOf(job);
                mJobs.remove(index);
                mJobs.add(index, job);
            } else {
                mJobs.add(job);
                notifyItemInserted(mJobs.size());
            }
        }
    }

    public ArrayList<JobViewModel> getJobs() {
        return (ArrayList<JobViewModel>) mJobs;
    }

    public void enableAnimation(boolean animate) {
        mEnableAnimation = animate;
    }

    public void updateSearch(@NonNull List<JobViewModel> jobsSearched, String query) {
        mSearchFilter = query;
        mJobs.clear();
        mJobs.addAll(jobsSearched);
        notifyDataSetChanged();
    }

    /**
     *
     */
    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.job_base_layout) LinearLayout mLayout;
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
