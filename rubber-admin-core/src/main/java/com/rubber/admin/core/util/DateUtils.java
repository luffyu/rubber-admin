package com.rubber.admin.core.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: 日期处理工具类
 * @author: YGF
 */
@SuppressWarnings("unused")
public class DateUtils {

    //常见日期格式类

    public final static String DF_yyyy_MM = "yyyy-MM";
    public final static String DF_yyyy_MM_dd = "yyyy-MM-dd";
    public final static String DF_yyyy_MM_dd_HH = "yyyy-MM-dd HH";
    public final static String DF_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public final static String DF_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String DF_yyyy_MM_dd_HH_mm_ssS = "yyyy-MM-dd HH:mm:ss.S";
    public final static String DF_HH_mm_ss = "HH:mm:ss";
    public final static String DF_HH_mm_ss_SSS = "HH:mm:ss.SSS";

    public final static String IN_yyyy_MM_dd = "yyyyMMdd";
    public final static String IN_yyyy_MM_dd_HH_mm_ss = "yyyyMMddHHmmss";


    public static DateTime createDataTime() {
        return new DateTime();
    }

    public static DateTime createDataTime(Date date) {
        return new DateTime(date);
    }

    /**
     * 把日期格式改变成想要的格式
     *
     * @param date  日期格式
     * @param dfKey 需要转换的格式
     */
    public static String formatData(Date date, String dfKey) {
        if (StringUtils.isEmpty(dfKey)) {
            dfKey = DF_yyyy_MM_dd_HH_mm_ss;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(dfKey);
    }

    public static String formatData(Date date) {
        return formatData(date, null);
    }

    public static String formatData(String dfKey) {
        return formatData(new Date(), dfKey);
    }

    /**
     * 获取当前 默认时间字符串
     */
    public static String getNowDate() {
        return formatData(new Date());
    }

    public static String getNowDate(String dfKey) {
        return formatData(new Date(), dfKey);
    }


    /**
     * 时间类的比较
     *
     * @return data1 早于 date2 : -1
     * data1 等于 date2 : 0
     * data1 晚于 date2 : 1
     */
    public static int compare(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("日期比较不能为null");
        }
        return date1.compareTo(date2);
    }


    /**
     * 获取当前当前的后几天
     */
    public static Date getAfterData(Date date, int day) {
        DateTime dateTime = createDataTime(date);
        return dateTime.plusDays(day).toDate();
    }

    /**
     * 获取第二天的时间
     */
    public static Date getNextData(Date date) {
        return getAfterData(date, 1);
    }

    /**
     * 获取某一天的起始时间
     * 例如2018-07-09 12:12:12 转换成 2018-07-09 00:00:00
     */
    public static Date getStartData(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天的末尾时间
     * 例如2018-07-09 12:12:12 转换成 2018-07-09 23:59:59
     */
    public static Date getEndData(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        return calendar.getTime();
    }

    /**
     * 返回两个日期直接 相差几天
     *
     * @param beginData 开始已经
     * @param endData   结束时间
     * @return 正数表示 beginData 比 endData 小n天 负数表示beginData 比 endData 大n天
     */
    public static int getBeginDays(Date beginData, Date endData) {
        DateTime bd1 = createDataTime(beginData);
        DateTime ed1 = createDataTime(endData);
        return Days.daysBetween(bd1.toLocalDate(), ed1.toLocalDate()).getDays();
    }


    /**
     * 把字符串转化成想要的日期格式
     */
    public static Date getDateByString(String str, String dfKey) {
        Date date = null;
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat(dfKey);
            date = myFormatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 输入日期信息
     */
    public static String toLocalString(Date date) {
        DateTime dateTime = createDataTime(date);
        return dateTime.toString(DF_yyyy_MM_dd_HH_mm_ss);
    }

}
