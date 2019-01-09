package com.fh.controller.controlplatfrom.autoAllot;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.controlplatfrom.autoalloc.ConsoleAutoAllocService;
import com.fh.service.controlplatfrom.waitmanualalloc.WaitManualAllocService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.OrderRoleService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;
/**
 * 
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 控制台_自动分配列表相关查询处理
 */
@Controller
@RequestMapping(value = "console_autoalloc")
public class AutoAllocController extends BaseController {
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
	private ConsoleAutoAllocService consoleAutoAllocService;
	@Autowired
	private OrderRoleService orderRoleService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
	 * @desc 自动分配列表
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findAutoAllotlist")
	public ModelAndView findWaitmanualAllotlist() throws Exception {
		 ModelAndView mv = new ModelAndView();
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/autoallotlist");
        return mv;
	}
	
	/**
	 * @desc 填充自动分配
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/fillAutoAlloc")
	public List<PageData> fillAutoAlloc() throws Exception {
		 PageData reqparem = this.getPageData();
		 List<PageData> autoAllotlist = consoleAutoAllocService.findAutoAllotlist(reqparem);
		 
		 if(CollectionUtils.isEmpty(autoAllotlist)) {
			 return autoAllotlist;
		 }
		 
		 // ===============================时间筛选=================================
		 // 当前时间
		 /*Date currentDate = new Date();
		 Long currentLong = currentDate.getTime();
		 // 如果时间筛选查询类型为 -1（不需要筛选）
		 String timeScreenType = (String) reqparem.get("timeScreenType");
		
		 if("-1".equals(timeScreenType) || StringUtils.isEmpty(timeScreenType)) {
			 return autoAllotlist;
		 } 
		 // 筛选时间
		 Integer screenmin = Integer.parseInt((String) reqparem.get("screenmin"));
		 
		 for (int i = 0; i < autoAllotlist.size(); i++) {
			 Boolean isremove = true;
			 
			 Integer orderid = (Integer) autoAllotlist.get(i).get("id");
			 // 订单关联取派员列表
			 List<PageData> orderlinkdmanList = orderRoleService.findOrderlinkdman(orderid);
			 for (int j = 0; j < orderlinkdmanList.size(); j++) {
				 PageData req = new PageData();
				 req.put("dmanuserid",  orderlinkdmanList.get(j).get("roleid"));
				 req.put("orderid", orderid);
				 PageData task = orderRoleService.findDmanOrderTask(req);
				 PageData send = orderRoleService.findDmanOrderSend(req);
				 // 动作类型
				 String taskroletype = (String)task.get("roletype");
				 String sendroletype = (String)send.get("roletype");
				 
				 // 最迟到达时间
				 long taskarrivedtime = ((Date) task.get("arrivedtime")).getTime();
				 long sendarrivedtime = ((Date) send.get("arrivedtime")).getTime();
				 
				 if("COMMONSCREEN".equals(timeScreenType)) {
					
					 if(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(taskroletype) 
							 || ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(taskroletype)) {
						 
						 long senddiffer = sendarrivedtime - currentLong;
						 
						 if(senddiffer <=0) {
							 // 如果发现当前时间超过了设置的最迟到达时间则需要查询出来
							 isremove = false;
							 break;
						 }
							
						  //计算相差分钟数  
						  Integer sendminutes = (int) ((senddiffer)/(1000 * 60));  
						  
						  if(screenmin >= sendminutes) {
							  // 如果发现设置的最迟到达时间则需要查询出来
							  isremove = false;
							  break;
						  }
					 }
					 
					 if((!ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(taskroletype) 
							 && !ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(taskroletype))
							 && (ROLE_TYPE.ROLE_AIRPORT_SEND.getValue().equals(sendroletype) 
									 || ROLE_TYPE.ROLE_HOTEL_SEND.getValue().equals(sendroletype))) {
						 // 取件时间筛选
						 long taskdiffer = taskarrivedtime - currentLong;
						 if(taskdiffer <=0) {
							 // 如果发现当前时间超过了设置的最迟到达时间则需要查询出来
							 isremove = false;
							 break;
						 }
						  //计算相差分钟数  
						  Integer taskminutes = (int) ((taskdiffer)/(1000 * 60));  
						  if(screenmin >= taskminutes) {
							  // 如果发现设置的最迟到达时间则需要查询出来
							  isremove = false;
							  break;
						  }
						 
						 // 送件时间筛选
						 long senddiffer = sendarrivedtime - currentLong;
						 if(senddiffer <=0) {
							 // 如果发现当前时间超过了设置的最迟到达时间则需要查询出来
							 isremove = false;
							 break;
						 }
						  //计算相差分钟数  
						  Integer sendminutes = (int) ((senddiffer)/(1000 * 60));  
						  if(screenmin >= sendminutes) {
							  // 如果发现设置的最迟到达时间则需要查询出来
							  isremove = false;
							  break;
						  }
					 }
				 } 
				 
				 if("TRANSITSCREEN".equals(timeScreenType)) {
					 if(taskroletype.contains("ROLE_TRANSIT") && sendroletype.contains("ROLE_TRANSIT")) {
						// 取件时间筛选
						 long taskdiffer = taskarrivedtime - currentLong;
						 if(taskdiffer <=0) {
							 // 如果发现当前时间超过了设置的最迟到达时间则需要查询出来
							 isremove = false;
							 break;
						 }
						  //计算相差分钟数  
						  Integer taskminutes = (int) ((taskdiffer)/(1000 * 60));  
						  if(screenmin >= taskminutes) {
							  // 如果发现设置的最迟到达时间则需要查询出来
							  isremove = false;
							  break;
						  }
						 
						 // 送件时间筛选
						 long senddiffer = sendarrivedtime - currentLong;
						 if(senddiffer <=0) {
							 // 如果发现当前时间超过了设置的最迟到达时间则需要查询出来
							 isremove = false;
							 break;
						 }
						  //计算相差分钟数  
						  Integer sendminutes = (int) ((senddiffer)/(1000 * 60));  
						  if(screenmin >= sendminutes) {
							  // 如果发现设置的最迟到达时间则需要查询出来
							  isremove = false;
							  break;
						  }
					 }
				 }
			 }
			 
			 // 有一个满足则不删除;
			 if(isremove) {
				 autoAllotlist.remove(i);
				 i++;
			 }
			 
		}*/
		 
