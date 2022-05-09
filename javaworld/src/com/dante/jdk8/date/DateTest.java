package com.dante.jdk8.date;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * Java8 日期测试类
 * 
 * @author dante
 *
 */
public final class DateTest {
	
	public static void main(String[] args) {
		
		LocalDate localDate = LocalDate.now();
		System.out.println("localDate => " + localDate + "，year is " + localDate.lengthOfYear());
		
		LocalTime localTime = LocalTime.now();
		
		System.out.println("localTime => " + localTime + ", format localTime => " + localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
		
		LocalDateTime localDateTime = LocalDateTime.now();
		System.out.println("localDateTime => " + localDateTime + "(YYYY => " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "，yyyy => " + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")");
		
		LocalDate ofDate = LocalDate.of(2012, 12, 12);
		LocalTime ofTime = LocalTime.of(12, 12, 12);
		LocalDateTime ofDateTime = LocalDateTime.of(ofDate, ofTime);
		System.out.println("星期 => " + ofDateTime.getDayOfWeek());
		
		LocalDateTime parseDateTime = LocalDateTime.parse("2013-10-10T10:10:10");
		System.out.println(parseDateTime + " <==> " + localDateTimeToDate(parseDateTime));
		
		Instant instant = Instant.now();
		System.out.println(instant);
		
		Date date = Date.from(instant);
		System.out.println(date);
		
		Duration between = Duration.between(ofDateTime, localDateTime);
		System.out.println(between.get(ChronoUnit.SECONDS));
		
		Period p = Period.between(ofDateTime.toLocalDate(), localDateTime.toLocalDate());
		System.out.println(p.getYears() + "-" + p.getMonths() + "-" + p.getDays());
		
		LocalDateTime oneYearLater = ofDateTime.plusYears(1L);
		System.out.println(oneYearLater + ", week = " + oneYearLater.getDayOfWeek());
		
		LocalDate beginMonthDay = localDate.with(TemporalAdjusters.firstDayOfMonth());
		System.out.println(beginMonthDay + " <==> " + localDateToDate(beginMonthDay));
		LocalDate endMonthDay = localDate.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println(endMonthDay + " <==> " + localDateToDate(endMonthDay));
		LocalDate sunday = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		System.out.println(sunday);
		
		yyyyDiffYYYY();
	}
	
	/**
	 * LocalDate 转 Date
	 * 
	 * @param localDate
	 * @return
	 */
	public final static Date localDateToDate(LocalDate localDate) {
		if(localDate == null) {
			return null;
		}
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Date 转 LocalDate
	 * 
	 * @param date
	 * @return
	 */
	public final static LocalDate DateToLocalDate(Date date) {
		if(date == null) {
			return null;
		}
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	/**
	 * LocalDateTime 转 Date
	 * 
	 * @param localDateTime
	 * @return
	 */
	public final static Date localDateTimeToDate(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Date 转 LocalDateTime
	 * 
	 * @param date
	 * @return
	 */
	public final static LocalDateTime dateToLocalDateTime(Date date) {
		if(date == null) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * YYYY 大写：基于周的日历年，即当天所在周属于的年份，一周从周日开始，周六结束，只要本周跨年，那么这周就算下一年
	 * yyyy 小写：普通的日历年
	 * 
	 * 结果：
	 * YYYY-MM-dd ==> 2018-12-31
	 * yyyy-MM-dd ==> 2017-12-31
	 * 
	 * 结论：若没有特殊的需求，要使用小写yyyy，即普通的日历年。
	 * 
	 */
	private final static void yyyyDiffYYYY() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2017, Calendar.DECEMBER, 31);
		Date strDate = calendar.getTime();
		SimpleDateFormat sf1 = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		System.out.println("YYYY-MM-dd ==> " + sf1.format(strDate));
		
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("yyyy-MM-dd ==> " + sf2.format(strDate));
	}
	
}
