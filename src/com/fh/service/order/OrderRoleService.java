package com.fh.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.util.PageData;

/**
 * QR码+图片路径
 */
@Service
public class OrderRoleService {
	
	@Autowired
    private DaoSupport dao;
	@Autowired
	private PushInfoService pushInfoService;
	

	/**
	 * 修改动作地址
	 */
	public void updatedestbyid(PageData pd) throws Exception {
		// 更改动作地址信息
		dao.update("OrderRoleMapper.updatedestbyid", pd);
		
		// 判断是否出发地
		if(pd.getString("roletype").indexOf("TASK") != -1 && !ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(pd.getString("roletype"))) {
			PageData taskReq = new PageData();
			taskReq.put("orderid", pd.getString("orderid"));
			taskReq.put("srcaddrtype", pd.getString("desttype"));
			taskReq.put("scrlandmark", pd.getString("addrname"));
			taskReq.put("srcaddress", pd.getString("addrdesc"));
			taskReq.put("srcgps", pd.getString("destgps"));
			dao.update("orderAddressMapper.updateSendinfo", taskReq);
		}
		// 判断是否为目的地
		if(pd.getString("roletype").indexOf("SEND") != -1 && !ROLE_TYPE.ROLE_TRANSIT_SEND.getValue().equals(pd.getString("roletype"))) {
			PageData sendReq = new PageData();
			sendReq.put("orderid", pd.getString("orderid"));
			sendReq.put("destaddrtype", pd.getString("desttype"));
			sendReq.put("destlandmark", pd.getString("addrname"));
			sendReq.put("destaddress", pd.getString("addrdesc"));
			sendReq.put("destgps", pd.getString("destgps"));
			dao.update("orderAddressMapper.updatedDestinfo", sendReq);
		}
	}
	
	/**
	 * 修改到达时间
	 */
	public void updatearrivedtimebyid(PageData pd) throws Exception {
		dao.update("OrderRoleMapper.updatearrivedtimebyid", pd);
		
		// 订单寄件时间
		if(pd.getString("roletype").indexOf("TASK") != -1 && !ROLE_TYPE.ROLE_TRANSIT_TASK.getValue().equals(pd.getString("roletype"))) {
			PageData taskReq = new PageData();
			taskReq.put("orderid", pd.getString("orderid"));
			taskReq.put("taketime", pd.getString("arrivedtime"));
			dao.update("OrderMainMapper.updatetaketime", taskReq);
		}
		// 判断收件出发地
		if(pd.getString("roletype").indexOf("SEND") != -1 && !ROLE_TYPE.ROLE_TRANSIT_SEND.getValue().equals(pd.getString("roletype"))) {
			PageData sendReq = new PageData();
			sendReq.put("orderid", pd.getString("orderid"));
			sendReq.put("sendtime", pd.getString("arrivedtime"));
			dao.update("OrderMainMapper.updatesendtime", sendReq);
		}
	}
	
	/**
	 *  更改取派员
	 */
	public void ichangedman(PageData pd) throws Exception {
		PageData roleinfo = (com.fh.util.PageData) dao.findForObject("OrderRoleMapper.findRoleidByOrderid", pd);
		Integer roleid = (Integer) roleinfo.get("roleid");
		dao.update("OrderRoleMapper.ichangedman", pd);
		pushInfoService.changeDman( Integer.parseInt(roleinfo.get("orderid").toString()), roleid, Integer.parseInt(pd.get("roleid").toString()));
	}
	
	/**
	 * 检验退款
	 */
	@SuppressWarnings("unchecked")
	public boolean checkRefund(String orderno) throws Exception {
		boolean reFundflag = true; 
		List<PageData> checkRefund = (List<PageData>) dao.findForList("OrderRoleMapper.checkRefund", orderno);
		if(CollectionUtils.isEmpty(checkRefund)){
			reFundflag = false;
		}
		return reFundflag;
	}
	
	/**
	 * 获取订单路径地址
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findOrderPath(Integer orderid) throws Exception {
		return (List<PageData>) dao.findForList("OrderRoleMapper.findOrderPath", orderid);
	}
	
	/**
	 * 获取订单途径路径
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> orderPathWay(Integer orderid) throws Exception {
		return (List<PageData>) dao.findForList("OrderRoleMapper.orderPathWay", orderid);
	}
	
	/**
	 * 删除订单下的分派取派员信息
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> delectByOrderidAndRoleid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("OrderRoleMapper.delectOrderRoleByOrderidAndRoleid", pd);
	}
	
	/**
	 * 订单所经历的取派员
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findOrderlinkdman(Integer orderid) throws Exception {
		return (List<PageData>) dao.findForList("OrderRoleMapper.findOrderlinkdman", orderid);
	}
	
	/**
	 * 查询取派员待取订单
	 */
	public PageData findDmanOrderTask(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderTask", pd);
	}
	
	/**
	 * 查询取派员待派订单
	 */
	public PageData findDmanOrderSend(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderSend", pd);
	}
	
	/**
	 * @desc 判断订单动作是否存在
	 * @auther zhangjj
	 * @date 2018年8月10日
	 */
	public PageData checkActiveIsExist(Integer orderid, String roletype) throws Exception {
		PageData pd = new PageData();
		pd.put("orderid", orderid);
		pd.put("roletype", roletype);
		return (PageData) dao.findForObject("OrderRoleMapper.checkActiveIsExist", pd);
	}
	
}
