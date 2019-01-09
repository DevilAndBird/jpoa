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
import com.fh.service.delivery.TransitCenterService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 集散中心基本信息处理
 */
@Controller
@RequestMapping(value="/transitCenter")
public class TransitCenterController extends BaseController{
	
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private AreaProvService areaProvService;
	
	/**
	 * 查询集散中心列表
	 * @throws Exception 
	 * */
	@RequestMapping(value="/findTransitCenterlistPage")
	public ModelAndView queryTransitList(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
        page.setPd(pd);
        
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if( null != provList && provList.size()>0 ){
            mv.addObject("provList", provList);
        }
        
        // 集散中心集合
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if( null != transitCenterList && transitCenterList.size()>0 ){
            mv.addObject("transitCenterList", transitCenterList);
        }
        
		List<PageData> transitCenterlistPage  = transitCenterService.findTransitCenterlistPage(page);
		if(transitCenterlistPage != null && transitCenterlistPage.size() > 0) {
		    mv.addObject("transitCenterlistPage", transitCenterlistPage);
		}
		
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/delivery/transitcenter/jsp/transitcenter_list");
		
		return mv;
	}
	
	/**
     *@desc:根据主键查询集散中心
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    @ResponseBody
    @RequestMapping(value = "/findTransitCenterById")
    public PageData findTransitCenterById() throws Exception {
        return (PageData)transitCenterService.findTransitCenterById(this.getPageData());
    }
    
    /**
     *@desc 根据id更新集散中心信息
     *@auther zhangjj
     *@history 2018年2月4日
     */
    @ResponseBody
    @RequestMapping(value="/updateTransitCenter")
    public String updateTransitCenterById() throws Exception{
        transitCenterService.updateTransitCenterById(this.getPageData());
        return "1";
    }
    
    /**
     *@desc 保存集散中心信息
     *@auther zhangjj
     *@history 2018年2月4日
     */
    @ResponseBody
    @RequestMapping(value="/saveTransitCenter")
    public int saveTransitCenter() throws Exception{
        return transitCenterService.saveTransitCenter(this.getPageData());
    }
    
    /**
     *@desc 查询集散中心位置坐标
     *@auther sunqp
     *@history 2018年4月27日
     */
    @ResponseBody
    @RequestMapping(value="/findTransitCentergps", produces = "application/json;charset=UTF-8")
    public List<PageData> findTransitCentergps() throws Exception{
    	PageData pd = this.getPageData();  
    	return transitCenterService.findTransitCentergps(pd);
    }
    
    /**
     *@desc 查询所有集散中心信息
     *@auther sunqp
     *@history 2018年4月27日
     */
    @ResponseBody
    @RequestMapping(value="/findTransitAll", produces = "application/json;charset=UTF-8")
    public List<PageData> findTransitAll() throws Exception{
    	// 集散中心集合
        return transitCenterService.findAll(this.getPageData());
    }
}
