package com.fooock.github.jobs.util;

import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Date;

/**
 *
 */
public final class DateUtil {

    private DateUtil() {
    }

    public static String elapsedTime(Date from, Date to) {
        Interval interval = new Interval(from.getTime(), to.getTime());
        Period period = interval.toPeriod();

        if (period.getHours() <= 0 && period.getDays() <= 0 && period.getMonths() <= 0)
            return String.format("Published %s minutes ago", period.getMinutes());
        if (period.getDays() <= 0 && period.getMonths() <= 0)
            return String.format("Published %s hours ago", period.getHours());
        if (getDays(period) > 30)
            return String.format("Published %s months ago", period.getMonths() == 0 ? 1 : period.getMonths());
        return String.format("Published %s days ago", getDays(period));
    }

    private static int getDays(Period period) {
        return period.getDays() + (period.getWeeks() * 7);
    }
}
