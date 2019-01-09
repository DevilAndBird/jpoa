package com.fh.service.controlplatfrom.dmanlist;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
public class DmanListService {
	@Resource
	private DaoSupport dao;
	
    /**
     * @desc 目的地为住宅和酒店的取件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
	public List<PageData> findTaskAddress_HOTELOROTHER() throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findTaskAddress_HOTELOROTHER", null);
    }
    
    /**
     * @desc 目的地为住宅和酒店的送件地址
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findSendAddress_HOTELOROTHER() throws Exception {
         return (List<PageData>) dao.findForList("OrderRoleMapper.findSendAddress_HOTELOROTHER", null);
    }
    
    /**
     * @desc 任务订单数查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public PageData findTaskOrderNum(PageData reqpd) throws Exception {
    	 PageData taskOrderNum = new PageData();
    	 List<PageData> transitList = (List<PageData>) dao.findForList("TransitCenterMapper.findAll", reqpd);
    	 List<PageData> counterList = (List<PageData>) dao.findForList("CounterServiceCenterMapper.findAll", reqpd);
    	 
    	 List<PageData> transitTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.transitTask", null);
    	 List<PageData> transitSendList = (List<PageData>) dao.findForList("OrderRoleMapper.transitSend", null);
    	 List<PageData> counterTaskList = (List<PageData>) dao.findForList("OrderRoleMapper.counterTask", null);
    	 List<PageData> counterSendList = (List<PageData>) dao.findForList("OrderRoleMapper.counterSend", null);
    	 
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
    
    
}
