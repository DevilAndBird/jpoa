package com.fh.service.delivery;

public class CounterServiceCenter {
	private Integer id;
	/** 机场类型 */
	private String type;
	/** 柜台名称 */
	private String servicecentername;
	/** 柜台具体详情名称 */
	private String remark;
	/** 柜台编码 */
	private String servicecentercode;
	/** 所在地省份id */
	private String provid;
	/** 所在地省份名称 */
	private String provname;
	/** 所在地城市id */
	private String cityid;
	/** 所在地城市名称 */
	private String cityname;
	private String regionid;
	/** 柜台地址 */
	private String address;
	/** 柜台联系人姓名 */
	private String linkman;
	/** 柜台电话 */
	private String linkphone;
	/**  */
	private String createdby;
	/** 添加时间 */
	private String addtime;
	private String modifiedby;
	private String modifytime;
	private String status;
	/** 所在地gps */
	private String gps;
	private String businesstimeflag;
	/** 是否有效 */
	private String isvalid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServicecentername() {
		return servicecentername;
	}

	public void setServicecentername(String servicecentername) {
		this.servicecentername = servicecentername;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServicecentercode() {
		return servicecentercode;
	}

	public void setServicecentercode(String servicecentercode) {
		this.servicecentercode = servicecentercode;
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

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
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

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getBusinesstimeflag() {
		return businesstimeflag;
	}

	public void setBusinesstimeflag(String businesstimeflag) {
		this.businesstimeflag = businesstimeflag;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
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
