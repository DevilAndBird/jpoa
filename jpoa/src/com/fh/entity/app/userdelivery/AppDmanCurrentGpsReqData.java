package com.fh.entity.app.userdelivery;

import java.io.Serializable;

public class AppDmanCurrentGpsReqData implements Serializable{
	/** 
	 * 取派员userid
	 *  */
	private Integer userid;
	/** 
	 * 取派员位置信息更新
	 */
	private String currentgps;
	/**
	 * 取派员姓名
	 */
	private String name;
	/**
	 * 取派员手机号码
	 */
	private String mobile;
	/**
	 * 取派员所在市id
	 */
	private String srccityid;
	
	

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getCurrentgps() {
		return currentgps;
	}

	public void setCurrentgps(String currentgps) {
		this.currentgps = currentgps;
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

	public String getSrccityid() {
		return srccityid;
	}

	public void setSrccityid(String srccityid) {
		this.srccityid = srccityid;
	}

	@Override
	public String toString() {
		return "AppDmanCurrentGpsReqData [userid=" + userid + ", currentgps="+ currentgps + ", name=" + name + ", mobile=" + mobile+ ", srccityid=" + srccityid + "]";
	}
	
	
	
}
