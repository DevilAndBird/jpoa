package com.fh.entity.configcenter;

import java.util.Date;

public class ConfigCenter {

	/** 主键 */
	private Integer id;
	/** 城市id */
	private String cityid;
	/** key */
	private String business_key;
	/** value */
	private String business_value;
	/** 是否失效 */
	private String isvalid;
	/** 添加时间 */
	private Date addtime;
	/** 城市名称 */
	private String cityname;
	/** 备注信息 */
	private String remark;

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getBusiness_key() {
		return business_key;
	}

	public void setBusiness_key(String business_key) {
		this.business_key = business_key;
	}

	public String getBusiness_value() {
		return business_value;
	}

	public void setBusiness_value(String business_value) {
		this.business_value = business_value;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
