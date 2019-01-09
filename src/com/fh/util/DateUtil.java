package com.fh.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static Calendar calendar = null;
	
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private final static SimpleDateFormat sdfDays = new SimpleDateFormat(
	"yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String format(Date d, String format) {
		if (d == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(d);
	}
	
	/**
	 * 功能描述：返回毫秒
	 * 陈玉石
	 * 2018年3月3日17:37:13
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * 功能描述：日期相加
	 * 陈玉石
	 * 2018年3月3日17:37:13
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	/**
	 * 获取YYYY格式
	 * 
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * 
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * 
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	* @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}
	
	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean  
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareTime(String s, String e) {
		if(fomatDate(s,"yyyy-MM-dd HH:mm:ss")==null||fomatDate(e,"yyyy-MM-dd HH:mm:ss")==null){
			return false;
		}
		return fomatDate(s,"yyyy-MM-dd HH:mm:ss").getTime() >=fomatDate(e,"yyyy-MM-dd HH:mm:ss").getTime();
	}
	
	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean  
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareTimeByMinute(String s, String e) {
		if(fomatDate(s,"yyyy-MM-dd HH:mm")==null||fomatDate(e,"yyyy-MM-dd HH:mm")==null){
			return false;
		}
		return fomatDate(s,"yyyy-MM-dd HH:mm").getTime() >=fomatDate(e,"yyyy-MM-dd HH:mm").getTime();
	}
	
	

	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 格式化日期
	 * 
	 * @return
	 */
	public static Date fomatDate(String date,String formatStr) {
		DateFormat fmt = new SimpleDateFormat(formatStr);
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date beginDate = null;
        java.util.Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }
    

	/*
	 * 字符串转化为日期
	 */
	public static String getStrByDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	
	/*
	 * 字符串转化为默认日期格式
	 * 陈玉石
	 * 2018年3月3日17:26:57
	 */
	public static String getDefaultDateStr(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 
	 * @Title: getDefaultDateStr
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * author：tangqm
	 * 2018年5月29日
	 * @param date
	 * @return
	 */
	public static String getDateTimeStr(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	
	/**
	 * 取到 hours 以前时间
	 * @param hours
	 * @return
	 */
	public static String headDate(String date ,int hours) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	    try {
	    	cal.setTime(df.parse(date));
			cal.add(Calendar.HOUR_OF_DAY, -hours);
			return df.format(cal.getTime());
		} catch (ParseException e) {
			 e.printStackTrace();
		}
	    return "";
	}
	
	/**
	 * 
	 * @Title: mulDate
	 * @Description: 时间相减
	 * author：tangqm
	 * 2018年6月2日
	 * @param date
	 * @param timespan
	 * @return
	 */
	public static String mulMinute(Date date,int timespan){
		long totaltime = getMillis(date);
		long span = timespan*60*1000;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			return df.format(new Date(totaltime-span));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * @Title: mulDate
	 * @Description: 时间相加
	 * author：tangqm
	 * 2018年6月2日
	 * @param date
	 * @param timespan
	 * @return
	 */
	public static String addMinute(Date date,int timespan){
		long totaltime = getMillis(date);
		long span = timespan*60*1000;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		try {
			return df.format(new Date(totaltime+span));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
    public static void main(String[] args) {
    	String headDate = DateUtil.headDate("2018-03-19 18:13:00", 4);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
    	String currentDate = df.format(System.currentTimeMillis());
    	System.out.println(currentDate+"***"+headDate);   
	    System.out.println( DateUtil.compareTime(currentDate, headDate));	
    }

}
