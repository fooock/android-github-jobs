package com.fooock.github.jobs.data.local;

import android.content.SharedPreferences;

import timber.log.Timber;

/**
 * Policy to check if saved entities has expired
 */
public class CachePolicy {
    private static final int CACHE_EXPIRATION = 1000 * 10 * 60; // 10 minutes
    private static final String CACHE_TIME_TAG = "timestamp";

    private final SharedPreferences mPreferences;

    public CachePolicy(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    /**
     * Check if the cache has expired
     *
     * @return True if expired, false otherwise
     */
    public boolean hasExpired() {
        final long lastSave = mPreferences.getLong(CACHE_TIME_TAG, 0);
        final long currentTime = System.currentTimeMillis();
        boolean expired = (currentTime - lastSave > CACHE_EXPIRATION);
        Timber.d("Data has expired? %s", expired);
        return expired;
    }

    public void updateExpiration() {
        mPreferences.edit().putLong(CACHE_TIME_TAG, System.currentTimeMillis()).apply();
    }

    public void clean() {
        mPreferences.edit().remove(CACHE_TIME_TAG).apply();
    }
}
