package com.fh.entity.h5;

public class H5ContactsReqBean {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 修改时间
	 */
	private String modifytime;
	/**
	 * 公众号标识
	 */
	private String openid;
	/**
	 * 添加时间
	 */
	private String addtime;
	/**
	 * 是否有效
	 */
	private String isvalid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	@Override
	public String toString() {
		return "H5ContactsReqBean [id=" + id + ", name=" + name + ", mobile="
				+ mobile + ", modifytime=" + modifytime + ", openid=" + openid
				+ ", addtime=" + addtime + ", isvalid=" + isvalid + "]";
	}
	
}
