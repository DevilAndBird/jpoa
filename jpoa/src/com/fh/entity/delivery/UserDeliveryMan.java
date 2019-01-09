package com.fh.entity.delivery;

import java.util.Date;

public class UserDeliveryMan {
	private Integer id;//主键id
	private String idno;//身份证号
	private String name;//姓名
	private String mobile;// 电话
	private String platenumber;//车牌号
	private String statusDesc;//状态名  并非数据库字段！
	private Integer isvalid;//是否有效
	private String gender;//性别
	private Integer transitid;//集散中心
	private Integer provid;//省份id
	private Integer cityid;//城市id
	private String createdby;//创建人
	private String regionname;//区域名
	private Integer userid;//外键   App_user的id
	public UserDeliveryMan() {
		super();
	}
	public UserDeliveryMan(Integer id, String idno, Integer isvalid, String gender,
			Integer transitid, Integer provid, Integer cityid, Date addtime,
			String createdby, Integer userid,String regionname) {
		super();
		this.id = id;
		this.idno = idno;
		this.isvalid = isvalid;
		this.gender = gender;
		this.transitid = transitid;
		this.provid = provid;
		this.cityid = cityid;
		this.createdby = createdby;
		this.userid = userid;
		this.regionname = regionname;
	}
    
	@Override
	public String toString() {
		return "UserDeliveryMan [id=" + id + ", idno=" + idno + ", name="
				+ name + ", mobile=" + mobile + ", platenumber=" + platenumber
				+ ", statusDesc=" + statusDesc + ", isvalid=" + isvalid
				+ ", gender=" + gender + ", transitid=" + transitid
				+ ", provid=" + provid + ", cityid=" + cityid + ", createdby="
				+ createdby + ", regionname=" + regionname + ", userid="
				+ userid + "]";
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public Integer getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getTransitid() {
		return transitid;
	}
	public void setTransitid(Integer transitid) {
		this.transitid = transitid;
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
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
