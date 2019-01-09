package com.fh.controller.config;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.fh.entity.ConfigCache;
import com.fh.entity.base.BaseConfig;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.service.base.BaseService;
import com.google.gson.Gson;

/**
 * 加载缓存(废弃)
 * @author tqm
 * @date 2018年11月5日
 */
@Controller
@RequestMapping(value = "config")
public class ConfigCacheListener implements InitializingBean,
		ServletContextAware {

	@Autowired
	private BaseService baseService;
	@Autowired
	private AirportInfoConfigService airportInfoConfigService;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out
				.println("=======================初始化成功=======================");
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		try {
			ConfigCache.configList = baseService.loadBaseConfig();
			ConfigCache.airportList = airportInfoConfigService
					.loadAirportInfoConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/getSelectValue")
	public void getSelectValue(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String orderType = request.getParameter("orderType");
		List<BaseConfig> orderAddressType = baseService.findBycodeType(orderType);
		String json = new Gson().toJson( orderAddressType );
		response.getWriter().write(json);
	}

}
