package com.fh.controller.area;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.area.AreaProvInfo;
import com.fh.service.area.AreaProvService;
import com.fh.util.PageData;
/**
 * 地区中心-省份
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 省级信息维护
 */
@Controller
@RequestMapping(value="prov")
public class AreaProvController extends BaseController{

	@Autowired
	public AreaProvService areaProvService;
	
	
	/**
	 * 删除省份信息
	 * 木桐
	 * 2018年1月17日21:38:53
	 */
	@RequestMapping(value="/delProv")
	public String delProv(){
		PageData pd = this.getPageData();
		try {
			areaProvService.delProv(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:provList";
	}
	
	
	/**
	 * 修改省份信息
	 * 木桐
	 * 2018年1月17日21:38:53
	 */
	@ResponseBody
	@RequestMapping(value="/updateProv")
	public String updateProv(HttpServletRequest request){
		
		PageData pd = this.getPageData();
		String modelName = pd.getString("modelName");//省份名
		
		try {
			modelName = new String(modelName.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelName", modelName);
			AreaProvInfo provInfo = areaProvService.checkName(pd);
			if( null != provInfo ){
				if( !provInfo.getId().equalsIgnoreCase(pd.getString("modelId")) ){
					return "1";
				}
			}
			
			areaProvService.updateProv(pd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "2";
	}
	
	/**
	 * 新增省份
	 * 木桐
	 * 2018年1月16日17:54:10
	 */
	@RequestMapping(value="/insertProv")
	public String insertProv(){
		PageData pd = this.getPageData();
		try {
			areaProvService.insertProv(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:provList";
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
			AreaProvInfo areaProvInfo = areaProvService.checkID(pd);
			if( null != areaProvInfo ){
				return "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		return "2";
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月16日15:10:43
	 */
	@RequestMapping(value="/chgStatus")
	public String changeStatus(){
		PageData pd = this.getPageData();
		try {
			areaProvService.changeStatus(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:provList";
	}
	
	/**
	 * 省级列表查询
	 * 木桐
	 * 2018-1-16 14:01:09
	 */
	@RequestMapping(value="provList")
	public ModelAndView provList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("area/prov/prov_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> provList = areaProvService.provList(page);
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
	 * 省级列表查询
	 * 木桐
	 * 2018-1-16 14:01:09
	 */
	@ResponseBody
	@RequestMapping(value="loadProvAll")
	public List<PageData> loadProvAll() throws Exception{
		 return areaProvService.findAll();
	}	
}
