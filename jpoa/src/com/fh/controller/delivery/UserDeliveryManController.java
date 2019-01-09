package com.fh.controller.delivery;


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

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.entity.order.OrderAddress;
import com.fh.service.area.AreaProvService;
import com.fh.service.delivery.RegionManagementService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.thirdparty.gaode.GaoDeMapBO;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;

/**
 * @author zhangjj
 * 取派员基本信息处理
 */
@Controller
@RequestMapping(value="/userDeliveryMan")
public class UserDeliveryManController extends BaseController{
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
    private AreaProvService areaProvService;
	@Autowired
    private TransitCenterService transitCenterService;
	@Autowired
	private OrderAddressService orderAddressService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
    private RegionManagementService regionManagementService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
	 * 取派员列表查询
	 * @param model
	 * @return
	 */
	@SuppressWarnings("all")
	@RequestMapping(value="/deliveryManlistPage")
	public ModelAndView deliveryManlistPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
        page.setPd(this.getPageData());
        
        // 省份信息
		List<PageData> provList = (List<PageData>) RedisUtil.get("provList");
        if( null != provList && provList.size()>0 ){
            mv.addObject("provList", provList);
        }
        
        //获取取派员下拉框
        List<PageData> regionDeliveryList = regionManagementService.selcRegionDeliveryManlist(this.getPageData());
        if (CollectionUtils.isNotEmpty(regionDeliveryList)) {
        	mv.addObject("regionDeliveryList", regionDeliveryList);
        }
        
        
		List<PageData> deliveryManlistPage = userDeliveryManService.findDeliveryManlistPage(page);
		if(deliveryManlistPage != null && deliveryManlistPage.size() >0) {
		    mv.addObject("deliveryManlistPage", deliveryManlistPage);
		}
		
		mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/deliveryman/jsp/userdeliveryMan_list");
		
