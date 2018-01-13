package com.fooock.github.jobs.di;

import com.fooock.github.jobs.GithubJobsApplication;
import com.fooock.github.jobs.data.remote.JobsApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */
@Module
public class IoModule {

    private final String mBaseUrl;

    public IoModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Singleton
    @Provides
    Cache providesCache(GithubJobsApplication application) {
        int size = 5 * 1024 * 1024;
        return new Cache(application.getCacheDir(), size);
    }

    @Singleton
    @Provides
    OkHttpClient providesClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(mBaseUrl).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    JobsApiService providesJobApiService(Retrofit retrofit) {
        return retrofit.create(JobsApiService.class);
    }
}
