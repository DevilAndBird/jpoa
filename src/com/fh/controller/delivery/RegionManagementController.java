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
import com.fh.service.area.AreaProvService;
import com.fh.service.delivery.RegionManagementService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.fh.util.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author zhangjj
 * 区域信息划分处理（市级区域、集散中心区域、集散中心取派员）
 */
@Controller
@RequestMapping(value = "/regionManage")
public class RegionManagementController extends BaseController {

    @Autowired
    private RegionManagementService regionManagementService;
    @Autowired
    private AreaProvService areaProvService;
    @Autowired
    private TransitCenterService transitCenterService;
    @Autowired
    private UserDeliveryManService userDeliveryManService;
    
    /**
     * @desc:查询市级区域列表
     * @author:zhangjj
     * @history:2018年1月29日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/regioncityList")
    public ModelAndView cityInfoList(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        page.setPd(this.getPageData());

        // 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }

        List<PageData> regionCityList = regionManagementService.regionCityListPage(page);
        if (null != regionCityList && regionCityList.size() > 0) {
            mv.addObject("regionCityList", regionCityList);
        }

        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/areaManage/regioncity_list");

        return mv;
    }

    /**
     * @desc:集散中心区域列表
     * @author:zhangjj
     * @history:2018年1月29日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/regionTransitCenter")
    public ModelAndView regionTransitCenterList(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        page.setPd(this.getPageData());

        // 省份信息
    	
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }

        List<PageData> regionTransitCenterList = regionManagementService.transitCenterListPage(page);
        if (null != regionTransitCenterList && regionTransitCenterList.size() > 0) {
            mv.addObject("regionTransitCenterList", regionTransitCenterList);
        }

        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/areaManage/regionTransitCenter_list");
        return mv;
    }

    /**
     * @desc:集散中心取派员区域列表
     * @author:zhangjj
     * @history:2018年1月29日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/regionDeliveryManList")
    public ModelAndView regionDeliveryManList(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);

        // 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }

        // 集散中心集合
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if (null != transitCenterList && transitCenterList.size() > 0) {
            mv.addObject("transitCenterList", transitCenterList);
        }

        // 快递人员
        List<PageData> userDeliveryManList = userDeliveryManService.findAll(pd);
        if (null != userDeliveryManList && userDeliveryManList.size() > 0) {
            mv.addObject("userDeliveryManList", userDeliveryManList);
        }

        //取派员下拉框
        List<PageData> regionDeliveryManList = regionManagementService.selcRegionDeliveryManlist(pd);
        if (null != regionDeliveryManList && regionDeliveryManList.size() > 0) {
            mv.addObject("regionDeliveryManList", regionDeliveryManList);
        }
        
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/delivery/areaManage/regionDeliveryMan_list");
        
        return mv;
    }

    /**
     * @desc:查看省市区域划分
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/viewBaiduMap")
    public ModelAndView viewBaiduMap() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jpoa/delivery/baiduMap/regioncity/jsp/viewregioncity");
        return mv;
    }

    /**
     * @desc:加载市级区域
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/loadRegionCity")
    public List<PageData> loadRegionCity() throws Exception {
        return regionManagementService.findAllRegionCity();
    }

    /**
     * @desc:城市区域增加
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/addregioncity")
    public ModelAndView addregioncity() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
        mv.addObject("provid", pd.get("loginperson_provid"));
        mv.addObject("cityid", pd.get("loginperson_cityid"));
        mv.setViewName("jpoa/delivery/baiduMap/regioncity/jsp/addregioncity");
        return mv;
    }

    /**
     * @desc:保存绘制gps
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/saveRegionCityGps")
    public void saveRegionCityGps(HttpServletResponse response) throws Exception {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        List<PageData> checkRegionCtiyExist = regionManagementService.checkRegionCtiyExist(this.getPageData());
        if (CollectionUtils.isNotEmpty(checkRegionCtiyExist)) {
            response.getWriter().print("FAIL:新增失败,已存在相应市级区域划分");
            return;
        }

        regionManagementService.saveRegionCityGps(this.getPageData());
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.getWriter().print("SUCCESS:新增成功");
    }
    
    /**
     * @desc:城市区域编辑
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/editRegionCity")
    public ModelAndView editRegionCity() throws Exception {
        ModelAndView mv = new ModelAndView();

        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }

        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/baiduMap/regioncity/jsp/editregioncity");
        return mv;
    }
    
    /**
     * @desc:根据id获取市级区域
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/findRegionCtiyByid")
    public PageData findRegionCtiyByid() throws Exception {
        return regionManagementService.findRegionCtiyByid(this.getPageData());
    }
    
    /**
     * @desc:修改绘制gps
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/updateRegionCity")
    public void updateRegionCity(HttpServletResponse response) throws Exception {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        regionManagementService.updateRegionCity(this.getPageData());
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.getWriter().print("SUCCESS:更新成功");
    }
    
    
    /**
     * @desc:查看集散中心区域划分
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/viewTransitCenter")
    public ModelAndView viewTransitCenter() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jpoa/delivery/baiduMap/transitCenter/jsp/viewtransitcenter");
        return mv;
    }
    
    /**
     * @desc:加载集散中心区域
     * @author:zhangjj tangqm
     * @history:2018年5月24日 16点22分
     */
    @ResponseBody
    @RequestMapping(value = "/loadTransitCenter")
    public List<PageData> loadTransitCenter() throws Exception {
        return regionManagementService.findAllRegionTransitCenter(this.getPageData());
    }
    
