package com.fh.entity.delivery;

import java.util.Date;

public class UserPickerInfo {
	private Integer id;//主键
	private Integer userid;//APP登陆用户表的主键
	private String name;//姓名
	private String idno;//身份证号
	private String airportname;//机场名称
	private String airportcode;//机场编码
	private Integer createdby;//创建人
	private Integer isvalid;//是否有效
	private Integer num;//行李
	private Date addtime;//添加时间
	public UserPickerInfo() {
		super();
	}
	public UserPickerInfo(Integer id, Integer userid, String idno,
			String airportname, String airportcode, Integer createdby,
			Integer isvalid, Date addtime) {
		super();
		this.id = id;
		this.userid = userid;
		this.idno = idno;
		this.airportname = airportname;
		this.airportcode = airportcode;
		this.createdby = createdby;
		this.isvalid = isvalid;
		this.addtime = addtime;
	}
	@Override
	public String toString() {
		return "User_picker_info [id=" + id + ", userid=" + userid + ", idno="
				+ idno + ", airportname=" + airportname + ", airportcode="
				+ airportcode + ", createdby=" + createdby + ", isvalid="
				+ isvalid + ", addtime=" + addtime + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getAirportname() {
		return airportname;
	}
	public void setAirportname(String airportname) {
		this.airportname = airportname;
	}
	public String getAirportcode() {
		return airportcode;
	}
	public void setAirportcode(String airportcode) {
		this.airportcode = airportcode;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
