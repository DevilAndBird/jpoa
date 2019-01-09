package com.fh.entity.h5;

public class H5CusOrderCouponBean {
	/** 客户id */
	private Integer cusid;
	/** 订单id */
	private Integer orderId;
	/** 优惠卷id */
	private Integer couponId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getCusid() {
		return cusid;
	}

	public void setCusid(Integer cusid) {
		this.cusid = cusid;
	}
}
