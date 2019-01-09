package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.order.OrderBaggage;

/**
 * 
 */
public class AppOrderAirportResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private Integer id;
	/** 订单编码 */
    private String orderno;
    /** 客户名称 */
    private String name;
    /** 号码 */
    private String mobile;
    /** 行李数 */
    private Integer num;
    /** 寄件时间 */
    private String taketime;
    /** 收件时间时间 */
    private String sendtime;
    private String modifytime;
    /** 渠道 */
    private String channel;
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public List<OrderBaggage> getOrderBaggageList() {
		return orderBaggageList;
	}
	public void setOrderBaggageList(List<OrderBaggage> orderBaggageList) {
		this.orderBaggageList = orderBaggageList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
