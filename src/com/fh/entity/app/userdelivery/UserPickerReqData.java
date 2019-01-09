 package com.fh.entity.app.userdelivery;

 public class UserPickerReqData {

	 /**
	  * 角色ID
	  */
	 private Integer  roleid;
	 /**
	  * 角色类型
	  */
	 private String roleType;
	 /**
	  * 省份id
	  */
	 private String provid;
	 /**
	  * 城市id
	  */
	 private String cityid;
	 /**
	  * 目的地
	  */
	 private String destaddr;
	 /**
	  * 集善中心id
	  */
	 private Integer transitid;
	 /**
	  * 是否完成
	  */
	 private String isfinish;

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

	public String getDestaddr() {
		return destaddr;
	}

	public void setDestaddr(String destaddr) {
		this.destaddr = destaddr;
	}
	
	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
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

	@Override
	public String toString() {
		return "UserPickerReqData [roleid=" + roleid + ", roleType=" + roleType
				+ ", provid=" + provid + ", cityid=" + cityid + ", destaddr="
				+ destaddr + ", transitid=" + transitid + ", isfinish="
				+ isfinish + "]";
	}

	 
}
