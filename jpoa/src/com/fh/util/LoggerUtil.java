package com.fh.util;


public class LoggerUtil {

	protected static Logger logger = Logger.getLogger(LoggerUtil.class);
	
	/**
	 * @desc 警告信息
	 * @auther zhangjj
	 * @date 2018年5月16日
	 */
	public static void warn(String errordesc, Exception ex) {
		logger.warn(errordesc, ex);
	}
	
	/**
	 * @desc 警告信息
	 * @auther zhangjj
	 * @date 2018年5月16日
	 */
	public static void warn(String errordesc) {
		logger.warn(errordesc);
	}
	
	/**
	 * @desc 异常信息
	 * @auther zhangjj
	 * @date 2018年5月16日
	 */
	public static void error(String errordesc, Exception ex) {
		logger.error(errordesc, ex);
	}
	
	/**
	 * @desc 打印信息
	 * @auther zhangjj
	 * @date 2018年5月16日
	 */
	public static void info(String info) {
		logger.info(info);
	}
}




