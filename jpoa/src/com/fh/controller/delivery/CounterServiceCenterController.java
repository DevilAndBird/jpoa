package com.fh.controller.delivery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.area.AreaProvService;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.service.delivery.RegionManagementService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 机场/高铁柜台信息处理
 */
@Controller
@RequestMapping(value = "/counterServiceCenter")
public class CounterServiceCenterController extends BaseController {

    @Autowired
    private ServiceCenterService serviceCenterService;
    @Autowired
    private AreaProvService areaProvService;
    @Autowired
    private AirportInfoConfigService airportInfoConfigService;
    @Autowired
    private RegionManagementService regionManagementService;
    
    /**
     *@desc:机场柜台管理列表_分页
     *@author:zhangjj
     *@history:2018年1月29日 
     */
    @RequestMapping(value = "/serviceCenterlistPage")
    public ModelAndView cityInfoList(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        
        page.setPd(pd);
        
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if( null != provList && provList.size()>0 ){
            mv.addObject("provList", provList);
        }
        
        // 服务中心列表
        List<PageData> serviceCenterList = serviceCenterService.findAll(pd);
        if( null != serviceCenterList && serviceCenterList.size()>0 ){
            mv.addObject("serviceCenterList", serviceCenterList);
        }
        
        //查询取派员区域 
        List<PageData> regionDeliveryManList = regionManagementService.selcRegionDeliveryManlist(pd);
        if (null != regionDeliveryManList && regionDeliveryManList.size() > 0) {
            mv.addObject("regionDeliveryMan", regionDeliveryManList);
        }
        
        List<PageData> serviceCenterlistPage = serviceCenterService.findServiceCenterlistPage(page);
        if (null != serviceCenterlistPage && serviceCenterlistPage.size() > 0) {
            mv.addObject("serviceCenterlistPage", serviceCenterlistPage);
        }
        
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/delivery/servicecenter/jsp/servicecenter_list");
        
        return mv;
    }
    
    /**
     *@desc 编辑快递员信息
     *@auther zhangjj
     *@history 2018年2月5日
     */
    @ResponseBody
    @RequestMapping(value="/updateCounterServiceCenter")
    public String updateCounterServiceCenterById()throws Exception{
        serviceCenterService.updateCounterServiceCenterById(this.getPageData());
        return "1";
    }
    
    /**
     *@desc 保存柜台中心
     *@auther zhangjj
     *@history 2018年2月5日
     */
    @ResponseBody
    @RequestMapping(value="/saveServiceCenter")
    public int saveServiceCenter() throws Exception{
        return serviceCenterService.saveServiceCenter(this.getPageData());
    }
    
    /**
     *@desc 查询机场位置坐标
     *@auther sunqp
     *@history 2018年4月27日 
     */
    @ResponseBody
    @RequestMapping(value="/findServiceCentergps", produces = "application/json;charset=UTF-8")
    public List<PageData> findServiceCentergps() throws Exception{
    	PageData pd = new PageData();
    	return serviceCenterService.findServiceCentergps(this.getPageData());
    }
    
    /**
     *@desc 查询服务中心信息
     *@auther sunqp
     *@history 2018年4月27日
     */
    @ResponseBody
    @RequestMapping(value="/findCounterAll", produces = "application/json;charset=UTF-8")
    public List<PageData> findCounterAll(String transmittype) throws Exception{
        return serviceCenterService.findCounterAll(this.getPageData().getString("loginperson_provid"), this.getPageData().getString("loginperson_cityid"), transmittype);
    }
    
}
