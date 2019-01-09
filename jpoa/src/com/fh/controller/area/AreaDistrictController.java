package com.fh.controller.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.area.AreaCityInfo;
import com.fh.entity.area.AreaDistrictInfo;
import com.fh.service.area.AreaCityService;
import com.fh.service.area.AreaDistrictService;
import com.fh.service.area.AreaProvService;
import com.fh.util.PageData;
/**
 * 地区中心-区县
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 县级信息维护
 */ 
@Controller
@RequestMapping(value="district")
public class AreaDistrictController extends BaseController{

	@Autowired
	private AreaCityService areaCityService;
	@Autowired
	private	AreaProvService areaProvService;
	@Autowired
	private AreaDistrictService areaDistrictService;
	
	
	/**
	 * 修改区县信息
	 * 木桐
	 * 2018年1月17日21:38:53
	 */
	@ResponseBody
	@RequestMapping(value="/updateDistrict")
	public String updateDistrict(){
		PageData pd = this.getPageData();
		String modelName = pd.getString("modelName");//区县名
		String modelProvID = pd.getString("modelProvID");//省份id
		String modelCityID = pd.getString("modelCityID");//城市id
		String flag = "1";
		try {
			modelName = new String(modelName.getBytes("iso-8859-1"),"utf-8");
			modelProvID = new String(modelProvID.getBytes("iso-8859-1"),"utf-8");
			modelCityID = new String(modelCityID.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelName", modelName);
			pd.put("modelProvID", modelProvID);
			pd.put("modelCityID", modelCityID);
			
			flag = areaDistrictService.updateDistrict(pd);
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
		mv.setViewName("area/district/district_list");
		PageData pd =this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> districtList = areaDistrictService.districtList(page);
			if( null != districtList && districtList.size()>0 ){
				mv.addObject("districtList", districtList);
			}
			
			List<PageData> cityList = areaCityService.findAll();
			if( null != cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null != provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
			AreaCityInfo cityInfo = areaCityService.getByName(pd.getString("modelCityV"));
			if( null != cityInfo ){
				pd.put("modelProvV", cityInfo.getProvid());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 新增区县
	 * 木桐
	 * 2018年1月16日17:54:10
	 */
	@RequestMapping(value="/insertDistrict")
	public String insertDistrict(){
		PageData pd = this.getPageData();
		try {
			areaDistrictService.insertDistrict(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:districtList";
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
			AreaDistrictInfo areaDistrictInfo = areaDistrictService.checkID(pd);
			if( null != areaDistrictInfo ){
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "2";
	}
	
	/**
	 * 区县列表查询
	 * 木桐
	 * 2018-1-16 14:01:09
	 */
	@RequestMapping(value="districtList")
	public ModelAndView districtList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/district/district_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> districtList = areaDistrictService.districtList(page);
			if( null != districtList && districtList.size()>0 ){
				mv.addObject("districtList", districtList);
			}
			
			List<PageData> cityList = areaCityService.findAll();
			if( null != cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null != provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
}
