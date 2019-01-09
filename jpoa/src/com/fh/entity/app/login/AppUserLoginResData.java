package com.fh.entity.app.login;

import java.util.List;

public class AppUserLoginResData {
	/** 手机号码 */
	private String mobile;
	/** 姓名*/
	private String name;
	/** 身份证 */
	private String idno;
	/** 身份证类型 */
	private String type;
	/** 密钥 */
	private String sign;
	/** 角色id */
	private Integer roleid;
	/** 用户角色 */
	private AppUserRole appuserrole;
	/** 角色实例 */
	private List<AppUserRole> roleList;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<AppUserRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<AppUserRole> roleList) {
		this.roleList = roleList;
	}
	public AppUserRole getAppuserrole() {
		return appuserrole;
	}
	public void setAppuserrole(AppUserRole appuserrole) {
		this.appuserrole = appuserrole;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	@Override
	public String toString() {
		return "AppUserLoginResData [mobile=" + mobile + ", name=" + name
				+ ", idno=" + idno + ", type=" + type + ", sign=" + sign
				+ ", roleid=" + roleid + ", appuserrole=" + appuserrole
				+ ", roleList=" + roleList + "]";
	}
	
}
