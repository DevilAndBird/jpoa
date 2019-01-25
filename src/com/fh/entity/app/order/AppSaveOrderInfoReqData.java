package com.fh.entity.app.order;

import com.fh.entity.customer.CusInfo;
import com.fh.entity.delivery.AppUser;
import com.fh.entity.order.*;

import java.util.List;

public class AppSaveOrderInfoReqData {

	/** 订单基础信息 */
	private OrderMainSpec orderMainSpec;
	/** 订单地址 */
	private OrderAddress orderAddress;
	/** 订单保价 */
	private OrderInsureInfo orderInsureInfo;
	/** 订单寄收人信息 */
	private OrderSenderReceiver orderSenderReceiver;
	/** 备注信息 */
	private OrderNotesInfo orderNotesInfo;
	/** 航班信息 */
	private OrderFlight orderFlight;
	/** 客户信息 */
	private CusInfo cusInfo;

	/**
	 * qr + 图片url
	 */
	private List<OrderBaggageReqData> orderBaggageReqDataList;

	/* 下单人员信息 */
	private OrderRole orderrole;


	/**
	 * 支付信息
	 */
	private OrderPayInfo orderPayInfo;

	public OrderMainSpec getOrderMainSpec() {
		return orderMainSpec;
	}

	public void setOrderMainSpec(OrderMainSpec orderMainSpec) {
		this.orderMainSpec = orderMainSpec;
	}

	public OrderAddress getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}

	public OrderInsureInfo getOrderInsureInfo() {
		return orderInsureInfo;
	}

	public void setOrderInsureInfo(OrderInsureInfo orderInsureInfo) {
		this.orderInsureInfo = orderInsureInfo;
	}

	public OrderSenderReceiver getOrderSenderReceiver() {
		return orderSenderReceiver;
	}

	public void setOrderSenderReceiver(OrderSenderReceiver orderSenderReceiver) {
		this.orderSenderReceiver = orderSenderReceiver;
	}

	public OrderNotesInfo getOrderNotesInfo() {
		return orderNotesInfo;
	}

	public void setOrderNotesInfo(OrderNotesInfo orderNotesInfo) {
		this.orderNotesInfo = orderNotesInfo;
	}

	public OrderFlight getOrderFlight() {
		return orderFlight;
	}

	public void setOrderFlight(OrderFlight orderFlight) {
		this.orderFlight = orderFlight;
	}

	public CusInfo getCusInfo() {
		return cusInfo;
	}

	public void setCusInfo(CusInfo cusInfo) {
		this.cusInfo = cusInfo;
	}

	public OrderPayInfo getOrderPayInfo() {
		return orderPayInfo;
	}

	public void setOrderPayInfo(OrderPayInfo orderPayInfo) {
		this.orderPayInfo = orderPayInfo;
	}

	public List<OrderBaggageReqData> getOrderBaggageReqDataList() {
		return orderBaggageReqDataList;
	}

	public void setOrderBaggageReqDataList(List<OrderBaggageReqData> orderBaggageReqDataList) {
		this.orderBaggageReqDataList = orderBaggageReqDataList;
	}

	public OrderRole getOrderrole() {
		return orderrole;
	}

	public void setOrderrole(OrderRole orderrole) {
		this.orderrole = orderrole;
	}
}