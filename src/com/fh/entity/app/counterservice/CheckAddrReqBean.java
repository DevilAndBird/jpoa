package com.fh.entity.app.counterservice;

public class CheckAddrReqBean {
	/** 省id */
	private String provid;
	/** 市id */
	private String cityid;
	/** 被检查的坐标 */
		private String byCheckgps;
	/** 是否将坐标转换（高德换成百度临时使用） */
	private Boolean istransitgps;

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

	public String getByCheckgps() {
		return byCheckgps;
	}

	public void setByCheckgps(String byCheckgps) {
		this.byCheckgps = byCheckgps;
	}

	public Boolean getIstransitgps() {
		return istransitgps;
	}

	public void setIstransitgps(Boolean istransitgps) {
		this.istransitgps = istransitgps;
	}
}
