package com.fh.service.controlplatfrom.dmandelis;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.dao.DaoSupport;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;

@Service
public class DmanDetailsService {
	@Resource
	private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
    /**
     * @desc 目的地为住宅和酒店的取件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
	public List<PageData> findTaskAddress_HOTELOROTHERByuserid(Integer dmanuserid) throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findTaskAddress_HOTELOROTHER", dmanuserid);
    }
    
    /**
     * @desc 目的地为住宅和酒店的送件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findSendAddress_HOTELOROTHERByDmanuserid(Integer dmanuserid) throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findSendAddress_HOTELOROTHER", dmanuserid);
    }
    
    
    
    /**
     * @desc 任务订单数查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    
    @SuppressWarnings("unchecked")
    public PageData findTaskOrderNumByDmanuserid(Integer dmanuserid,PageData reqpd) throws Exception {
    	 PageData taskOrderNum = new PageData();
    	
    	 List<PageData> transitList = (List<PageData>) dao.findForList("TransitCenterMapper.findAll", reqpd);
    	 List<PageData> counterList = (List<PageData>) dao.findForList("CounterServiceCenterMapper.findAll", reqpd);
    	 
    	 List<PageData> transitTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.transitTask", dmanuserid);
    	 List<PageData> transitSendList = (List<PageData>) dao.findForList("OrderRoleMapper.transitSend", dmanuserid);
    	 List<PageData> counterTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.counterTask", dmanuserid);
    	 List<PageData> counterSendList = (List<PageData>) dao.findForList("OrderRoleMapper.counterSend", dmanuserid);
    	 
    	 for (PageData transit : transitList) {
    		 String transitid = ((Integer) transit.get("id")).toString();
    		 
    		 transit.put("task_count", 0);
    		 if(CollectionUtils.isNotEmpty(transitTaskList)) {
    			 for (PageData transitTask : transitTaskList) {
					if(transitid.equals(transitTask.get("destaddress"))) {
						transit.put("task_count", transitTask.get("task_count"));
						break;
					}
	    		 }
    		 }
    		 
    		 transit.put("send_count", 0);
    		 if(CollectionUtils.isNotEmpty(transitSendList)) {
    			 for (PageData transitSend : transitSendList) {
	 				if(transitid.equals(transitSend.get("destaddress"))) {
	 					transit.put("send_count", transitSend.get("send_count"));
	 					break;
	 				}
	     		 }
    		 }
		 }
    	 
    	 for (PageData counter : counterList) {
    		 String counterid = ((Integer) counter.get("id")).toString();
    		 
    		 counter.put("task_count", 0);
    		 if(CollectionUtils.isNotEmpty(counterTaskList)) {
    			 for (PageData counterTask : counterTaskList) {
					if(counterid.equals(counterTask.get("destaddress"))) {
						counter.put("task_count", counterTask.get("task_count"));
						break;
					}
	    		 }
    		 }
    		 
    		 counter.put("send_count", 0);
    		 if(CollectionUtils.isNotEmpty(counterSendList)) {
    			 for (PageData counterSend : counterSendList) {
	 				if(counterid.equals(counterSend.get("destaddress"))) {
	 					counter.put("send_count", counterSend.get("send_count"));
	 					break;
	 				}
	     		 }
    		 }
		 }
    	 
    	 taskOrderNum.put("transitList", transitList);
    	 taskOrderNum.put("counterList", counterList);
    	
         return taskOrderNum;
    }
    
    /**
     * @desc 任务订单数查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findDmanTodayOrderInfo(PageData reqParam) throws Exception {
    	Integer dmanuserid = Integer.parseInt((String) reqParam.get("dmanuserid"));
    	List<String> isfinishList = JSONArray.parseArray((String) reqParam.get("isfinishList"), String.class);
    	reqParam.put("isfinishList", isfinishList);
    	// 特殊处理 处理 已完成订单
		if(isfinishList.contains(IS_FINISH.FINISHED.getValue())) {
			reqParam.put("isfinishFlag", IS_FINISH.FINISHED.getValue());
		}
    	
    	// 取派员当天订单信息 
    	List<PageData> dmanTodayOrderInfo = (List<PageData>) dao.findForList("OrderRoleMapper.findDmanTodayOrderInfo", reqParam);
    	
    	if(CollectionUtils.isEmpty(dmanTodayOrderInfo)) {
    		return new ArrayList<PageData>();
    	}
    	
    	for (int i = 0; i< dmanTodayOrderInfo.size(); i++) {
    		PageData pageData = dmanTodayOrderInfo.get(i);
    		
    		PageData req = new PageData();
    		req.put("dmanuserid", dmanuserid);
    		req.put("orderid", (Integer) pageData.get("orderid"));
    		
    		// 取件动作
    		PageData dmanOrderTask = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderTask", req);
    		pageData.put("dmanOrderTask", "");
    		if(dmanOrderTask != null) {
    			pageData.put("dmanOrderTask", dmanOrderTask);
    		}
    		
    		
    		// 派件动作
     		PageData dmanOrderSend = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderSend", req);
            pageData.put("dmanOrderSend", "");
    		if(dmanOrderSend != null) {
    			pageData.put("dmanOrderSend", dmanOrderSend);
    		} 
    		
		}
    	
    	return dmanTodayOrderInfo;
    }
    
    /**
     * @desc 集散中心后下个取派员的路径问题
     * @auther zhangjj
     * @date 2018年5月17日
     */
    public PageData findOrderTransitLaterPath(PageData reqParam) throws Exception {
    	
    	PageData nextDmanPath = new PageData();
    	
    	// 查询一个订单中转集散后下个取派员id
    	Integer nextDmanUserid = (Integer) dao.findForObject("OrderRoleMapper.findOrderTransitLaterUseridInfo", reqParam);
    	
    	// 未查询到下个取派员
    	if(nextDmanUserid == null || nextDmanUserid == 0) {
    		LoggerUtil.warn("订单id：" + reqParam.get("orderid") + ",集散中心后未分配取派员请检查");
    		return nextDmanPath;
    	}
    	
    	PageData req = new PageData();
    	req.put("orderid", reqParam.get("orderid"));
    	req.put("dmanuserid", nextDmanUserid);
    	
    	PageData dmanOrderTask = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderTask", req);
    	nextDmanPath.put("dmanOrderTask", "");
		if(dmanOrderTask != null) {
			nextDmanPath.put("dmanOrderTask", dmanOrderTask);
		}
		
		PageData dmanOrderSend = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderSend", req);
		nextDmanPath.put("dmanOrderSend", "");
		if(dmanOrderSend != null) {
			nextDmanPath.put("dmanOrderSend", dmanOrderSend);
		}
		
    	return nextDmanPath;
    }
    
