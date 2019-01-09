package com.fh.service.order.orderinfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.*;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.order.*;
import com.fh.entity.app.transitcenter.AppTransitCenter;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.entity.delivery.UserDeliveryMan;
import com.fh.entity.order.*;
import com.fh.entity.wxpublicnum.event.WxRefundResData;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.service.SmsSendService;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.delivery.TransitCenterService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.service.wxpublicnum.WXPublicNumService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.DateUtil;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 
 * 项目名称：oa
 * 
 * 类描述： 类名称：com.fh.service.order.orderinfo.OrderInfoService 创建人：tqm
 * 创建时间：2018年1月30日 下午3:49:29 修改人： 修改时间：2018年1月30日 下午3:49:29 修改备注：
 * 
 * @version V1.0
 */
@Service
public class OrderInfoService {

	@Autowired
	private DaoSupport dao;
	@Autowired
	private PushInfoService pushInfoService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	@Autowired
	private WXPublicNumService wxPublicNumService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private OrderInsureInfoService orderInsureInfoService;
    @Autowired
    private AutoAllotService autoAllotService;
    @Autowired
    private ConfigCenterService configCenterService;
    @Autowired
    private TransitCenterService transitCenterService;
    @Autowired
    private ServiceCenterService serviceCenterService;
    @Autowired
    private UserDeliveryManService userDeliveryManService;
    @Autowired
    private OrderPayInfoService orderPayInfoService;

	/**
	 * 
	 * @Title: orderInfoList
	 * @Description: 查询订单列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> orderMainlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"OrderMainMapper.orderMainlistPage", page);
	}

	/**
	 * 
	 * @Title: findOrderDetail
	 * @Description: 查询订单角色动作
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findRoleType(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderMainMapper.findRoleType",
				pd);
	}

	/**
	 * 
	 * @Title: findOrderDetail
	 * @Description: 查询单条订单明细
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public PageData findOrderInfoDetail(PageData pd) throws Exception {
		return (PageData) dao.findForObject(
				"OrderMainMapper.findOrderInfoDetail", pd);
	}

	/**
	 * 
	 * @Title: findOrderDetail
	 * @Description: 查询订单明细
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("all")
	public PageData findOrderDetail(Integer orderid) throws Exception {
		return (PageData) dao.findForObject("OrderMainMapper.findOrderDetail",
				orderid);
	}

	/**
	 * 
	 * @Title: orderRoleTypeListPage
	 * @Description: 查询订单角色轨迹表
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> orderRoleTypeListPage(Integer orderid) throws Exception {
		return (List<PageData>) dao.findForList(
				"OrderMainMapper.orderRoleTypeListPage", orderid);
	}

	/**
	 * @desc 检查订单状态(集合)
	 * @auther zhangjj
	 * @date 2018年4月4日
	 */
	@SuppressWarnings("all")
	public List<PageData> checkOrderStatusByOrderidList(
			List<Integer> orderidList, String status) throws Exception {
		PageData pd = new PageData();
		pd.put("orderidList", orderidList);
		pd.put("status", status);
		return (List<PageData>) dao.findForList(
				"OrderMainMapper.checkOrderStatusByOrderidList", pd);
	}

	/**
	 * @desc 检查订单状态(单个)
	 * @auther zhangjj
	 * @date 2018年4月4日
	 */
	@SuppressWarnings("all")
	public List<PageData> checkOrderStatusByOrderid(Integer orderid,
			String status) throws Exception {
		PageData pd = new PageData();
		pd.put("orderid", orderid);
		pd.put("status", status);
		return (List<PageData>) dao.findForList(
				"OrderMainMapper.checkOrderStatusByOrderid", pd);
	}

