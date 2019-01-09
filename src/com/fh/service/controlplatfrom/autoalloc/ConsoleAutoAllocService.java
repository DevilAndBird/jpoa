package com.fh.service.controlplatfrom.autoalloc;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.service.order.OrderRoleService;
import com.fh.util.PageData;

@Service
public class ConsoleAutoAllocService {
	@Resource
	private DaoSupport dao;
	@Autowired
	private OrderRoleService orderRoleService;
	
    /**
     * @desc 自动分配查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public List<PageData> findAutoAllotlist(PageData pd) throws Exception {
    	List<PageData> autoallotlist = (List<PageData>) dao.findForList("OrderMainMapper.findAutoAllotlist", pd);
    	
    	/*for (PageData autoallot : autoallotlist) {
			// 特殊处理如果是柜台，则判断是否已取件
    		autoallot.put("airport_tasked_flag", "");
    		String srcaddrtype = autoallot.getString("srcaddrtype");
    		if(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(srcaddrtype)) {
    			PageData checkres = orderRoleService.checkActiveIsExist((Integer)autoallot.get("id"), ROLE_TYPE.ROLE_AIRPORT_TASKED.getValue());
    			autoallot.put("airport_tasked_flag", checkres != null? "Y":"N");
    		}
		}*/
    	
    	return autoallotlist;
    }
    
    /**
     * @desc 自动分配查询
     * @auther zhangjj
     * @date 2018年5月17日
     */
    @SuppressWarnings("unchecked")
    public PageData orderpathdetails(PageData pd) throws Exception {
    	Integer orderid = Integer.parseInt((String)pd.get("orderid"));
    	    	
    	PageData orderinfo = (PageData) dao.findForObject("OrderMainMapper.findorderandcus", orderid);
    	
    	List<PageData> orderlinkdmanList = (List<PageData>) dao.findForList("OrderRoleMapper.findOrderlinkdman", orderid);
    	orderinfo.put("orderlinkdmanList", "");
    	if(CollectionUtils.isEmpty(orderlinkdmanList)) {
    		return orderinfo;
    	}
    	orderinfo.put("orderlinkdmanList", orderlinkdmanList);
    	
		for (PageData orderlinkdman : orderlinkdmanList) {
			// 取派员信息
			PageData reqtemp = new PageData();
			reqtemp.put("dmanuserid", orderlinkdman.get("roleid"));
			reqtemp.put("loginperson_provid", pd.get("loginperson_provid"));
			reqtemp.put("loginperson_cityid", pd.get("loginperson_cityid"));
			List<PageData> dmaninfo = (List<PageData>) dao.findForList("UserDeliveryManMapper.findDmanList_contralplatform", reqtemp);
			orderlinkdman.put("dmaninfo", dmaninfo.get(0));
			
			// 取件信息
			PageData reqtemp2 = new PageData();
			reqtemp2.put("dmanuserid", orderlinkdman.get("roleid"));
			reqtemp2.put("orderid", orderid);
			PageData task = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderTask", reqtemp2);
			PageData send = (PageData) dao.findForObject("OrderRoleMapper.findDmanOrderSend", reqtemp2);
			orderlinkdman.put("task", "");
			orderlinkdman.put("send", "");
			if(task != null) {
				orderlinkdman.put("task", task);
			}
			if(send != null) {
				orderlinkdman.put("send", send);
			}
		}
    	
    	return orderinfo;
    }
    
  
}
