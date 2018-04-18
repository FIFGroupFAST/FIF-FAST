/**
 * Copyright (c) 2012 FIF. All Rights Reserved. <BR>
 * <BR>
 * This software contains confidential and proprietary information of FIF.
 * ("Confidential Information"). <BR>
 * <BR>
 * Such Confidential Information shall not be disclosed and it shall only be
 * used in accordance with the terms of the license agreement entered into with
 * FIF; other than in accordance with the written permission of FIF. <BR>
 * 
 * Created on: Aug 25, 2012
 *
 * Author           Date         Version Description <BR>
 * ---------------- ------------ ------- --------------------------------- <BR>
 * 
 *  
 */

package co.id.fifgroup.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	public static final String DEFAULT_FORMAT = "dd-MMM-yyyy";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static final Map<String, SimpleDateFormat> cache = new ConcurrentHashMap<String, SimpleDateFormat>(5);

	public static String format(Date date) {
		String result = "";
		try {
			if (MAX_DATE.equals(date))
				result = "N/A";
			else
				result = sdf.format(date);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	public static String format(Date date, String pattern) {
		String result = "";
		try {
			SimpleDateFormat formatter = cache.get(pattern);
			if (null == formatter) {
				formatter = new SimpleDateFormat(pattern);
				cache.put(pattern, formatter);
			}
			if(date != null) {
				result = formatter.format(date);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return result;
	}

	public static Date MAX_DATE;
	static {
		try {
			Calendar cal = GregorianCalendar.getInstance();
			cal.clear();
			cal.set(4712, 11, 31);
			MAX_DATE = cal.getTime();
		} catch (Exception ex)  { }
	}

	public static Date truncate(Date date) {
		return DateUtils.truncate(date, Calendar.DATE);
	}

	public static Calendar truncate(Calendar cal) {
		return DateUtils.truncate(cal, Calendar.DATE);
	}
	
	public static Date add(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	public static boolean before(Date date, Date when) {
		try {
			return sdf.parse(sdf.format(date)).before(sdf.parse(sdf.format(when)));
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	public static boolean compareDate(Date dateFrom, Date dateTo) {
		boolean status = false;
		if(dateTo == null) {
			dateTo = MAX_DATE;
		}
		
		if(dateFrom != null) {
			if(dateTo.before(dateFrom)) {
				status = true;
			} else {
				status = false;
			}
		}
		return status;
	}
	
	public static boolean isBetween(Date toCompare, Date minDate, Date maxDate){
		if(toCompare.compareTo(minDate) >= 0 && toCompare.compareTo(maxDate) <= 0) return true;
		return false;
	}
	
	public static boolean isBetweenExclusive(Date toCompare, Date minDate, Date maxDate){
		if(toCompare.compareTo(minDate) > 0 && toCompare.compareTo(maxDate) < 0) return true;
		return false;
	}
	
	public static boolean isBetweenOrEquals(Date toCompare, Date minDate, Date maxDate){
		if(toCompare.after(minDate) && toCompare.before(maxDate)
				|| toCompare.equals(minDate)
				|| toCompare.equals(maxDate)) return true;
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static Date changeDateToOrigin(Long time){
		Date afterChangeDate  = new Date(time);
		afterChangeDate.setDate(01);
		afterChangeDate.setMonth(Calendar.JANUARY);
		afterChangeDate.setYear(70);
		return new Date(afterChangeDate.getTime());
	}
	
	public static long rangeTwoDate(Date startDate, Date endDate) {
		return ((DateUtils.truncate(endDate, Calendar.DATE).getTime() - DateUtils.truncate(startDate, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000)) + 1;
	}
	
	//edited GAL 15103120081532 - Modifikasi Form PD Untuk Budget Control
	public static long getWorkingDaysBetweenTwoDates(Date startDate, Date endDate,List<Date> holidayDate) {
	    Calendar startCal = Calendar.getInstance();
	    startCal.setTime(startDate);        

	    Calendar endCal = Calendar.getInstance();
	    endCal.setTime(endDate);
	    
	    Long rangeDay = ((DateUtils.truncate(endDate, Calendar.DATE).getTime() - DateUtils.truncate(startDate, Calendar.DATE).getTime()) / (24 * 60 * 60 * 1000)) + 1;
	    long workDays = 0;
	    
	    //Return 0 if start and end are the same
	    if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
	        return 1;
	    }

	    for(int i=0; i < rangeDay.intValue();i++) {
	        for(Date holiday : holidayDate){
		        if(startCal.equals(holiday) && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
		        	--workDays;
		        }
	        }
	        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	            ++workDays;
	        }
	        startCal.add(Calendar.DAY_OF_MONTH, 1);
	    }

	    return workDays;
	}
	//
	
	public static Period getPeriods(Date birthDate) {
		Calendar today = Calendar.getInstance();
		Calendar date = Calendar.getInstance();
		date.setTime(birthDate);
		Period period = new Period(date.getTimeInMillis(), today.getTimeInMillis(), PeriodType.yearMonthDay());
		return period;
	}
	
	public static String getProgressTime(Date receivedDate, Date actionDate) {
		String progressTime = "";
		int days = (int)((actionDate.getTime() - receivedDate.getTime()) / (1000 * 60 * 60 * 24));
		int difhours = (int)((actionDate.getTime() - receivedDate.getTime()) % (1000 * 60 * 60 * 24));
		int hours = (int)((difhours) / (1000 * 60 * 60));
		progressTime = days + " day(s), "+ hours + " hour(s)";
		return progressTime;
	}
	
	public static Date getStartOfYear(int year){
		DateTime dt = new DateTime();
		return truncate(dt.withYear(year).withMonthOfYear(1).withDayOfMonth(1).toDate());
	}
	
	public static Date getEndOfYear(int year){
		DateTime dt = new DateTime();
		return truncate(dt.withYear(year).withMonthOfYear(12).withDayOfMonth(31).toDate());
	}
	
	public static Date getStartOfMonth(int year,int month){
		DateTime dt = new DateTime();
		return truncate(dt.withYear(year).withMonthOfYear(month).withDayOfMonth(1).toDate());
	}
	
	public static Date getEndOfMonth(int year,int month){
		DateTime dt = new DateTime();
		return truncate(dt.withYear(year).withMonthOfYear(month).dayOfMonth().withMaximumValue().toDate());
	}
	
	public static Date getToday() {
		return new Date();
	}
	
	public static Date getNthDayOfMonth(int n, int day, int month, int year) {
		LocalDate ld = new LocalDate(year, month, 1).withDayOfWeek(day);
		if(n > 1) {
			LocalDate ldNthDay = ld.plusWeeks(n - 1);
			LocalDate lastDayInMonth = ld.dayOfMonth().withMaximumValue();
			if(ldNthDay.isAfter(lastDayInMonth)) {
				throw new IllegalArgumentException("There is no " + n + " " + ld.dayOfWeek().getAsText() + " in this month.");
			}
			return DateUtils.truncate(ldNthDay.toDate(), Calendar.DATE);
		}
		return DateUtils.truncate(ld.toDate(), Calendar.DATE);
	}
}
