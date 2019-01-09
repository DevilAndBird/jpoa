package com.fh.entity.h5;

public class H5OrderInvoiceInfoReqBean {
	/**
	 * 发票id
	 */
	private Integer id;
	/**
	 * 订单id
	 */
	private Integer orderid;
	/**
	 * 发票抬头
	 */
	private String title;
	/**
	 * 税号
	 */	
	private String taxno;
	/**
	 * 发票类型
	 */
	private String invoicetype;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 发票时间
	 */
	private String invoicetime;
	/**
	 * 序列号
	 */
	private String serialno;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 金额
	 */
	private Float money;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 添加时间 
	 */
	private String addtime;
	/** 收件人姓名 */
	private String sendname;
	/** 收件人手机号 */
	private String sendphone;
	/** 寄送地址 */
	private String sendaddr;
	/** 门牌号信息 */
	private String housenum;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTaxno() {
		return taxno;
	}
	public void setTaxno(String taxno) {
		this.taxno = taxno;
	}
	public String getInvoicetype() {
		return invoicetype;
	}
	public void setInvoicetype(String invoicetype) {
		this.invoicetype = invoicetype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvoicetime() {
		return invoicetime;
	}
	public void setInvoicetime(String invoicetime) {
		this.invoicetime = invoicetime;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getSendaddr() {
		return sendaddr;
	}
	public void setSendaddr(String sendaddr) {
		this.sendaddr = sendaddr;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getSendphone() {
		return sendphone;
	}
	public void setSendphone(String sendphone) {
		this.sendphone = sendphone;
	}
	public String getHousenum() {
		return housenum;
	}
	public void setHousenum(String housenum) {
		this.housenum = housenum;
	}
}
