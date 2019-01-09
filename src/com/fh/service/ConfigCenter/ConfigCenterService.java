package com.fh.service.ConfigCenter;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.configcenter.ConfigCenter;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigCenterService {
	
	@Resource
	private DaoSupport dao;
	
	
	/** 计价规则信息存储 */
	public static final Map<String, String> priceDetail = new HashMap<String, String>();
	
	
	/**
	 * @desc 根据城市和城市下的配置key获取所需配置
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	public String getConfig(String cityid, String key) throws Exception {
		if(priceDetail.get(cityid + key) != null) {
			return priceDetail.get(cityid + key);
		} 
		
		synchronized (cityid + key) {
			if(priceDetail.get(cityid + key) != null) {
				return priceDetail.get(cityid + key);
			}
			
			String value = getConfigCenterValue(cityid, key);
			value = StringUtils.isNotBlank(value) ? value : "";
			LoggerUtil.info("配置中心获取配置：cityid：" + cityid + ",key：" + key + ",value:" + value);
			priceDetail.put(cityid + key, value);
			return value;
		}
	}
	
	/**
	 * @desc 刷新配置信息
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	public void reflushSpecialPriceDetail(String cityid, String key) throws Exception {
		priceDetail.put(cityid + key, getConfigCenterValue(cityid, key));
	}
	
	/**
	 * @desc 增加配置中心配置
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	public void insertConfigCenter(ConfigCenter configCenter) throws Exception {
		// 去重复判断
		ExceptionUtil.checkBoolean(StringUtils.isNotBlank(getConfigCenterValue(configCenter.getCityid(), configCenter.getBusiness_key())), "配置中心已经存在此配置");
		dao.findForObject("ConfigCenterMapper.insert", configCenter);
	}
	
	public void updateValueOrIsValis(ConfigCenter configCenter) throws Exception {
		dao.update("ConfigCenterMapper.update", configCenter);
	}
	
	/**
	 * @desc 配置中心列表查询
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	public List<ConfigCenter> getConfigCenterlistPage(Page page) throws Exception {
		return (List<ConfigCenter>) dao.findForList("ConfigCenterMapper.findConfigCenterlistPage", page);
	}
	
	/**
	 * @desc 取配置中心的值
	 * @auther zhangjj
	 * @date 2018年10月25日
	 */
	private String getConfigCenterValue(String cityid, String key) throws Exception {
		ConfigCenter configCenter = new ConfigCenter();
		configCenter.setCityid(cityid);
		configCenter.setBusiness_key(key);
		return (String) dao.findForObject("ConfigCenterMapper.findValueByCityidAnd", configCenter);
	}

}
