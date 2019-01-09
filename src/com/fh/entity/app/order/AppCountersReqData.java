package com.fh.entity.app.order;

public class AppCountersReqData {
	/** 省份id */
	private String provid;
	/** 城市id */
	private String cityid;
	/* 网点类型 */
	private String branchType;

	public String getProvid() {
		return provid;
	}

	public void setProvid(String provid) {
		this.provid = provid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getBranchType() {
		return branchType;
	}

	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
}
