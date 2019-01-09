package com.fh.entity.h5;


public class H5OrderMain {
	/** 订单信息 */
	private H5OrderInfo orderMain;
	/** 订单地址 */
	private H5OrderAddress orderAddr;
	/** 订单保险信息 */
	private H5OrderInsure orderInsure;
	/** 寄件人收件人信息 */
	private H5OrderSenderReceiver senderReceiver;
	/** 备注信息 */
	private H5OrderNotesInfo h5OrderNotesInfo;
	/** 优惠卷信息 */
	private H5Counponinfo h5Counponinfo;

	public H5OrderSenderReceiver getSenderReceiver() {
		return senderReceiver;
	}

	public void setSenderReceiver(H5OrderSenderReceiver senderReceiver) {
		this.senderReceiver = senderReceiver;
	}

	public H5OrderAddress getOrderAddr() {
		return orderAddr;
	}

	public void setOrderAddr(H5OrderAddress orderAddr) {
		this.orderAddr = orderAddr;
	}

	public H5OrderInfo getOrderMain() {
		return orderMain;
	}

	public void setOrderMain(H5OrderInfo orderMain) {
		this.orderMain = orderMain;
	}

	public H5OrderInsure getOrderInsure() {
		return orderInsure;
	}

	public void setOrderInsure(H5OrderInsure orderInsure) {
		this.orderInsure = orderInsure;
	}

	public H5OrderNotesInfo getH5OrderNotesInfo() {
		return h5OrderNotesInfo;
	}

	public void setH5OrderNotesInfo(H5OrderNotesInfo h5OrderNotesInfo) {
		this.h5OrderNotesInfo = h5OrderNotesInfo;
	}

	public H5Counponinfo getH5Counponinfo() {
		return h5Counponinfo;
	}

	public void setH5Counponinfo(H5Counponinfo h5Counponinfo) {
		this.h5Counponinfo = h5Counponinfo;
	}

}
