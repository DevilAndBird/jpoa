package com.fh.entity.h5;

public class H5UseCouponReqBean {

	/** 优惠码 */
	private String couponcode;
	/** 总支付金额 */
	private Float totalmoney;

	public String getCouponcode() {
		return couponcode;
	}

	public void setCouponcode(String couponcode) {
		this.couponcode = couponcode;
	}

	public Float getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Float totalmoney) {
		this.totalmoney = totalmoney;
	}

}
