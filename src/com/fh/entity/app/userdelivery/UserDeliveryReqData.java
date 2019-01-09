 package com.fh.entity.app.userdelivery;
/**
 * 取派员接口请求类
 * @author 
 * @date 2018年11月6日
 */
 public class UserDeliveryReqData {

	 
	 /**
	  * 角色ID
	  */
	 private Integer  roleid;
	 /**
	  * 角色类型
	  */
	 private String roletype;
	/**
	 * 是否完成
	 */
	 private String isfinish;
	 /**
	  * 省份id
	  */
	 private String provid;
	 /**
	  * 城市id
	  */
	 private String cityid;
	 /**
	  * 目的地类型
	  */
	 private String desttype;
	 /**
	  * 目的地
	  */
	 private String destaddress;
	 /**
	  * 集善中心id
	  */
	 private Integer transitid;
	

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
	
	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRoletype() {
		return roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public Integer getTransitid() {
		return transitid;
	}

	public void setTransitid(Integer transitid) {
		this.transitid = transitid;
	}

	public String getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(String isfinish) {
		this.isfinish = isfinish;
	}

	public String getDesttype() {
		return desttype;
	}

	public void setDesttype(String desttype) {
		this.desttype = desttype;
	}
	
	public Integer getId() {
		return roleid;
	}

	public void setId(Integer roleid) {
		this.roleid = roleid;
	}

}
