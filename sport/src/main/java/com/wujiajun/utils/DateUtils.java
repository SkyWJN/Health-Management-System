package com.wujiajun.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author wujiajun
 * @date 2023/5/8/ 17:00
 */
public class DateUtils {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 返回当前时间字符串 年月日
     * @return
     */
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD);
        return format.format(new Date());
    }

    /**
     * 返回当前时间字符串 年月日时分秒
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(new Date());
    }

    /**
     * 返回当前时间字符串 年月日时分秒
     * @return
     */
    public static String parseDateTime(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将时间字符串转换为时间对象
     * @param time
     * @return
     */
    public static Date formatDate(String time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 传递10位时间戳返回正常的YYYY-MM-DD的格式
     * @param timestamp
     * @return
     */
    public static String timeStampConvertString(long timestamp) {
        Date date = new Date(timestamp * 1000);
        return parseDateTime(date, YYYY_MM_DD);
    }

    /**
     * 传入时间字符串和天数
     * 返回该日期一周前的时间
     * @param date 时间字符串
     * @param day 天数
     * @return
     */
    public static String getWeekBeforeDate(String date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(formatDate(date, YYYY_MM_DD)));
        cal.add(Calendar.DATE, -day);
        Date time = cal.getTime();
        return parseDateTime(time, YYYY_MM_DD);
    }

    /**
     * 根据时间得到本周是星期几
     * @param date
     * @return
     */
    public static int getWeekOfDate(String date) {
        int[] week = { 7, 1, 2, 3, 4, 5, 6 };
        Calendar cal = Calendar.getInstance();
        cal.setTime(Objects.requireNonNull(formatDate(date, YYYY_MM_DD)));
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return week[w];
    }
}
