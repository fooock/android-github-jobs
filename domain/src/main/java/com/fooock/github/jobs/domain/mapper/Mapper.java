package com.fooock.github.jobs.domain.mapper;

/**
 *
 */
public interface Mapper<F, T> {

    T map(F from);
}
