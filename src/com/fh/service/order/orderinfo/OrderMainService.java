package com.fh.service.order.orderinfo;

import com.fh.common.constant.ConfigCenterKeys;
import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.*;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Point;
import com.fh.entity.app.dm.AppOrderMain;
import com.fh.entity.app.dm.AppOrders;
import com.fh.entity.app.order.AppSaveOrderInfoReqData;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanResData;
import com.fh.entity.app.transitcenter.TransitOrderReqData;
import com.fh.entity.app.transitcenter.TransitOrderResData;
import com.fh.entity.customer.CusCouponInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.delivery.AppUser;
import com.fh.entity.delivery.TaskMainDriver;
import com.fh.entity.delivery.UserDeliveryMan;
import com.fh.entity.h5.H5OrderInfo;
import com.fh.entity.h5.H5OrderInfoBean;
import com.fh.entity.h5.H5OrderMain;
import com.fh.entity.order.*;
import com.fh.service.SmsSendService;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.base.BaseService;
import com.fh.service.customer.CusCouponInfoService;
import com.fh.service.order.OrderBaggageService;
import com.fh.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对订单主表的查询操作
 * 2018年3月3日17:02:16
 * @author chenyushi
 *
 */
@Service
public class OrderMainService
{
	@Autowired
    private DaoSupport dao;
	@Autowired
	private BaseService baseService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private CusCouponInfoService cusCouponInfoService;
	@Autowired
	private CouponInfoService couponInfoService;
	@Autowired
    private OrderBaggageService orderBaggageService;
	@Autowired
	private OrderInfoService orderInfoService;

	
	
	public void updateCancle( Integer id ) throws Exception{
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", ORDER_STATUS.CANCELLED.getValue());
		dao.update("OrderMainMapper.updateCencleStatus", pd);
	}
	
