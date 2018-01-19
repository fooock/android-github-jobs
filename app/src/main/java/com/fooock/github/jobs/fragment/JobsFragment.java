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

    @BindView(R.id.rv_jobs) RecyclerView mJobList;
    @BindView(R.id.pb_loader) ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mRefreshLayout;

    @Inject JobsPresenter mJobsPresenter;

    private JobsAdapter mJobsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        setHasOptionsMenu(true);
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
        mJobList.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onUpdateMore(int page) {
                Timber.d("Get results for page %s", page);
                mJobsPresenter.loadJobs(page, false);
            }
        });
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
    public void onUpdateSearch(List<JobViewModel> jobsSearched) {
        Timber.d("Found %s matches", jobsSearched.size());
        mJobsAdapter.updateSearch(jobsSearched);
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

        SearchView searchView = getSearchView(menu);
        searchView.setIconified(false);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(TAG_JOBS_LIST, mJobsAdapter.getJobs());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mJobsPresenter.loadFirstPage(true);
            return;
        }
        ArrayList<JobViewModel> jobs = savedInstanceState.getParcelableArrayList(TAG_JOBS_LIST);
        mJobsAdapter.update(jobs);
    }

    private SearchView getSearchView(final Menu menu) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.mnu_search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                menu.findItem(R.id.mnu_settings).setVisible(false);
                mJobsAdapter.enableAnimation(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.findItem(R.id.mnu_settings).setVisible(true);
                mJobsAdapter.enableAnimation(true);
                return true;
            }
        });
        SearchView searchView = (SearchView) menuItem.getActionView();
        if (searchManager == null) return searchView;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        return searchView;
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