    /**
     * @desc:集散中心区域增加
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/addTransitCenter")
    public ModelAndView addTransitCenter() throws Exception {
        ModelAndView mv = new ModelAndView();

        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
        // 集散中心
        List<PageData> transitCenterList = transitCenterService.findAll(this.getPageData());
        if( null != transitCenterList && transitCenterList.size()>0 ){
            mv.addObject("transitCenterList", transitCenterList);
        }

        mv.setViewName("jpoa/delivery/baiduMap/transitCenter/jsp/addtransitcenter");
        return mv;
    }
    
    
    /**
     * @desc:保存绘制gps
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/saveRegionTransCenter")
    public void saveRegionTransCenter(HttpServletResponse response,Page page) throws Exception {
    	String returnmsg = "新增成功";
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PageData pd = this.getPageData();
        page.setPd(pd);
        Integer transitid = regionManagementService.saveRegionTransit(pd);
		List<PageData> transitRegion = new Gson().fromJson(pd.getString("gps"), new TypeToken<List<PageData>>(){}.getType());
        //增加判断两个集散中心区域交接则不予添加        
		pd.put("transitid", "");//去除集散中心条件
        List<PageData> regionDeliveryManList = regionManagementService.regionDeliveryManListPage(page);
        if(CollectionUtils.isEmpty(regionDeliveryManList)){
        	 returnmsg = "当前区域并没有营业区域，请核实";
        }else{        	
        	regionManagementService.checkWorkRegion(regionDeliveryManList, transitRegion, transitid);
        }
        response.getWriter().print(returnmsg);
    }
    
    
    /**
     * @desc:根据id获取集散中心区域
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/findRegionTransitByid")
    public PageData findRegionTransitByid() throws Exception {
        return regionManagementService.findRegionTransitByid(this.getPageData());
    }
    
    /**
     * @desc:集散中心区域编辑
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/editTransitCenter")
    public ModelAndView editTransitCenter() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
        
        PageData regionTransit =  regionManagementService.findRegionTransitByid(pd);
        pd.put("timespan", regionTransit.get("timespan"));
        pd.put("transitid", regionTransit.get("transitid"));
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if( null != transitCenterList && transitCenterList.size()>0 ){
            mv.addObject("transitCenterList", transitCenterList);
        }

        mv.addObject("pd", pd);
        mv.setViewName("jpoa/delivery/baiduMap/transitCenter/jsp/edittransitcenter");
        return mv;
    }
    
    /**
     * @desc:删除集散中心
     * @author:tangqm
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/deleteTransitCenter")
    public void deleteTransitCenter(HttpServletRequest request, HttpServletResponse response) throws Exception {
        regionManagementService.deleteRegionTransit(this.getPageData());
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("删除成功");
    }
    
    /**
     * @desc:修改绘制gps
     * @author:zhangjj tangqm
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/updateRegionTransit")
    public void updateRegionTransit(HttpServletResponse response,Page page) throws Exception {
        String returnmsg = "SUCCESS:更新成功";
        PageData pd =  this.getPageData();
        page.setPd(pd);
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        regionManagementService.updateRegionTransit(pd);
        List<PageData> transitRegion = new Gson().fromJson(pd.getString("gps"), new TypeToken<List<PageData>>(){}.getType());
        //增加判断两个集散中心区域交接则不予添加        
		pd.put("transitid", "");//去除集散中心条件
        List<PageData> regionDeliveryManList = regionManagementService.regionDeliveryManListPage(page);
        if(CollectionUtils.isEmpty(regionDeliveryManList)){
        	 returnmsg = "Fail:当前区域并没有营业区域，请核实";
        }else{        	
        	 String id = (String) pd.get("id");
        	regionManagementService.checkWorkRegion(regionDeliveryManList, transitRegion,Integer.parseInt(id));
        }
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.getWriter().print(returnmsg);
    }
    
    /**
     * @desc:加载快递员管理区域
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/loadRegiondman")
    public List<PageData> loadRegiondman() throws Exception {
        return regionManagementService.findAllRegionDman(this.getPageData());
    }
    
    /**
     * @desc:查看省市区域划分
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/viewRegionDman")
    public ModelAndView viewRegionDman() throws Exception {
        ModelAndView mv = new ModelAndView();
        
        mv.setViewName("jpoa/delivery/baiduMap/regiondeliveryman/jsp/viewregiondman");
        return mv;
    }
    
    /**
     * @desc:取派员区域新增
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/addRegionDman")
    public ModelAndView addRegionDman() throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        
        // 省份信息
        mv.addObject("provList", RedisUtil.get("provList"));
        List<PageData> cityList = (List<PageData>) RedisUtil.get("cityList");
        PageData cityPd = (PageData) Tools.getObiect(cityList,"id", pd.getString("loginperson_cityid"));
        String cityname = "";
        if(cityPd!=null){   
        	cityname = cityPd.getString("name");
        }
        pd.put("cityname", cityname);
        mv.addObject("pd",pd );
        mv.setViewName("jpoa/delivery/baiduMap/regiondeliveryman/jsp/addregiondman");
        return mv;
    }
    
    /**
     * @desc:保存绘制gps
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/saveRegionDman")
    public void saveRegionDman(HttpServletResponse response) throws Exception {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
//        List<PageData> checkRegionDmanExist = regionManagementService.checkRegionDmanExist(this.getPageData());
//        if (CollectionUtils.isNotEmpty(checkRegionDmanExist)) {
//            response.getWriter().print("FAIL:新增失败,已存在相应取派员区域划分");
//            return;
//        }

        
        regionManagementService.saveRegionDman(this.getPageData());
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.getWriter().print("SUCCESS:新增成功");
    }
    
    
    /**
     * @desc:取排员区域编辑
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/editRegionDman")
    public ModelAndView editRegionDman() throws Exception {
        ModelAndView mv = new ModelAndView();
        
        // 快递人员
//        List<PageData> userDeliveryManList = userDeliveryManService.findAll(this.getPageData());
//        if (null != userDeliveryManList && userDeliveryManList.size() > 0) {
//            mv.addObject("userDeliveryManList", userDeliveryManList);
//        }
        mv.addObject("provList", RedisUtil.get("provList"));
        PageData pd = regionManagementService.findRegionDmanByid(this.getPageData());
		List<PageData> cityList = (List<PageData>) RedisUtil.get("cityList");
        PageData cityPd = (PageData) Tools.getObiect(cityList,"id", pd.getString("cityid"));
        String cityname = "";
        if(cityPd!=null){   
        	cityname = cityPd.getString("name");
        }
        pd.put("cityname", cityname);
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/delivery/baiduMap/regiondeliveryman/jsp/editregiondman");
        return mv;
    }
    
    /**
     * @desc:根据id获取取派员区域划分
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/findRegionDmanByid")
    public PageData findRegionDmanByid() throws Exception {
        return regionManagementService.findRegionDmanByid(this.getPageData());
    }
    
    /**
     * @desc:更新取派员绘制gps
     * @author:zhangjj
     * @history:2018年1月18日
     */
    @RequestMapping(value = "/updateRegionDman")
    public void updateRegionDman(HttpServletResponse response) throws Exception {

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        regionManagementService.updateRegionDman(this.getPageData());
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.getWriter().print("SUCCESS:更新成功");
    }
    
}