	/**
	 * 获取订单对象
	 * 木桐
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public PageData getById( Integer id ) throws Exception {
		return (PageData) dao.findForObject("OrderMainMapper.getById", id);
	}
	
	/**
	 * @desc 生成app的订单
	 * @auther zhangjj
	 * @date 2018年4月18日
	 */
	public String saveH5Order( H5OrderMain order)throws Exception {
		// 保存订单信息=======================================================
		OrderMainSpec orderMain = new OrderMainSpec();
		BeanUtils.copyProperties(order.getOrderMain(), orderMain);
		// 订单id生成
		IdWorker worker = IdWorker.getInstance();
		String orderno = "JPWX" + worker.getDefaultFormatId();
		orderMain.setOrderno( orderno );
		//2018年12月25日 待支付调整为待收取
        orderMain.setStatus( ORDER_STATUS.WAITPICK.getValue());
		// 订单类型
		orderMain.setType(order.getOrderAddr().getSrcaddrtype() + "TO" + order.getOrderAddr().getDestaddrtype());
		// 提取码
		String fetchcode = (String) dao.findForObject("FetchcodeStoreMapper.findFetchcode", null);
		orderMain.setFetchcode(fetchcode);
		dao.save( "OrderMainMapper.insert", orderMain);
		int orderId = orderMain.getId();
		// 提取码失效处理
		dao.update("FetchcodeStoreMapper.updateIsvalid_INVALID", fetchcode);
		// ================================================================
		
		// 保险信息保存=======================================================
		OrderInsureInfo orderInsure = new OrderInsureInfo();
		orderInsure.setOrderid( orderId );
		orderInsure.setInsurecode(order.getOrderInsure().getInsureCode());
		dao.save( "OrderInsureInfoMapper.insert", orderInsure );
		// ================================================================
		
		// 地址信息==========================================================
		OrderAddress orderAddr = new OrderAddress();
		BeanUtils.copyProperties( order.getOrderAddr(), orderAddr );
		orderAddr.setOrderid( orderId );
		// 特殊处理 高德坐标转换成百度坐标系
		if(order.getOrderAddr().getSrcistransitgps()) {
			// 搞得地图坐标获取
			JSONObject gaode_json = JSONObject.fromObject(order.getOrderAddr().getSrcgps());
	    	Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
	    	// 高德转换百度
	    	orderAddr.setSrcgps(MapUtil.GD_TRANS_BD(point).toEntity());
		}
		if(order.getOrderAddr().getDestistransitgps()) {
			// 搞得地图坐标获取
			JSONObject gaode_json = JSONObject.fromObject(order.getOrderAddr().getDestgps());
	    	Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
	    	// 高德转换百度
	    	orderAddr.setDestgps(MapUtil.GD_TRANS_BD(point).toEntity());
		}
		dao.save( "orderAddressMapper.insert", orderAddr );
		// ================================================================
		
		// 寄件人收件人信息====================================================
		OrderSenderReceiver sr = new OrderSenderReceiver();
		BeanUtils.copyProperties( order.getSenderReceiver(), sr );
		sr.setOrderid( orderId );
		dao.save( "OrderSenderReceiverMapper.insert", sr );
		// ================================================================
		
		// 备注=============================================================
		if(StringUtils.isNotBlank(order.getH5OrderNotesInfo().getNotes())) {
			OrderNotesInfo orderNotesInfo = new OrderNotesInfo();
			orderNotesInfo.setOrderid(orderId);
			BeanUtils.copyProperties( order.getH5OrderNotesInfo(), orderNotesInfo );
			dao.save( "orderNotesInfoMapper.insert",  orderNotesInfo);
		}
		// ================================================================
		
		// 航班信息=============================================================
		// ================================================================
		
		// 优惠卷信息=============================================================
		if (order.getH5Counponinfo() != null && StringUtils.isNotBlank(order.getH5Counponinfo().getCouponcode())) {
			CusCouponInfo couponInfo = new CusCouponInfo();
			couponInfo.setCouponcode(order.getH5Counponinfo().getCouponcode());
			couponInfo.setCusid(Integer.valueOf(order.getOrderMain().getCusid()));
			couponInfo.setOrderid(orderId);
			dao.save( "CusCouponInfoMapper.insert",  couponInfo);
		}
		// ================================================================

		// 2018年12月25日 增加支付状态 start
		OrderPayInfo orderPayInfo = new OrderPayInfo();
		orderPayInfo.setOrderid(orderId);
		orderPayInfo.setStatus(ORDER_PAY_STUTAS.WAITPAY.getValue());
		orderPayInfo.setType(ORDER_PAY_TYPE.WEIXIN.getValue());
		orderPayInfo.setMoney(orderMain.getActualmoney());
		dao.save( "OrderPayInfoMapper.insertOrderPayInfo",  orderPayInfo);
		// end



		return orderno;
	}
	
	
/**
 * @desc app渠道的订单
 * @auther zhangjj
 * @date 2018年4月18日
 */
public String saveAppOrder(AppSaveOrderInfoReqData saveOrderInfoReqBean)throws Exception {

	// 保存客户信息=======================================================
	CusInfo cusInfo = null;
	synchronized (saveOrderInfoReqBean.getCusInfo().getMobile()) {
		// 客户信息只能是一条
		cusInfo = (CusInfo) dao.findForObject("CusInfoMapper.findByMobile", saveOrderInfoReqBean.getCusInfo().getMobile());

		if (cusInfo == null) {
			cusInfo = saveOrderInfoReqBean.getCusInfo();
			dao.save("CusInfoMapper.insert", cusInfo);
		}
	}


	// =======================================================

	// 保存订单信息=======================================================
	OrderMainSpec orderMain = saveOrderInfoReqBean.getOrderMainSpec();
	orderMain.setCusid(cusInfo.getId() + "");
	// 订单id生成
	IdWorker worker = IdWorker.getInstance();
	String orderno = "JPWX" + worker.getDefaultFormatId();
	orderMain.setOrderno( orderno );
	// 订单类型
	String orderStatus = ORDER_STATUS.TAKEGOOGSOVER.getValue();
	List<OrderBaggageReqData> orderBaggageReqDataList1 = saveOrderInfoReqBean.getOrderBaggageReqDataList();
	if(CollectionUtils.isNotEmpty(orderBaggageReqDataList1)) {
		for (OrderBaggageReqData orderBaggageReqData : orderBaggageReqDataList1) {
			if(StringUtils.isBlank(orderBaggageReqData.getBaggageid())||CollectionUtils.isEmpty(orderBaggageReqData.getImgurlList())){
				orderStatus=ORDER_STATUS.WAITPICK.getValue();
			}
		}
//		orderStatus = StringUtils.isNotBlank(orderBaggageReqDataList1.get(0).getBaggageid())? ORDER_STATUS.TAKEGOOGSOVER.getValue() : ORDER_STATUS.WAITPICK.getValue();
	}else{
		orderStatus=ORDER_STATUS.WAITPICK.getValue();
	}
	orderMain.setStatus(orderStatus);
	// 订单类型
	orderMain.setType(saveOrderInfoReqBean.getOrderAddress().getSrcaddrtype() + "TO" + saveOrderInfoReqBean.getOrderAddress().getDestaddrtype());
	dao.save( "OrderMainMapper.insert", orderMain);
	Integer orderId = orderMain.getId();
	// ================================================================

	// 保险信息保存=======================================================
	OrderInsureInfo orderInsureInfo = saveOrderInfoReqBean.getOrderInsureInfo();
	orderInsureInfo.setOrderid(orderId);
	dao.save( "OrderInsureInfoMapper.insert", saveOrderInfoReqBean.getOrderInsureInfo());
	// ================================================================

	// 地址信息==========================================================
	OrderAddress orderAddress = saveOrderInfoReqBean.getOrderAddress();
	orderAddress.setOrderid( orderId );
	dao.save( "orderAddressMapper.insert", orderAddress);
	// ================================================================

	// 寄件人收件人信息====================================================
	OrderSenderReceiver sr = saveOrderInfoReqBean.getOrderSenderReceiver();
	sr.setOrderid( orderId );
	dao.save( "OrderSenderReceiverMapper.insert", sr );
	// ================================================================

	// 备注=============================================================
	OrderNotesInfo orderNotesInfo = saveOrderInfoReqBean.getOrderNotesInfo();
	if(StringUtils.isNotBlank(orderNotesInfo.getNotes())) {
		orderNotesInfo.setOrderid(orderId);
		dao.save( "orderNotesInfoMapper.insert",  orderNotesInfo);
	}
	// ================================================================

	// 航班信息=============================================================
	OrderFlight orderFlight = saveOrderInfoReqBean.getOrderFlight();
	if(StringUtils.isNotBlank(orderFlight.getSendflightno()) || StringUtils.isNotBlank(orderFlight.getTakeflightno())) {
		orderFlight.setOrderid(orderId);
		dao.save( "OrderFlightMapper.insert",  orderFlight);
	}
	// =============================================================

	// 支付信息保存
    OrderPayInfo orderPayInfo = saveOrderInfoReqBean.getOrderPayInfo();
    orderPayInfo.setOrderid(orderId);
	orderPayInfo.setMoney(orderMain.getActualmoney());
	dao.save( "OrderPayInfoMapper.insertOrderPayInfo", orderPayInfo);

	// qr + 图片
    List<OrderBaggageReqData> orderBaggageReqDataList = orderBaggageReqDataList1;

    if(CollectionUtils.isNotEmpty(orderBaggageReqDataList)) {
        // 校验是否有重复qr码
        List<String> checkBagidRep = new ArrayList<String>();
        for (OrderBaggageReqData orderBaggageReqData : orderBaggageReqDataList) {
            checkBagidRep.add(orderBaggageReqData.getBaggageid());
        }
        ExceptionUtil.isTrue(checkBagidRep.size() == new HashSet<Object>(checkBagidRep).size(), "qr码不能重复");

        // 图片上传成功
        for (OrderBaggageReqData orderBaggageReqData : orderBaggageReqDataList) {
        	// QR 为空， 不做处理
			if(StringUtils.isBlank(orderBaggageReqData.getBaggageid())) {
				continue;
			}
			String baggageid = orderBaggageReqData.getBaggageid();
			if(orderBaggageReqData.getBaggageid().indexOf(ConfigCenterKeys.QR_PREFIX)<0){
				baggageid = ConfigCenterKeys.QR_PREFIX + orderBaggageReqData.getBaggageid();
			}
            orderBaggageReqData.setBaggageid(baggageid);
			orderBaggageReqData.setOrderid(orderId);
			orderBaggageService.insertQRAndImgUrl(orderBaggageReqData);
        }
    }

    // 如果是已取件则需要在orderrole 增加一条记录
    if(ORDER_STATUS.TAKEGOOGSOVER.getValue().equals(orderStatus)) {
		OrderRole orderRole = saveOrderInfoReqBean.getOrderRole();
		orderRole.setOrderid(orderId);
		orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASKED.getValue());
		orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
		orderRole.setActionfinishtime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
		dao.save("OrderRoleMapper.insert", orderRole);
	}


