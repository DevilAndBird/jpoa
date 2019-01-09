package com.fh.util;

import org.springframework.context.ApplicationContext;

public class ApplicationContextUtil {

	 private static ApplicationContext appCtx;  
     
	 /**
	  * 初始化appCtx对象
	  * @param context
	  */
	    public static void init(ApplicationContext context) {  
	        appCtx = context;  
	    }  
	      
	  /**
	   * 获取appCtx对象
	   * @return
	   */
	    public static ApplicationContext getApplicationContext() {  
	        return appCtx;  
	    }  
	
}
