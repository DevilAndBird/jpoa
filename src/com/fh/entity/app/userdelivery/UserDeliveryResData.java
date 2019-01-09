package com.fh.entity.app.userdelivery;

public class UserDeliveryResData {
	/**
	 * 取派员id
	 */
	private Integer id;
	/**
	 * 取派员登陆id
	 */
	private Integer userid;
	/**
	 * 姓名
	 */
	private String drivername;
	/**
	 * 手机号
	 */
	private String drivermobile;
	/**
	 * 车牌号
	 */
	private String platenumber;
	/**
	 * 形式状态
	 */
	private String travelstatus;
	/**
	 * 行驶状态描述
	 */
	private String travelstatusDesc;
	/**
	 * 状态
	 */
	private String statusDesc;
	/**
	 * 行李
	 */
	private Integer num;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDrivername() {
		return drivername;
	}

	public void setDrivername(String drivername) {
		this.drivername = drivername;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getDrivermobile() {
		return drivermobile;
	}

	public void setDrivermobile(String drivermobile) {
		this.drivermobile = drivermobile;
	}

	public String getTravelstatus() {
		return travelstatus;
	}

	public void setTravelstatus(String travelstatus) {
		this.travelstatus = travelstatus;
	}

	public String getTravelstatusDesc() {
		return travelstatusDesc;
	}

	public void setTravelstatusDesc(String travelstatusDesc) {
		this.travelstatusDesc = travelstatusDesc;
	}

	@Override
	public String toString() {
		return "UserDeliveryResData [id=" + id + ", userid=" + userid
				+ ", drivername=" + drivername + ", drivermobile="
				+ drivermobile + ", platenumber=" + platenumber
				+ ", travelstatus=" + travelstatus + ", travelstatusDesc="
				+ travelstatusDesc + ", statusDesc=" + statusDesc + ", num="
				+ num + "]";
	}

}
