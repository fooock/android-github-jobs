package com.fooock.github.jobs.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.fooock.github.jobs.data.entity.JobData;

import java.util.List;

/**
 *
 */
@Dao
public interface JobDao {

    @Query("SELECT * FROM jobs")
    List<JobData> getJobs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(List<JobData> jobs);
}
