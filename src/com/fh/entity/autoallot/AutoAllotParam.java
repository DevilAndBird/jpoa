package com.fh.entity.autoallot;

import java.util.Date;
import java.util.List;

import com.fh.entity.delivery.BaiduCoord;

public class AutoAllotParam {

	/**
	 * 订单编号
	 */
	private Integer orderid;
	/**
	 * 物流路线id
	 */
	private Integer haulwayid;
	/**
	 * 订单类型
	 */
	private String ordertype;
	/**
	 * 取件时间
	 */
	private Date taketime;
	/**
	 * 寄件时间
	 */
	private Date sendtime;
	/**
	 * 集散中心时间间隔
	 */
	private Integer trantspan;
	/**
	 * 出发地
	 */
	private String srcaddress;
	/**
	 * 出发地类型
	 */
	private String srctype;
	/**
	 * 出发地id
	 */
	private String srcaddressid;
	/**
	 * 出发地标志名
	 */
	private String srcaddressname;
	/**
	 * 出发地人员id
	 */
	private Integer srcroleid;
	/**
	 * 出发地类型
	 */
	private String srcroletype;
	/**
	 * 出发地时间间隔
	 */
	private Integer srctspan;
	/**
	 * 目的地
	 */
	private String destaddress;
	/**
	 * 目的地类型
	 */
	private String desttype;
	/**
	 *目的地id 
	 */
	private String destaddressid;
	/**
	 * 目的地标志名
	 */
	private String desaddressname;
	/**
	 * 目的地时间间隔
	 */
	private Integer destspan;
	/**
	 * 目的地人员id
	 */
	private Integer desroleid;
	/**
	 * 出发地gps
	 */
	private BaiduCoord srcgps;
	/**
	 * 目的地gps
	 */
	private BaiduCoord desgps;
	/**
	 * 目的地类型
	 */
	private String desroletype;
	/**
	 * 出发地区域id
	 */
	private Integer srcRegionid;
	/**
	 * 目的地区域id
	 */
	private Integer desRegionid;
	/**
	 * 出发地集散中心
	 */
	private Integer srcTransitd;
	/**
	 * 目的地集散中心
	 */
	private Integer desTransitd;
	/**
	 * 省份id
	 */
	private String provid;
	/**
	 * 城市id
	 */
	private String cityid;
	/**
	 * 出发地标志地
	 */
	private String scrlandmark;
	/**
	 * 目的地标志地
	 */
	private String destlandmark;
	/**
	 * 出发地省份
	 */
	private String srcprovname;
	/**
	 * 出发地城市
	 */
	private String srccityname;
	/**
	 * 出发地区县
	 */
	private String srcdistrictname;
	/**
	 * 目的地省份
	 */
	private String destprovname;
	/**
	 * 目的地城市
	 */
	private String destcityname;
	/**
	 * 目的地区县
	 */
	private String destdistrictname;
	/** 出发地gps */
	private String src_gps;
	/** 目的地gps */
	private String dest_gps;
	
	private List<HaulWayDetail>hualWayDetail;
	
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public Date getTaketime() {
		return taketime;
	}
	public void setTaketime(Date taketime) {
		this.taketime = taketime;
	}
	public Date getSendtime() {
		return sendtime;
	}
	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
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
	public BaiduCoord getSrcgps() {
		return srcgps;
	}
	public void setSrcgps(BaiduCoord srcgps) {
		this.srcgps = srcgps;
	}
	public BaiduCoord getDesgps() {
		return desgps;
	}
	public void setDesgps(BaiduCoord desgps) {
		this.desgps = desgps;
	}
	public Integer getSrcTransitd() {
		return srcTransitd;
	}
	public void setSrcTransitd(Integer srcTransitd) {
		this.srcTransitd = srcTransitd;
	}
	public Integer getDesTransitd() {
		return desTransitd;
	}
	public void setDesTransitd(Integer desTransitd) {
		this.desTransitd = desTransitd;
	}
	public Integer getSrcRegionid() {
		return srcRegionid;
	}
	public void setSrcRegionid(Integer srcRegionid) {
		this.srcRegionid = srcRegionid;
	}
	public Integer getDesRegionid() {
		return desRegionid;
	}
	public void setDesRegionid(Integer desRegionid) {
		this.desRegionid = desRegionid;
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
	public Integer getHaulwayid() {
		return haulwayid;
	}
	public void setHaulwayid(Integer haulwayid) {
		this.haulwayid = haulwayid;
	}
	public List<HaulWayDetail> getHualWayDetail() {
		return hualWayDetail;
	}
	public void setHualWayDetail(List<HaulWayDetail> hualWayDetail) {
		this.hualWayDetail = hualWayDetail;
	}
	public String getSrcaddressid() {
		return srcaddressid;
	}
	public void setSrcaddressid(String srcaddressid) {
		this.srcaddressid = srcaddressid;
	}
	public String getDestaddressid() {
		return destaddressid;
	}
	public void setDestaddressid(String destaddressid) {
		this.destaddressid = destaddressid;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public Integer getSrcroleid() {
		return srcroleid;
	}
	public void setSrcroleid(Integer srcroleid) {
		this.srcroleid = srcroleid;
	}
	public Integer getDesroleid() {
		return desroleid;
	}
	public void setDesroleid(Integer desroleid) {
		this.desroleid = desroleid;
	}
	public String getSrctype() {
		return srctype;
	}
	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}
	public String getDesttype() {
		return desttype;
	}
	public void setDesttype(String desttype) {
		this.desttype = desttype;
	}
	public String getSrcroletype() {
		return srcroletype;
	}
	public void setSrcroletype(String srcroletype) {
		this.srcroletype = srcroletype;
	}
	public String getDesroletype() {
		return desroletype;
	}
	public void setDesroletype(String desroletype) {
		this.desroletype = desroletype;
	}
	public String getSrcaddressname() {
		return srcaddressname;
	}
	public void setSrcaddressname(String srcaddressname) {
		this.srcaddressname = srcaddressname;
	}
	public String getDesaddressname() {
		return desaddressname;
	}
	public void setDesaddressname(String desaddressname) {
		this.desaddressname = desaddressname;
	}
	public Integer getDestspan() {
		return destspan;
	}
	public void setDestspan(Integer destspan) {
		this.destspan = destspan;
	}
	public Integer getTrantspan() {
		return trantspan;
	}
	public void setTrantspan(Integer trantspan) {
		this.trantspan = trantspan;
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
	public Integer getSrctspan() {
		return srctspan;
	}
	public void setSrctspan(Integer srctspan) {
		this.srctspan = srctspan;
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
	public String getSrc_gps() {
		return src_gps;
	}
	public void setSrc_gps(String src_gps) {
		this.src_gps = src_gps;
	}
	public String getDest_gps() {
		return dest_gps;
	}
	public void setDest_gps(String dest_gps) {
		this.dest_gps = dest_gps;
	}
}
