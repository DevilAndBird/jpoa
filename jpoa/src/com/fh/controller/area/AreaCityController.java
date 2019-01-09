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
import com.fh.service.area.AreaCityService;
import com.fh.service.area.AreaProvService;
import com.fh.util.PageData;
/**
 * 地区中心-城市
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 城级信息维护
 */
@Controller
@RequestMapping(value="city")
public class AreaCityController extends BaseController{

	@Autowired
	private AreaCityService areaCityService;
	@Autowired
	private AreaProvService areaProvService;
	
	/**
	 * 修改城市信息
	 * 木桐
	 * 2018年1月17日21:38:53
	 */
	@ResponseBody
	@RequestMapping(value="/updateCity")
	public String updateCity(){
		PageData pd = this.getPageData();
		String modelName = pd.getString("modelName");//城市名
		String modelProvID = pd.getString("modelProvID");//省份id
		String flag = "1";
		try {
			
			modelName = new String(modelName.getBytes("iso-8859-1"),"utf-8");
			modelProvID = new String(modelProvID.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelName", modelName);
			pd.put("modelProvID", modelProvID);
			
			flag = areaCityService.updateCity(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 新增城市
	 * 木桐
	 * 2018年1月16日17:54:10
	 */
	@RequestMapping(value="/insertCity")
	public String insertCity(){
		PageData pd = this.getPageData();
		try {
			areaCityService.insertCity(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:cityList";
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
			AreaCityInfo areaCityInfo = areaCityService.checkID(pd);
			if( null != areaCityInfo ){
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "2";
	}
	
	/**
	 * goBack
	 * 木桐
	 * 2018年1月18日15:45:13
	 */
	@RequestMapping(value="/goBack")
	public ModelAndView goBack(Page page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/city/city_list");
		PageData pd =this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> cityList = areaCityService.cityList(page);
			if( null !=  cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null !=  provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
	 * 城市列表查询
	 * 木桐
	 * 2018-1-16 14:01:09
	 */
	@RequestMapping(value="cityList")
	public ModelAndView cityList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/city/city_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> cityList = areaCityService.cityList(page);
			if( null !=  cityList && cityList.size()>0 ){
				mv.addObject("cityList", cityList);
			}
			
			List<PageData> provList = areaProvService.findAll();
			if( null !=  provList && provList.size()>0 ){
				mv.addObject("provList", provList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**
     * 城市列表查询
     * 木桐
     * 2018-1-16 14:01:09
     */
	@ResponseBody
    @RequestMapping(value="/findCityByProvid")
    public List<AreaCityInfo> findCityByProvid(AreaCityInfo areaCityInfo) throws Exception{
        return areaCityService.findCityByProvid(areaCityInfo);
    }
	
}
