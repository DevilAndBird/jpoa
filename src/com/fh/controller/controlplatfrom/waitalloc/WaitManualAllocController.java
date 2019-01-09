package com.fh.controller.controlplatfrom.waitalloc;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.controller.base.BaseController;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.controlplatfrom.waitmanualalloc.WaitManualAllocService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 控制台_待人工分配列表查询统计
 */
@Controller
@RequestMapping(value = "waitmanualalloc")
public class WaitManualAllocController extends BaseController {
	@Autowired
	private WaitManualAllocService waitManualAllocService;
	@Autowired
	private OrderInfoService orderInfoService;
    @Autowired
    private ServiceCenterService serviceCenterService;
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private AutoAllotService autoAllotService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
	 * @desc 待人工分配订单列表
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findWaitmanualAllotlist")
	public ModelAndView findWaitmanualAllotlist() throws Exception {
		 ModelAndView mv = new ModelAndView();
         mv.setViewName("jpoa/controlplatfrom/waitmanualalloc/jsp/waitallotlist");
        return mv;
	}
	
	/**
	 * @desc 待人工分配订单列表
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/fillWaitmanualAlloc")
	public List<PageData> fillWaitmanualAlloc() throws Exception {
		return waitManualAllocService.findWaitmanualAlloc(this.getPageData());
	}
	
	/**
	 * @desc 待人工分配订单列表编辑
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findWaitmanualAllotedit")
	public ModelAndView findWaitmanualAllotedit() throws Exception {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/waitmanualalloc/jsp/waitallotedit");
        return mv;
	}
	
    /**
     * 订单分派完成
     * @author zhangjj
     * @history 2018年03月04日
     */
    @RequestMapping(value = "/waitallotfinish")
    public void waitallotfinish(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        autoAllotService.insertAutoAllotLog(Integer.parseInt((String) pd.getString("orderid")),  ALLOT_LOG_CODE.MANUAL_ALLOT_SUCC.getValue(), ALLOT_LOG_CODE.MANUAL_ALLOT_SUCC.getName());
       
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:订单分派完成！！！");
    }
    
}
