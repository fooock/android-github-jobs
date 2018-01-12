package com.fooock.github.jobs;

import android.app.Application;

import com.fooock.github.jobs.di.AppComponent;
import com.fooock.github.jobs.di.AppModule;
import com.fooock.github.jobs.di.DaggerAppComponent;
import com.fooock.github.jobs.di.IoModule;

/**
 * Base application class
 */
public abstract class GithubJobsApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .ioModule(new IoModule("https://jobs.github.com/"))
                .build();

        initialize();
    }

    /**
     * Initialize things, like analytics, loggers...
     */
    abstract void initialize();

    /**
     * @return Application component
     */
    public AppComponent component() {
        return mAppComponent;
    }
}