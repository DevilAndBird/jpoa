package com.fh.entity.h5;

public class H5CheckAddrReqBean {
	/** 省id */
	private String provid;
	/** 市id */
	private String cityid;
	/** 县id */
	private String districtid;
	/** 省份名称 */
	private String provname;
	/** 市名称 */
	private String cityname;
	/** 县名称 */
	private String districtname;
	/** 地标性建筑 */
	private String landmark;
	/** 详细地址 */
	private String address;
	/** 被检查的地址 */
	private String byCheckAddr;
	/** 被检查的坐标 */
	private String byCheckgps;
	/** 是否将坐标转换（高德换成百度临时使用） */
	private boolean istransitgps;
	

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

	public String getDistrictid() {
		return districtid;
	}

	public void setDistrictid(String districtid) {
		this.districtid = districtid;
	}

	public String getProvname() {
		return provname;
	}

	public void setProvname(String provname) {
		this.provname = provname;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getDistrictname() {
		return districtname;
	}

	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getByCheckAddr() {
		return byCheckAddr;
	}

	public void setByCheckAddr(String byCheckAddr) {
		this.byCheckAddr = byCheckAddr;
	}

	public String getByCheckgps() {
		return byCheckgps;
	}

	public void setByCheckgps(String byCheckgps) {
		this.byCheckgps = byCheckgps;
	}

	public boolean getIstransitgps() {
		return istransitgps;
	}

	public void setIstransitgps(boolean istransitgps) {
		this.istransitgps = istransitgps;
	}
	
}
