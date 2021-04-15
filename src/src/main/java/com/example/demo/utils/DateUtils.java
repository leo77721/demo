package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.expression.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	
	public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_YMD = "yyyyMMdd";
	public static final String ISO_DATETIME_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String ISO_DATETIME_WITH_MILLISECOND_FORMAT = "dd-MMM-yyyy HH:mm:ss.SSS";
	public static final String ISO_DATE_FORMAT = "dd-MMM-yyyy";
	public static final String ISO_SHORT_DATE_FORMAT = "dd-MMM-yy";
	public static final String ISO_TIME_FORMAT = "HH:mm:ss";
	public static final String ISO_TIME_WITH_MILLISECOND_FORMAT = "HH:mm:ss.SSS";
	public static final String UNIX_LONG_DATE_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
	public static final String US_DATE_FORMAT = "MM/dd/yyyy";
	public static final String US_SHORT_DATE_FORMAT = "MM/dd/yy";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_START = "yyyy-MM-dd 00:00:00";
	public static final String DATE_FORMAT_END = "yyyy-MM-dd 23:59:59";
	public static final String DATE_FORMAT_MONTH = "yyyy-MM";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String COMPARE_DAY="day";
	public static final String COMPARE_TIME="time";
	/**
     * 将日期字符串解析为日期类型(String to Date)
     *
     * @param dateString 不可心为null或空
     * @param format     可以为null,默认格式化"yyyy-MM-dd"
     * @return 日期
     */
    public static Date parse(String dateString, String format) throws Exception {
        try {
            if (format == null) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateString);
        } catch (Exception e) {
            throw new Exception("格式化日期产生异常", e);
        }
    }
	/**
     * 将日期格式化为特定的字符串(Date to String)
     *
     * @param date   java.util.Date
     * @param format 可以为null,默认格式为"yyyy-MM-dd HH:mm:ss"
     * @return 返回"yyyy-MM-dd HH:mm:ss,yyyy-MM-dd"格式的字符串,如果为空或null则的返回yyyy-MM-dd
     * HH:mm:ss
     */
    public static String format(Date date, String format) throws Exception {
        if (date == null)
            return "";
        try {
            if (format == null) {
                format = DATETIME_FORMAT;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            throw new Exception("日期格式化产生异常", e);
        }
    }
	
    // 获得本周一0点时间  
    public static Date getWeekStart(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }  
  
    // 获得本周日24点时间  
    public static Date getWeekEnd(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(getWeekStart(date));  
        cal.add(Calendar.DAY_OF_WEEK, 7);  
        return cal.getTime();  
    }  
  
    // 获得本月第一天0点时间  
    public static Date getMonthStart(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();  
    }  
  
    // 获得本月最后一天24点时间  
    public static Date getMonthEnd(Date date) {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
        cal.set(Calendar.HOUR_OF_DAY, 24);  
        return cal.getTime();  
    } 
    
    // 获得本月最后一天24点时间  
    public static Date getDayEnd(String day)throws Exception {  
    	SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT);
        return sdf.parse(day+" 23:59:59");  
    } 
    
	public static String getFirstDayOfMonth(String month) throws Exception{
		SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM"); 
		Date d = formatMonth.parse(month);
		Calendar cale =  Calendar.getInstance(); ;  
        cale.setTime(d);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String firstday;  
        // 获取本月的第一天  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        firstday = format.format(cale.getTime());  
      
        return firstday;
	};
	
	public static String getFirstDayOfMonth(Date d) throws Exception{
		Calendar cale =  Calendar.getInstance(); ;  
        cale.setTime(d);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String firstday;  
        // 获取本月的第一天  
        cale.add(Calendar.MONTH, 0);  
        cale.set(Calendar.DAY_OF_MONTH, 1);  
        firstday = format.format(cale.getTime());  
      
        return firstday;
	};
	
	public static String getLastDayOfMonth(String month) throws Exception{
		SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM"); 
		Date d = formatMonth.parse(month);
		Calendar cale =  Calendar.getInstance(); ;  
        cale.setTime(d);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String  lastday;  
      
        // 获取本月的最后一天  
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);  
        lastday = format.format(cale.getTime());  
        
        return lastday;
	};
	public static String getLastDayOfMonth(Date d) throws Exception{
		Calendar cale =  Calendar.getInstance(); ;  
        cale.setTime(d);
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String  lastday;  
      
        // 获取本月的最后一天  
        cale.add(Calendar.MONTH, 1);  
        cale.set(Calendar.DAY_OF_MONTH, 0);  
        lastday = format.format(cale.getTime());  
        
        return lastday;
	};
	
	
	/**
	 * 获取当前月份的每一天
	 * @param month
	 * @return
	 */
	public static List<String> getDayListOfMonth(String month,String outFormat) {

		if(StringUtils.isEmpty(outFormat)){
			outFormat=DATE_FORMAT;
		}
		
		List<String> lDate = new ArrayList<>();
		try {
			
			SimpleDateFormat formatMonth = new SimpleDateFormat("yyyy-MM"); 
			Date d = formatMonth.parse(month);
			Calendar cale =  Calendar.getInstance(); ;  
	        cale.setTime(d);
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        String startDay,endDay;  
	        // 获取本月的第一天  
	        cale.add(Calendar.MONTH, 0);  
	        cale.set(Calendar.DAY_OF_MONTH, 1);  
//	        startDay = sdf.format(cale.getTime());  
	    	Date dBegin =cale.getTime();
	        // 获取本月的最后一天  
	        cale.add(Calendar.MONTH, 1);  
	        cale.set(Calendar.DAY_OF_MONTH, 0);  
	        endDay = sdf.format(cale.getTime());  
			

//			Date dBegin = sdf.parse(startDay);
			Date dEnd = sdf.parse(endDay);

			SimpleDateFormat ofmt = new SimpleDateFormat(outFormat);
			startDay = ofmt.format(dBegin);
			lDate.add(startDay);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 测试此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(ofmt.format(calBegin.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lDate;
	
	}
	
	/**
	 * 获取num个日之前的所有日期(负数是之前，正数是之后)
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static List<String> findDaysStr(String day,Integer num) throws Exception{
		Date newDay = parse(day, DATE_FORMAT);
		return findDaysStr(newDay, num);
	}
	public static List<String> findDaysStr(Date day,Integer num) throws Exception{
		List<String> days = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if(num<0){
			cal.add(Calendar.DAY_OF_YEAR, num);
			while (cal.getTime().before(day)) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
				days.add(format(cal.getTime(),DATE_FORMAT));
			}
		}else{
			days.add(format(cal.getTime(),DATE_FORMAT));
			for(int i=1;i<num;i++){
				cal.add(Calendar.DAY_OF_YEAR, 1);
				days.add(format(cal.getTime(),DATE_FORMAT));
			}
		}
		return days;
	}
	
	/**
	 * 获取num个月之前的所有月份(负数是之前，正数是之后)
	 * @param month
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public static List<String> findMonthsStr(String month,Integer num) throws Exception{
		Date nowMonth = parse(month, DATE_FORMAT_MONTH);
		return findMonthsStr(nowMonth, num);
	}
	public static List<String> findMonthsStr(Date nowMonth,Integer num) throws Exception{
		List<String> months = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowMonth);
		if(num<0){
			cal.add(Calendar.MONTH, num);
			while (cal.getTime().before(nowMonth)) {
				cal.add(Calendar.MONTH, 1);
				months.add(format(cal.getTime(),DATE_FORMAT_MONTH));
			}
		}else{
			months.add(format(cal.getTime(),DATE_FORMAT_MONTH));
			for(int i=1;i<num;i++){
				cal.add(Calendar.MONTH, 1);
				months.add(format(cal.getTime(),DATE_FORMAT_MONTH));
			}
		}
		return months;
	}
	
	/**
	 * 获取startMonth-endMonth之间的所有月份(startMonth，endMonth都能取到)
	 * @return
	 * @throws Exception
	 */
	public static List<String> findMonthsStr(String startMonth,String endMonth) throws Exception{
		List<String> months = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		Date start = parse(startMonth, DATE_FORMAT_MONTH);
		Date end = parse(endMonth, DATE_FORMAT_MONTH);
		cal.setTime(start);
		while (cal.getTime().before(end)) {
			months.add(format(cal.getTime(),DATE_FORMAT_MONTH));
			cal.add(Calendar.MONTH, 1);
		}
		months.add(endMonth);
		return months;
	}
	
	public static int daysBetween(String smdate,String bdate) throws Exception{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));     
    }
	
	/**
	 * 字符串日期比较date1相对于date2
	 * @param date1（yyyy-mm-dd）
	 * @param date2（yyyy-mm-dd）
	 * @return -2：报错，-1：之前，0:相等，1：之后，2：date1为空，3：date2为空
	 */
	public static Integer compareDate(String date1,String date2) {
		if(StringUtils.isNotEmpty(date1)){
			return 2;
		}
		if(StringUtils.isNotEmpty(date2)){
			return 3;
		}
		Integer result = null;
		try {
			Date day1 = getDayStart(date1);
			Date day2 = getDayStart(date2);
			result = compareDate(day1, day2);
		} catch (Exception e) {
			e.printStackTrace();
			result = -2;
		}
		return result;
	}
	
	/**
	 * 日期比较
	 * @param date1
	 * @param date2
	 * @return -1：之前，0:相等，1：之后，2：date1为空，3：date2为空
	 */
	public static Integer compareDate(Date date1,Date date2) {
		if(date1==null){
			return 2;
		}
		if(date2==null){
			return 3;
		}
		Integer result = null;
		if(date1.after(date2)){
			result = 1;
		}else if(date1.before(date2)){
			result = -1;
		}else{
			result = 0;
		}
		return result;
	}
	
	// 获得当天第一天0点时间  
    public static Date getDayStart(String day) throws Exception {  
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(day+" 00:00:00");  
    } 
    
    /**
	 * 开始日期和结束日期都能取到
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static List<String> findDatesStr(String startDay, String endDay)  {
		List<String> lDate = new ArrayList<>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date dBegin = sdf.parse(startDay);
			Date dEnd = sdf.parse(endDay);

			
			lDate.add(startDay);
			Calendar calBegin = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calBegin.setTime(dBegin);
			Calendar calEnd = Calendar.getInstance();
			// 使用给定的 Date 设置此 Calendar 的时间
			calEnd.setTime(dEnd);
			// 测试此日期是否在指定日期之后
			while (dEnd.after(calBegin.getTime())) {
				// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
				calBegin.add(Calendar.DAY_OF_MONTH, 1);
				lDate.add(sdf.format(calBegin.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lDate;
	}
	
	
	/**
	 * 获取本周一
	 * @param date
	 * @return yyyy-mm-dd
	 */
	public static Date findFirstWeekDay(Date date)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(i == 0){
			i = 7;
		}
		cal.add(Calendar.DAY_OF_WEEK, 1-i);
		Date res = cal.getTime();
		return res;
	}
	
	/**
	 * 获取日期所在周的日期（当date为止,不包含date）
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static List<String> findFirstWeekDayStr(Date date) throws Exception  {
		Date findFirstWeekDay = findFirstWeekDay(date);
		String startDay = format(findFirstWeekDay, DATE_FORMAT);
		String endDay = format(date, DATE_FORMAT);
		List<String> list = findDatesStr(startDay, endDay);
		if(list.size()>1){
			list.remove(list.size()-1);
		}
		return list;
	}
	
	// java.util.Date --> java.time.LocalDate
	public static LocalDate UDateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		LocalDate localDate = localDateTime.toLocalDate();
		return localDate;
	}
}
