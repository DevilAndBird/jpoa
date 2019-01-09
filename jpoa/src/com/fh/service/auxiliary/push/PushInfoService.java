package com.fh.service.auxiliary.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fh.common.constant.PushConstent;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.PUSHINFO_TYPE;
import com.fh.common.constant_enum.VOICEINFO_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.auxiliary.push.req.AppPushNum;
import com.fh.entity.auxiliary.push.PushInfo;
import com.fh.entity.delivery.UserDeliveryMan;
import com.fh.entity.order.OrderRole;
import com.fh.thirdparty.jiguangpush.JiGuangPushBO;
import com.fh.util.DateUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;
import com.fh.util.PropertyConfigurer;


/**
 * @desc 推送消息存储
 * @auther zhangjj
 * @date 2018年5月8日
 */
@Service
public class PushInfoService {
	
	@Autowired
    private DaoSupport dao;
	@Autowired
	private JiGuangPushBO jiGuangPushBO;
	@Autowired
	private PropertyConfigurer propertyConfigurer;
	
	/**
	 * @desc 新的订单分派给取派员
	 * @auther zhangjj
	 * @date 2018年5月10日
	 */
	@Async
	public void allocNewOrderDman(Integer userid, Integer orderid) throws Exception {
		try {
			// 组装推送对象
			List<Integer> useridList = new ArrayList<Integer>();
			useridList.add(userid);
			
			// 获取订单信息
			PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById", orderid);
			String orderno = (String)ordermain.get("orderno");
			
			// 话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.ALLOC_NEWORDER_DMAN);
			pushConstentMap.put(PushConstent.ORDERNO_SUFFIX, orderno.substring(orderno.length() - 4, orderno.length()));
			
			// 业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.ORDERNO_EXTRAS, orderno);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.NEWROLE.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.ORDERPAGE);
			cbPush(pushConstentMap, useridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单分配成功发消息异常,请检查", e);
		}
	}
	
	/**
	 * @desc 取消订单
	 * @auther tangqm
	 * @date 2018年5月10日
	 */
	@Async
	public void cancelOrderDman(Integer userid, Integer orderid) throws Exception {
		try {
			// 组装推送对象
			List<Integer> useridList = new ArrayList<Integer>();
			useridList.add(userid);
			
			// 获取订单信息
			PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById", orderid);
			String orderno = (String)ordermain.get("orderno");
			
			// 话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.CANCEL_ORDER);
			pushConstentMap.put(PushConstent.ORDERNO_SUFFIX, orderno.substring(orderno.length() - 4, orderno.length()));
			
			// 业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.ORDERNO_EXTRAS, orderno);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.CANCEL.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.DEFAULTPAGE);
			cbPush(pushConstentMap, useridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单取消发消息异常,请检查", e);
		}
	}

	/**
	 * @desc 订单反馈消息推送
	 * @auther tangqm
	 * @date 2018年12月17日
	 */
	@Async
	public void processPush(Integer userid, Integer orderid) throws Exception {
		try {
			//组装推送对象
			List<Integer> useridList = new ArrayList<Integer>();
			useridList.add(userid);

			//获取订单信息
			PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById", orderid);
			String orderno = (String)ordermain.get("orderno");

			//话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.FEEDBACK);
			pushConstentMap.put(PushConstent.ORDERNO_SUFFIX, orderno.substring(orderno.length() - 4, orderno.length()));

			//业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.ORDERNO_EXTRAS, orderno);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.FEEDBACK.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.FEEDBACKPAGE);
			extras.put(PushConstent.PAGE_PARAMETER,orderid);
			cbPush(pushConstentMap, useridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单取消发消息异常,请检查", e);
		}
	}
	
	/**
	 * @desc 即将达到推送信息
	 * @auther zhangjj
	 * @date 2018年5月10日
	 */
	@Async
	@SuppressWarnings("unchecked")
	public void dmanArrivingPush(Integer roleid, String roletype,  String isfinish, String desttype, String destaddress) throws Exception {
		try {
			// 组装推送对象_集散or机场
			List<Integer> useridList = getuseridListByDestInfo(desttype, destaddress);
			// 为空则不推送消息
			if(CollectionUtils.isEmpty(useridList)) {
				return;
			}
			// 话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.DMAN_ARRIVING);
			UserDeliveryMan userDeliveryMan = (UserDeliveryMan) dao.findForObject("UserDeliveryManMapper.findDmanByuserid", roleid);
			pushConstentMap.put(PushConstent.DMAN_NAME, userDeliveryMan.getName());
			pushConstentMap.put(PushConstent.DMAN_PLATNUM, userDeliveryMan.getPlatenumber());
			OrderRole orderRole = new OrderRole();
			orderRole.setRoleid(roleid);
			orderRole.setRoletype(roletype);
			orderRole.setIsfinish(isfinish);
			orderRole.setDesttype(desttype);
			orderRole.setDestaddress(destaddress);
			Map<String, Integer> map = (Map<String, Integer>) dao.findForObject("OrderRoleMapper.statisticsOrdernumAndLugNum", orderRole);
			pushConstentMap.put(PushConstent.ORDER_NUM, map.get(PushConstent.ORDER_NUM)+"");
			pushConstentMap.put(PushConstent.LUG_NUM, map.get(PushConstent.LUG_NUM)+"");
			// 业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.DMAN_USERID_EXTRAS, roleid);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.COMINGSOON.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.DEFAULTPAGE);
			cbPush(pushConstentMap, useridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单即将达到发消息异常,请检查", e);
		}
	}
	
	/**
	 * @desc 确认到达推送信息
	 * @auther zhangjj
	 * @date 2018年5月10日
	 */
	@Async
	@SuppressWarnings("all")
	public void dmanArrivedPush(Integer roleid, String roletype,  String isfinish, String desttype, String destaddress) throws Exception {
		try{
			// 组装推送对象_集散or机场
			List<Integer> useridList = getuseridListByDestInfo(desttype, destaddress);
			// 为空则不推送消息
			if(CollectionUtils.isEmpty(useridList)) {
				return;
			}
			// 话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.DMAN_ARRIVED);
			UserDeliveryMan userDeliveryMan = (UserDeliveryMan) dao.findForObject("UserDeliveryManMapper.findDmanByuserid", roleid);
			pushConstentMap.put(PushConstent.DMAN_NAME, userDeliveryMan.getName());
			pushConstentMap.put(PushConstent.DMAN_PLATNUM, userDeliveryMan.getPlatenumber());
			OrderRole orderRole = new OrderRole();
			orderRole.setRoleid(roleid);
			orderRole.setRoletype(roletype);
			orderRole.setIsfinish(isfinish);
			orderRole.setDesttype(desttype);
			orderRole.setDestaddress(destaddress);
			Map<String, Integer> map = (Map<String, Integer>) dao.findForObject("OrderRoleMapper.statisticsOrdernumAndLugNum", orderRole);
			pushConstentMap.put(PushConstent.ORDER_NUM, map.get(PushConstent.ORDER_NUM)+"");
			pushConstentMap.put(PushConstent.LUG_NUM, map.get(PushConstent.LUG_NUM)+"");
			
			// 业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.DMAN_USERID_EXTRAS, roleid);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.ARRIVED.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.DEFAULTPAGE);
			cbPush(pushConstentMap, useridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单确认到达发消息异常,请检查", e);
		}
	}
	
	/**
	 * @desc 更改取派员
	 * @auther zhangjj
	 * @date 2018年5月10日
	 */
	public void changeDman(Integer orderid, Integer olddmanuserid, Integer newdmanuserid) throws Exception {
		try{
			// 推送对象
			List<Integer> useridList = new ArrayList<Integer>();
			useridList.add(newdmanuserid);
			
			// 获取订单信息
			PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById", orderid);
			String orderno = (String)ordermain.get("orderno");
			// 话术参数组装
			Map<String, String> pushConstentMap = new HashMap<String, String>();
			pushConstentMap.put(PushConstent.KEY, PushConstent.ALLOC_NEWORDER_DMAN);
			pushConstentMap.put(PushConstent.ORDERNO_SUFFIX, orderno.substring(orderno.length() - 4, orderno.length()));
			// 业务参数
			JSONObject extras = new JSONObject();
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.NEWROLE.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.DEFAULTPAGE);
			cbPush(pushConstentMap, useridList, extras);
			
			// 改派
			Map<String, String> pushUpdateMap = new HashMap<String, String>();
			pushUpdateMap.put(PushConstent.KEY, PushConstent.UPDATE_ROLE);
			pushUpdateMap.put(PushConstent.ORDERNO_SUFFIX, orderno.substring(orderno.length() - 4, orderno.length()));
			List<Integer> oldUseridList = new ArrayList<Integer>();
			oldUseridList.add(olddmanuserid);
			extras.put(PushConstent.INFO_TYPE, VOICEINFO_TYPE.UPDATEROLE.getValue().toLowerCase());
			extras.put(PushConstent.PAGE,PushConstent.DEFAULTPAGE);
			cbPush(pushUpdateMap, oldUseridList, extras);
		} catch(Exception e) {
			LoggerUtil.error("订单更改取派员发消息异常,请检查", e);
		}
	}


			
	/**
	 * @desc 全局推送
	 * @auther sqp
	 * @date 2018年5月22日
	 */
	public void systemPush(List<Integer> useridList, String notice, String theme) throws Exception {		
		pushPersistence(useridList, notice, null, PUSHINFO_TYPE.SYSTEMMSG.getValue(), theme);
	}
	
	/**
     * @desc 插入推送信息
     * @auther zhangjj
     * @date 2018年5月8日
     */
	public void insert(PushInfo pushInfo) throws Exception {
        dao.save("PushInfoMapper.insert", pushInfo);
    }
	
	/**
	 * @desc 根据人员登陆id信息查询推送信息
	 * @auther zhangjj
	 * @date 2018年5月8日
	 */
	@SuppressWarnings("unchecked")
	public List<PushInfo> getPushInfoByUserid(Integer userid, String type) throws Exception{
		PageData pd = new PageData();
		pd.put("userid", userid);
		pd.put("type", type);
		if(PUSHINFO_TYPE.ORDERMSG.getValue().equals(type)){
			pd.put("addtime", DateUtil.getDays());
		}
		return (List<PushInfo>) dao.findForList("PushInfoMapper.findByUserid", pd);
	}		
	
	/**
	 * @desc 更新成已读
	 * @auther zhangjj
	 * @date 2018年5月8日
	 */
	public void updateIsreadToY(Integer userid, Integer id, String type, String isread) throws Exception{
		PageData pd = new PageData();
		pd.put("userid", userid);
		pd.put("id", id);
		pd.put("type", type);
		pd.put("isread", isread);
		dao.update("PushInfoMapper.updateIsreadToY", pd);
	}
	
	/**
	 * @desc 根据人员登陆id信息查询推送信息条数
	 * @auther zhangjj
	 * @date 2018年5月8日
	 */
	@SuppressWarnings("unchecked")
	public List<AppPushNum> countIsread_N(Integer userid) throws Exception{
		return (List<AppPushNum>) dao.findForList("PushInfoMapper.countIsread_N", userid);
	}
	
	/**
	 * 
	 * @Title: pushInfoList
	 * @Description: 查询推送消息列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> pushInfolistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("PushInfoMapper.pushInfolistPage", page);
	}
	
	/**
	 * 
	 * @Title: pushInfoUserList
	 * @Description: 查询推送消息人员列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> pushInfoUserlistPage(Page page) throws Exception{
		return (List<PageData>) dao.findForList("AppUserMapper.pushInfoUserlistPage", page);
	}
	/**
	 * 
	 */
	
	@SuppressWarnings("all")
	public List<Integer> pushInfoUserlistPageallsum() throws Exception{
		return (List<Integer>) dao.findForList("AppUserMapper.pushInfoUserAllDmansum", null);
	}
	@SuppressWarnings("all")
	public List<Integer> pushInfoUserlistPageallqu() throws Exception{
		return (List<Integer>) dao.findForList("AppUserMapper.pushInfoUserAllDmanqu", null);
	}
	@SuppressWarnings("all")
	public List<Integer> pushInfoUserlistPageallji() throws Exception{
		return (List<Integer>) dao.findForList("AppUserMapper.pushInfoUserAllDmanji", null);
	}
	@SuppressWarnings("all")
	public List<Integer> pushInfoUserlistPageallgui() throws Exception{
		return (List<Integer>) dao.findForList("AppUserMapper.pushInfoUserAllDmangui", null);
	}
	
	/**
	 * @desc 推送
	 * @auther zhangjj
	 * @date 2018年5月9日
	 * orderno:订单号;pushCentenKey:推送内容; useridList:推送对象集合;extras:业务数据参数
	 */
	private void cbPush(Map<String, String> pushCentenMap, List<Integer> useridList, JSONObject extras) throws Exception {
		// 推送话术组装	
		String alert = buildTrick(pushCentenMap);
		
		// 消息持久化
		pushPersistence(useridList, alert, extras, PUSHINFO_TYPE.ORDERMSG.getValue(), null);
	}
	
	/**
	 * @desc 组装话术
	 * @auther zhangjj
	 * @date 2018年5月9日
	 */
	private String buildTrick(Map<String, String> pushCentenMap) {
		String alert = propertyConfigurer.getProperty(pushCentenMap.get(PushConstent.KEY));
		Set<String> keySet = pushCentenMap.keySet();
		for (String key : keySet) {
			if(pushCentenMap.get(key) == null) {
				continue;
			}
			alert = alert.replace(key, pushCentenMap.get(key));
		}
		return alert;
	}
	
	/**
	 * @desc 消息持久化
	 * @auther zhangjj
	 * @date 2018年5月23日
	 */
	private void pushPersistence (List<Integer> useridList, String alert, JSONObject extras, String pushtype,String theme) throws Exception {
		// 推送持久化				
		for (Integer userid : useridList) {
			PushInfo pushInfo = new PushInfo();
			pushInfo.setPushcontent(alert);
			pushInfo.setExtras(extras != null? extras.toString():null);
			pushInfo.setUserid(userid);
			pushInfo.setType(pushtype);
			pushInfo.setTheme(theme);
			dao.save("PushInfoMapper.insert", pushInfo);
		}
		// 极光推送  
		jiGuangPushBO.pushRest(useridList, alert, extras);
	}
	
	/**
	 * @desc 查询集散中心的人员userid/查询服务中心的人员userid
	 * @auther zhangjj
	 * @date 2018年5月10日
	 */
	@SuppressWarnings("unchecked")
	private List<Integer> getuseridListByDestInfo(String desttype, String destid) throws Exception {
		List<Integer> useridList = null;
		if(DESTINSATION_TYPE.SERVICECERTER.getValue().equals(desttype)) {
			useridList = (List<Integer>) dao.findForList("CounterServiceCenterMapper.findUseridByServicecenterid", destid);
			return useridList;
		} 
		if(DESTINSATION_TYPE.TRANSITCERTER.getValue().equals(desttype)) {
			useridList = (List<Integer>) dao.findForList("TransitCenterMapper.findUseridByid", destid);
		}
		return useridList;
	}
	
	
	
}
