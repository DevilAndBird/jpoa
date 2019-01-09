package com.fh.controller.customer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.customer.CusCompliantService;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 客户投诉反馈信息处理
 */
@Controller
@RequestMapping(value="/compliant")
public class CusCompliantController extends BaseController{

	@Autowired
	private CusCompliantService cusCompliantService;
	
	
	/**
	 * 导出
	 * 木桐
	 * 2018年1月22日11:20:04
	 */
	@RequestMapping(value="outExcel")
	public ModelAndView outExcel(){
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户ID");
			titles.add("用户名称");
			titles.add("电话");
			titles.add("投诉内容");
			titles.add("是否处理");
			titles.add("创建时间");
			titles.add("处理记录");
			titles.add("处理人");
			titles.add("处理时间");
			dataMap.put("titles", titles);
			
			List<PageData> varOList = cusCompliantService.findAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String addtime = null;
				String backtime = null;
				if( (varOList.get(i).get("addtime")) !=null ){
					addtime = sdf.format((Date)varOList.get(i).get("addtime"));
				}
				if( (varOList.get(i).get("backtime")) !=null ){
					backtime = sdf.format((Date)varOList.get(i).get("backtime"));
				}
				
				PageData vpd = new PageData();
				vpd.put("var1", (Integer)varOList.get(i).get("cusid")+"");
				vpd.put("var2", varOList.get(i).getString("cusname"));
				vpd.put("var3", varOList.get(i).getString("mobile"));
				vpd.put("var4", varOList.get(i).getString("content"));
				vpd.put("var5", (Integer)varOList.get(i).get("status")==1?"待处理":"已处理");
				vpd.put("var6", addtime);
				vpd.put("var7", varOList.get(i).getString("feedback"));
				vpd.put("var8", varOList.get(i).getString("processusername"));
				vpd.put("var9", backtime);
				
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	
	/**
	 * 投诉反馈
	 * 木桐
	 * 2018年2月2日10:31:31
	 */
	@RequestMapping(value="/list")
	public ModelAndView list( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customer/cuscompliant/compliant_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> compliantList = cusCompliantService.compliantList(page);
			if( null != compliantList && compliantList.size()>0 ){
				mv.addObject("compliantList", compliantList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( StringUtils.isNotEmpty(pd.getString("beginDate")) ){
			pd.put("beginDate", pd.getString("beginDate").substring(0, 10));
		}
		if( StringUtils.isNotEmpty(pd.getString("endDate")) ){
			pd.put("endDate", pd.getString("endDate").substring(0, 10));
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	
}
