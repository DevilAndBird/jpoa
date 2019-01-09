package com.fh.controller.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.area.AreaRoadInfo;
import com.fh.service.area.AreaCityService;
import com.fh.service.area.AreaDistrictService;
import com.fh.service.area.AreaProvService;
import com.fh.service.area.AreaRoadService;
import com.fh.util.PageData;
/**
 * 地区中心-街道
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj 
 * 县级信息维护
 */
@Controller
@RequestMapping(value="road")
public class AreaRoadController extends BaseController{

	
	@Autowired
	private AreaCityService areaCityService;
	@Autowired
	private AreaProvService areaProvService;
	@Autowired
	private AreaDistrictService areaDistrictService;
	@Autowired
	private AreaRoadService areaRoadService;
	
	
	/**
	 * 修改路街信息
	 * 木桐
	 * 2018年1月17日21:38:53
	 */
	@ResponseBody
	@RequestMapping(value="/updateRoad")
	public String updateRoad(){
		PageData pd = this.getPageData();
		String modelName = pd.getString("modelName");//路街名
		String modelProvID = pd.getString("modelProvID");//省份id
		String modelCityID = pd.getString("modelCityID");//城市id
		String modelDistrictID = pd.getString("modelDistrictID");//区县id
		String flag = "1";
		try {
			modelName = new String(modelName.getBytes("iso-8859-1"),"utf-8");
			modelProvID = new String(modelProvID.getBytes("iso-8859-1"),"utf-8");
			modelCityID = new String(modelCityID.getBytes("iso-8859-1"),"utf-8");
			modelDistrictID = new String(modelDistrictID.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelName", modelName);
			pd.put("modelProvID", modelProvID);
			pd.put("modelCityID", modelCityID);
			pd.put("modelDistrictID", modelDistrictID);
			
			flag = areaRoadService.updateRoad(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * goBack
	 * 木桐
	 * 2018年1月18日15:45:13
	 */
	@RequestMapping(value="/goBack")
	public ModelAndView goBack(Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/road/road_list");
		PageData pd =this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> cityList = areaCityService.findAll();
			if( null !=  cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null !=  provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
			List<PageData> districtList = areaDistrictService.findAll();
			if( null !=  districtList && districtList.size()>0 ){
				mv.addObject("districtList", districtList);
			}
			
			List<PageData> roadList = areaRoadService.roadList(page);
			if( null !=  roadList && roadList.size()>0 ){
				mv.addObject("roadList", roadList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 新增街道
	 * 木桐
	 * 2018年1月16日17:54:10
	 */
	@RequestMapping(value="/insertRoad")
	public String insertRoad(){
		PageData pd = this.getPageData();
		try {
			areaRoadService.insertRoad(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:roadList";
	}
	
	/**
	 * 核对ID是否存在
	 * 木桐
	 * 2018年1月17日15:52:44
	 */
	@ResponseBody
	@RequestMapping(value="/checkID")
	public String checkID(){
		PageData pd = this.getPageData();
		try {
			AreaRoadInfo areaRoadInfo = areaRoadService.checkID(pd);
			if( null != areaRoadInfo ){
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "2";
	}
	
	/**
	 * 路街列表查询
	 * 木桐
	 * 2018-1-16 14:01:09
	 */
	@RequestMapping(value="roadList")
	public ModelAndView roadList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/road/road_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> cityList = areaCityService.findAll();
			if( null !=  cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null !=  provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
			List<PageData> districtList = areaDistrictService.findAll();
			if( null !=  districtList && districtList.size()>0 ){
				mv.addObject("districtList", districtList);
			}
			
			List<PageData> roadList = areaRoadService.roadList(page);
			if( null !=  roadList && roadList.size()>0 ){
				mv.addObject("roadList", roadList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
}
