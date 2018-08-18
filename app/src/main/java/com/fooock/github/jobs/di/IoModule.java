package com.fooock.github.jobs.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import com.fooock.github.jobs.GithubJobsApplication;
import com.fooock.github.jobs.data.DataSource;
import com.fooock.github.jobs.data.local.AppDatabase;
import com.fooock.github.jobs.data.local.CachePolicy;
import com.fooock.github.jobs.data.local.JobDao;
import com.fooock.github.jobs.data.local.LocalDataSource;
import com.fooock.github.jobs.data.remote.JobsApiService;
import com.fooock.github.jobs.data.repository.DefaultRepository;
import com.fooock.github.jobs.domain.Repository;

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

    @Singleton
    @Provides
    DataSource providesLocalDataSource(JobDao jobDao, CachePolicy cachePolicy) {
        return new LocalDataSource(jobDao, cachePolicy);
    }

    @Singleton
    @Provides
    Repository providesRepository(JobsApiService apiService, DataSource localDataSource) {
        return new DefaultRepository(apiService, localDataSource);
    }

    @Singleton
    @Provides
    AppDatabase providesAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "jobs-db").build();
    }

    @Singleton
    @Provides
    JobDao providesJobDao(AppDatabase database) {
        return database.getJobDao();
    }

    @Singleton
    @Provides
    CachePolicy providesCachePolicy(SharedPreferences preferences) {
        return new CachePolicy(preferences);
    }
}
