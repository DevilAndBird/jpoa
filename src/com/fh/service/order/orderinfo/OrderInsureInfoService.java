package com.fh.service.order.orderinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.order.OrderInsureInfo;
import com.fh.util.PageData;

/**
 * 订单保险信息服务类
 * @author chenyushi
 *
 */
@Service
public class OrderInsureInfoService {
	@Autowired
    private DaoSupport dao;
	
	/**
	 * @Title: saveOrderSenderReceiver
	 * @Description: 保存订单的保险信息
	 * @param int
	 * @return
	 * @throws Exception
	 * 陈玉石
	 * 2018年3月4日15:53:50
	 */
	public int save(OrderInsureInfo obj) throws Exception {
		return (int) dao.save( "OrderInsureInfoMapper.insert", obj );
	}
	
	/**
	 * @Title: getByOrderId
	 * @Description: 获取订单的收寄人信息
	 * @param int
	 * @return
	 * @throws Exception
	 * 陈玉石
	 * 2018年3月4日15:53:46
	 */
	public OrderInsureInfo getByOrderId(Integer orderId) throws Exception {
		PageData pd = new PageData();
		pd.put( "orderid" , orderId);
		return (OrderInsureInfo)dao.findForObject("OrderInsureInfoMapper.findByOrderid", pd );
	}
}
