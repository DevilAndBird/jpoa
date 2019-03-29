package com.fh.listener;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.fh.common.constant.CacheMap;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fh.service.area.AreaCityService;
import com.fh.service.area.AreaProvService;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.util.ApplicationContextUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * redis预加载省份城市信息
 * @author tangqm
 *
 */
@Component
public class StartCacheListener implements ServletContextListener{

	private final Logger log= Logger.getLogger(StartCacheListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    // 通过WebApplicationContext获取ApplicationContext对象  
        ApplicationContext context = WebApplicationContextUtils  
                .getWebApplicationContext(sce.getServletContext());
		try {
        //预加载所有本地缓存
//		setAllCacheMap();
//        if(RedisUtil.exists("provList") && RedisUtil.exists("cityList")){
//        	return;
//        }
        AreaProvService apService = (AreaProvService) context.getBean("areaProvService");  
        AreaCityService acService = (AreaCityService) context.getBean("areaCityService");  
        AutoAllotService autoAllotService = (AutoAllotService) context.getBean("autoAllotService");  
        // 设置ApplicationContext对象的值  
        ApplicationContextUtil.init(context);  
        log.info("预加载redis省份城市消息");
			List<PageData> provList = apService.findAll();
			List<PageData> cityList = acService.findAll();
			List<PageData> autoAllotRuleList = autoAllotService.findAutoAllotRule();
			RedisUtil.set("provList", provList);
			RedisUtil.set("cityList", cityList);
			RedisUtil.set("autoAllotRuleList", autoAllotRuleList);

		} catch (Exception e) {
			log.error(e);
		}
	}

	protected void setAllCacheMap() throws IllegalAccessException {
         Field[] declaredFields = CacheMap.class.getDeclaredFields();
         for (Field declaredField : declaredFields) {
             Object value = RedisUtil.get(declaredField.getName());
             if (value == null) {
                 continue;
             }
             Map<String,String> localCache = new Gson().fromJson(value.toString(), Map.class);
             declaredField.set(CacheMap.class,localCache);
         }
     }

}
