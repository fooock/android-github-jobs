package com.fooock.github.jobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fooock.github.jobs.di.AppComponent;

/**
 *
 */
public class GithubJobsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        component().inject(this);
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
