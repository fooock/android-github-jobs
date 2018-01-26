package com.fooock.github.jobs.util;

import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

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

        PeriodFormatter formatter;

        if (period.getYears() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendYears()
                    .appendSuffix(" year ago", " years ago")
                    .printZeroNever()
                    .toFormatter();

        } else if (period.getMonths() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendMonths()
                    .appendSuffix(" month ago", " months ago")
                    .printZeroNever()
                    .toFormatter();

        } else if (period.getWeeks() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendWeeks()
                    .appendSuffix(" week ago", " weeks ago")
                    .printZeroNever()
                    .toFormatter();

        } else if (period.getDays() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendDays()
                    .appendSuffix(" day ago", " days ago")
                    .printZeroNever()
                    .toFormatter();

        } else if (period.getHours() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendHours()
                    .appendSuffix(" hour ago", " hours ago")
                    .printZeroNever()
                    .toFormatter();

        } else if (period.getMinutes() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendMinutes()
                    .appendSuffix(" minute ago", " minutes ago")
                    .printZeroNever()
                    .toFormatter();

        } else {
            formatter = new PeriodFormatterBuilder()
                    .appendPrefix("Published ")
                    .appendSeconds()
                    .appendSuffix(" second ago", " seconds ago")
                    .printZeroNever()
                    .toFormatter();
        }
        return formatter.print(period);
    }
}
