package com.apical.oddm.infra.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * 日期转换相关，线程安全
 * @author lgx
 * 2016-11-9 
 */
public class OddmDateUtil {
	
	public static FastDateFormat yearFmat = FastDateFormat.getInstance("yyyy");

	public static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");

	public static FastDateFormat week = FastDateFormat.getInstance("W");

	public static FastDateFormat dateFmat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	
	private static String[] dayParse = {"yyyy-MM-dd"};

	//private static String[] dateParse = {"yyyy-MM-dd HH:mm:ss"};

	//反格式化文件名，获取登录时间
	public static Date dayParse(String value) throws ParseException {
		return DateUtils.parseDate(value, dayParse);
	}
	/**
	 * 获取日期差
	 * @param date
	 * @return
	 */
	public static Integer getDayDiffer(Date date) {
		if (date != null) {
			long differ = System.currentTimeMillis() - date.getTime();
			differ = differ/1000/60/60/24;
			return (int) differ;
		}
		return null;
	}
	
	
	/**
	 * 获取月份最后一天
	 * @param sDate
	 * @return
	 * @throws ParseException 
	 */
	public static Date getMonthMaxDay(String sDate) throws ParseException {  
       // SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();  
        Date date = dayParse(sDate + "-1");  
        cal.setTime(date);  
        int last = cal.getActualMaximum(Calendar.DATE);  
        return date = dayParse(sDate + "-"+last);
    }
	
	public static void main(String[] args) throws ParseException {
		/*Date date = dayParse("2016-9-5");
		Integer dayDiffer = getDayDiffer(date);
		System.out.println(dayDiffer);*/
		Date monthMaxDay = getMonthMaxDay("2017-2");
		System.out.println(monthMaxDay);
	}
}
