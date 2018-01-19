package com.fooock.github.jobs.presenter;

import com.fooock.github.jobs.R;
import com.fooock.github.jobs.domain.ObserverAdapter;
import com.fooock.github.jobs.domain.interactor.FilterJobs;
import com.fooock.github.jobs.domain.interactor.GetJobs;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.presenter.mapper.JobOfferMapper;
import com.fooock.github.jobs.presenter.view.JobsView;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 *
 */
public class JobsPresenter extends Presenter<JobsView> {

    private static final int JOBS_FIRST_PAGE = 0;

    private final GetJobs mGetJobs;
    private final FilterJobs mFilterJobs;

    private final JobOfferMapper mJobOfferMapper = new JobOfferMapper();

    @Inject
    public JobsPresenter(GetJobs getJobs, FilterJobs filterJobs) {
        mGetJobs = getJobs;
        mFilterJobs = filterJobs;
    }

    public void loadFirstPage(boolean showLoading) {
        loadJobs(JOBS_FIRST_PAGE, showLoading);
    }

    public void loadJobs(int page, boolean showLoading) {
        if (isAttached() && showLoading) getView().loading(true);
        mGetJobs.execute(new ObserverAdapter<List<JobOffer>>() {
            @Override
            public void onNext(List<JobOffer> jobOffers) {
                if (isAttached()) {
                    getView().loading(false);
                    getView().onJobsLoaded(mJobOfferMapper.map(jobOffers));
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isAttached()) {
                    getView().loading(false);
                    getView().onError(R.string.error_generic_msg, e);
                }
            }
        }, page);
    }

    @Override
    public void detach() {
        super.detach();
        mGetJobs.close();
    }

    /**
     * Filter current results by the given query
     *
     * @param query Query to filter results
     */
    public void filterBy(String query) {
        Timber.d("Find %s", query);
        mFilterJobs.execute(new ObserverAdapter<List<JobOffer>>() {
            @Override
            public void onNext(List<JobOffer> jobOffers) {
                if (isAttached()) {
                    Timber.d("Found %s matches", jobOffers.size());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isAttached()) getView().onError(R.string.error_generic_msg, e);
            }
        }, query);
    }
}
