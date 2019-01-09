package com.fh.entity.order;

import java.io.Serializable;

/**
 * 订单角色动作
 * 
 * @author tangqm
 * @date 2018年12月25日
 */
public class OrderPayInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	private Integer id;
	/** 订单id */
	private Integer orderid;
	/** 金额 */
	private Float money;
	/** 状态 */
	private String status;
	/** 类型 */
	private String type;
	/** 支付时间 */
	private String paytime;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}


}