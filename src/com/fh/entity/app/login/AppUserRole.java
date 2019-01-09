package com.fh.entity.app.login;

public class AppUserRole {
	/** id */
	private Integer id;
	/** 角色姓名 */
	private String roleName;
	/** 登陆人员所在地gps */
	private String gps;
	/** 省份 */
	private String provid;

	/** 省份名称 */
	private String provname;
	/** 城市名称 */
	private String cityname;
	/** 城市 */
	private String cityid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

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

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
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

}
