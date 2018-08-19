package com.fooock.github.jobs.presenter;

import com.fooock.github.jobs.domain.ObserverAdapter;
import com.fooock.github.jobs.domain.interactor.GetJobDetail;
import com.fooock.github.jobs.domain.model.JobOffer;
import com.fooock.github.jobs.presenter.view.JobDetailView;

import javax.inject.Inject;

import timber.log.Timber;

/**
 *
 */
public class JobDetailPresenter extends Presenter<JobDetailView> {

    private final GetJobDetail mGetJobDetail;

    @Inject
    public JobDetailPresenter(GetJobDetail getJobDetail) {
        mGetJobDetail = getJobDetail;
    }

    public void loadJobDetail(final String jobId) {
        Timber.d("Load job detail for %s", jobId);
        mGetJobDetail.execute(new ObserverAdapter<JobOffer>() {
            @Override
            public void onNext(JobOffer jobOffer) {
                if (isAttached()) {
                    getView().onShowLoading(false);
                    getView().onShowJobDetail(jobOffer.getDescription());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isAttached()) getView().onShowLoading(false);
            }
        }, jobId);
    }
}
