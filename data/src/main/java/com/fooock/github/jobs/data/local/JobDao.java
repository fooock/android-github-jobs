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

    @Query("SELECT * FROM jobs WHERE title LIKE :query OR location LIKE :query OR company LIKE :query")
    List<JobData> filterBy(String query);
}
