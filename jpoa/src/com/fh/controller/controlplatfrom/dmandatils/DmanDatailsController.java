package com.fh.controller.controlplatfrom.dmandatils;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Point;
import com.fh.service.controlplatfrom.dmandelis.DmanDetailsService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.OrderRoleService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.MapUtil;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 控制台_取派员详情列表查询统计
 */
@Controller
@RequestMapping(value = "controlplatform_dmanDatails")
public class DmanDatailsController extends BaseController {
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private DmanDetailsService dmanDetailsService;
	@Autowired
	private OrderRoleService orderRoleService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
     * @desc 热力部署_取派员详情
     * @auther zhangjj
     * @date 2018年5月16日
     */
    @RequestMapping(value = "/thermaldeployment")
    public ModelAndView main() throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/controlplatfrom/dmandetails/jsp/dmanDetails");
        return mv;
    }
	
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
	 * @desc 根据取派员查询订单信息
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/dmaDetails")
	public List<PageData> dmaDetails() throws Exception {
		return dmanDetailsService.findDmanTodayOrderInfo(this.getPageData());
	}
	
	
	/**
	 * @desc 查询集散中心后下个取派员的路径
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/orderTransitLaterPath")
	public PageData findOrderTransitLaterPath() throws Exception {
		return dmanDetailsService.findOrderTransitLaterPath(this.getPageData());
	}
	
	/**
	 * @desc 订单分配列表填充数据查询
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/orderallocfillpage")
	public PageData orderallocfillpage() throws Exception {
		return dmanDetailsService.orderallocfillpage(this.getPageData());
	}
	
	/**
     *@desc 更新
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @ResponseBody
    @RequestMapping(value="/updatedestbyid", produces = "application/json;charset=UTF-8")
    public void updateDman(HttpServletResponse response) throws Exception{
    	PageData req = this.getPageData();
    	// 如果是高德地图的话则转换使用
    	if("1".equals((String)req.get("istrans"))) {
    		String gpstemp = (String) req.get("destgps");
    		JSONObject gaode_json = JSONObject.fromObject(gpstemp);
        	Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
        	Point pointtemp = MapUtil.GD_TRANS_BD(point);
        	req.put("destgps", pointtemp.toEntity());
    	}
    	
    	orderRoleService.updatedestbyid(req);
    	
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:更新地址成功,请检查");
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
         mv.setViewName("jpoa/controlplatfrom/dmandetails/jsp/modifydmanalloc");
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
         mv.setViewName("jpoa/controlplatfrom/dmandetails/jsp/modifyaddivedtime");
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
	 * @desc 地址查询页面
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@RequestMapping(value = "/addrselect")
	public ModelAndView addrselect() throws Exception {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/controlplatfrom/dmandetails/jsp/addrselect");
        return mv;
	}
    
}
