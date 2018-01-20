package com.fooock.github.jobs.fragment;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.activity.GithubJobsActivity;
import com.fooock.github.jobs.adapter.JobsAdapter;
import com.fooock.github.jobs.adapter.listener.EndlessScrollListener;
import com.fooock.github.jobs.di.AppComponent;
import com.fooock.github.jobs.model.JobViewModel;
import com.fooock.github.jobs.presenter.JobsPresenter;
import com.fooock.github.jobs.presenter.view.JobsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class JobsFragment extends Fragment implements JobsView,
        SwipeRefreshLayout.OnRefreshListener, JobsAdapter.SelectedJobListener, SearchView.OnQueryTextListener {

    private static final String TAG_JOBS_LIST = "github.jobs";
    private static final String TAG_SEARCH_QUERY = "search.query";

    @BindView(R.id.rv_jobs) RecyclerView mJobList;
    @BindView(R.id.pb_loader) ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.empty_results) LinearLayout mEmptyResultLayout;

    @Inject JobsPresenter mJobsPresenter;

    private final EndlessScrollListener mEndlessScrollListener = new EndlessScrollListener() {
        @Override
        public void onUpdateMore(int page) {
            Timber.d("Get results for page %s", page);
            mJobsPresenter.loadJobs(page, false);
        }
    };

    private JobsAdapter mJobsAdapter;
    private SearchView mSearchView;
    private String mSearchFilter;

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
        mJobsAdapter = new JobsAdapter(getActivity(), this);
        mJobList.setAdapter(mJobsAdapter);
        mJobList.addOnScrollListener(mEndlessScrollListener);
        mRefreshLayout.setOnRefreshListener(this);
        mJobsPresenter.attach(this);
    }

    @Override
    public void onDestroyView() {
        mJobsPresenter.detach();
        super.onDestroyView();
    }

    private AppComponent component() {
        return ((GithubJobsActivity) getActivity()).component();
    }

    @Override
    public void onJobsLoaded(List<JobViewModel> jobs) {
        Timber.d("Found %s jobs", jobs.size());
        setHasOptionsMenu(true);
        mRefreshLayout.setRefreshing(false);
        mJobsAdapter.update(jobs);
    }

    @Override
    public void onError(int message, Throwable error) {
        Timber.e(error);
        Snackbar.make(mRefreshLayout, message, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mJobsPresenter.loadFirstPage(true);
                    }
                }).show();
    }

    @Override
    public void loading(boolean isLoading) {
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        mJobList.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onUpdateSearch(List<JobViewModel> jobsSearched, String query) {
        Timber.d("Found %s matches for %s", jobsSearched.size(), query);
        mEmptyResultLayout.setVisibility(View.GONE);
        mJobsAdapter.updateSearch(jobsSearched, query);
    }

    @Override
    public void onEmptyResults(String query) {
        Timber.d("No search results found for %s", query);
        mEmptyResultLayout.setVisibility(View.VISIBLE);
        mJobList.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        Timber.d("Refresh jobs");
        mJobsPresenter.loadFirstPage(false);
    }

    @Override
    public void onSelectedJob(String jobId) {
        Timber.d("Get detail for %s", jobId);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_jobs, menu);
        configureSearchView(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!mJobsAdapter.getJobs().isEmpty())
            outState.putParcelableArrayList(TAG_JOBS_LIST, mJobsAdapter.getJobs());
        if (mSearchView != null)
            outState.putString(TAG_SEARCH_QUERY, mSearchView.getQuery().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mJobsPresenter.loadFirstPage(true);
            return;
        }
        ArrayList<JobViewModel> jobs = savedInstanceState.getParcelableArrayList(TAG_JOBS_LIST);
        if (jobs == null || jobs.isEmpty()) return;
        onJobsLoaded(jobs);

        mSearchFilter = savedInstanceState.getString(TAG_SEARCH_QUERY);
    }

    private void configureSearchView(final Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.mnu_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menu.findItem(R.id.mnu_settings).setVisible(false);
                mJobsAdapter.enableAnimation(false);
                mRefreshLayout.setEnabled(false);
                mEndlessScrollListener.disableDynamicLoading();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.findItem(R.id.mnu_settings).setVisible(true);
                mJobsAdapter.enableAnimation(true);
                mRefreshLayout.setEnabled(true);
                mEndlessScrollListener.enableDynamicLoading();
                return true;
            }
        });
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_hint));
        if (searchManager != null)
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setIconified(false);
        mSearchView.setMaxWidth(Integer.MAX_VALUE);
        mSearchView.setOnQueryTextListener(this);

        if (mSearchFilter != null && !mSearchFilter.isEmpty())
            mSearchView.setQuery(mSearchFilter, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mJobsPresenter.filterBy(newText);
        return true;
    }
}
