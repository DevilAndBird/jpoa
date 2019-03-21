package com.fh.entity.h5;

public class H5OrderInfoBean {

	/**
	 * 订单id
	 */
	private Integer id;
	/**
	 * 行李编号
	 */
	private String orderno;
	/** 渠道 */
	private String channel;
	/**
	 * 客户id
	 */
	private String cusid;
	/**
	 * 行李数量
	 */
	private Integer num;
	/**
	 * 取件时间
	 */
	private String taketime;
	/**
	 * 寄件时间
	 */
	private String sendtime;

	/**
	 * 实际金额
	 */
	private Float actualmoney;
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
	/** 出发地类型 */
	private String srcaddrtype;
	/** 目的地类型 */
	private String destaddrtype;
	/** 出发地标志 */
	private String scrlandmark;
	/** 目的地标志 */
	private String destlandmark;
	/** 出发地址  */
	private String srcaddress;
	/** 目的地址 */
	private String destaddress;
	/** 备注 */
	private String remark;
	/**
	 * 保险
	 */
	private String insurecode;
	/**
	 * 寄件方式
	 */
	private String mailingway;
	/**
	 * 代寄人姓名
	 */
	private String realname;
	/**
	 * 代寄人手机号码
	 */
	private String realphone;
	/** 
	 * 订单状态类型 
	 */
	private String orderstatusType;
	/** 
	 * 是否开发票 
	 */
	private String neadinvoice;

	private String payStatus;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public String getSrcaddress() {
		return srcaddress;
	}
	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}
	public String getDestaddress() {
		return destaddress;
	}
	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}
	public String getInsurecode() {
		return insurecode;
	}
	public void setInsurecode(String insurecode) {
		this.insurecode = insurecode;
	}
	public String getMailingway() {
		return mailingway;
	}
	public void setMailingway(String mailingway) {
		this.mailingway = mailingway;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getRealphone() {
		return realphone;
	}
	public void setRealphone(String realphone) {
		this.realphone = realphone;
	}
	public String getOrderstatusType() {
		return orderstatusType;
	}
	public void setOrderstatusType(String orderstatusType) {
		this.orderstatusType = orderstatusType;
	}
	public String getNeadinvoice() {
		return neadinvoice;
	}
	public void setNeadinvoice(String neadinvoice) {
		this.neadinvoice = neadinvoice;
	}
	public String getScrlandmark() {
		return scrlandmark;
	}
	public void setScrlandmark(String scrlandmark) {
		this.scrlandmark = scrlandmark;
	}
	public String getDestlandmark() {
		return destlandmark;
	}
	public void setDestlandmark(String destlandmark) {
		this.destlandmark = destlandmark;
	}
	public String getSrcaddrtype() {
		return srcaddrtype;
	}
	public void setSrcaddrtype(String srcaddrtype) {
		this.srcaddrtype = srcaddrtype;
	}
	public String getDestaddrtype() {
		return destaddrtype;
	}
	public void setDestaddrtype(String destaddrtype) {
		this.destaddrtype = destaddrtype;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}
