package com.fh.service.order.orderinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.order.OrderSenderReceiver;

/**
 * 订单收寄人服务类
 * @author chenyushi
 *
 */
@Service
public class OrderSenderReceiverService {

	@Autowired
    private DaoSupport dao;
	
	/**
	 * @Title: saveOrderSenderReceiver
	 * @Description: 保存订单的收寄人
	 * @param int
	 * @return
	 * @throws Exception
	 * 陈玉石
	 * 2018年3月4日15:53:50
	 */
	public int save(OrderSenderReceiver obj) throws Exception {
		return (int) dao.save("OrderSenderReceiverMapper.insert", obj);
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
	public OrderSenderReceiver getByOrderId(Integer orderId) throws Exception {
		return (OrderSenderReceiver)dao.save("OrderSenderReceiverMapper.findByOrderid", orderId);
	}
}
