package com.highway.lottery.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {
    public static LocalDate getCurrentWeekStart() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public static LocalDate getCurrentWeekEnd() {
        return getCurrentWeekStart().plusDays(6);
    }
}
