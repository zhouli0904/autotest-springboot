package com.ddmc.autotestspringboot.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeUtil {

    public static Long addDay(LocalDateTime localDateTime, int day) {
        LocalDateTime plusDays = localDateTime.plusDays(day);
        return Timestamp.valueOf(plusDays).getTime();
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Long aLong = addDay(localDateTime, 10);
        System.out.println(aLong);
        Date date = new Date(aLong);
        System.out.println(date);
    }
}
