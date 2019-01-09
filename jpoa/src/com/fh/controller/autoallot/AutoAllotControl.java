package com.fh.controller.autoallot;

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
import com.fh.entity.autoallot.HaulWay;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.delivery.RegionManagementService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.util.PageData;
import com.google.gson.Gson;
/**
 * 自动分配
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 自动分配（暂时未用到）
 */
@Controller
@RequestMapping(value="autoallot")
public class AutoAllotControl extends BaseController{

	@Autowired
	private RegionManagementService regionManagementService;
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private AutoAllotService autoAllotService;
	
    /**
     * @desc  跳转各区域物流路线管理
     * @auther tangqm
     * @date 2018年5月27日
     */
    @RequestMapping(value = "/toHaulwayPage")
    public ModelAndView toHaulwayPage(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        // 集散中心
        List<PageData> transitCenterList = transitCenterService.findAll(pd);
        if( CollectionUtils.isNotEmpty(transitCenterList) ){
        	mv.addObject("transitCenterList", transitCenterList);
        }
        List<PageData> haulwayList= autoAllotService.haulwaylistPage(page);
        mv.addObject("pd", pd);
        mv.addObject("haulwayList", haulwayList);
        mv.setViewName("jpoa/autoallot/haulwaymannage/jsp/haulway");
        return mv;
    }
    
    /**
     * @desc  预加载
     * @auther tangqm
     * @date 2018年5月27日
     */
    @RequestMapping(value = "/loadHaulwayMap")
    public void loadHaulwayMap(HttpServletRequest request, HttpServletResponse response) throws Exception {
         PageData pd = this.getPageData();
        //集散中心区域以及营业区域
        List<PageData> regionTransitCenter = regionManagementService.findAllRegionTransitCenter(pd);
        //集散中心点
        pd.put("srcprovid", pd.get("loginperson_provid"));
        pd.put("srccityid", pd.get("loginperson_cityid"));
        List<PageData> transitCentergps = transitCenterService.findTransitCentergps(pd);
        pd.put("regionTransitCenter", regionTransitCenter);
        pd.put("transitCentergps", transitCentergps);
    	//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(new Gson().toJson(pd));
    }
    
    /**
     * @desc  删除路线
     * @auther tangqm
     * @date 2018年5月27日
     */
    @RequestMapping(value="/deleteHualWay")
    public void deleteHualWay(HttpServletRequest request, HttpServletResponse response,HaulWay haulWay) throws Exception{
    	autoAllotService.deleteHualWay(this.getPageData());
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().print("SUCCESS:删除成功!");
    }
    
    /**
     * 
     * @Title: saveAutoAllotWay
     * @Description: 保存路线
     * author：tangqm
     * 2018年6月2日
     * @param request
     * @param response
     * @param haulWay
     * @throws Exception
     */
    @RequestMapping(value="/saveAutoAllotWay")
    public void saveAutoAllotWay(HttpServletRequest request, HttpServletResponse response,HaulWay haulWay) throws Exception{
    	autoAllotService.saveAutoAllotWay(this.getPageData());
    	response.setHeader("Content-type", "text/html;charset=UTF-8");  
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().print("SUCCESS:保存成功!");
    }
    
    /**
     * @desc:根据id获取集散中心关联点
     * @author:tangqm
     * @history:2018年1月18日
     */
    @ResponseBody
    @RequestMapping(value = "/previewMap")
    public List<PageData> previewMap() throws Exception {
        return autoAllotService.findPreviewMap(this.getPageData());
    }
    
}
