package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        Predicate<LocalTime> predicate1 = startTime == null ? localTime -> true : localTime -> localTime.compareTo(startTime) >= 0;
        Predicate<LocalTime> predicate2 = endTime == null ? localTime -> true : localTime -> localTime.compareTo(endTime) < 0;
        return predicate1.and(predicate2).test(lt);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