	/**
	 * 
	 * @Title: orderOperateTrailListPage
	 * @Description: 订单操作轨迹列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> orderOperateTrailListPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"orderOperateTrailMapper.orderOperateTrailListPage", page);
	}

	/**
	 * @DESC 订单分配给取派员
	 * @author zhangjj
	 */
	public String cbOrderAllotDman(OrderRole orderRole) throws Exception {
		// 更新
		dao.save("OrderRoleMapper.insert", orderRole);

		if (ROLE_TYPE.ROLE_HOTEL_TASK.getValue()
				.equals(orderRole.getRoletype())) {
			PageData reqPd = new PageData();
			reqPd.put("orderid", orderRole.getOrderid());
			reqPd.put("status", ORDER_STATUS.WAITPICK.getValue());
		}
		String resultInfo = "SUCCESS:添加成功！";
		// 温馨提示：
		if (ROLE_TYPE.ROLE_HOTEL_TASK.getValue()
				.equals(orderRole.getRoletype())) {
			resultInfo = "SUCCESS:添加成功！请选择去往集散中心送件或去机场送件";
		}
		if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(
				orderRole.getRoletype())) {
			resultInfo = "SUCCESS:添加成功！请选择去往集散中心送件或去酒店送件";
		}
		// 推送信息
		pushInfoService.allocNewOrderDman(orderRole.getRoleid(), orderRole.getOrderid()); 
		return resultInfo;
	}

	/**
	 * @DESC 删除订单用户关系
	 * @author zhangjj
	 */
	public int deleteUserDelivery(OrderRole orderRole) throws Exception {
		return (int) dao.delete("OrderRoleMapper.delete", orderRole);
	}

	/**
	 * @DESC 查询订单快递员关系
	 * @author zhangjj
	 */
	@SuppressWarnings("all")
	public List<PageData> orderUsereliveryList(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"OrderRoleMapper.orderUserDeliveryList", page);
	}

	/**
	 * @DESC 取出订单上一个的动作节点
	 * @author zhangjj
	 */
	@SuppressWarnings("all")
	public PageData findDescLimit1ByOrderid(Integer orderid) throws Exception {
		return (PageData) dao.findForObject(
				"OrderRoleMapper.findDescLimit1ByOrderid", orderid);
	}

	/**
	 * @desc 根据行李QR码查询行李信息
	 * @auther zhangjj
	 * @date 2018年3月15日
	 */
	public OrderBaggage findOrderBaggageBybagid(String baggageid)
			throws Exception {
		return (OrderBaggage) dao.findForObject(
				"OrderBaggageMapper.findBybagid", baggageid);
	}

	/**
	 * @desc 更新订单状态
	 * @auther zhangjj
	 * @history 2018年2月6日
	 */
	public void updateOrderStatus(PageData pd) throws Exception {
		dao.update("OrderMainMapper.updateOrderStatus", pd);
		if (ORDER_STATUS.RELEASEOVER.getValue().equals(pd.get("status"))) {
			PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", pd.get("orderid"));
			ordermain.put("actionfinishtime", DateUtil.getTime());
			OrderRole  orderRole = new OrderRole();
		    orderRole.setOrderid((Integer)pd.get("orderid"));
		    orderRole.setRoleid((Integer)pd.get("roleid"));
		    orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_RELEASEOVER.getValue());
		    orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
		    orderRole.setActionfinishtime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
		    dao.save("OrderRoleMapper.insert", orderRole);
			try {
				// 公众号通知
				WeixinUtil.deliverySucc(ordermain);
	        }catch(Exception e) {
				LoggerUtil.warn("行李释放时,公众号通知异常：", e);
			}
		}
	}
	
	/**
	 * @desc 生成订单状态
	 * @auther tangqm
	 * @history 2018-05-21 21:27
	 */
	public void cbGenerateOrder(PageData pd) throws Exception {
		dao.update("OrderMainMapper.updateOrderStatus", pd);
		OrderRole  orderRole = new OrderRole();
	    orderRole.setOrderid((Integer)pd.get("orderid"));
	    orderRole.setRoleid((Integer)pd.get("roleid"));
	    orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASKED.getValue());
	    orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
	    orderRole.setActionfinishtime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
	    dao.save("OrderRoleMapper.insert", orderRole);
	}

	/**
	 * @desc 保存订单行李QR码
	 * @auther zhangjj
	 * @history 2018年2月6日
	 */
	public void linkOrderAndQR(OrderBaggage orderBaggage) throws Exception {
		dao.save("OrderBaggageMapper.insertQR", orderBaggage);
	}

	/**
	 * @desc 生成QR码(用于生成电子格式qr码，已使用处理，避免重复生成)
	 * @auther zhangjj
	 * @history 2018年2月6日
	 */
	public void insertQRCode(PageData pd) throws Exception {
		dao.save("OrderBaggageMapper.insertQRCode", pd);
	}

	/**
	 * @desc App_订单列表查询
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@SuppressWarnings("all")
	public List<AppOrderResData> findAppOrderMainList(
			AppOrderReqData appOrderReqData) throws Exception {
		List<AppOrderResData> appOrderResDataList = (List<AppOrderResData>) dao
				.findForList("OrderMainMapper.findAppOrderMainList",
						appOrderReqData);
		List<String> orderroleList = appOrderReqData.getOrderroleList();

		if (CollectionUtils.isEmpty(appOrderResDataList)) {
			// 未查询出订单列表
			return appOrderResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-05-21 21:25
		Iterator<AppOrderResData> iterator = appOrderResDataList.iterator();
		while (iterator.hasNext()) {
			AppOrderResData appOrderResData = iterator.next();
			PageData pd = new PageData();
			pd.put("id", appOrderResData.getId());
			pd.put("roletype", appOrderResData.getRoletype());
			pd.put("destaddress", appOrderResData.getDestaddress());
			String isvalidType = findTransitArrived(appOrderResData.getId(), appOrderResData.getRoletype(), appOrderResData.getDestaddress());
			// 是否抵达集散中心
			if (ISVALID_TYPE.INVALID.getValue().equals(isvalidType)) {
				iterator.remove();
				continue;
			}

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderResData.getId());
			appOrderResData.setOrderBaggageList(orderBaggageList);

			// 查询条件
			List<String> querytype = appOrderReqData.getListQueryType();

			if (CollectionUtils.isNotEmpty(querytype)) {
				// 集散中心or散件
				if (querytype.contains(LISTQUERY_TYPE.TRANSIT.getValue())) {
					List<OrderRole> gotoTransitList = (List<OrderRole>) dao
							.findForList(
									"OrderRoleMapper.findOrderGoToTransitList",
									appOrderResData.getId());

					if (CollectionUtils.isNotEmpty(gotoTransitList)) {
						List<AppTransitCenter> appTransitCenterList = new ArrayList<AppTransitCenter>();
						for (OrderRole orderRole : gotoTransitList) {
							AppTransitCenter appTransitCenter = new AppTransitCenter();
							appTransitCenter.setSeq(orderRole.getId() + "");
							appTransitCenter.setId(Integer.parseInt(orderRole
									.getDestaddress()));
							appTransitCenter.setName(orderRole
									.getDestaddrname());
							appTransitCenter.setIsfinist(orderRole
									.getIsfinish());
							appTransitCenterList.add(appTransitCenter);
						}
						appOrderResData
								.setAppTransitCenterList(appTransitCenterList);
					}
				}

			}
		}
		return appOrderResDataList;
	}

	/**
	 * 查询集散中心寄存件
	 */
	@SuppressWarnings("all")
	public List<AppOrderJicunResData> findAppOrderMainJicunList(
			AppOrderJicunReqData appOrderJicunReqData) throws Exception {

		List<AppOrderJicunResData> appOrderJicunResDataList = (List<AppOrderJicunResData>) dao
				.findForList("OrderMainMapper.findAppOrderMainJicunList",
						appOrderJicunReqData);
		// List<String> orderroleList = appOrderReqData.getOrderroleList();

		if (CollectionUtils.isEmpty(appOrderJicunResDataList)) {
			// 未查询出寄存件列表
			return appOrderJicunResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-05-21 21:25
		Iterator<AppOrderJicunResData> iterator = appOrderJicunResDataList
				.iterator();
		while (iterator.hasNext()) {
			AppOrderJicunResData appOrderJicunResData = iterator.next();

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderJicunResData.getId());
			appOrderJicunResData.setOrderBaggageList(orderBaggageList);

		}
		return appOrderJicunResDataList;
	}

	/**
	 * 查询集散中心已完成件
	 */
	@SuppressWarnings("all")
	public List<AppOrderFinishResData> findAppOrderMainFinishList(
			AppOrderFinishReqData appOrderFinishReqData) throws Exception {

		List<AppOrderFinishResData> appOrderFinishResDataList = (List<AppOrderFinishResData>) dao
				.findForList("OrderMainMapper.findAppOrderMainFinishList",
						appOrderFinishReqData);

		if (CollectionUtils.isEmpty(appOrderFinishResDataList)) {
			// 未查询出寄存件列表
			return appOrderFinishResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-05-21 21:25
		Iterator<AppOrderFinishResData> iterator = appOrderFinishResDataList
				.iterator();
		while (iterator.hasNext()) {
			AppOrderFinishResData appOrderFinishResData = iterator.next();

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderFinishResData.getId());
			appOrderFinishResData.setOrderBaggageList(orderBaggageList);

		}
		return appOrderFinishResDataList;
	}

	/**
	 * @desc App_任务列表(未增加同一个地址汇总功能)
	 * @auther zhangjj
	 * @date 2018年8月10日
	 */
	@SuppressWarnings("all")
	public List<AppOrderResData> findAppOrderTaskList(AppOrderReqData appOrderReqData) throws Exception {
		// 入参拆分
		if(CollectionUtils.isNotEmpty(appOrderReqData.getOrderroleList())){			
			appOrderReqData.setRoletype(appOrderReqData.getOrderroleList().get(0));
		}
		// 返回参数定义
		List<AppOrderResData> appOrderResDataList = (List<AppOrderResData>) dao.findForList("OrderMainMapper.findAppOrderTaskList", appOrderReqData);
		// 任务列表查询
		if (CollectionUtils.isEmpty(appOrderResDataList)) {
			return appOrderResDataList;
		}

		for (AppOrderResData resData : appOrderResDataList) {
			// 获取取派员同一个订单下个动作信息
			if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(resData.getRoletype())) {
				resData.setNextBindAction(setNextBindAction(resData.getId(), resData.getRoleid()));
			}
			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao.findForList("OrderBaggageMapper.findByOrderid",resData.getId());
			resData.setOrderBaggageList(orderBaggageList);
			// 目的地地址坐标
			resData.setDestaddressGps(StringUtils.isNotBlank(resData.getDestGps())? JSONObject.parseObject(resData.getDestGps(), BaiduCoord.class) : null);
			// 机场是否收件
			resData.setIstake(findCenterTake(resData.getRoletype(), resData.getStatus(), resData.getIsfinish()));
			// 是否抵达集散中心
			resData.setIsarrived(findTransitArrived(resData.getId(), resData.getRoletype(), resData.getDestaddress()));
		}

		return appOrderResDataList;
	}
	
	/**
	 * @desc App_订单列表(同一个地址汇总功能)
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@SuppressWarnings("all")
	public List<Map<String, List<AppOrderResData>>> findAppOrderTaskList_total(AppOrderReqData appOrderReqData) throws Exception {
		// 入参拆分
		if(CollectionUtils.isNotEmpty(appOrderReqData.getOrderroleList())){			
			appOrderReqData.setRoletype(appOrderReqData.getOrderroleList().get(0));
		}
		// 返回参数定义
		List<Map<String, List<AppOrderResData>>> totalData = new ArrayList<Map<String, List<AppOrderResData>>>();
		// 任务列表查询
		List<AppOrderResData> appOrderResDataList = (List<AppOrderResData>) dao.findForList("OrderMainMapper.findAppOrderTaskList",appOrderReqData);
		// 未查询出订单列表
		if (CollectionUtils.isEmpty(appOrderResDataList)) {
			return totalData;
		}
		
		// 目的地相同汇总map
		Map<String, List<AppOrderResData>> appRowMapData = new HashMap<String, List<AppOrderResData>>();
		
		for (AppOrderResData resData : appOrderResDataList) {
			// 获取取派员同一个订单下个动作信息
			if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(resData.getRoletype())) {
				resData.setNextBindAction(setNextBindAction(resData.getId(), resData.getRoleid()));
			}
			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao.findForList("OrderBaggageMapper.findByOrderid",resData.getId());
			resData.setOrderBaggageList(orderBaggageList);
			// 目的地地址坐标
			resData.setDestaddressGps(StringUtils.isNotBlank(resData.getDestGps())? JSONObject.parseObject(resData.getDestGps(), BaiduCoord.class) : null);
			// 机场是否收件标志
			resData.setIstake(findCenterTake(resData.getRoletype(), resData.getStatus(), resData.getIsfinish()));
			// 是否抵达集散中心标志
			resData.setIsarrived(findTransitArrived(resData.getId(), resData.getRoletype(), resData.getDestaddress()));
			// 目的地相同汇总
			if(ORDER_CHANNEL.WINXIN_GS.getValue().equals(resData.getChannel())) {// 只有金牌类型才可以合并订单信息
				String issamedest_key = resData.getRoletype() + resData.getDesttype() + resData.getDestaddrdesc() + resData.getIsfinish();
				List<AppOrderResData> samedestaddrList = appRowMapData.get(issamedest_key);
				if(CollectionUtils.isEmpty(samedestaddrList)) {// 为空则创建
					samedestaddrList = new ArrayList<AppOrderResData>();
				} 
				samedestaddrList.add(resData);
				appRowMapData.put(issamedest_key, samedestaddrList);
			} else {// 专车
				Map<String, List<AppOrderResData>> map_tmep = new HashMap<String, List<AppOrderResData>>();
				List<AppOrderResData> list_temp = new ArrayList<AppOrderResData>();
				list_temp.add(resData);
				map_tmep.put("TaskList", list_temp);
				totalData.add(map_tmep);
			}
		}
		// 目的地相同汇总_按照时间调整顺序
		List<String> listtemp = IteratorUtils.toList(appRowMapData.keySet().iterator());
		for (int i = listtemp.size() - 1; i >= 0; i--) {
			Map<String, List<AppOrderResData>> map_tmep = new HashMap<String, List<AppOrderResData>>();
			map_tmep.put("TaskList", appRowMapData.get(listtemp.get(i)));
			totalData.add(map_tmep);
		}

		return totalData;
	}

	/**
	 * @Desc: App_地图信息集合
	 * @author：tangqm 
	 * @history:2018年7月11日
	 */
	@SuppressWarnings("all")
	public List<AppOrderResData> findAppMapTaskList(AppOrderReqData appOrderReqData) throws Exception {
		// 任务列表查询
		List<AppOrderResData> appOrderResDataList = (List<AppOrderResData>) dao.findForList("OrderMainMapper.findAppMapTaskList", appOrderReqData);
		// 任务列表为空的话则直接返回
		if (CollectionUtils.isEmpty(appOrderResDataList)) {
			return appOrderResDataList;
		}
		
		for (AppOrderResData resData : appOrderResDataList) {
			// 取派员同一个订单下个动作
			if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(resData.getRoletype())
					|| ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(resData.getRoletype())) {
				resData.setNextBindAction(setNextBindAction(resData.getId(), resData.getRoleid()));
			}
			// 获取动作目的地gps
			resData.setDestaddressGps(StringUtils.isNotBlank(resData.getDestGps())? JSONObject.parseObject(resData.getDestGps(), BaiduCoord.class) : null);
		}

		return appOrderResDataList;
	}

	@SuppressWarnings("all")
	public String notifyAppCus(AppOrderReqData appOrderReqData) throws Exception{
		PageData pd = new PageData();
		UserDeliveryMan userDeliveryMan  = (UserDeliveryMan) dao.findForObject("UserDeliveryManMapper.findDmanByuserid", appOrderReqData.getRoleid());    
		List<PageData>  checkCount =(List<PageData>) dao.findForList("OrderRoleMapper.findOrderRoleById", appOrderReqData); 
		if(CollectionUtils.isEmpty(checkCount)){			
			PageData openid =   (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", appOrderReqData.getId());    
			openid.put("udmname", userDeliveryMan.getName());
			openid.put("regionname", userDeliveryMan.getRegionname());
			dao.update("OrderRoleMapper.updateCountById", appOrderReqData); 
			
			try {
				// 公众号信息通知
				WeixinUtil.orderStatus(openid, "即将到达");
				// 发送短消息
				smsSendService.smsTemplateSend("{'mobile':'"+ (String) openid.getString("mobile") +"','header': '"+ MsgOfTmpCode.SMS_HEADER +"', 'dmanname':'"+ userDeliveryMan.getName() +"','dmanmobile':'"+ userDeliveryMan.getMobile() +"', 'smscode':'X017'}");
			}catch(Exception e) {
				LoggerUtil.warn("消息通知发送失败,消息编码：X017", e);
			}
			
			return "通知成功!";
		}
		return "请勿重复通知";
	}

	/**
	 * 
	 * @Title: findAppOrderNumTaskList
	 * @Description: 查询app订单任务数量 author：tangqm 2018年7月10日
	 * @param appOrderReqData
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public AppOrderNumResData findAppOrderNumTaskList(
			AppOrderReqData appOrderReqData) throws Exception {
		AppOrderNumResData appOrderNumResData = (AppOrderNumResData) dao
				.findForObject("OrderMainMapper.findAppOrderNumTaskList",
						appOrderReqData);
		return appOrderNumResData;
	}

	

	/**
	 * @desc app_订单详情查询
	 * @auther zhangjj
	 * @history 2018年2月28日
	 */
	@SuppressWarnings("all")
	public AppOrderDetailsResData findAppAppOrderDetails(AppOrderDetailsReqData appOrderDetailsReqData) throws Exception {
		AppOrderDetailsResData appOrderDetailsResData = (AppOrderDetailsResData) dao.findForObject("OrderMainMapper.findAppOrderDetails", appOrderDetailsReqData);
		ExceptionUtil.checkNotNull(appOrderDetailsResData,"未查出订单基本信息,请检查入参是否正确");

		List<String> queryDetailsType = appOrderDetailsReqData.getQueryDetailsType();

		// 如果详情查询类型为空，则只查询最基本的订单信息
		if (CollectionUtils.isEmpty(queryDetailsType)) {
			return appOrderDetailsResData;
		}

		// 寄件人收件人信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERSENDERRECEIVER.getValue())) {
			OrderSenderReceiver orderSenderReceiver = (OrderSenderReceiver) dao.findForObject("OrderSenderReceiverMapper.findByOrderid", appOrderDetailsResData.getId()); 
			appOrderDetailsResData.setOrderSenderReceiver(orderSenderReceiver);
		}

		// 查询QR码+图片路径信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue())) {
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao.findForList("OrderBaggageMapper.findByOrderid", appOrderDetailsResData.getId());
			
			// 特殊处理
			if(CollectionUtils.isNotEmpty(orderBaggageList)) {
				for (OrderBaggage orderBaggage : orderBaggageList) {
					String imgurl = orderBaggage.getImgurl();
					
					if(StringUtils.isBlank(imgurl)) {
						continue;
					}
					
					Map<String, Object> parseObject = JSONObject.parseObject(imgurl, Map.class);
			    	
					OrderImgurl orderImgurl = new OrderImgurl();
					// 收取行李照片
					Object co = parseObject.get(IMGURL_BUSINESS_TYPE.COOLECT.getValue());
					if(co != null) {
						orderImgurl.setCOOLECT(JSONArray.parseArray(co.toString(), String.class));
					}
					// 释放行李照片
					Object re= parseObject.get(IMGURL_BUSINESS_TYPE.RELEASE.getValue());
					if(re != null) {
						orderImgurl.setRELEASE(JSONArray.parseArray(re.toString(), String.class));
					}
					orderBaggage.setOrderImgurl(orderImgurl);
				}
			}
			
			appOrderDetailsResData.setOrderBaggageList(orderBaggageList);
		}

		// 查询订单备份信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue())) {
			List<OrderNotesInfo> orderNotesInfoList = (List<OrderNotesInfo>) dao.findForList("orderNotesInfoMapper.findByOrderid", appOrderDetailsResData.getId());
			appOrderDetailsResData.setOrderNotesInfoList(orderNotesInfoList);
		}
		
		

		// 查询航班信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERFLIGHT.getValue())) {
			OrderFlight orderFlight = (OrderFlight) dao.findForObject("OrderFlightMapper.findByOrderid", appOrderDetailsResData.getId());
			appOrderDetailsResData.setOrderFlight(orderFlight);
		}

		// 查询客户信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue())) {
			CusInfo cusInfo = (CusInfo) dao.findForObject("CusInfoMapper.getById", appOrderDetailsResData.getCusid());
			appOrderDetailsResData.setCusInfo(cusInfo);
		}

		// 查询订单地址信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue())) {
			OrderAddress orderAddress = (OrderAddress) dao.findForObject("orderAddressMapper.findByOrderid", appOrderDetailsResData.getId());
			appOrderDetailsResData.setOrderAddress(orderAddress);
		}

		// 订单价格明细
		if(queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDER_PRICE_DETAIL.getValue())) {
			float basemoney = 0.0f;// 基础费用
			float prem = 0.0f;// 保费
			float extramoney = 0.0f;// 额外费用（过夜费）
			Map<String, String> lugBaseCost = new HashMap<String, String>();// 行李费用详情

			Float cutmoney = appOrderDetailsResData.getCutmoney();// 优惠卷
			Float actualmoney = appOrderDetailsResData.getActualmoney();// 实际支付金额
			Float totalmoney = appOrderDetailsResData.getTotalmoney();// 总支付支付金额
			Integer num = appOrderDetailsResData.getNum();// 行李数量
			String remark = appOrderDetailsResData.getRemark();// 行李数详情
			String mailingway = appOrderDetailsResData.getMailingway();// 寄件方式

			// 保费
			OrderInsureInfo orderInsureInfo = orderInsureInfoService.getByOrderId(appOrderDetailsResData.getId());
			prem = INSURE_TYPE.getPrem(orderInsureInfo.getInsurecode()).getPremium() * num;

			if(StringUtils.isBlank(remark)){
				extramoney = (float) (num*49-49);//第一件行李免费
				basemoney = totalmoney - prem - extramoney - cutmoney;
				lugBaseCost.put("基础运费", basemoney + "");
			}else{
				String[] split =remark.split("\\|");
				for (int j = 0; j < split.length; j++) {
					String bag = split[j];
					if(j==0 && !"0".equals(bag) && StringUtils.isNotBlank(bag)){
						basemoney+=69*Integer.parseInt(bag);
						lugBaseCost.put("普通行李", "69*" + bag);
					}
					if(j==1 && !"0".equals(bag) && StringUtils.isNotBlank(bag)){
						basemoney+=49*Integer.parseInt(bag);
						lugBaseCost.put("小件行李", "49*" + bag);
					}
					if(j==2 && !"0".equals(bag) && StringUtils.isNotBlank(bag)){
						basemoney+=99*Integer.parseInt(bag);
						lugBaseCost.put("特殊行李", "99*" + bag);
					}
				}
				extramoney= totalmoney - basemoney - prem - cutmoney;
			}


			OrderPriceDetatils orderPriceDetatils = new OrderPriceDetatils();
			orderPriceDetatils.setLugBaseCostMap(lugBaseCost);
			orderPriceDetatils.setBaseMoney(basemoney);
			orderPriceDetatils.setPrem(prem);
			orderPriceDetatils.setExtraMoney(extramoney);
			orderPriceDetatils.setActualMoney(actualmoney);
			orderPriceDetatils.setCutMoney(cutmoney);
			orderPriceDetatils.setTotalMoney(totalmoney);
			appOrderDetailsResData.setOrderPriceDetatils(orderPriceDetatils);
		}
		
		if(queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ACTION_DETAILS.getValue())) {
	        // 订单角色动作轨迹
	        List<PageData> actionList = this.orderRoleTypeListPage(appOrderDetailsResData.getId());
			appOrderDetailsResData.setActiondetail(actionList);
		}
		
		// 当前分配取派员信息
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERDMAN.getValue())) {
			List<OrderRole> orderRoleList = appOrderDetailsReqData.getOrderRoleList();
			ExceptionUtil.checkNotNull(orderRoleList, "当订单详情查询类型为取派员时，角色动作必传");

			List<UserDeliveryMan> userDeliveryManList = new ArrayList<UserDeliveryMan>();
			for (OrderRole orderRole : orderRoleList) {
				PageData pd = new PageData();
				pd.put("orderid", appOrderDetailsResData.getId());
				pd.put("roletype", orderRole.getRoletype());
				pd.put("isfinish", orderRole.getIsfinish());
				Integer roleid = (Integer) dao.findForObject("OrderRoleMapper.findRoleid", pd);

				if (roleid == null) {
					// 未查到该订单已经分配取派员
					continue;
				}

				UserDeliveryMan userDeliveryMan = (UserDeliveryMan) dao.findForObject("UserDeliveryManMapper.findDmanByuserid", roleid);
				if (userDeliveryMan != null) {
					userDeliveryManList.add(userDeliveryMan);
				}
			}
			appOrderDetailsResData.setUserDeliveryManList(userDeliveryManList);
		}

		// 去集散中心送件查询(包括未完成)
		if (queryDetailsType.contains(QUERY_ORERYDETAILS_TYPE.ORDERTRANSIT
				.getValue())) {
			List<OrderRole> gotoTransitList = (List<OrderRole>) dao.findForList("OrderRoleMapper.findOrderGoToTransitList", appOrderDetailsResData.getId());
			if (CollectionUtils.isEmpty(gotoTransitList)) {
				// 该订单下还未分配去往的集散中心
				return appOrderDetailsResData;
			}
			List<AppTransitCenter> appTransitCenterList = new ArrayList<AppTransitCenter>();
			for (OrderRole orderRole : gotoTransitList) {
				AppTransitCenter appTransitCenter = new AppTransitCenter();
				appTransitCenter.setSeq(orderRole.getId() + "");
				appTransitCenter.setId(Integer.parseInt(orderRole.getDestaddress()));
				appTransitCenter.setName(orderRole.getDestaddrname());
				appTransitCenter.setIsfinist(orderRole.getIsfinish());
				appTransitCenterList.add(appTransitCenter);
			}
			appOrderDetailsResData.setAppTransitCenterList(appTransitCenterList);
		}

		return appOrderDetailsResData;
	}
	
	/**
	 * @desc 订单费用详情
	 * @auther zhangjj
	 * @date 2018年10月9日
	 */
	private void getOrderPriceDetails() {
		
	}

	/**
	 * @desc 根据orderid 查询地址信息
	 * @auther zhangjj
	 * @date 2018年3月14日
	 */
	public OrderAddress findOrderAddressByOrderid(Integer orderid)
			throws Exception {
		return (OrderAddress) dao.findForObject(
				"orderAddressMapper.findOrderAddressByOrderid", orderid);
	}

	/**
	 * 统计订单中行李总数
	 */
	public int countOrderBaggageNum(OrderMainSpec orderMainSpec)
			throws Exception {
		return (int) dao.findForObject("OrderMainMapper.countOrderBaggageNum",
				orderMainSpec);
	}

	/**
	 * 根据根据QR码查询查询出所有订单的的行李书
	 */
	public Integer countBaggageNumByQR(
			CountBaggageNumReqData countBaggageNumReqData) throws Exception {
		// 根据QR码查询出订单信息
		PageData pd = new PageData();
		pd.put("baggageid", countBaggageNumReqData.getBaggageid());
		OrderBaggage orderBaggage = (OrderBaggage) dao.findForObject(
				"OrderBaggageMapper.findByBeggageid", pd);
		// QR码对应的行李是否存在
		ExceptionUtil.checkNotNull(orderBaggage, "QR码失效，请尽快检查");

		pd = new PageData();
		pd.put("orderid", orderBaggage.getOrderid());
		pd.put("roletype", countBaggageNumReqData.getRoletype());
		pd.put("isfinish", countBaggageNumReqData.getIsfinish());
		pd.put("desttype", countBaggageNumReqData.getDesttype());
		pd.put("destaddress", countBaggageNumReqData.getDestaddress());
		Integer roleid = (Integer) dao.findForObject(
				"OrderRoleMapper.findOrderRoleByRoleType", pd);

		// 查询这个订单下目前状态
		pd = new PageData();
		pd.put("roleid", roleid);
		pd.put("roletype", countBaggageNumReqData.getRoletype());
		pd.put("isfinish", countBaggageNumReqData.getIsfinish());
		pd.put("desttype", countBaggageNumReqData.getDesttype());
		pd.put("destaddress", countBaggageNumReqData.getDestaddress());
		return (Integer) dao.findForObject("OrderMainMapper.countBatchBagNum",
				pd);
	}

	/**
	 * 是否扫描
	 * 
	 * @throws Exception
	 */
	public void updateOrderBaggageIsScan(OrderBaggageReqData orderBaggageReqData)
			throws Exception {
		PageData pd = new PageData();
		pd.put("orderid", orderBaggageReqData.getOrderid());
		pd.put("baggageid", orderBaggageReqData.getBaggageid());
		pd.put("isscan", orderBaggageReqData.getIsscan());
		dao.update("OrderBaggageMapper.updateOrderBaggageIsScan", pd);
	}

	/**
	 * 签字地址保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public int saveSignUrl(AppOrderReqData appOrderReqData) throws Exception {
		PageData pd = new PageData();
		pd.put("id", appOrderReqData.getId());
		pd.put("signurl", appOrderReqData.getSignurl());
		pd.put("signname", appOrderReqData.getSignname());
		return (int) dao.save("OrderMainMapper.saveSignUrl", pd);
	}

	/**
	 * 根据主键删除动作信息 zhangjj
	 */
	public void delectOrderRoleByid(Integer orderOrderid) throws Exception {
		dao.delete("OrderRoleMapper.delectOrderRoleByid", orderOrderid);
	}

	/**
	 * 确认发车
	 *
	 * @param rqData
	 * @throws Exception
	 */
	public void updateOrderRoleState(AppOrderReqData rqData) throws Exception {
		PageData pd = new PageData();
		pd.put("roleid", rqData.getRoleid());
		pd.put("orderid", rqData.getOrderidList());
		pd.put("roletype", rqData.getRoletype());
		pd.put("isfinish", IS_FINISH.ONGOING.getValue().toString());
		dao.update("OrderRoleMapper.updateOrderRoleState", pd);
	}

	/**
	 * @desc 检查关联QR是否超出行李数
	 * @auther zhangjj
	 * @date 2018年3月15日
	 */
	public Integer checkLinkQRUltralimit(Integer orderid) throws Exception {
		return (Integer) dao.findForObject(
				"OrderBaggageMapper.checkLinkQRUltralimit", orderid);
	}

	/**
	 * @desc 获取取派员当前位置
	 * @auther zhangjj
	 * @date 2018年3月14日
	 */
	public void updateChangeDman(Integer orderid, Integer orderroleid,
			Integer newdmanuserid, Integer olddmanuserid) throws Exception {
		// 动作取派员取消
		PageData pd = new PageData();
		pd.put("id", orderroleid);
		pd.put("roleid", newdmanuserid);
		dao.update("OrderRoleMapper.updateChangeDman", pd);

		// 推送消息
		pushInfoService.changeDman(orderid, newdmanuserid, olddmanuserid);
	}

	/**
	 * 修改状态
	 * 
	 * @param id
	 * @param status
	 * @throws Exception
	 */
	public void updateStatusByid(Integer id, String status) throws Exception {
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("status", status);
		dao.update("OrderMainMapper.updateStatusByid", pd);
		
	}

	@SuppressWarnings("all")
	public String cbCancelOrder(Integer orderid, Float refundmoney,String refundreason,String operator)
			throws Exception {
		
		String msg = "退款成功";
		// 更改订单状态为取消订单
		PageData findOrderDetail = findOrderDetail(orderid);
		String status = (String) findOrderDetail.get("status");
		Float actualmoney = (Float) findOrderDetail.get("actualmoney");
		String orderno = (String) findOrderDetail.get("orderno");
		if (actualmoney == null) {
			return "订单原实际金额为空，请联系IT处理!";
		}
		if (ORDER_STATUS.CANCELLED.getValue().equals(status)) {
			return "请勿重复退款!";
		}
		if (refundmoney > actualmoney) {
			return "最大金额:" + actualmoney + "元";
		}
		WxRefundResData wxRefundResData = wxPublicNumService.refund(orderno,
				refundmoney, actualmoney);
		
		if (wxRefundResData != null
				&& wxRefundResData.getResult_code().equals("SUCCESS")
				&& wxRefundResData.getReturn_code().equals("SUCCESS")) {
			updateStatusByid(orderid, ORDER_STATUS.CANCELLED.getValue());// 修改订单状态
			msg = "退款成功";
			PageData pd  = new PageData();
			pd.put("orderid", orderid);
			pd.put("refundmoney", refundmoney);
			pd.put("refundreason", refundreason);
			pd.put("operator", operator);
			pd.put("status", '5');
			dao.save("OrderRefundMapper.insert", pd);
		} else {
			msg = wxRefundResData.getErr_code_des();
		}
		PageData pd = (com.fh.util.PageData) dao.findForObject("OrderRoleMapper.findRoleidByOrderid_Refund", orderid);
		if(pd != null){			
			// 推送退款信息
			pushInfoService.cancelOrderDman((Integer)pd.get("roleid"), orderid);
		}
		return msg;
	}

	/**
	 * @desc 判断是否已经抵达集散中心
	 * @auther zhangjj
	 * @date 2018年8月9日
	 */
	@SuppressWarnings("all")
	private String findTransitArrived(Integer orderid, String roletype, String destaddress) throws Exception {
		if (ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(roletype)) {
			// 参数组装
			PageData pd = new PageData();
			pd.put("id", orderid);
			pd.put("roletype", roletype);
			pd.put("destaddress", destaddress);
			List<OrderRole> gotoTransitList = (List<OrderRole>) dao.findForList("OrderRoleMapper.findTransitArrived", pd);
			if (CollectionUtils.isEmpty(gotoTransitList)) {
				return ISVALID_TYPE.INVALID.getValue();
			}
			return ISVALID_TYPE.VALID.getValue();
		}
		return ISVALID_TYPE.VALID.getValue();
	}
	
	/**
	 * @desc 判断订单是否机场已经收件
	 * @auther zhangjj
	 * @date 2018年8月9日
	 */
	@SuppressWarnings("all")
	private String findCenterTake(String roletype, String orderStatus, String isfinish) throws Exception {
		if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(roletype)
				&& !ORDER_STATUS.TAKEGOOGSOVER.getValue().equals(orderStatus)
				&& IS_FINISH.UNFINISHED.getValue().equals(isfinish)) {
			return ISVALID_TYPE.INVALID.getValue();
		}
		return ISVALID_TYPE.VALID.getValue();
	}

	/**
	 * @desc 查询待取动作的下个动作待送
	 * @auther zhangjj
	 * @date 2018年4月23日
	 */
	private OrderRole setNextBindAction(Integer orderid, Integer roleid) throws Exception {
		// 组装信息
		PageData pd = new PageData();
		pd.put("orderid", orderid);// 订单id
		pd.put("roleid", roleid);
		return (OrderRole) dao.findForObject("OrderRoleMapper.findNextBindRoleAction", pd);
	}

	/**
	 * @desc 如果订单动作没有最迟到达时间;则最迟到达时间为 客户设定的取件时间 or 派件时间
	 * @auther zhangjj
	 * @date 2018年4月24日
	 */
	private String setArrivedTimeWhenNull(String roletype, String tasktime, String sendtime) {
		String arrivedtime = "";

		if (ROLE_TYPE.ROLE_AIRPORT_TASK.getValue().equals(roletype)
				|| ROLE_TYPE.ROLE_HOTEL_TASK.getValue().equals(roletype)) {
			return tasktime;
		}
		if (ROLE_TYPE.ROLE_AIRPORT_SEND.getValue().equals(roletype)
				|| ROLE_TYPE.ROLE_HOTEL_SEND.getValue().equals(roletype)) {
			return sendtime;
		}

		return arrivedtime;
	}

	/**
	 * @desc 修改取派员出勤状态
	 * @auther tangqm
	 * @date 2018年5月16日
	 */
	public void updateAttendance(Integer id, String attendance)
			throws Exception {
		PageData pd = new PageData();
		pd.put("id", id);
		pd.put("attendance", attendance);
		dao.update("UserDeliveryManMapper.updateAttendance", pd);

	}

	/**
	 * 自定义Strig适配器 tangqm
	 */
	private static final TypeAdapter STRING = new TypeAdapter() {
		public String read(JsonReader reader) throws IOException {
			if (reader.peek() == JsonToken.NULL) {
				reader.nextNull();
				return "";
			}
			return reader.nextString();
		}

		@Override
		public void write(JsonWriter writer, Object value) throws IOException {
			if (value == null) {
				// 在这里处理null改为空字符串
				writer.value("");
				return;
			}
			writer.value(value.toString());
		}

	};

	/**
	 * 查询机场柜台寄存件
	 */
	@SuppressWarnings("all")
	public List<AppOrderAirportResData> findAppOrderAirportHosting(
			AppOrderAirportReqData appOrderJicunReqData) throws Exception {

		List<AppOrderAirportResData> appOrderJicunResDataList = (List<AppOrderAirportResData>) dao
				.findForList("OrderMainMapper.findAppOrderAirportHosting",
						appOrderJicunReqData);
		// List<String> orderroleList = appOrderReqData.getOrderroleList();

		if (CollectionUtils.isEmpty(appOrderJicunResDataList)) {
			// 未查询出寄存件列表
			return appOrderJicunResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-06-20 21:25
		Iterator<AppOrderAirportResData> iterator = appOrderJicunResDataList
				.iterator();
		while (iterator.hasNext()) {
			AppOrderAirportResData appOrderJicunResData = iterator.next();

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderJicunResData.getId());
			appOrderJicunResData.setOrderBaggageList(orderBaggageList);

		}
		return appOrderJicunResDataList;
	}

	/**
	 * 查询机场柜台已完成件
	 */
	@SuppressWarnings("all")
	public List<AppOrderAirportResData> findAppOrderAirportFinish(
			AppOrderAirportReqData appOrderFinishReqData) throws Exception {

		List<AppOrderAirportResData> appOrderFinishResDataList = (List<AppOrderAirportResData>) dao
				.findForList("OrderMainMapper.findAppOrderAirportFinish",
						appOrderFinishReqData);

		if (CollectionUtils.isEmpty(appOrderFinishResDataList)) {
			// 未查询出寄存件列表
			return appOrderFinishResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-06-20 21:25
		Iterator<AppOrderAirportResData> iterator = appOrderFinishResDataList
				.iterator();
		while (iterator.hasNext()) {
			AppOrderAirportResData appOrderFinishResData = iterator.next();

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderFinishResData.getId());
			appOrderFinishResData.setOrderBaggageList(orderBaggageList);

		}
		return appOrderFinishResDataList;
	}

	/**
	 * 查询机场柜台待取件
	 */
	@SuppressWarnings("all")
	public List<AppOrderAirportResData> findAppOrderAirportTake(AppOrderAirportReqData appOrderJicunReqData) throws Exception {
		return (List<AppOrderAirportResData>) dao.findForList("OrderMainMapper.findAppOrderAirportTake",appOrderJicunReqData);
	}
	
	/**
	 * 查询机场柜台待支付
	 */
	@SuppressWarnings("all")
	public List<AppOrderAirportResData> findAppOrderAirportWaitPay(AppOrderAirportReqData appOrderJicunReqData) throws Exception {
		return (List<AppOrderAirportResData>) dao.findForList("OrderMainMapper.findAppOrderAirportWaitPay",appOrderJicunReqData);
	}

	/**
	 * 查询机场柜台待派件
	 */
	@SuppressWarnings("all")
	public List<AppOrderAirportResData> findAppOrderAirportSend(
			AppOrderAirportReqData appOrderFinishReqData) throws Exception {

		List<AppOrderAirportResData> appOrderFinishResDataList = (List<AppOrderAirportResData>) dao
				.findForList("OrderMainMapper.findAppOrderAirportSend",
						appOrderFinishReqData);

		if (CollectionUtils.isEmpty(appOrderFinishResDataList)) {
			// 未查询出寄存件列表
			return appOrderFinishResDataList;
		}

		// tangqm 利用迭代器去除未抵达集散中心的订单 2018-06-20 21:25
		Iterator<AppOrderAirportResData> iterator = appOrderFinishResDataList
				.iterator();
		while (iterator.hasNext()) {
			AppOrderAirportResData appOrderFinishResData = iterator.next();

			// QR码查询
			List<OrderBaggage> orderBaggageList = (List<OrderBaggage>) dao
					.findForList("OrderBaggageMapper.findByOrderid",
							appOrderFinishResData.getId());
			appOrderFinishResData.setOrderBaggageList(orderBaggageList);

		}
		return appOrderFinishResDataList;
	}

	/**
	 * @desc 订单分配信息查询
	 * @auther zhangjj
	 * @date 2018年7月13日
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findOrderAllocinfo(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"OrderMainMapper.findOrderAllocinfo", pd);
	}
	
	/**
	 * 
	 * @Title: updateprepaid
	 * @Description: 修改订单支付成功
	 * author：tangqm
	 * 2018年10月22日
	 * @throws Exception
	 */
	public void updateprepaid(String orderno) throws Exception{
		PageData pd = new PageData();
		pd.put("orderno", orderno);
		PageData orderIdByOrderNo = autoAllotService.getOrderIdByOrderNo(pd);
		orderPayInfoService.updateIsvalidByid((Integer)orderIdByOrderNo.get("orderid"), ORDER_PAY_STUTAS.PREPAID.getValue());
		try {
			PageData smsdto = autoAllotService.getOrderIdByOrderNo(pd);
			String srcaddrtype = (String) smsdto.get("srcaddrtype");
			String scrlandmark = (String) smsdto.get("scrlandmark");
			Date taketime = (Date) smsdto.get("taketime");
			String mobile = (String) smsdto.get("mobile");
			
			if(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(srcaddrtype)){			
				smsSendService.smsTemplateSend("{'mobile':'"+ mobile+"','header': '"+ 
						MsgOfTmpCode.SMS_HEADER +"', 'orderno':'"+ orderno +"', 'scrlandmark':'"+ scrlandmark +"', 'taketime':'"+ taketime +"','smscode':'orderGenerate'}");
			}else{
				smsSendService.smsTemplateSend("{'mobile':'"+ mobile +"','header': '"+ 
						MsgOfTmpCode.SMS_HEADER +"', 'orderno':'"+ orderno +"','smscode':'X016'}");
			}
		} catch(Exception e) {
			LoggerUtil.warn("app更新订单状态为已经支付，发送发送消息失败， 消息编码是：X016");
		}
		
	}
	
	

	/**
     * @desc 订单寄件人收件人信息更改
     * @auther zhangjj
     * @date 2018年11月09日
     */
    public void updateSenderReceiver(PageData pd) throws Exception {
        dao.update("OrderSenderReceiverMapper.updateSenderReceiver", pd);
    }
	
    /**
     * @desc 订单寄收时间更新
     * @auther zhangjj
     * @date 2018年11月09日
     */
    public void updateTaketimeOrSendTime(OrderMainSpec orderMainSpec) throws Exception {
        dao.update("OrderMainMapper.updateTaketimeOrSendTime", orderMainSpec);
    }

}
