package com.fooock.github.jobs.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.fooock.github.jobs.GithubJobsApplication;
import com.fooock.github.jobs.Navigation;
import com.fooock.github.jobs.domain.executor.MainThread;
import com.fooock.github.jobs.domain.executor.ThreadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 *
 */
@Module
public class AppModule {

    private final GithubJobsApplication mApplication;

    public AppModule(GithubJobsApplication application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    GithubJobsApplication providesApplication() {
        return mApplication;
    }

    @Singleton
    @Provides
    Navigation providesNavigation() {
        return new Navigation();
    }

    @Singleton
    @Provides
    MainThread providesMainThread() {
        return new MainThread() {
            @Override
            public Scheduler scheduler() {
                return AndroidSchedulers.mainThread();
            }
        };
    }

    @Singleton
    @Provides
    ThreadExecutor providesThreadExecutor() {
        return new DefaultExecutor();
    }

    @Singleton
    @Provides
    Context providesContext() {
        return mApplication.getApplicationContext();
    }

    /**
     * Default implementation of {@link ThreadExecutor}
     */
    static final class DefaultExecutor implements ThreadExecutor {

        private final ThreadPoolExecutor threadPoolExecutor;

        private static final int CORE_POOL_SIZE = 3;
        private static final int MAX_POOL_SIZE = 5;
        private static final int KEEP_ALIVE_TIME = 10;

        private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
        private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

        @Inject
        DefaultExecutor() {
            threadPoolExecutor = new ThreadPoolExecutor(
                    CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TIME_UNIT, WORK_QUEUE);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            threadPoolExecutor.execute(command);
        }
    }
}
