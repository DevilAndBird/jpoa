package com.fh.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.order.overtimeorder.OverTimeOrderService;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 订单超时信息处理
 */
@Controller
@RequestMapping(value = "overtimeorder")
public class OverTimeOrderControl extends BaseController {
	
	@Autowired
	private OverTimeOrderService overTimeOrderService;

	/**
	 * 
	 * @Title: listOverTimeOrder
	 * @Description: 查询超时订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "listOverTimeOrder")
	public ModelAndView listOverTimeOrder(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("order/overtimeorder/overtimeorder_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> overTimeOrderList = overTimeOrderService.overTimeOrderList(page);
		if (CollectionUtils.isNotEmpty(overTimeOrderList)) {
			mv.addObject("overTimeOrderList", overTimeOrderList);
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
    /**
     * 
     * @Title: listOverTimeDetail
     * @Description: 查询订单明细
     * @param page
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "listOverTimeDetail")
	public ModelAndView listOverTimeDetail(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("order/overtimeorder/overtimeorder_detail");
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> overTimeDetailList = overTimeOrderService.overTimeDetailList(page);
		if (CollectionUtils.isNotEmpty(overTimeDetailList)) {
			mv.addObject("overTimeDetailList", overTimeDetailList);
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
   
	/**
	 * 
	 * @Title: saveSmsTemplate
	 * @Description:新增超时订单
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/saveOverTimeOrder")
	public String saveOverTimeOrder() throws Exception{
		PageData pd = this.getPageData();
		overTimeOrderService.saveOverTimeOrder(pd);
		return "redirect:/overtimeorder/listOverTimeOrder";  
	}
	
	/**
	 * 
	 * @Title: outExcel
	 * @Description: 导出excel
	 * @return
	 */
	@RequestMapping(value="/outExcel")
	public ModelAndView outExcel(Page page){
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("城市");
			titles.add("快递员");
			titles.add("手机号码");
			titles.add("超时订单数");
			titles.add("归属集散中心");
			dataMap.put("titles", titles);
			List<PageData> varOList = overTimeOrderService.overTimeOrderList(page);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("cityname"));
				vpd.put("var2", varOList.get(i).getString("name"));
				vpd.put("var3", varOList.get(i).getString("mobile"));
				vpd.put("var4", varOList.get(i).get("num")+"");
				vpd.put("var5", varOList.get(i).getString("transitname"));
				
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

}
