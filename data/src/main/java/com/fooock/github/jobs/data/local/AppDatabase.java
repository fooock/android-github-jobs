package com.fooock.github.jobs.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.fooock.github.jobs.data.entity.JobData;

/**
 *
 */
@Database(entities = {JobData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract JobDao getJobDao();
}
