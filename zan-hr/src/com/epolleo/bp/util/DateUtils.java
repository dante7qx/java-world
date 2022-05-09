package com.epolleo.bp.util;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: 系统时间公共操作方法类
 * </p>
 * <li>提供取得系统时间的所有共用方法</li>
 */
public class DateUtils {
    private static Date date;
    private static Calendar CALENDAR = Calendar.getInstance();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    /**
     * 取得当前时间
     * 
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 取得昨天此时的时间值
     * 
     * @return 昨天日期
     */
    public static Date getYesterdayDate() {
        return new Date(getCurTimeMillis() - 0x5265c00L);
    }

    /**
     * 将String类型日期转化成java.sql.Timestamp类型"2003-01-01"格式
     * 
     * @param str
     *            String
     * @param format
     *            String
     * @return Timestamp
     */
    public static java.sql.Timestamp stringToTimestamp(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return new java.sql.Timestamp(date.getTime());
    }

    /**
     * @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(Calendar.getInstance().getTime());
        Timestamp time = Timestamp.valueOf(date);
        return time;
    }

    public static Timestamp getTimestamp(Timestamp time) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        return Timestamp.valueOf(date);
    }

    /**
     * 取得当前时间的长整型表示
     * 
     * @return 当前时间（long）
     */
    public static long getCurTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 取得当前时间的特定表示格式的字符串
     * 
     * @param formatDate
     *            时间格式（如：yyyy/MM/dd hh:mm:ss）
     * @return 当前时间
     */
    public static synchronized String getCurFormatDate(String formatDate) {
        date = getCurrentDate();
        simpleDateFormat.applyPattern(formatDate);
        return simpleDateFormat.format(date);
    }

