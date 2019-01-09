package com.fh.controller.delivery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.delivery.AppUser;
import com.fh.service.area.AreaProvService;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.service.delivery.AppUserService;
import com.fh.service.delivery.RegionManagementService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserServiceCenterService;
import com.fh.service.delivery.UserTransitCenterService;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * @author zhangjj
 * 角色登陆信息处理
 */
@Controller
@RequestMapping(value = "/appUser")
public class AppUserController extends BaseController {

    @Autowired
    private AppUserService appUserService;
    @Autowired
    private UserTransitCenterService userTransitCenterService;
    @Autowired
    private UserServiceCenterService userServiceCenterService;
    @Autowired
    private AreaProvService areaProvService;
    @Autowired
    private TransitCenterService transitCenterService;
    @Autowired
    private AirportInfoConfigService airportInfoConfigService;
    @Autowired
    private ServiceCenterService serviceCenterService;
    @Autowired
    private RegionManagementService regionManagementService;
    
    
    /**
     * @desc 后台人员登陆信息表分页查询
     * @auther zhangjj
     * @history 2018年2月6日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/appUserlistPage")
    public ModelAndView appUserlistPage(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        
        List<PageData> appUserlistPage = appUserService.findAppUserlistPage(page);
        if (CollectionUtils.isNotEmpty(appUserlistPage)) {
            mv.addObject("appUserlistPage", appUserlistPage);
        }

        // 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
		   if (CollectionUtils.isNotEmpty(provList)) {
            mv.addObject("provList", provList);
        }
        
        // 集散中心集合
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if (CollectionUtils.isNotEmpty(transitCenterList)) {
            mv.addObject("transitCenterList", transitCenterList);
        }
        
        // 机场列表
        List<PageData> airprotInfoConfigList =  airportInfoConfigService.loadAirportInfoConfig();
        if (CollectionUtils.isNotEmpty(airprotInfoConfigList)) {
            mv.addObject("airprotInfoConfigList", airprotInfoConfigList);
        }
        
        // 服务中心列表
        List<PageData> serviceCenterList = serviceCenterService.findAll(pd);
        if (CollectionUtils.isNotEmpty(serviceCenterList)) {
            mv.addObject("serviceCenterList", serviceCenterList);
        }
        //获取取派员下拉框
        List<PageData> regionDeliveryList = regionManagementService.selcRegionDeliveryManlist(pd);
        if (CollectionUtils.isNotEmpty(regionDeliveryList)) {
        	mv.addObject("regionDeliveryList", regionDeliveryList);
        }
        
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/delivery/appuser/jsp/appuser_list");

        return mv;
    }
    
    /**
     *@desc 根据用户登陆主键查询集散中心主键，并跳转查询集散中心
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @RequestMapping(value = "/findUserTransitCenterByUserid")
    public String findUserTransitCenterByUserid() throws Exception {
        
        PageData userTransitCenter = userTransitCenterService.findUserTransitCenterByUserid(this.getPageData());
        if (userTransitCenter != null) {
            return "redirect:/transitCenter/findTransitCenterlistPage.do?transitCenterid=" + userTransitCenter.get("transitid");
        }
        
        throw new RuntimeException("登陆用户信息未关联集散中心，用户主键信息为:" + this.getPageData().get("userid"));
    }
    
    /**
     *@desc 关联appuser和集散中心
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @ResponseBody
    @RequestMapping(value="/appUserLinkTransitCenter")
    public String appUserLinkTransitCenter(){
        try {
            userTransitCenterService.appUserLinkTransitCenter(this.getPageData());
        } catch (Exception e) {
            e.printStackTrace();
            return "2";
        }
        return "1";
    }
    
    /**
     *@desc 根据用户登陆主键查询服务中心主键，并跳转查询服务中心
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @RequestMapping(value = "/findUserServiceCenterByUserid")
    public String findUserServiceCenterByUserid() throws Exception {
        
        PageData userServiceCenter = userServiceCenterService.findUserServiceCenterByUserid(this.getPageData());
        if (userServiceCenter != null) {
            return "redirect:/counterServiceCenter/serviceCenterlistPage.do?servicecenterid=" + userServiceCenter.get("servicecenterid");
        }
        
        throw new RuntimeException("登陆用户信息未关联服务中心，用户主键信息为:" + this.getPageData().get("userid"));
    }
    
    /**
     *@desc 关联appuser和柜台中心
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @ResponseBody
    @RequestMapping(value="/appUserLinkServiceCenter")
    public String appUserLinkServiceCenter(){
        try {
            userServiceCenterService.appUserLinkServiceCenter(this.getPageData());
        } catch (Exception e) {
            e.printStackTrace();
            return "2";
        }
        return "1";
    }

  
    
    /**
     * @desc 保存用户
     * @auther zhangjj tangqm
     * @history 2018年2月5日
     */
    @RequestMapping(value = "/saveAppUser", produces = "application/json;charset=UTF-8")
    public void saveAppUser(HttpServletRequest request, HttpServletResponse response,AppUser appUser)  throws Exception    {
			String msg = appUserService.saveAppUser(appUser);
	        response.setHeader("Content-type", "text/html;charset=UTF-8");
	        response.setCharacterEncoding("UTF-8");
	        response.getWriter().print(msg);
			logger.info(msg);
    }
    
    /**
     *@desc 编辑角色登陆信息
     *@auther zhangjj
     *@history 2018年2月9日
     */
    @RequestMapping(value="/updateAppUserById")
    public void updateAppUserById(HttpServletResponse response)throws Exception{
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
		PageData pd = this.getPageData();
		
		/*PageData findByMobile = appUserService.findByMobile(pd.getString("mobile"));
		if(findByMobile != null) {
			response.getWriter().print("FAIL:删除失败,手机号已存在！");
			return;
		}*/
		
    	appUserService.updateAppUserById(pd);
    	response.getWriter().print("SUCCESS:更新成功！");
    }

}
