package com.apical.oddm.facade.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;

/**
 * Date和String的转换
 * 
 * @author
 *
 */
public class TimeUtil {
	public static FastDateFormat dayFmat = FastDateFormat.getInstance("yyyy-MM-dd");
	public static FastDateFormat dateFmat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	public static FastDateFormat dayFmatForExcel = FastDateFormat.getInstance("yyyyMMddHHmmss");
	public static FastDateFormat dayFmatForOrderFollowup = FastDateFormat.getInstance("yyyy年MM月dd日");
	public static FastDateFormat dayFmatForMaterialFollowup = FastDateFormat.getInstance("yyyy/MM/dd");

	public static String dateToString(Date date) {
		String str = null;
		// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// if (type.equals("SHORT")) {
		// format = DateFormat.getDateInstance(DateFormat.SHORT);
		if (date != null)
			str = dayFmat.format(date);
		// } else if (type.equals("MEDIUM")) {
		// format = DateFormat.getDateInstance(DateFormat.MEDIUM);
		// str = format.format(date);
		// } else if (type.equals("FULL")) {
		// format = DateFormat.getDateInstance(DateFormat.FULL);
		// str = format.format(date);
		// }
		return str;
	}

	public static String dateToStringDetaile(Date date) {
		String str = null;
		if (date != null)
			str = dateFmat.format(date);
		return str;
	}

	public static String dateToStringOrderFollowup(Date date) {
		String str = null;
		if (date != null)
			str = dayFmatForOrderFollowup.format(date);
		return str;
	}

	public static String dateToStringMaterialFollowup(Date date) {
		String str = null;
		if (date != null)
			str = dayFmatForMaterialFollowup.format(date);
		return str;
	}

	public static String dateToStringForExcel(Date date) {
		String str = null;
		if (date != null)
			str = dayFmatForExcel.format(date);
		return str;
	}

	public static Date stringToDate(String str) {
		if (StringUtils.isBlank(str))
			return null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// date = java.sql.Date.valueOf(str);

		return date;
	}

	public static Date datToDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sDate = sdf.format(date);
		Date stringToDate = stringToDate(sDate);
		return stringToDate;
	}

	public static String timestampToString(Timestamp time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String date = null;
		// date = time.toString();
		date = dayFmat.format(time);
		return date;
	}

	public static String timestampToStringDetaile(Timestamp time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = null;
		date = dateFmat.format(time);
		return date;
	}

	public static Timestamp stringToTimestamp(String time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp date = null;
		format.setLenient(false);
		try {
			date = new Timestamp(format.parse(time).getTime());
			System.out.println(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static String timestampToStrings(Timestamp time) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = null;
		// date = time.toString(); //2016-11-28 09:10:02.3
		if (time != null)
			date = dateFmat.format(time);
		return date;
	}

	public static Timestamp stringToTimestamps(String time) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		// 要转换字符串 str_test
		Timestamp ts = null;
		try {
			ts = new Timestamp(format.parse(time).getTime());
			// System.out.println(ts.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ts;
	}

	public static void main(String[] args) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String format = dateFmat.format(timestamp);
		// System.out.println(timestamp.toString());
		// System.out.println("......"+format);
	}
}