    /**
     * 取得某日期时间的特定表示格式的字符串
     * 
     * @param format
     *            时间格式
     * @param date
     *            某日期
     */
    public static synchronized String getDate2Str(String format, Date date) {
        if (date == null) {
            return "";
        }
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 将日期转换为长字符串（包含：年-月-日 时:分:秒）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy-MM-dd HH:mm:ss 的字符串
     */
    public static String getDate2LStr(Date date) {
        return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
    }

    /**
     * 将日期转换为长字符串（包含：年-月-日 时:分:秒）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy/MM/dd HH:mm:ss 的字符串
     */
    public static String getDate2LStr2(Date date) {
        return getDate2Str("yyyy/MM/dd HH:mm:ss", date);
    }

    /**
     * 将日期转换为中长字符串（包含：年-月-日 时:分）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy-MM-dd HH:mm 的字符串
     */
    public static String getDate2MStr(Date date) {
        return getDate2Str("yyyy-MM-dd HH:mm", date);
    }

    /**
     * 将日期转换为中长字符串（包含：年/月/日 时:分）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy/MM/dd HH:mm 的字符串
     */
    public static String getDate2MStr2(Date date) {
        return getDate2Str("yyyy/MM/dd HH:mm", date);
    }

    /**
     * 将日期转换为短字符串（包含：年-月-日）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy-MM-dd 的字符串
     */
    public static String getDate2SStr(Date date) {
        return getDate2Str("yyyy-MM-dd", date);
    }

    public static String getDate2SStr1(Date date) {
        return getDate2Str("yyyyMMdd", date);
    }

    /**
     * 将日期转换为短字符串（包含：年/月/日）
     * 
     * @param date
     *            日期
     * @return 返回型如：yyyy/MM/dd 的字符串
     */
    public static String getDate2SStr2(Date date) {
        return getDate2Str("yyyy/MM/dd", date);
    }

    /**
     * 取得型如：yyyyMMddhhmmss的字符串 yyyyMMddhhmmss 12小时制 yyyyMMddHHmmss 24小时制
     * 
     * @param date
     * @return 返回型如：yyyyMMddhhmmss 的字符串
     */
    public static String getDate2All(Date date) {
        return getDate2Str("yyyyMMddHHmmss", date);
    }

    /**
     * 取得时间：hhmmss的字符串
     * 
     * @param date
     * @return 返回型如：hhmmss 的字符串
     */
    public static String getTime2All(Date date) {
        return getDate2Str("HHmmss", date);
    }

    /**
     * 将长整型数据转换为Date后，再转换为型如yyyy-MM-dd HH:mm:ss的长字符串
     * 
     * @param l
     *            表示某日期的长整型数值
     * @return 日期型的字符串
     */
    public static String getLong2LStr(long l) {
        date = getLongToDate(l);
        return getDate2LStr(date);
    }

    /**
     * 将长整型数据转换为Date后，再转换为型如yyyy-MM-dd的长字符
     * 
     * @param l
     *            表示某日期的长整型数
     * @return 日期型的字符
     */
    public static String getLong2SStr(long l) {
        date = getLongToDate(l);
        return getDate2SStr(date);
    }

    /**
     * 将长整型数据转换为Date后，再转换指定格式的字符
     * 
     * @param l
     *            表示某日期的长整型数
     * @param formatDate
     *            指定的日期格
     * @return 日期型的字符
     */
    public static String getLong2SStr(long l, String formatDate) {
        date = getLongToDate(l);
        simpleDateFormat.applyPattern(formatDate);
        return simpleDateFormat.format(date);
    }

    private static synchronized Date getStrToDate(String format, String str) {
        simpleDateFormat.applyPattern(format);
        ParsePosition parseposition = new ParsePosition(0);
        return simpleDateFormat.parse(str, parseposition);
    }

    /**
     * 将某指定的字符串转换为某类型的字符串
     * 
     * @param format
     *            转换格式
     * @param str
     *            要转换的字符
     * @return 转换后的字符
     */
    public static String getStr2Str(String format, String str) {
        Date date = getStrToDate(format, str);
        return getDate2Str(format, date);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd HH:mm:ss的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2LDate(String str) {
        return getStrToDate("yyyy-MM-dd HH:mm:ss", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy/MM/dd HH:mm:ss的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2LDate2(String str) {
        return getStrToDate("yyyy/MM/dd HH:mm:ss", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd HH:mm的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2MDate(String str) {
        return getStrToDate("yyyy-MM-dd HH:mm", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy/MM/dd HH:mm的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2MDate2(String str) {
        return getStrToDate("yyyy/MM/dd HH:mm", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate(String str) {
        return getStrToDate("yyyy-MM-dd", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate2(String str) {
        return getStrToDate("yyyy/MM/dd", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyyMMdd的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate3(String str) {
        return getStrToDate("yyyyMMdd", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyMMdd的时
     * 
     * @param str
     *            将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate4(String str) {
        return getStrToDate("yyMMdd", str);
    }

    /**
     * 将某长整型数据转换为日期
     * 
     * @param l
     *            长整型数
     * @return 转换后的日期
     */
    public static Date getLongToDate(long l) {
        return new Date(l);
    }

    /**
     * 以分钟的形式表示某长整型数据表示的时间到当前时间的间
     * 
     * @param l
     *            长整型数
     * @return 相隔的分钟数
     */
    public static int getOffMinutes(long l) {
        return getOffMinutes(l, getCurTimeMillis());
    }

    /**
     * 以分钟的形式表示两个长整型数表示的时间间
     * 
     * @param from
     *            始的长整型数
     * @param to
     *            结束的长整型数据
     * @return 相隔的分钟数
     */
    public static int getOffMinutes(long from, long to) {
        return (int) ((to - from) / 60000L);
    }

    /**
     * 以微秒的形式赋给个Calendar实例
     * 
     * @param l
     *            用来表示时间的长整型数据
     */
    public static void setCalendar(long l) {
        CALENDAR.clear();
        CALENDAR.setTimeInMillis(l);
    }

    /**
     * 以日期的形式赋�给某Calendar
     * 
     * @param date
     *            指定日期
     */
    public static void setCalendar(Date date) {
        CALENDAR.clear();
        CALENDAR.setTime(date);
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某年
     */
    public static int getYear() {
        return CALENDAR.get(1);
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某月
     */
    public static int getMonth() {
        return CALENDAR.get(2) + 1;
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某天
     */
    public static int getDay() {
        return CALENDAR.get(5);
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某小
     */
    public static int getHour() {
        return CALENDAR.get(11);
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某分
     */
    public static int getMinute() {
        return CALENDAR.get(12);
    }

    /**
     * 在此之前要由个Calendar实例的存
     * 
     * @return 返回某秒
     */
    public static int getSecond() {
        return CALENDAR.get(13);
    }

    private static SimpleDateFormat dateFormat = new SimpleDateFormat(
        "yyyy-MM-dd");

    public static synchronized String getDateString(Date date) {
        return dateFormat.format(date);
    }

    public static synchronized Date getDateFromString(String dateString) {
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            System.err.println(e.getMessage() + "日期转换异常！");
        }
        return null;
    }

    /**
     * 
     * 时间计算公用方法，以一个时间为基准，返回前后的一段时间，可作为查询等操作的依据。<br>
     * 需注意： <li>间隔为0表示当天； <li>间隔为-1表示当天向前一天到当天，即自然语义的“两天内”或“一天前”； <li>
     * 间隔为1表示表示当天向当天后一天，即自然语义的“两天内”或“一天后”；
     * 
     * 
     * @param originDate
     *            基准时间
     * @param interval
     *            累加天数
     * @return Date[] 第一个元素为起始时间，第二个元素为结束时间。如果输入参数错误，返回null；
     * 
     * @author：HY
     * @date：2012-1-19 下午3:41:45
     */
    public static Date[] getFixedIntervalDates(Date originDate, int interval) {
        if (originDate == null)
            return null;

        Date[] re = new Date[2];
        Calendar originCalendar = Calendar.getInstance();
        originCalendar.setTime(originDate);

        if (interval >= 0) {// 从originDate的00:00:00開始，往后加interval天；
            setStartOfDay(originCalendar);
            re[0] = originCalendar.getTime();

            originCalendar.add(Calendar.DATE, interval);
            setEndOfDay(originCalendar);
            re[1] = originCalendar.getTime();
        } else if (interval < 0) {// 从originDate的23:59:59開始，往前減interval天；
            setEndOfDay(originCalendar);
            re[1] = originCalendar.getTime();

            originCalendar.add(Calendar.DATE, interval);
            setStartOfDay(originCalendar);
            re[0] = originCalendar.getTime();
        }
        return re;
    }

    private static void setEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
    }

    private static void setStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
    }

}
