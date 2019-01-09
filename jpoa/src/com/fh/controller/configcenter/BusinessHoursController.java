package com.fh.controller.configcenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.base.BaseConfig;
import com.fh.entity.configcenter.ConfigCenter;
import com.fh.service.ConfigCenter.BusinessHoursService;
import com.fh.service.base.BaseService;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * @author zhangjj
 * 配置中心_营业时间支持动态可配置可修改可增加
 */
@Controller
@RequestMapping(value = "businesshours")
public class BusinessHoursController extends BaseController {
	
	@Autowired
	private BusinessHoursService businessHoursService;
	@Autowired
	private BaseService baseService;
	
	/**
	 * @desc  跳转营业时间页面
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	@RequestMapping(value="/businessHourslistPage")
	public ModelAndView list( Page page ) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		// 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
        List<PageData> businessHourslistPage = businessHoursService.getBusinessHourslistPage(page);
		if( CollectionUtils.isNotEmpty(businessHourslistPage) ){
			mv.addObject("businesshourslist", businessHourslistPage);
		}
		List<BaseConfig> orderAddressType = baseService.findBycodeType("ORDERADDRESSTYPE");
		if(CollectionUtils.isNotEmpty(orderAddressType)){
			mv.addObject("orderaddresslist", orderAddressType);
		}
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/config/businesshours/jsp/business_hours");
		return mv;
	}
	
	/**
	 * 
	 * @Title: saveConfig
	 * @Description: 新增营业时间
	 * author：tangqm
	 * 2018年10月31日
	 * @param request
	 * @param response
	 * @param configCenter
	 * @throws Exception
	 */
    @RequestMapping(value = "/saveBusinessHours")
    public void saveConfig(HttpServletRequest request, HttpServletResponse response)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	try {
    		PageData pd = this.getPageData();
    		businessHoursService.insertBusinessHours(pd);
    		response.getWriter().print("SUCCESS:新增营业时间成功");
    	} catch(Exception e) {
    		LoggerUtil.error("SUCCESS:新增营业时间失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
    
    /**
     * 
     * @Title: cancelBusinessHours
     * @Description: 删除营业时间
     * author：tangqm
     * 2018年10月31日
     * @param request
     * @param response
     * @param configCenter
     * @throws Exception
     */
    @RequestMapping(value = "/cancelBusinessHours")
    public void cancelBusinessHours(HttpServletRequest request, HttpServletResponse response, ConfigCenter configCenter)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    	try {
    		PageData pd = this.getPageData();
    		businessHoursService.deleteBusinessHours(pd);
    		response.getWriter().print("SUCCESS:删除营业时间成功");
    	} catch(Exception e) {
    		LoggerUtil.error("ERROR:删除营业时间失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
    
    /**
     * 
     * @Title: updateBusinessHours
     * @Description: 修改营业时间
     * author：tangqm
     * 2018年10月31日
     * @param request
     * @param response
     * @param configCenter
     * @throws Exception
     */
    @RequestMapping(value = "/updateBusinessHours")
    public void updateBusinessHours(HttpServletRequest request, HttpServletResponse response, ConfigCenter configCenter)  throws Exception    {
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	try {
    		PageData pd = this.getPageData();
    		businessHoursService.updateBusinessHours(pd);
    		response.getWriter().print("SUCCESS:删除营业时间成功");
    	} catch(Exception e) {
    		LoggerUtil.error("ERROR:删除营业时间失败", e);
    		response.getWriter().print(e.getMessage());
    	}
    }
	
	
}
