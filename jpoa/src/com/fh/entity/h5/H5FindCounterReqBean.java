package com.fh.entity.h5;

public class H5FindCounterReqBean {
	/** 省id */
	private String provid;
	/** 市id */
	private String cityid;
	/** 运送类型 */
	private String transmittype;

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

	public String getTransmittype() {
		return transmittype;
	}

	public void setTransmittype(String transmittype) {
		this.transmittype = transmittype;
	}
}
