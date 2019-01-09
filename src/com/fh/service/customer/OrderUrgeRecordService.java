package com.fh.service.customer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.customer.OrderUrgeRecord;
import com.fh.util.PageData;

@Service
public class OrderUrgeRecordService {

	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增催单信息
	 * @param record
	 */
	public void insert( OrderUrgeRecord obj )throws Exception
	{
		dao.save( "OrderUrgeRecordMapper.insert", obj );
	}
	
	/**
	 * 催单记录列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<OrderUrgeRecord> orderUrgeRecordList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String dmanname = pd.getString("dmanname");
		String beginDate = pd.getString("beginDate");
		String endDate = pd.getString("endDate");
		if( null != dmanname ){
			pd.put("dmanname", dmanname.replace(" ", ""));
		}
		if( !StringUtils.isEmpty(beginDate) ){
			pd.put("beginDate", beginDate+" 00:00:00");
		}
		if( !StringUtils.isEmpty(endDate) ){
			pd.put("endDate", endDate+" 23:59:59");
		}
		page.setPd(pd);
		return (List<OrderUrgeRecord>)dao.findForList("OrderUrgeRecordMapper.orderUrgeRecordlistPage", page);
	}
	
}