		 return autoAllotlist;
	}
	
	/**
	 * @desc 查看订单途径路径
	 * @auther zhangjj
	 * @date 2018年7月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/orderPathWay")
	public List<PageData> orderPathWay() throws Exception {
		return orderRoleService.orderPathWay(Integer.valueOf((String)this.getPageData().get("orderid")));
	}
	
	
	/**
	 * @desc 订单路径列表详情
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findAutoAllotdetail")
	public ModelAndView findAutoAllotdetail() throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/autoallotdetail");
        return mv;
	}
	
	/**
	 * @desc 订单路径详情
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/orderpathdetails")
	public PageData orderpathdetails() throws Exception {
		return consoleAutoAllocService.orderpathdetails(this.getPageData());
	}
	
	/**
	 * @desc 订单列表路径详情
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findautoallotdetailedit")
	public ModelAndView findautoallotdetailedit() throws Exception {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/autoallotdetailedit");
         return mv;
	}
	
	/**
     * @DESC 删除动作
     * @author zhangjj
     * @history 2018年03月04日
     */
    @RequestMapping(value = "/delectByOrderidAndRoleid")
    public void delectOrderRoleByid(HttpServletResponse response) throws Exception {
    	orderRoleService.delectByOrderidAndRoleid(this.getPageData());
        
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:删除成功！");
    }
	
    
    /**
	 * @desc 分派取派员
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/findallotdman")
	public ModelAndView findallotdman() throws Exception {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/allotdman");
        return mv;
	}
	
	/**
	 * @desc 地址查询页面
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/addrselect")
	public ModelAndView addrselect() throws Exception {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/addrselect");
        return mv;
	}
    
}
