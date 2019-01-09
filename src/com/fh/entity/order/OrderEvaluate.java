package com.fh.entity.order;

import java.io.Serializable;

/**
 * @desc 评论
 * @auther zhangjj
 * @date 2018年4月10日
 */
public class OrderEvaluate implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 评价内部主键 */
	private Integer id;
	/** 订单内部编码 */
	private Integer orderid;
	/** 评价描述 */
	private String evaluatedesc;
	/** 人员服务态度 */
	private String staffattitude;
	/** 物流服务 */
	private String logisticsservice;

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

	public String getEvaluatedesc() {
		return evaluatedesc;
	}

	public void setEvaluatedesc(String evaluatedesc) {
		this.evaluatedesc = evaluatedesc;
	}

	public String getStaffattitude() {
		return staffattitude;
	}

	public void setStaffattitude(String staffattitude) {
		this.staffattitude = staffattitude;
	}

	public String getLogisticsservice() {
		return logisticsservice;
	}

	public void setLogisticsservice(String logisticsservice) {
		this.logisticsservice = logisticsservice;
	}
}