		return mv;
	}
	
	/**
	 *@desc 编辑取派员信息
	 *@auther zhangjj
	 *@history 2018年2月5日
	 */
	@ResponseBody
	@RequestMapping(value="/updateUserDeliveryMan", produces = "application/json;charset=UTF-8")
	public String updateUserDeliveryMan()throws Exception{
		userDeliveryManService.updateUserDeliveryManById(this.getPageData());
		return "1";
	}
	
	/**
     *@desc 保存取派员信息
     *@auther zhangjj
     *@history 2018年2月4日
     */
    @ResponseBody
    @RequestMapping(value="/saveUserDeliveryMan", produces = "application/json;charset=UTF-8")
    public int saveUserDeliveryMan() throws Exception{
        return userDeliveryManService.saveUserDeliveryMan(this.getPageData());
    }
    
	/**
	 * @desc 查看取派员当前位置
	 * @auther zhangjj
	 * @date 2018年3月14日
	 */
	@RequestMapping(value="/viewdmancurrentgps", produces = "application/json;charset=UTF-8")
	public ModelAndView viewdmancurrentgps(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.addObject("pd", this.getPageData());
        mv.setViewName("jpoa/delivery/baiduMap/dmancurrentgps/jsp/viewdmancurrentgps");
		return mv;
	}
    
    /**
     *@desc 查询寄件地址、收件地址坐标
     *@auther sunqp
     *@history 2018年4月27日 
     */
    @ResponseBody
    @RequestMapping(value="/findsrcaddessGps", produces = "application/json;charset=UTF-8")
    public PageData findsrcaddessGps() throws Exception{
    	PageData pd = this.getPageData();   
    	OrderAddress orderAddress = userDeliveryManService.findsrcaddessGps(pd);
    	
    	// 出发地地址格式化
    	String srcaddress = orderAddress.getSrcaddress();
    	// 获取出发地地址坐标
    	BaiduCoord srcgps = GaoDeMapBO.getLngAndLat(srcaddress);
    	if(srcgps==null){
    		srcaddress = orderAddress.getSrcprovname() + orderAddress.getSrccityname() + orderAddress.getSrcdistrictname() + orderAddress.getSrcaddress();
    		srcgps = GaoDeMapBO.getLngAndLat(srcaddress);
    	}
    	
    	// 目的地地址格式化
    	String destaddress = orderAddress.getDestaddress();
    	// 获取目的地地址坐标
        BaiduCoord destgps = GaoDeMapBO.getLngAndLat(destaddress);
          
     	if(destgps==null){
     		destaddress =  orderAddress.getDestprovname() + orderAddress.getDestcityname() + orderAddress.getDestdistrictname() + orderAddress.getDestaddress();
    		destgps = GaoDeMapBO.getLngAndLat(destaddress);
    	}
        
        // gps信息组装
    	PageData pdres = new PageData();
    	String srcaddressgps = "{'lng':'" + srcgps.getLng() +"','lat':'"+srcgps.getLat()+"'}"; 
    	String destaddressgps = "{'lng':'" + destgps.getLng() + "','lat':'" + destgps.getLat() + "'}";  	
    	
    	pdres.put("srcaddress", srcaddressgps);
    	pdres.put("srcaddressname", orderAddress.getSrcaddress());
    	pdres.put("destaddress", destaddressgps);
    	pdres.put("destaddressname", orderAddress.getDestaddress());
    	
    	return  pdres;
    	 
    }
	
    /**
     *@desc 根据id查询快递员位置坐标
     *@auther zhangjj
     *@history 2018年2月4日
     */
    @SuppressWarnings("unchecked")
	@ResponseBody
    @RequestMapping(value="/findDmanCuttentgps", produces = "application/json;charset=UTF-8")
    public List<AppDmanCurrentGpsReqData> findDmanCuttentgps() throws Exception{
    	PageData pd = this.getPageData();    	
    	String provcityKey = pd.getString("srccityid");    	

    	List<PageData> list = userDeliveryManService.findDmanId(pd);
    	List<AppDmanCurrentGpsReqData> returnList = new ArrayList<AppDmanCurrentGpsReqData>();//查到取派员id List
    	Map<String, AppDmanCurrentGpsReqData> testMap = (Map<String, AppDmanCurrentGpsReqData>) RedisUtil.get(provcityKey);
    	if (testMap == null) {
			return null;
		}
    	for (PageData pageData : list) {
    		Integer userid = (Integer) pageData.get("userid");
    		AppDmanCurrentGpsReqData appDmanCurrentGpsReqData = testMap.get(userid.toString());    		
    		if (appDmanCurrentGpsReqData == null) {
				continue;
			}
			returnList.add(appDmanCurrentGpsReqData);
		}    	   	
        return returnList;
        
    }       
    
    /**
     *@desc 查询订单所在区域的所有取派员信息
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @ResponseBody
    @RequestMapping(value="/findDmanByProvidAndCityid", produces = "application/json;charset=UTF-8")
    public List<PageData> findDmanByProvidAndCityid() throws Exception{
    	// 查询订单所在区域
    	PageData pd = getPageData();
    	Integer orderid =Integer.parseInt((String) pd.get("orderid"));
    	OrderAddress orderAddress = orderAddressService.findByOrderid(orderid);
    	// 根据省市信息查询出在这个区域取派员
    	String provid = orderAddress.getSrcprovid();
    	String cityid = orderAddress.getSrccityid();
    	return userDeliveryManService.findDmanByProvidAndCityid(provid, cityid);
    }
    
    
    /**
     *@desc 修改取派员
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @ResponseBody
    @RequestMapping(value="/updateDman", produces = "application/json;charset=UTF-8")
    public void updateDman(HttpServletResponse response) throws Exception{
    	PageData pd = this.getPageData();
    	Integer orderid = Integer.parseInt((String) pd.get("orderid"));
    	Integer orderroleid = Integer.parseInt((String) pd.get("orderroleid"));
    	Integer newdmanuserid = Integer.parseInt((String) pd.get("newdmanuserid"));
    	Integer olddmanuserid = Integer.parseInt((String) pd.get("olddmanuserid"));
    	// 更改动作角色取派员
    	orderInfoService.updateChangeDman(orderid, orderroleid,newdmanuserid, olddmanuserid);
    	
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("SUCCESS:更新取派员成功");
    }
    
    /**
     * 修改取派员出勤
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/updateAttendance")
    public void updateAttendance(HttpServletResponse response,String udmId,String attendance) throws Exception{
    	Integer id = Integer.parseInt(udmId);
    	// 更改动作角色取派员
    	orderInfoService.updateAttendance(id, attendance);
    	
    	// 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
    	response.setHeader("Content-type", "text/html;charset=UTF-8");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().print("SUCCESS:取派员更新成功");
    }
    
    
    
}
