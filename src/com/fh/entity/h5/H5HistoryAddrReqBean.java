package com.fh.entity.h5;

public class H5HistoryAddrReqBean {
	/** 省份id */
	private String provid;
	/** 城市id */
	private String cityid;
	/** 对当前公众号唯一标识 */
	private String openid;
	/** 地址类型 */
	private String addresstype;
	
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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(String addresstype) {
		this.addresstype = addresstype;
	}
}
