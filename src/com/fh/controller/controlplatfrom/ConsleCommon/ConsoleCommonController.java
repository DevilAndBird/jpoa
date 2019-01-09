package com.fh.controller.controlplatfrom.ConsleCommon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.constant_enum.APPUSER_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.controlplatfrom.consolecommon.ConsoleCommonService;
import com.fh.service.delivery.CounterServiceCenter;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.OrderRoleService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * @author zhangjj
 * 控制台_公共查询信息处理
 */
@Controller
@RequestMapping(value = "consolecommon")
public class ConsoleCommonController extends BaseController {
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private ConsoleCommonService consoleCommonService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	@Autowired
	private OrderRoleService orderRoleService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private TransitCenterService transitCenterService;
	@Autowired
	private ServiceCenterService serviceCenterService;
	@Autowired
	private AutoAllotService autoAllotService;

	/**
	 * @desc 取派员列表查询
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/fillRoleGpsData")
	public PageData fillRoleGpsData() throws Exception {
		// 请求参数
		PageData reqData = this.getPageData();
	    // 返回参数
		PageData fillrolegpsdata = new PageData();
		// 组装取派员位置和任务信息
		build_Dman_Task_Gps(reqData, fillrolegpsdata);
    	// 取件地址住宅地址
		build_taskhotelgps(reqData, fillrolegpsdata);
		// 送件地址住宅地址
		build_sendhotelgps(reqData, fillrolegpsdata);
    	// 集散中心任务订单数查询
    	fillrolegpsdata.put("taskOrderNum", consoleCommonService.findTaskOrderNum(reqData));
    	
    	return fillrolegpsdata;
	}
	
	/**
	 * @desc 组装取派员位置和任务信息
	 * @auther zhangjj
	 * @date 2018年5月29日
	 */
	private void build_Dman_Task_Gps(PageData reqData, PageData resdata) throws Exception {
		// 取派员信息(待取_UNFINISHED、待派_UNFINISHED、已经完成_FINISHED)
		List<PageData> dmaninfoList = userDeliveryManService.findDmanList_contralplatform(reqData);
		// redis 查询取派员位置
		@SuppressWarnings("unchecked")
		Map<String, AppDmanCurrentGpsReqData> dmancurrentGpsList = (Map<String, AppDmanCurrentGpsReqData>) RedisUtil.get(reqData.getString("loginperson_cityid"));
		// 填充dman
		resdata.put("dman", "");
		if (dmancurrentGpsList == null) {
			LoggerUtil.warn("redis取派员位置列表未查到，请及时检查原因：市：" + reqData.getString("loginperson_cityid"));
			return;
		}
			
		for (PageData dmantemp : dmaninfoList) {
    		AppDmanCurrentGpsReqData dmangpstemp = dmancurrentGpsList.get(((Integer) dmantemp.get("userid")).toString());
    		dmantemp.put("currentgps", ""); 
    		if (dmangpstemp == null) {
				LoggerUtil.warn("某取派员列表未及时更新，取派员姓名：" + dmantemp.getString("name") + ",取派员手机号：" + dmantemp.getString("mobile"));
				continue;
    		}
    		// 取派员坐标填充
    		dmantemp.put("currentgps", dmangpstemp.getCurrentgps()); 
		}
		
		// 填充dman
		resdata.put("dman", dmaninfoList);
	}
	
	/**
	 * @desc 去酒/住 取件地址gps组装
	 * @auther zhangjj
	 * @date 2018年5月29日
	 */
	private void build_taskhotelgps(PageData reqData, PageData resdata) throws Exception {
		// 取件地址住宅地址
    	List<PageData> taskAddressList = consoleCommonService.findTaskAddress_HOTELOROTHER(reqData);
    	resdata.put("taskaddressgps_hotel", "");
    	if(CollectionUtils.isEmpty(taskAddressList)) {
    		return;
    	}
    	resdata.put("taskaddressgps_hotel", taskAddressList);
	}
	
	/**
	 * @desc  酒/住 送件地址gps组装
	 * @auther zhangjj
	 * @date 2018年5月29日
	 */
	private void build_sendhotelgps(PageData reqData, PageData resdata) throws Exception {
		// 送件地址住宅地址
    	List<PageData> findSendAddress = consoleCommonService.findSendAddress_HOTELOROTHER(reqData);
    	resdata.put("sendaddressgps_hotel", "");
    	if(CollectionUtils.isEmpty(findSendAddress)) {
    		return;
    	}
		resdata.put("sendaddressgps_hotel", findSendAddress);
	};
	
