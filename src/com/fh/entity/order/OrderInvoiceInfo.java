package com.fh.entity.order;

import java.util.Date;

/**
 * 订单发条
 * 
 * @author tangqm
 */
public class OrderInvoiceInfo {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 订单id
	 */
	private Integer orderid;
	/**
	 * 添加时间
	 */
	private String addtime;
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
	 * 开票时间
	 */
	private Date invoicetime;
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
	/** 银行类型 */
	private String banktype;
	/** 银行卡类型 */
	private String bankno;
	/** 纳税人地址 */
	private String addr;
	/** 纳税人电话 */
	private String phone;
	/** 寄送地址 */
	private String sendname;
	/** 寄送地址 */
	private String sendphone;
	/** 寄送地址 */
	private String sendaddr;
	/** 门牌号 */
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

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
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

	public Date getInvoicetime() {
		return invoicetime;
	}

	public void setInvoicetime(Date invoicetime) {
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

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Override
	public String toString() {
		return "OrderInvoiceInfo [id=" + id + ", orderid=" + orderid
				+ ", addtime=" + addtime + ", title=" + title + ", taxno="
				+ taxno + ", invoicetype=" + invoicetype + ", status=" + status
				+ ", invoicetime=" + invoicetime + ", serialno=" + serialno
				+ ", email=" + email + ", money=" + money + ", content="
				+ content + ", notes=" + notes + ", banktype=" + banktype
				+ ", bankno=" + bankno + ", addr=" + addr + ", phone=" + phone
				+ ", sendname=" + sendname + ", sendphone=" + sendphone
				+ ", sendaddr=" + sendaddr + ", housenum=" + housenum + "]";
	}
	
	

}