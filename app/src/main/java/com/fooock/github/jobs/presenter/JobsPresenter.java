package com.fooock.github.jobs.presenter;

import com.fooock.github.jobs.domain.ObserverAdapter;
import com.fooock.github.jobs.domain.interactor.GetJobs;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.presenter.mapper.JobOfferMapper;
import com.fooock.github.jobs.presenter.view.JobsView;

import java.util.List;

import javax.inject.Inject;

/**
 *
 */
public class JobsPresenter extends Presenter<JobsView> {

    private static final int JOBS_FIRST_PAGE = 0;

    private final GetJobs mGetJobs;
    private final JobOfferMapper mJobOfferMapper = new JobOfferMapper();

    @Inject
    public JobsPresenter(GetJobs getJobs) {
        mGetJobs = getJobs;
    }

    public void loadFirstPage() {
        loadJobs(JOBS_FIRST_PAGE);
    }

    public void loadJobs(int page) {
        if (isAttached()) getView().loading(true);
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
                if (isAttached()) getView().loading(false);
            }
        }, page);
    }

    @Override
    public void detach() {
        super.detach();
        mGetJobs.close();
    }
}