	/**
	 * @desc 取派员信息查询userid
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/findDmanList")
	public List<PageData> findDmanList() throws Exception {
		return userDeliveryManService.findDmanList_contralplatform(this.getPageData());
	}
	
	/**
	 * @desc 订单运输路线其中一个取派员运输路径查询
	 * @auther zhangjj
	 * @date 2018年5月14日
	 * @param orderid、dmanuserid
	 */
	@ResponseBody
	@RequestMapping(value = "/finddmanpathbaseinfo")
	public PageData finddmanpathbaseinfo() throws Exception {
		return consoleCommonService.finddmanpathbaseinfo(this.getPageData());
	}
	
	/**
     *@desc 修改地址弹窗
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @RequestMapping(value="/modifydmanalloc", produces={"text/html;charset=UTF-8;","application/json;"})
    public ModelAndView modifydmanalloc(HttpServletResponse response) throws Exception{
    	 ModelAndView mv = new ModelAndView();
         mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/modifydmanalloc");
         return mv;
    }
    
    /**
     *@desc 修改最迟到达时间
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @RequestMapping(value="/modifyaddivedtime", produces={"text/html;charset=UTF-8;","application/json;"})
    public ModelAndView modifyaddivedtime(HttpServletResponse response) throws Exception{
    	 ModelAndView mv = new ModelAndView();
         mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/autoallot/jsp/modifyaddivedtime");
         return mv;
    }
    
    /**
     *@desc 更新
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @ResponseBody
    @RequestMapping(value="/updatearrivedtimebyid", produces = "application/json;charset=UTF-8")
    public void updatearrivedtimebyid(HttpServletResponse response) throws Exception{
    	orderRoleService.updatearrivedtimebyid(this.getPageData());
    	
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:更新最迟到达成功,请检查");
    }
    
    /**
     *@desc 更新
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @ResponseBody
    @RequestMapping(value="/ichangedman", produces = "application/json;charset=UTF-8")
    public void ichangedman(HttpServletResponse response) throws Exception{
    	orderRoleService.ichangedman(this.getPageData());
    	
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:更新取派员成功");
    }
    
    /**
     * @DESC 订单分配给角色
     * @author zhangjj
     * @history 2018年03月04日
     */
    @RequestMapping(value = "manualallotorder", produces = "application/json;charset=UTF-8")
    public void manualallotorder(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        String msg = autoAllotService.cbSemiAllot(pd);
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(msg);
    }

	/**
	 * @desc: 订单角色动作轨迹
	 * @author zhangjj
	 */
    @ResponseBody
	@RequestMapping(value="/viewOrderRole", produces = "application/json;charset=UTF-8")
	public List<PageData> viewOrderRole() throws Exception {
	    
	    // 订单角色动作轨迹
	    List<PageData> actionList = orderInfoService.orderRoleTypeListPage(Integer.valueOf(this.getPageData().getString("id")));
	   
	    if (CollectionUtils.isEmpty(actionList)) {
	    	return new ArrayList<PageData>();
	    }
	    

        for (PageData action : actionList) {
            Integer userid = (Integer)action.get("roleid");//用户id
            String usertype = action.getString("usertype");//用户类型
            // 集散中心
            if(APPUSER_TYPE.TRANSIT_CENTER.getValue().equals(usertype)) {
                PageData transitPd = transitCenterService.findByUserid(userid);
                if(transitPd != null) {
                    action.put("rolename", transitPd.get("linkman"));
                    action.put("rolemobile", transitPd.get("linkphone"));
                }
            }
            // 机场/高铁
            if(APPUSER_TYPE.SERVICE_CENTER.getValue().equals(usertype)) {
            	CounterServiceCenter counterServiceCenter = serviceCenterService.findByUserid(userid);
                if(counterServiceCenter != null) {
                    action.put("rolename", counterServiceCenter.getLinkman());// 目的地
                    action.put("rolemobile", counterServiceCenter.getLinkphone());// 目的地
                }
            }
            // 取派员
            if(APPUSER_TYPE.DELIVERY_MAN.getValue().equals(usertype)) {
                PageData dmanPd = userDeliveryManService.findByUserid(userid);
                if(dmanPd != null) {
                    action.put("rolename", dmanPd.get("name"));
                    action.put("rolemobile", dmanPd.get("mobile"));
                }
            }
        }
        
        return actionList;
    }
    
    /**
     * @DESC 删除动作
     * @author zhangjj
     * @history 2018年03月04日
     */
    @RequestMapping(value = "/delectOrderRoleByid")
    public void delectOrderRoleByid(HttpServletResponse response) throws Exception {
        
        PageData pd = this.getPageData();
        Integer orderOrderid = Integer.parseInt((String)pd.get("id"));
        orderInfoService.delectOrderRoleByid(orderOrderid);
        
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:删除成功！");
    }
    
}
