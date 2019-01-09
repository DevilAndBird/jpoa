package com.fh.entity.h5;

public class H5OrderAddress {
	/** 出发地类型 */
	private String srcaddrtype;
	/** 地址id 一般用于柜台 */
	private Integer srcaddressid;
	/** 出发省份ID */
	private String srcprovid;
	/** 出发地市ID */
	private String srccityid;
	/** 出发区县ID */
	private String srcdistrictid;
	/** 目的地地标性建筑 */
	private String scrlandmark;
	/** 出发省份名字 */
	private String srcprovname;
	/** 出发地市名称 */
	private String srccityname;
	/** 出发区县name */
	private String srcdistrictname;
	/** 出发地址 */
	private String srcaddress;
	/** 目的地gps */
	private String srcgps;
    /** 是否转换成百度坐标系(零时使用待以后系统全部换成高德则不需要) */
    private Boolean srcistransitgps;
	
	/** 目的地类型 */
	private String destaddrtype;
	/** 地址id 一般用于柜台 */
	private Integer destaddressid;
	/** 到达省份ID */
	private String destprovid;
	/** 到达地市ID */
	private String destcityid;
	/** 到达区县ID */
	private String destdistrictid;
	/** 出发地地标性建筑 */
	private String destlandmark;
	/** 到达省份名字 */
	private String destprovname;
	/** 到达地市名字 */
	private String destcityname;
	/** 到达区县名字 */
	private String destdistrictname;
	/** 到达地址 */
	private String destaddress;
	/** 目的地gps */
	private String destgps;
    /** 是否转换成百度坐标系(零时使用待以后系统全部换成高德则不需要) */
    private Boolean destistransitgps;
    /** 备注 */
    private String remark;

	public String getSrcprovid() {
		return srcprovid;
	}

	public void setSrcprovid(String srcprovid) {
		this.srcprovid = srcprovid;
	}

	public String getSrccityid() {
		return srccityid;
	}

	public void setSrccityid(String srccityid) {
		this.srccityid = srccityid;
	}

	public String getSrcdistrictid() {
		return srcdistrictid;
	}

	public void setSrcdistrictid(String srcdistrictid) {
		this.srcdistrictid = srcdistrictid;
	}

	public String getDestprovid() {
		return destprovid;
	}

	public void setDestprovid(String destprovid) {
		this.destprovid = destprovid;
	}

	public String getDestcityid() {
		return destcityid;
	}

	public void setDestcityid(String destcityid) {
		this.destcityid = destcityid;
	}

	public String getDestdistrictid() {
		return destdistrictid;
	}

	public void setDestdistrictid(String destdistrictid) {
		this.destdistrictid = destdistrictid;
	}

	public String getSrcprovname() {
		return srcprovname;
	}

	public void setSrcprovname(String srcprovname) {
		this.srcprovname = srcprovname;
	}

	public String getSrccityname() {
		return srccityname;
	}

	public void setSrccityname(String srccityname) {
		this.srccityname = srccityname;
	}

	public String getSrcdistrictname() {
		return srcdistrictname;
	}

	public void setSrcdistrictname(String srcdistrictname) {
		this.srcdistrictname = srcdistrictname;
	}

	public String getDestprovname() {
		return destprovname;
	}

	public void setDestprovname(String destprovname) {
		this.destprovname = destprovname;
	}

	public String getDestcityname() {
		return destcityname;
	}

	public void setDestcityname(String destcityname) {
		this.destcityname = destcityname;
	}

	public String getDestdistrictname() {
		return destdistrictname;
	}

	public void setDestdistrictname(String destdistrictname) {
		this.destdistrictname = destdistrictname;
	}

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}

	public String getSrcaddrtype() {
		return srcaddrtype;
	}

	public void setSrcaddrtype(String srcaddrtype) {
		this.srcaddrtype = srcaddrtype;
	}

	public String getDestaddrtype() {
		return destaddrtype;
	}

	public void setDestaddrtype(String destaddrtype) {
		this.destaddrtype = destaddrtype;
	}

	public String getScrlandmark() {
		return scrlandmark;
	}

	public void setScrlandmark(String scrlandmark) {
		this.scrlandmark = scrlandmark;
	}

	public String getDestlandmark() {
		return destlandmark;
	}

	public void setDestlandmark(String destlandmark) {
		this.destlandmark = destlandmark;
	}

	public Integer getSrcaddressid() {
		return srcaddressid;
	}

	public void setSrcaddressid(Integer srcaddressid) {
		this.srcaddressid = srcaddressid;
	}

	public String getSrcgps() {
		return srcgps;
	}

	public void setSrcgps(String srcgps) {
		this.srcgps = srcgps;
	}

	public Boolean getSrcistransitgps() {
		return srcistransitgps;
	}

	public void setSrcistransitgps(Boolean srcistransitgps) {
		this.srcistransitgps = srcistransitgps;
	}

	public Integer getDestaddressid() {
		return destaddressid;
	}

	public void setDestaddressid(Integer destaddressid) {
		this.destaddressid = destaddressid;
	}

	public String getDestgps() {
		return destgps;
	}

	public void setDestgps(String destgps) {
		this.destgps = destgps;
	}

	public Boolean getDestistransitgps() {
		return destistransitgps;
	}

	public void setDestistransitgps(Boolean destistransitgps) {
		this.destistransitgps = destistransitgps;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
