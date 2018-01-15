package com.fooock.github.jobs.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fooock.github.jobs.GithubJobsApplication;
import com.fooock.github.jobs.Navigation;
import com.fooock.github.jobs.R;
import com.fooock.github.jobs.di.AppComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class GithubJobsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    @Inject Navigation mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        component().inject(this);

        setSupportActionBar(mToolbar);

        if (savedInstanceState != null) return;
        mNavigation.showJobsFragment(getFragmentManager());
    }

    /**
     * @return The {@link GithubJobsApplication}
     */
    private GithubJobsApplication application() {
        return (GithubJobsApplication) getApplication();
    }

    /**
     * @return The {@link AppComponent}
     */
    public AppComponent component() {
        return application().component();
    }
}
