package co.id.fifgroup.systemadmin.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;

import co.id.fifgroup.systemadmin.constant.DayName;


public class EpochConverter {

	private static BigDecimal getDuration(long time, long time2) {
//			
		DateTime dt1 = new DateTime(time);
		DateTime dt2 = new DateTime(time2);
		Period period = new Period(dt1, dt2);
		BigDecimal hour = new BigDecimal(period.get(DurationFieldType.hours()));
		int a = dt2.getMinuteOfDay() - hour.intValue()*60;
		int b = dt1.getMinuteOfDay();
		
		if(a-b>30){
			hour = new BigDecimal(hour.intValue() + "." + 5);
		}
		return hour;
	}
	
	
	public static void main(String []args){
		
		
		
		
		
		Date dates = new Date();
		dates.setTime(1390656120000L);//1390459560000//1390545960000
		System.out.println(dates);
		
//		
//		Double d = new Double(1.25);
//		Double dd = new Double(2);
//		System.out.println(d*dd);
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//		try {
//			Date jan = sdf.parse("08-11-2013 07:00");
//			Date nov = sdf.parse("08-11-2013 18:00");
//			
//			System.out.println(getDuration(jan.getTime(), nov.getTime()));
////			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		
//		Calendar cal = Calendar.getInstance();
//		System.out.println(cal.getTime());
//		int weekday = cal.get(Calendar.DAY_OF_WEEK);
//		if(weekday != Calendar.MONDAY){
//			int days = (Calendar.SATURDAY - weekday+2)%7;
//			cal.add(Calendar.DAY_OF_YEAR, days);
//		}
//		System.out.println(cal.getTime());
//		cal.add(Calendar.DAY_OF_YEAR, getDayNumberByDayName(DayName.SUNDAY.getValue()));
//		System.out.println(cal.getTime());
		
//		cal.set(Calendar.DATE, cal.get(Calendar.DATE)+7-DayName.MONDAY.getKey());
//		System.out.println(cal.getTime());
//		
//		DateTime datex = new DateTime();
//		datex.get                                  
	}
	
	public static int getDayNumberByDayName(String dayName) {
		if (dayName.contains("SUN")) {
			return DayName.SUNDAY.getKey() - 1;
		} else if (dayName.contains("MON")) {
			return DayName.MONDAY.getKey() - 1;
		} else if (dayName.contains("TUE")) {
			return DayName.TUESDAY.getKey() - 1;
		} else if (dayName.contains("WED")) {
			return DayName.WEDNESDAY.getKey() - 1;
		} else if (dayName.contains("THU")) {
			return DayName.THURSDAY.getKey() - 1;
		} else if (dayName.contains("FRI")) {
			return DayName.FRIDAY.getKey() - 1;
		} else
			return DayName.SATURDAY.getKey() - 1;
	}
}
