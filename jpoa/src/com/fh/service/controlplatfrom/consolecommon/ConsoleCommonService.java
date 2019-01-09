package com.fh.service.controlplatfrom.consolecommon;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
public class ConsoleCommonService {
	@Resource
	private DaoSupport dao;
	
    /**
     * @desc 集散中心/柜台任务订单数查询
     * @auther zhangjj
     * @date 2018年5月17日
     * @reqparam loginperson_provid、loginperson_cityid、dmanuserid
     */
    @SuppressWarnings("unchecked")
    public PageData findTaskOrderNum(PageData reqpd) throws Exception {
    	 PageData taskOrderNum = new PageData();
    	 List<PageData> transitList = (List<PageData>) dao.findForList("TransitCenterMapper.findAll", reqpd);
    	 List<PageData> counterList = (List<PageData>) dao.findForList("CounterServiceCenterMapper.findAll", reqpd);
    	 
    	 List<PageData> transitTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.transitTask", reqpd);
    	 List<PageData> transitSendList = (List<PageData>) dao.findForList("OrderRoleMapper.transitSend", reqpd);
    	 List<PageData> counterTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.counterTask", reqpd);
    	 List<PageData> counterSendList = (List<PageData>) dao.findForList("OrderRoleMapper.counterSend", reqpd);
    	 
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
     * @desc 目的地为住宅和酒店的取件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
	public List<PageData> findTaskAddress_HOTELOROTHER(PageData pd) throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findTaskAddress_HOTELOROTHER", pd);
    }
    
    /**
     * @desc 目的地为住宅和酒店的送件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findSendAddress_HOTELOROTHER(PageData pd) throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findSendAddress_HOTELOROTHER", pd);
    }
    
    
    /**
     * @desc 订单运输路线其中一个取派员运输路径查询
     * @auther zhangjj
     * @date 2018年5月17日
     * @param orderid、dmanuserid
     */
	public PageData finddmanpathbaseinfo(PageData reqParam) throws Exception {
		
    	PageData orderallocfillpage = new PageData();
    	
    	// 订单的基本信息
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
		
    	return orderallocfillpage;
    }
}
