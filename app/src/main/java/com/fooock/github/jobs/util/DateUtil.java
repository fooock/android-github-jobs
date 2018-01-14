package com.fooock.github.jobs.util;

import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Date;

/**
 *
 */
public class DateUtil {

    public static String elapsedTime(Date from, Date to) {
        Interval interval = new Interval(from.getTime(), to.getTime());
        Period period = interval.toPeriod();
        if (period.getHours() == 0)
            return String.format("Published %s minutes ago", period.getMinutes());
        if (period.getDays() == 0)
            return String.format("Published %s hours ago", period.getHours());
        if (period.getMonths() == 0)
            return String.format("Published %s days ago", period.getHours());
        if (period.getYears() == 0)
            return String.format("Published %s months ago", period.getHours());
        return String.format("Published %s year ago", period.getYears());
    }
}
