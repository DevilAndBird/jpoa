package com.fh.entity.h5;

public class H5CusinfoReqBean {
	/** 对当前公众号唯一标识 */
	private String openid;
	/** 客户id */
	private Integer cusid;

	private String mobile;

	public Integer getCusid() {
		return cusid;
	}

	public void setCusid(Integer cusid) {
		this.cusid = cusid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
