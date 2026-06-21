package com.seek.food.util.CommonUtil;


import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtil {
    private LocalDateTimeUtil() {}
    public static final String Default_Time_Format="yyyy-MM-dd HH:mm:ss";
    public static LocalDateTime getNowAfterHours(int number) {
        return LocalDateTime.now().plusHours(number);
    }
    public static long getStampBy(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long getPlusHoursStampByNow(int number) {
        return LocalDateTime.now().plusHours(number).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    public static long getStampByNow() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