    /**
     * @desc 订单分配列表填充数据查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
	public PageData orderallocfillpage(PageData reqParam) throws Exception {
		
    	PageData orderallocfillpage = new PageData();
    	
    	PageData ordermain = (PageData) dao.findForObject("OrderMainMapper.getById", Integer.parseInt((String)reqParam.getString("orderid")));
    	orderallocfillpage.put("ordermain", ordermain);
    	
		PageData dmanOrderTask = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderTask", reqParam);
		orderallocfillpage.put("dmanOrderTask", "");
		if(dmanOrderTask != null) {
			orderallocfillpage.put("dmanOrderTask", dmanOrderTask);
		}
		
		PageData dmanOrderSend = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderSend", reqParam);
		orderallocfillpage.put("dmanOrderSend", "");
		if(dmanOrderSend != null) {
			orderallocfillpage.put("dmanOrderSend", dmanOrderSend);
		}
    	
//		PageData req = new PageData();//TODO 这个是否有必要查询
//		req.put("divdmanuserid", reqParam.get("dmanuserid"));
//		req.put("loginperson_provid", reqParam.get("loginperson_provid"));
//		req.put("loginperson_cityid", reqParam.get("loginperson_cityid"));
//	 	List<PageData> changeDmanList = (List<PageData>) dao.findForList("UserDeliveryManMapper.findDmanList_contralplatform", req);
//	 	
//	 	orderallocfillpage.put("changeDmanList", changeDmanList);
		
    	return orderallocfillpage;
    }
    
}
