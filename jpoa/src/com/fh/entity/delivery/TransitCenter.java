package com.fh.entity.delivery;

import java.util.Date;

public class TransitCenter {
	
	/** 去往集散中心顺序 */
	private Integer id;//主键ID
	private String name;//集散中心名
	private Integer provid;//省份ID
	private Integer cityid;//城市id
	private String address;//地址
	private String linkman;//联系人
	private String linkphone;//联系电话
	private Integer createdby;//创建人
	private Integer isvalid;//是否有效	1:有效，2：无效
	private Date addtime;//添加时间
	public TransitCenter() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TransitCenter(Integer id, String name, Integer provid,
			Integer cityid, String address, String linkman, String linkphone,
			Integer createdby, Integer isvalid, Date addtime) {
		super();
		this.id = id;
		this.name = name;
		this.provid = provid;
		this.cityid = cityid;
		this.address = address;
		this.linkman = linkman;
		this.linkphone = linkphone;
		this.createdby = createdby;
		this.isvalid = isvalid;
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "TransitCenter [id=" + id + ", name=" + name + ", provid="
				+ provid + ", cityid=" + cityid + ", address=" + address
				+ ", linkman=" + linkman + ", linkphone=" + linkphone
				+ ", createdby=" + createdby + ", isvalid=" + isvalid
				+ ", addtime=" + addtime + "]";
	}
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
	public Integer getProvid() {
		return provid;
	}
	public void setProvid(Integer provid) {
		this.provid = provid;
	}
	public Integer getCityid() {
		return cityid;
	}
	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public Integer getCreatedby() {
		return createdby;
	}
	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}
	public Integer getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
