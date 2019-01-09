package com.fh.controller.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.order.ProblemOrder;
import com.fh.service.app.AppOrderToubleInfoService;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 问题件处理
 */
/**
 * @author zhangjj
 *
 */
/**
 * @author zhangjj
 *
 */
/**
 * @author zhangjj
 *
 */
@Controller
@RequestMapping(value = "problemorder")
public class ProblemOrderControl extends BaseController {

	@Autowired
	private AppOrderToubleInfoService orderToubleInfoService;


    /**
     * @desc 反馈列表查询
     * @auther zhangjj
     * @history 2018年12月10日
     */
	@RequestMapping(value = "listProblemOrder")
	public ModelAndView listProblemOrder(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		
		List<PageData> findOrderlistPage = orderToubleInfoService.findOrderlistPage(page);
		if (CollectionUtils.isNotEmpty(findOrderlistPage)) {
			mv.addObject("problemOrderList", findOrderlistPage);
		}
		
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/order/problemorder/jsp/problemorder_list");
		return mv;
	}

	/**
     * @desc 反馈列表详情
     * @auther zhangjj
     * @history 2018年12月10日
     */
    @RequestMapping(value = "problemDetails")
    public ModelAndView problemDetails(Integer orderid) throws Exception {
        ModelAndView mv = new ModelAndView();
        
        List<ProblemOrder> findOrderTouble = orderToubleInfoService.findOrderTouble(orderid);
        if (CollectionUtils.isNotEmpty(findOrderTouble)) {
            mv.addObject("problemDetails", findOrderTouble);
        }
        
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/order/problemorder/jsp/problemorder_details");
        return mv;
    }
	
	/**
     * @desc 处理
     * @auther zhangjj
     * @history 2018年12月10日
     */
    @ResponseBody
    @RequestMapping(value = "/process" , method = RequestMethod.POST)
	public Map<String, Object> process(ProblemOrder problemOrder) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            orderToubleInfoService.process(problemOrder);
            map.put("success", true);
            map.put("problemOrder", problemOrder);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMeg", e.getMessage());
        }
        return map;
    }
    
    /**
     * @desc 根据订单id统计订单下未处理信息
     * @auther zhangjj
     * @history 2018年12月10日
     */
    @ResponseBody
    @RequestMapping(value = "/refresh_unsolved" , method = RequestMethod.POST)
    public Map<String, Object> refresh_unsolved(Integer orderid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Integer unsolved_num = orderToubleInfoService.findUnsolvedByOrderid(orderid);
            map.put("success", true);
            map.put("unsolved_num", unsolved_num);
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMeg", e.getMessage());
        }
        return map;
    }
    
}
