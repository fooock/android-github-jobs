package com.fooock.github.jobs.domain.executor;

import io.reactivex.Scheduler;

/**
 *
 */
public interface MainThread {

    Scheduler scheduler();
}
