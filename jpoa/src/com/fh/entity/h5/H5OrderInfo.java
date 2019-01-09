package com.fh.entity.h5;

public class H5OrderInfo {

	/** 航班号 */
	private String flightno;
	/**
	 * 客户id
	 */
	private String cusid;
	/**
	 * 类型编码
	 */
	private String type;
	/**
	 * 类型名称
	 */
	private String typeDesc;
	/**
	 * 状态编码
	 */
	private String status;
	/**
	 * 状态名称
	 */
	private String statusDesc;
	/**
	 * 订单总额
	 */
	private Float totalmoney;
	/**
	 * 减免额
	 */
	private Float cutmoney;
	/**
	 * 实际金额
	 */
	private Float actualmoney;
	/**
	 * 行李数量
	 */
	private Integer num;
	/**
	 * 渠道编码: "weixin"
	 */
	private String channel;

	/**
	 * 备注
	 */
	private String notes;

	/**
	 * 客户名
	 */
	private String cusname;
	/**
	 * 客户手机
	 */
	private String cusmobile;
	
	/**
	 * 寄件人名
	 */
	private String sendername;
	/**
	 * 寄件人手机号码
	 */
	private String senderphone;
	/**
	 * 收件人名
	 */
	private String receivername;
	/**
	 * 收件人手机号码
	 */
	private String receiverphone;
	
	/** 寄件时间 */
	private String taketime;
	/** 收件时间 */
	private String sendtime;
	/**
	 * 寄件方式
	 */
	private String mailingway;
	/** 领会方式 */
	private String backway;
	private String addtime;
	private String orderno;
	private String statuscodename;
	/** 备注 */
	private String remark;
	
	public String getStatuscodename() {
		return statuscodename;
	}

	public void setStatuscodename(String statuscodename) {
		this.statuscodename = statuscodename;
	}

	public String getFlightno() {
		return flightno;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public void setFlightno(String flightno) {
		this.flightno = flightno;
	}

	public String getCusid() {
		return cusid;
	}

	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Float getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}

	public Float getCutmoney() {
		return cutmoney;
	}

	public void setCutmoney(Float cutmoney) {
		this.cutmoney = cutmoney;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getCusmobile() {
		return cusmobile;
	}

	public void setCusmobile(String cusmobile) {
		this.cusmobile = cusmobile;
	}

	public Float getActualmoney() {
		return actualmoney;
	}

	public void setActualmoney(Float actualmoney) {
		this.actualmoney = actualmoney;
	}

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getSenderphone() {
		return senderphone;
	}

	public void setSenderphone(String senderphone) {
		this.senderphone = senderphone;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}

	public String getReceiverphone() {
		return receiverphone;
	}

	public void setReceiverphone(String receiverphone) {
		this.receiverphone = receiverphone;
	}

	public String getMailingway() {
		return mailingway;
	}

	public void setMailingway(String mailingway) {
		this.mailingway = mailingway;
	}

	public String getTaketime() {
		return taketime;
	}

	public void setTaketime(String taketime) {
		this.taketime = taketime;
	}

	public String getSendtime() {
		return sendtime;
	}

	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

	public String getBackway() {
		return backway;
	}

	public void setBackway(String backway) {
		this.backway = backway;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
