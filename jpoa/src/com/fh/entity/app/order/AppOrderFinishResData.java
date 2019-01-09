package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.app.counterservice.AppCounterCenter;
import com.fh.entity.app.transitcenter.AppTransitCenter;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.entity.order.OrderBaggage;
import com.fh.entity.order.OrderRole;

/**
 * 
 */
public class AppOrderFinishResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单内id*/
    private Integer id;
	/** 订单编码 */
    private String orderno;
    /** 取派员姓名 */
    private String name;
    /** 行李数 */
    private Integer num;
    /** 动作类型 */
    private String roletype;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	public List<OrderBaggage> getOrderBaggageList() {
		return orderBaggageList;
	}
	public void setOrderBaggageList(List<OrderBaggage> orderBaggageList) {
		this.orderBaggageList = orderBaggageList;
	}
	

}