    return orderno;
}


    /**
	 * 用户确定取件
	 * 陈玉石 zhangjj tangqm
	 * 2018年3月3日19:59:56
	 * 1、修改订单状态为已取件状态
	 * 2、增加OrderRole记录
	 * @param orderId
	 * @param userId
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public void cbConfirmTakenOrders( Integer orderid, Integer userId) throws Exception {
		// 校验订单状态
		PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById" , orderid);
		ExceptionUtil.checkNotNull(ordermain, "根据订单id:"+ orderid +"未查询出订单信息");
	    ExceptionUtil.isTrue(ORDER_STATUS.WAITPICK.getValue().equals(ordermain.getString("status")), "订单状态:" + ordermain.getString("status") + ",不允许确认取件");
		// 校验QR码是否上传
		List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao.findForList("OrderBaggageMapper.findByOrderid" , orderid);
		ExceptionUtil.checkNotCollectNotEmpty(orderBaggageList, "QR码未关联");
	    ExceptionUtil.checkBoolean(orderBaggageList.size() != (Integer) ordermain.get("num"), "QR码未关联");
	    for (OrderBaggage obj : orderBaggageList) {
			ExceptionUtil.checkNotEmpty(obj.getBaggageid(), "QR码未关联");
			ExceptionUtil.checkNotEmpty(obj.getImgurl(), "行李图片未上传");
		}
		
	    // 订单状态改成确认取件
		PageData pd = new PageData();
		pd.put( "id" , orderid );
		pd.put( "status" , ORDER_STATUS.TAKEGOOGSOVER.getValue());
		dao.update( "OrderMainMapper.updateTakenStatus" , pd );
		
		// 取派员角色动作完成更新
 		OrderRole orderRoleReq = new OrderRole();
        orderRoleReq.setOrderid(orderid);
        orderRoleReq.setRoleid(userId);
        orderRoleReq.setRoletype(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
        orderRoleReq.setIsfinish(IS_FINISH.ONGOING.getValue());
        // check 去酒店取件只有进行中才能取件成功
		PageData checkaction = (PageData) dao.findForObject("OrderRoleMapper.checkactive", orderRoleReq);
		ExceptionUtil.checkNotNull(checkaction, "请确认发车！！！");
		
        dao.update("OrderRoleMapper.updateFINISHED", orderRoleReq);
        
        //切换任务列表显示
        dao.update("OrderRoleMapper.updateIsShow", orderRoleReq);
        
        UserDeliveryMan userDeliveryMan  = (UserDeliveryMan) dao.findForObject("UserDeliveryManMapper.findDmanByuserid", userId);    
        PageData openid =   (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", pd);    
        openid.put("udmname", userDeliveryMan.getName());
        openid.put("regionname", userDeliveryMan.getRegionname());
        WeixinUtil.orderStatus(openid,"已取件");
	}
	
	/**
	 * 用户确定派件
	 * 陈玉石
	 * 2018年3月3日19:59:56
	 * 1、修改订单状态为已派件状态
	 * 2、增加OrderRole记录
	 * 2018年3月3日19:59:52
	 */
	public void confirmSentOrders( Integer orderid, Integer roleid)throws Exception
	{
// start tangqm 由后台人员操作自动往里面加一条记录
//		OrderRole role = new OrderRole();
//		role.setOrderid( orderId );
//		role.setRoleid( userId );
//		role.setRoletype( ROLE_TYPE.ROLE_SENDER.getValue() );
//		dao.save( "OrderRoleMapper.insert" , role );
		
		PageData pd = new PageData();//updateTakeStatus
		pd.put( "status" , ORDER_STATUS.DELIVERYOVER.getValue() );
		pd.put( "id" , orderid );
		dao.update( "OrderMainMapper.updateTakenStatus" , pd );
		
		 // 取派员角色动作完成更新
        OrderRole orderRole = new OrderRole();
        orderRole.setRoleid(roleid);
        orderRole.setOrderid(orderid);
        orderRole.setRoletype(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
        orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
        dao.update("OrderRoleMapper.updateIsfinish", orderRole);
        
        // 根据orderid查询订单地址信息
        OrderAddress orderAddress = (OrderAddress) dao.findForObject("orderAddressMapper.findByOrderid", orderid);
        // 抵达酒店节点添加
        orderRole = new OrderRole();
        orderRole.setOrderid(orderid);
        orderRole.setRoleid(roleid);
        orderRole.setRoletype(ROLE_TYPE.ROLE_ARRIVE_HOTEL.getValue());
        orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
        orderRole.setActionfinishtime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        orderRole.setDesttype(orderAddress.getDestaddrtype());
        orderRole.setDestaddrname(orderAddress.getDestlandmark());
        orderRole.setDestaddrdesc(orderAddress.getDestaddress());
        orderRole.setDestgps(orderAddress.getDestgps());
        dao.save("OrderRoleMapper.insert", orderRole);
        
        
      
        try {
			// 查询取派员的信息
			AppUser appUser = (AppUser) dao.findForObject("AppUserMapper.findUserInfoByid", roleid);

        	PageData ordermain =   (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", orderid);  
            ordermain.put("actionfinishtime", DateUtil.getTime());
        	// 公众号通知
            WeixinUtil.deliverySucc(ordermain);
            // 短信通知
			smsSendService.smsTemplateSend("{'dmanmobile':'"+ appUser.getMobile() +"','mobile':'"+ (String) ordermain.getString("mobile") +"','header': '"+ MsgOfTmpCode.SMS_HEADER +"', 'destlandmark':'"+ orderAddress.getDestlandmark() +"','smscode':'X020'}");
		}catch(Exception e) {
			LoggerUtil.warn("消息通知发送失败,消息编码：X020", e);
		}
	}
	/**
	 * 合并任务和订单
	 * 陈玉石
	 * 2018年3月3日19:25:21
	 * @param orderList
	 * @param taskList
	 * @return
	 */
	public AppOrders mergeTaskAndOrders( List<OrderMainSpec> orderList, List<TaskMainDriver> taskList )
	{
		AppOrders orders = new AppOrders();
		List<AppOrderMain> onGoingOrders = new ArrayList<AppOrderMain>();	//进行中订单
		List<AppOrderMain> gettingOrders = new ArrayList<AppOrderMain>();	//待取订单
		List<AppOrderMain> sendingOrders = new ArrayList<AppOrderMain>();	//待派订单
		List<AppOrderMain> finishedOrders = new ArrayList<AppOrderMain>();	//已完成订单
		if( orderList != null && orderList.size() > 0 )
		{
			for( OrderMainSpec order : orderList )
			{
				if( !StringUtils.isBlank( order.getStatus() ) )
				{
					if( order.getStatus().equalsIgnoreCase( ORDER_STATUS.TAKEGGOOSING.getValue() ))	//待取件订单
					{
						onGoingOrders.add( transOrder2AppOrder(order ) );
						gettingOrders.add( transOrder2AppOrder(order ) );
					}else if( order.getStatus().equalsIgnoreCase( ORDER_STATUS.ALLOTDELIVERY.getValue() )
							|| order.getStatus().equalsIgnoreCase( ORDER_STATUS.DELIVERYING.getValue() ) )	//派送中订单
					{
						sendingOrders.add( transOrder2AppOrder(order ) );
						onGoingOrders.add( transOrder2AppOrder(order ) );
					}else if( order.getStatus().equalsIgnoreCase( ORDER_STATUS.DELIVERYOVER.getValue() ) )	//已完成订单
					{
						finishedOrders.add( transOrder2AppOrder(order ) );
					}
				}
			}
		}
		if( taskList != null && taskList.size() > 0 )
		{
			for( TaskMainDriver task : taskList )
			{
				if( !StringUtils.isBlank( task.getStatus() ) )
				{
					if( task.getStatus().equalsIgnoreCase( TASKMAINDRIVER_STATUS.STARTED.getValue() )
							|| task.getStatus().equalsIgnoreCase( TASKMAINDRIVER_STATUS.ARRIVING.getValue() ) )	//待取件订单
					{
						onGoingOrders.add( transTask2AppOrder( task ) );
					}else if( task.getStatus().equalsIgnoreCase( TASKMAINDRIVER_STATUS.ARRIVED.getValue() ) )	//派送中订单
					{
						finishedOrders.add( transTask2AppOrder( task ) );
					}
				}
			}
		}
		orders.setFinishedOrders( finishedOrders );
		orders.setGettingOrders( gettingOrders );
		orders.setSendingOrders( sendingOrders );
		orders.setOnGoingOrders( onGoingOrders );
		return orders;
	}
	
	/**
	 * 转换订单主表到app任务表
	 * @param order
	 * @return
	 */
	public AppOrderMain transOrder2AppOrder( OrderMainSpec order )
	{
		AppOrderMain rt = new AppOrderMain();
		rt.setSrcAddr( order.getSrcaddress() );
		rt.setDestAddr( order.getDestaddress() );
		rt.setOrderid( order.getId() );
		rt.setOrderStatus( order.getStatus() );
		rt.setTimeStr( "" );			//订单的取派时间
		rt.setType( 1 );
		rt.setNum( order.getNum() );
		return rt;
	}
	
	/**
	 * 转换任务主表到app任务表
	 * @return
	 */
	public AppOrderMain transTask2AppOrder( TaskMainDriver task )
	{
		AppOrderMain rt = new AppOrderMain();
		rt.setSrcAddr( task.getSrcaddr() );
		rt.setDestAddr( task.getDestaddr() );
		rt.setOrderid( task.getId());
		rt.setOrderStatus( task.getStatus() );
		rt.setTimeStr( "" );			//订单的取派时间
		rt.setType( 2 );
		rt.setNum( 0 );
		return rt;
	}
	
	/**
	 * 根据userid获取近一个月内的订单
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public List<OrderMainSpec> getOrderListByUserId( Integer userId ) throws Exception
	{
		Date curDate = new Date();
		Date date = DateUtil.addDate( curDate, -30 );
		String dateStr = DateUtil.getDefaultDateStr( date );
		PageData pd = new PageData();
		pd.put( "roleid" , userId );
		pd.put( "timeParam" , dateStr );
		List<OrderMainSpec> rtList = (List<OrderMainSpec>)dao.findForList( "OrderRoleMapper.queryOrderListByUserId" , pd );
		return rtList;
	}
	
    /**
     * @DESC 根据QR码查询订单信息
     * @author zhangjj
     * @history 2018年03月04日
     */
    public List<AppUnloadScanResData> findAppOrderBaggageid(AppUnloadScanReqData appUnloadScanReqData) throws Exception {
    	List<AppUnloadScanResData> resList = (List<AppUnloadScanResData>) dao.findForList("OrderMainMapper.findAppOrderBaggageid", appUnloadScanReqData);
    	// 校验 订单状态为待卸车才可以通过
        ExceptionUtil.checkNotNull(resList, "查询结果为空！");
        return resList;
    }

    /**
     * @DESC 集散中心查询去往市内件
     * @author zhangjj
     * @history 2018年03月10日
     */
    @SuppressWarnings("unchecked")
	public List<TransitOrderResData> cbTransitFindOrderListCityScope(TransitOrderReqData transitOrderReqData) throws Exception  {
        PageData pd = new PageData();
        pd.put("isfinish", transitOrderReqData.getIsfinish());
        pd.put("srcaddress", transitOrderReqData.getSrcaddress());
        return (List<TransitOrderResData>) dao.findForList("OrderMainMapper.findOrderListByCityScope", pd);
    }
    
    /**
     * @DESC 集散中心查询订单信息
     * @author zhangjj
     * @history 2018年03月10日
     */
    @SuppressWarnings("unchecked")
	public List<TransitOrderResData> cbTransitFindOrderListAirPortScope(TransitOrderReqData transitOrderReqData) throws Exception  {
        PageData pd = new PageData();
        pd.put("srcaddress", transitOrderReqData.getSrcaddress());
        pd.put("isfinish", transitOrderReqData.getIsfinish());
        return (List<TransitOrderResData>) dao.findForList("OrderMainMapper.findOrderListByCounterCenterScope", pd);
    }
    
    public static void main(String[] args) {
    	        Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
               SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
                System.out.println("当前的时间：" + df.format(new Date()));
	}
    
    /**
     * @DESC 查询订单列表
     * @author sunqp
     * @history 2018年04月10日
     */
	@SuppressWarnings("unchecked")
	public List<H5OrderInfoBean> queryOrderListByCusId( Integer cusid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "cusid", cusid );
        pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
        return (List<H5OrderInfoBean>) dao.findForList("OrderMainMapper.queryOrderListByCusId", pd);
    }
	
	/**
	 * @DESC 查询未支付订单列表
	 * @author sunqp
	 * @history 2018年04月18日
	 */
	@SuppressWarnings("unchecked")
	public List<H5OrderInfoBean> queryWaitpayOrderListByCusId( Integer cusid ) throws Exception {
		PageData pd = new PageData();
		pd.put( "cusid", cusid );
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		return (List<H5OrderInfoBean>) dao.findForList("OrderMainMapper.queryWaitpayOrderListByCusId", pd);
	}
	
	/**
	 * @DESC 查询退款订单列表
	 * @author sunqp
	 * @history 2018年04月18日
	 */
	@SuppressWarnings("unchecked")
	public List<H5OrderInfoBean> queryRefundOrderListByCusId( Integer cusid ) throws Exception {
		PageData pd = new PageData();
		pd.put( "cusid", cusid );
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		return (List<H5OrderInfoBean>) dao.findForList("OrderMainMapper.queryRefundOrderListByCusId", pd);
	}
	
	/**
	 * @DESC 查询已完成订单列表
	 * @author sunqp
	 * @history 2018年04月18日
	 */
	@SuppressWarnings("unchecked")
	public List<H5OrderInfoBean> queryFinishOrderListByCusId( Integer cusid ) throws Exception {
		PageData pd = new PageData();
		pd.put( "cusid", cusid );
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		return (List<H5OrderInfoBean>) dao.findForList("OrderMainMapper.queryFinishOrderListByCusId", pd);
	}
	
	/**
	 * @DESC 查询已完成订单列表
	 * @author sunqp
	 * @history 2018年04月18日
	 */
	@SuppressWarnings("unchecked")
	public List<H5OrderInfoBean> queryOngoingOrderListByCusId( Integer cusid ) throws Exception {
		PageData pd = new PageData();
		pd.put( "cusid", cusid );
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		pd.put( "waitpayStatus", ORDER_STATUS.WAITPPAY.getValue());
		return (List<H5OrderInfoBean>) dao.findForList("OrderMainMapper.queryOngoingOrderListByCusId", pd);
	}
	
	/**
     * @DESC 删除订单
     * @author sunqp
     * @history 2018年04月10日
     */
	public void updateIsvalidByid( Integer orderid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "orderid", orderid );         
        pd.put( "isvalid", ISVALID_TYPE.INVALID.getValue());
        dao.update("OrderMainMapper.updateIsvalidByid", pd);
    }
	
	/**
     * @DESC 查询订单角色动作
     * @author sunqp
     * @history 2018年04月10日
     */
	@SuppressWarnings("unchecked")
	public List<OrderRole> queryStatusByOrderId( Integer orderid, String orderno ) throws Exception {
	    PageData pd = new PageData();
	    pd.put("orderid", orderid);
		pd.put("orderno", orderno);
        return (List<OrderRole>) dao.findForList("OrderRoleMapper.queryStatusByOrderId", pd);
    }
	
	/**
     * @DESC 查询订单status状态
     * @author sunqp
     * @history 2018年04月10日
     */
	@SuppressWarnings("unchecked")
	public String getOrderStatusByOrderid( Integer orderid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "orderid", orderid );
        return (String) dao.findForObject("OrderMainMapper.getOrderStatusByOrderid", pd);
    }
	
	/**
     * @DESC 查询订单neadinvoice
     * @author sunqp
     * @history 2018年04月17日
     */
	public String getOrderNeadinvoiceByOrderid( Integer orderid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "orderid", orderid );
        return (String) dao.findForObject("OrderMainMapper.getOrderNeadinvoiceByOrderid", pd);
    }
	
	/**
     * @DESC 修改订单neadinvoice
     * @author sunqp
     * @history 2018年04月17日
     */
	public void updateInvoiceNeadinvoice( Integer orderid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "orderid", orderid );         
        pd.put( "neadinvoice", ISVALID_TYPE.VALID.getValue());
        dao.update("OrderMainMapper.updateInvoiceNeadinvoice", pd);
    }
    
	/**
     * @DESC 查询订单角色动作
     * @author sunqp
     * @history 2018年04月10日
     */
	public H5OrderInfo getH5OrderInfo( Integer orderid, String orderno) throws Exception {
        PageData pd = new PageData();
        pd.put( "orderid", orderid );
        pd.put("orderno", orderno);
        return (H5OrderInfo) dao.findForObject("OrderMainMapper.getH5OrderInfo", pd);
    }
	
	/**
	 * @desc 更具id 查询客户信息
	 * @auther zhangjj
	 * @date 2018年8月2日
	 */
	public PageData findOpenidByOrderid( Integer orderid ) throws Exception {
        return (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", orderid);
    }

}
