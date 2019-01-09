package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.order.OrderBaggage;

/**
 * 
 */
public class AppOrderJicunResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Integer id;
	/** 订单编码 */
    private String orderno;
    /** 动作类型 */
    private String roletype;
    /** 行李数 */
    private Integer num;
    /** 最迟到达时间 */
    private String arrivedtime;
    
    /** 订单QR码查询 */
    private List<OrderBaggage> orderBaggageList;

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

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getArrivedtime() {
		return arrivedtime;
	}

	public void setArrivedtime(String arrivedtime) {
		this.arrivedtime = arrivedtime;
	}

	public List<OrderBaggage> getOrderBaggageList() {
		return orderBaggageList;
	}

	public void setOrderBaggageList(List<OrderBaggage> orderBaggageList) {
		this.orderBaggageList = orderBaggageList;
	}
    
	
}
