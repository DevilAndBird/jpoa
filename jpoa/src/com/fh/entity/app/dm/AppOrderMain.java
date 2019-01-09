package com.fh.entity.app.dm;

public class AppOrderMain {
	/** 订单id */
	private Integer orderid;
	/** 出发地id */
	private String srcAddr;
	/** 目的地id */
	private String destAddr;
	/** 订单状态 */
	private String orderStatus;
	/** 时间戳 */
	private String timeStr;	//时间
	/** 订单类型:1、订单任务  2、班车任务 */
	private Integer type;		
	/** 行李数量 */
	private Integer num;		
	
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getSrcAddr() {
		return srcAddr;
	}
	public void setSrcAddr(String srcAddr) {
		this.srcAddr = srcAddr;
	}
	public String getDestAddr() {
		return destAddr;
	}
	public void setDestAddr(String destAddr) {
		this.destAddr = destAddr;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
}
