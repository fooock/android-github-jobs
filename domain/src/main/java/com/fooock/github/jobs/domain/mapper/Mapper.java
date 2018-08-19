package com.fooock.github.jobs.domain.mapper;

import java.util.List;

/**
 *
 */
public interface Mapper<F, T> {

    T map(F from);

    List<T> map(List<F> from);
}
