package com.fh.controller.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.customer.CusInvoiceInfo;
import com.fh.service.order.orderinfo.OrderInvoiceService;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 客户发票信息处理
 */
@Controller
@RequestMapping(value="/cusInvoice")
public class OrderInvoiceInfoController extends BaseController{

	@Autowired
	private OrderInvoiceService orderInvoiceService;
	
	
	/**
	 * 审核发票是否存在
	 * 木桐
	 * 2018年2月2日18:17:20
	 */
	@ResponseBody
	@RequestMapping(value="/insertInvoice")
	public String insertInvoice(){
		PageData pd = this.getPageData();
		try {
			CusInvoiceInfo cusInvoiceInfo = orderInvoiceService.checkInfo(pd);
			if( null != cusInvoiceInfo ){
				return "2";
			}
			orderInvoiceService.insertInvoice(pd);
		} catch (Exception e) {
			e.printStackTrace();
			return "2";
		}
		return "1";
	}
	
	/**
	 * 发票管理列表
	 * 木桐
	 * 2018年2月2日16:45:39
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public ModelAndView list( Page page ) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		PageData pd = this.getPageData();
		page.setPd(pd);
		
		List<PageData> cusInvoiceList = orderInvoiceService.orderInvoicelistPage(page);
		if( null != cusInvoiceList && cusInvoiceList.size()>0 ){
			mv.addObject("cusInvoiceList", cusInvoiceList);
		}
		
		mv.addObject("pd", pd);
		mv.setViewName("customer/cusinvoice/cusinvoice_list");
		return mv;
	}
	
	/**
     * 
     * @Title: outExcel
     * @Description: 导出excel
     * @return
     */
    @RequestMapping(value = "outExcel")
    public ModelAndView outExcel() throws Exception {
        PageData pd = this.getPageData();
        List<PageData> varOList = orderInvoiceService.queryOrderInvoiceList(pd);
        
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("订单号");
        titles.add("用户名");
        titles.add("手机号码");
        titles.add("发票类型");
        titles.add("抬头");
        titles.add("税号");
        titles.add("金额");
        titles.add("收件人姓名");
        titles.add("收件人手机号");
        titles.add("寄件地址");
        titles.add("生成日期");
        dataMap.put("titles", titles);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            vpd.put("var1", varOList.get(i).getString("orderno"));
            vpd.put("var2", varOList.get(i).getString("cusname"));
            vpd.put("var3", varOList.get(i).getString("mobile"));
            vpd.put("var4", varOList.get(i).getString("invoicetypedesc"));
            vpd.put("var5", varOList.get(i).getString("title"));
            vpd.put("var6", varOList.get(i).getString("taxno"));
            vpd.put("var7", varOList.get(i).get("money")+"");
            vpd.put("var8", varOList.get(i).getString("sendname"));
            vpd.put("var9", varOList.get(i).getString("sendphone"));
            vpd.put("var10", varOList.get(i).getString("sendaddr") + varOList.get(i).getString("housenum"));
            vpd.put("var11", varOList.get(i).get("addtime")+"");
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        ModelAndView mv = new ModelAndView(erv, dataMap);
        mv.addObject("pd", pd);
        return mv;
    }
	
}
