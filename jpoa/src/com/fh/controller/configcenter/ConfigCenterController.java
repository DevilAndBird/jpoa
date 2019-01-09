package com.fh.controller.configcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.configcenter.ConfigCenter;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * @author zhangjj
 * 配置中心支持可配置
 */
@Controller
@RequestMapping(value = "configcenter")
public class ConfigCenterController extends BaseController {
	
	@Autowired
	private ConfigCenterService configCenterService;
	
	/**
	 * @desc 配置中心列表查询
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	@RequestMapping(value="/configCenterlistPage")
	public ModelAndView list( Page page ) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		
		// 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
		
		List<ConfigCenter> configCenterlistPage = configCenterService.getConfigCenterlistPage(page);
		if( null != configCenterlistPage && configCenterlistPage.size()>0 ){
			mv.addObject("configCenterList", configCenterlistPage);
		}
		
		
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/config/configcenter/jsp/configcenter_list");
		return mv;
	}
	
	
	/** 
     * @desc 新增配置信息
     * @auther zhangjj
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/saveConfig")
    public void saveConfig(HttpServletRequest request, HttpServletResponse response, ConfigCenter configCenter)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	try {
    		configCenterService.insertConfigCenter(configCenter);
    		response.getWriter().print("SUCCESS:新增配置中心配置成功");
    	} catch(Exception e) {
    		LoggerUtil.error("SUCCESS:新增配置中心配置失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
    
    
    /** 
     * @desc 修改配置信息
     * @auther zhangjj
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/updateConfig")
    public void updateConfig(HttpServletRequest request, HttpServletResponse response, ConfigCenter configCenter)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	try {
    		configCenterService.updateValueOrIsValis(configCenter);
    		response.getWriter().print("SUCCESS:更新配置中心配置成功");
    	} catch(Exception e) {
    		LoggerUtil.error("ERROR:更新配置中心配置失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
    
    /** 
     * @desc 刷新配置信息
     * @auther zhangjj
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/reflushConfig")
    public void reflushConfig(HttpServletRequest request, HttpServletResponse response, ConfigCenter configCenter)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
    	try {
    		configCenterService.reflushSpecialPriceDetail(configCenter.getCityid(), configCenter.getBusiness_key());
    		response.getWriter().print("SUCCESS:刷新配置中心配置成功");
    	} catch(Exception e) {
    		LoggerUtil.error("ERROR:刷新配置中心配置失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
}
