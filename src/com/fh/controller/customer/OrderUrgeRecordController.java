package com.fh.controller.customer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.customer.OrderUrgeRecord;
import com.fh.service.customer.OrderUrgeRecordService;
import com.fh.util.PageData;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：催单控制类
 * 类名称：com.fh.controller.customer.OrderUrgeRecordController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:58:03   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:58:03   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/orderUrgeRecord")
public class OrderUrgeRecordController extends BaseController{

	@Autowired
	private OrderUrgeRecordService orderUrgeRecordService;
	/**
	 * 
	 * @Title: list
	 * @Description: 催单信息查询
	 * author：
	 * 2018年9月26日
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("customer/orderurgrecord/orderurgrecord_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<OrderUrgeRecord> orderUrgeRecordList = orderUrgeRecordService.orderUrgeRecordList(page);
			if( null != orderUrgeRecordList && orderUrgeRecordList.size()>0 ){
				mv.addObject("orderUrgeRecordList", orderUrgeRecordList);
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
