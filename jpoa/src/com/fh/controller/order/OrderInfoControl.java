
package com.fh.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant_enum.*;
import com.fh.controller.base.BaseController;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.entity.Page;
import com.fh.entity.Point;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderDetailsResData;
import com.fh.entity.order.*;
import com.fh.entity.system.User;
import com.fh.rule.Exception.RuleException;
import com.fh.service.area.AreaProvService;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.base.AirportInfoConfigService;
import com.fh.service.base.BaseService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.delivery.UserPickerInfoService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.OrderBaggageService;
import com.fh.service.order.OrderNotesInfoService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderInsureInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author zhangjj
 * 订单及周边信息处理
 */
@Controller
@RequestMapping(value = "orderinfo")
public class OrderInfoControl extends BaseController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderMainService orderMainService;
    @Autowired
    private UserPickerInfoService userPickerInfoService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private TransitCenterService transitCenterService;
    @Autowired 
    private ServiceCenterService serviceCenterService;
    @Autowired
    private UserDeliveryManService userDeliveryManService;
    @Autowired
    private AirportInfoConfigService airportInfoConfigService;
    @Autowired
    private AreaProvService areaProvService;
    @Autowired
    private BaiDuMapBO baiDuMapBO;
    @Autowired
    private AutoAllotService autoAllotService;
    @Autowired
    private OrderNotesInfoService orderNotesInfoService;
    @Autowired
    private OrderBaggageService orderBaggageService;
	@Autowired
	private OrderInsureInfoService orderInsureInfoService;
	@Autowired
	private OrderAddressService orderAddressService;
    
    /**
     * 
     * @Title: listOrderInfo
     * @Description: 查询订单列表
     * @param page
     * @return
     */
    @RequestMapping(value = "listOrderInfo")
    public ModelAndView listOrderInfo(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        List<PageData> orderInfoList = orderInfoService.orderMainlistPage(page);

        if (CollectionUtils.isNotEmpty(orderInfoList)) {
            mv.addObject("orderInfoList", orderInfoList);
        }

        mv.addObject("pd", pd);
        mv.setViewName("jpoa/order/ordermain/jsp/ordermain_list");
        return mv;
    }

    /**
     * @desc 后台明细页面
     * @auther zhangjj
     * @date 2018年8月24日
     */
    @RequestMapping({ "listOrderDetail" } )
    public ModelAndView listOrderDetail() throws Exception {
        PageData pd = this.getPageData();
        
        //组装参数
        AppOrderDetailsReqData reqDetails = new AppOrderDetailsReqData();
        reqDetails.setOrderid(Integer.parseInt((String)pd.get("id")));
        List<String> queryDetailsType = new ArrayList<String>();
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue());// 客户信息
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());// 地址
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());// 行李qr码
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERSENDERRECEIVER.getValue());// 寄件收件
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue());// 备注
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDER_PRICE_DETAIL.getValue());// 订单价格详情
        queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ACTION_DETAILS.getValue());// 动作类型详情
        reqDetails.setQueryDetailsType(queryDetailsType);
        // 查询订单详情
        AppOrderDetailsResData resDetails = orderInfoService.findAppAppOrderDetails(reqDetails);
       
        // mv 返回
        ModelAndView mv = new ModelAndView();
        // 订单上传图片特殊处理
        List<OrderBaggage> orderBaggageList = resDetails.getOrderBaggageList();
        if(CollectionUtils.isNotEmpty(orderBaggageList)) {
        	List<Map<String, Object>> lugQRAndImginfo = new ArrayList<Map<String, Object>>();
        	for (OrderBaggage luginfo : orderBaggageList) {
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("QR", luginfo.getBaggageid());
        		if(StringUtils.isNotBlank(luginfo.getImgurl())) {
        			Map<String, Object> parseObject = JSONObject.parseObject(luginfo.getImgurl(), Map.class);
    	
			    	Iterator<String> iterator = parseObject.keySet().iterator();
			    	while (iterator.hasNext()) {
			    		String key = iterator.next();
			    		
			    		if(IMGURL_BUSINESS_TYPE.COOLECT.getValue().equals(key) || IMGURL_BUSINESS_TYPE.RELEASE.getValue().equals(key)) {
			    			String value = parseObject.get(key).toString();
				    		map.put(key, JSONArray.parseArray(value, String.class));
			    		}
					}
        		}
        		
        		lugQRAndImginfo.add(map);
			}
        	 mv.addObject("lugQRAndImginfo", lugQRAndImginfo);
        }
        
        mv.addObject("resDetails", resDetails);
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/order/ordermain/jsp/ordermain_detail");
        return mv;
    }
    
    /**
     * @desc json 转 map
     * @auther zhangjj
     * @date 2018年8月23日
     */
    private Map<String, List<String>> jsonToMap(String json){
    	Map<String,List<String>> map = new HashMap<String,	List<String>>();
    	
    	Map<String, Object> parseObject = JSONObject.parseObject(json, Map.class);
    	
    	Iterator<String> iterator = parseObject.keySet().iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next();
    		String value = parseObject.get(key).toString();
    		map.put(key, JSONArray.parseArray(value, String.class));
		}
    	return map;
    }

    /**
     * @desc: 分配快递员页面
     * @author tangqm
     */
    @RequestMapping(value = "listOrderUserDelivery", produces = "application/json;charset=UTF-8")
    public ModelAndView listOrderUserDelivery(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        pd.put("orderid", pd.get("id"));
        mv.setViewName("jpoa/order/ordermain/jsp/waitallocmap");
        mv.addObject("pd", pd);
        return mv;
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
         mv.setViewName("jpoa/order/ordermain/jsp/waitallotedit");
        return mv;
	}
	
	/**
	 * @desc 待人工分配订单列表
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/findOrderAllocinfo")
	public List<PageData> fillWaitmanualAlloc() throws Exception {
		List<PageData> pd = orderInfoService.findOrderAllocinfo(this.getPageData());
		return pd;
	}
    
    /**
     * @desc: 进行中、已完成、未开始订单动作
     * @author 
     */
    @ResponseBody
    @RequestMapping(value = "listOrderRole", produces = "application/json;charset=UTF-8")
    public List<PageData> listOrderRole() throws Exception {
    	PageData pd = this.getPageData();
        List<PageData> roleTypeList = orderInfoService.findRoleType(pd);
        return roleTypeList;
    }

    /**
     * 
     * @Title: listOrderUserPicker
     * @Description: 分配取件员
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value="listOrderUserPicker", produces = "application/json;charset=UTF-8")
    public ModelAndView listOrderUserPicker(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jpoa/order/ordermain/jsp/orderpicker_add");
        PageData pd = getPageData();
        pd.put("roletype", APPUSER_TYPE.AIRPORT_PICKER.getValue());
        page.setPd(pd);
        List<PageData> userPickerList = userPickerInfoService.findUserPickerlistPage(page);
        List<PageData> orderUserDeliveryList = orderInfoService.orderUsereliveryList(page);
        if (CollectionUtils.isNotEmpty(userPickerList)) {
            mv.addObject("userPickerList", userPickerList);
        }
        if (CollectionUtils.isNotEmpty(orderUserDeliveryList)) {
            mv.addObject("orderUserDelivery", orderUserDeliveryList);
        }
        page.setPd(pd);
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 
     * @Title: outExcel
     * @Description: 导出excel
     * @return
     */
    @RequestMapping(value = "outExcel")
    public ModelAndView outExcel(Page page) throws Exception {
        PageData pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("用户名");
        titles.add("手机号码");
        titles.add("订单号");
        titles.add("合作伙伴订单号");
        titles.add("寄件人姓名");
        titles.add("寄件人电话");
        titles.add("状态");
        titles.add("金额");
        titles.add("优惠券");
        titles.add("实收");
        titles.add("订单来源");
        titles.add("城市");
        titles.add("地址");
        titles.add("类型");
        titles.add("生成日期");
        dataMap.put("titles", titles);
        List<PageData> varOList = orderInfoService.orderMainlistPage(page);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            String totalmoney = varOList.get(i).get("totalmoney")+"";
            String cutmoney = varOList.get(i).get("cutmoney")+"";
            String actualmoney = varOList.get(i).get("actualmoney")+"";
            String addtime = varOList.get(i).get("addtime")+"";
            vpd.put("var1", varOList.get(i).getString("cusname"));
            vpd.put("var2", varOList.get(i).getString("cusmobile"));
            vpd.put("var3", varOList.get(i).getString("orderno"));
            vpd.put("var4", varOList.get(i).getString("porderno"));
            vpd.put("var5", varOList.get(i).getString("sendername"));
            vpd.put("var6", varOList.get(i).getString("senderphone"));
            vpd.put("var7", varOList.get(i).getString("status"));
            vpd.put("var8", totalmoney);
            vpd.put("var9", cutmoney);
            vpd.put("var10", actualmoney);
            vpd.put("var11", varOList.get(i).getString("channel"));
            vpd.put("var12", varOList.get(i).getString("srccityname"));
            vpd.put("var13", varOList.get(i).getString("destaddress"));
            vpd.put("var14", varOList.get(i).getString("typedesc"));
            vpd.put("var15", addtime);
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        ModelAndView mv = new ModelAndView(erv, dataMap);
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * @DESC 订单分配给角色
     * @author zhangjj
     * @history 2018年03月04日
     */
    @RequestMapping(value = "allotUserDelivery", produces = "application/json;charset=UTF-8")
    public void allotUserDelivery(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        String isshow = "Y";//用于任务列表是否展示，临时性方案，故不用枚举，等更好方案将删除此字段
        OrderRole orderRole = new OrderRole();
        orderRole.setOrderid(Integer.parseInt((String)pd.get("orderid")));
        orderRole.setRoleid(Integer.parseInt((String)pd.get("userid")));
        orderRole.setIsfinish(IS_FINISH.UNFINISHED.getValue());
        orderRole.setRoletype((String)pd.get("roletype"));
        orderRole.setDesttype(((String)pd.get("desttype")));
        orderRole.setDestaddress((String)pd.get("destaddress"));
        orderRole.setDestaddrname((String)pd.get("destaddrname"));
        orderRole.setDestaddrdesc((String)pd.get("destaddrdesc"));
        String arrivedstr = (String) pd.get("arrivedtime");
        Date arrivedtime = null;
        if(StringUtils.isNotBlank(arrivedstr)){
        	arrivedtime = DateUtil.fomatDate(arrivedstr, "yyyy-MM-dd HH:mm");
        	orderRole.setArrivedtime(DateUtil.format(arrivedtime, "yyyy-MM-dd HH:mm"));
        }
        
        if(pd.get("roletype").toString().indexOf("SEND")>0){
        	isshow="N";
        }
        orderRole.setIsshow(isshow);
        // 默认查询上个节点目的地，填充本次查询的出发地
        PageData lastNode = orderInfoService.findDescLimit1ByOrderid(Integer.parseInt((String)pd.get("orderid")));
        // 有上个节点就加入
        if(lastNode != null) {
        	orderRole.setSrctype(lastNode.getString("desttype"));
        	orderRole.setSrcaddress(lastNode.getString("destaddress"));
        	orderRole.setSrcaddrname(lastNode.getString("destaddrname"));
        	orderRole.setSrcaddrdesc(lastNode.getString("destaddrdesc"));
        }
        
        
        String returnInfo = orderInfoService.cbOrderAllotDman(orderRole);
        
        // 用UTF-8转码，而不是用默认的ISO8859 ,避免中文乱码
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(returnInfo);
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
    
    /**
     * 
     * @Title: listOrderInfo
     * @Description: 跳到新增订单页面
     * @param page
     * @return
     */
    @RequestMapping(value = "saveOrderMainPage")
    public ModelAndView saveOrderMainPage(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        // 省份信息
        List<PageData> provList = areaProvService.findAll();
        if (null != provList && provList.size() > 0) {
            mv.addObject("provList", provList);
        }
        mv.addObject("pd", pd);
        mv.setViewName("jpoa/order/ordermain/jsp/order_add");
        return mv;
    }
    
    /**  
     * 保存订单
     * @param request
     * @param response
     * @throws Exception
     */
 /*   @RequestMapping(value = "saveOrderMain")
    public void  saveOrderMain(HttpServletRequest request, HttpServletResponse response,OrderMainSave orderMainSave) throws Exception {
    	IdWorker worker = IdWorker.getInstance();
		String orderno = "JPWX" + worker.getDefaultFormatId();
		int orderid = orderMainService.saveOrderBackstage(orderMainSave, orderno);
		PageData pd = new PageData();
		pd.put("orderid", orderid);
		pd.put("orderno", orderno);
//		autoAllot(pd);自动分配注释
		//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("新增成功");
    }*/
    
    /**
     * @desc  自动分配
     * @auther tangqm
     * @date 2018年6月2日 21点16分
     */
	public String autoAllot(PageData pd) {
		String code = ALLOT_LOG_CODE.AUTO_ALLOT_SUCCESS.getValue();
		String remark = "自动分配成功";
    	try {
    		autoAllotService.cbAutoAllot(pd);
		} catch (RuleException e) {
			logger.error(e);
			code = e.getCode();
			remark = e.getMessage();
		} catch (Exception e) {
			logger.error(e);
			code = ALLOT_LOG_CODE.FAIL.getValue();
			remark = "系统异常";
		}finally{
			pd.put("remark", remark);
			autoAllotService.insertAutoAllotLog((Integer)pd.get("orderid"),code, remark);
			if(code.equals(ALLOT_LOG_CODE.AGAINALLOT.getValue())){					
				WeixinUtil.autoFail(pd);
	    	}
		}
    	return code;
    }
    
    /**  
     * 取消订单
     * @throws Exception
     */
    @RequestMapping(value = "/cancelOrder")
    public void  cancelOrder(HttpServletResponse response) throws Exception {
    	Integer orderid = Integer.parseInt((String) getPageData().get("id"));
    	String str = (String) getPageData().get("refundmoney");
    	Float refundmoney =Float.valueOf(str) ;
    	String refundreason =  (String) getPageData().get("refundreason");
    	User user = (User) SecurityUtils.getSubject().getSession().getAttribute( Const.SESSION_USER );
    	String msg = orderInfoService.cbCancelOrder(orderid, refundmoney,refundreason,user.getNAME());
		//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(msg);
    }
    
    /**  
     * 更新订单状态
     */
    @RequestMapping(value = "/updateStatus")
    public void  updateStatus(HttpServletResponse response) throws Exception {
    	
    	Integer orderid = Integer.parseInt((String) getPageData().get("id"));
    	String status = (String) getPageData().get("status");
    	
    	// 更改订单状态
    	orderInfoService.updateStatusByid(orderid, status);
    	
		//用UTF-8转码，而不是用默认的ISO8859  ,避免中文乱码
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("SUCCESS:更新订单状态成功");
    }
    
    /**
     * @desc 更新订单寄送人信息
     * @auther tangqm
     * @date 2018年6月2日 21点16分
     */
    @RequestMapping(value = "/updateSenderReceiver")
    @ResponseBody
    public Map<String, Object> updateSenderReceiver() {
        // 更改订单状态
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            orderInfoService.updateSenderReceiver(this.getPageData());
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);        
            res.put("resMag", e.getMessage());
        }
        return res;
    }
    
    /**
     * @desc 添加订单备注信息
     * @auther tangqm
     * @date 2018年6月2日 21点16分
     */
    @RequestMapping(value = "/saveOrderNotes")
    @ResponseBody
    public Map<String, Object> saveOrderNotes(OrderNotesInfo orderNotesInfo) {
        // 更改订单状态
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            orderNotesInfoService.insert(orderNotesInfo);
            res.put("success", true);
            res.put("notesid", orderNotesInfo.getId());
            res.put("addusername", orderNotesInfo.getAddusername());
        } catch (Exception e) {
            res.put("success", false);        
            res.put("resMag", e.getMessage());
        }
        return res;
    }
    
    /**
     * @desc 更改订单备注信息
     * @auther zhangjj
     * @date 2018年6月2日 21点16分
     */
    @RequestMapping(value = "/updateOrderNotes")
    @ResponseBody
    public Map<String, Object> updateOrderNotes(OrderNotesInfo orderNotesInfo) {
        // 更改订单状态
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            orderNotesInfoService.updateNotes(orderNotesInfo);
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);        
            res.put("resMag", e.getMessage());
        }
        return res;
    }
    
    /**
     * @desc 更新订单地址
     * @auther zhangjj
     * @date 2018年6月2日 21点16分
     */
    @RequestMapping(value = "/updateOrderAddr")
    @ResponseBody
    public Map<String, Object> updateOrderAddr(String istrans, OrderAddress orderAddress) {
        // 更改订单状态
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
         // 如果是高德地图的话则转换使用
            if("1".equals(istrans)) {
                if(StringUtils.isNotBlank(orderAddress.getSrcgps())) {
                    String gpstemp = orderAddress.getSrcgps();
                    JSONObject gaode_json = JSONObject.parseObject(gpstemp);
                    Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
                    Point pointtemp = MapUtil.GD_TRANS_BD(point);
                    orderAddress.setSrcgps(pointtemp.toEntity());
                } else {
                    String gpstemp = orderAddress.getDestgps();
                    JSONObject gaode_json = JSONObject.parseObject(gpstemp);
                    Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
                    Point pointtemp = MapUtil.GD_TRANS_BD(point);
                    orderAddress.setDestgps(pointtemp.toEntity());
                }
            }
            
            orderAddressService.updateOrderAddr(orderAddress);
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);        
            res.put("resMag", e.getMessage());
        }
        return res;
    }
    
    /**
     *@desc 修改地址弹窗
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @RequestMapping(value="/modifyOrderAddr")
    public ModelAndView modifydmanalloc() throws Exception{
         ModelAndView mv = new ModelAndView();
         mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/order/ordermain/jsp/modifyorderaddr");
         return mv;
    }
    
    /**
     *@desc 修改最迟到达时间
     *@auther zhangjj
     *@history 2018年4月9日
     */
    @RequestMapping(value="/modifyTaskTimeOrSendTime")
    public ModelAndView modifyaddivedtime() throws Exception{
         ModelAndView mv = new ModelAndView();
         mv.addObject("pd", this.getPageData());
         mv.setViewName("jpoa/order/ordermain/jsp/modifytime");
         return mv;
    }
    
    /**
     * @desc 订单寄收时间更新
     * @auther zhangjj
     * @date 2018年6月2日 21点16分
     */
    @RequestMapping(value = "/updateTakeTimeOrSendTime")
    @ResponseBody
    public Map<String, Object> updateTakeTimeOrSendTime(OrderMainSpec orderMainSpec) {
        // 更改订单状态
        HashMap<String, Object> res = new HashMap<String, Object>();
        try {
            orderInfoService.updateTaketimeOrSendTime(orderMainSpec);;
            res.put("success", true);
        } catch (Exception e) {
            res.put("success", false);        
            res.put("resMag", e.getMessage());
        }
        return res;
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
         mv.setViewName("jpoa/order/ordermain/jsp/addrselect");
        return mv;
    }
}
