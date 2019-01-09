package com.fh.entity.autoallot;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：路线明细
 * 类名称：com.fh.entity.autoallot.HaulWayDetail     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午8:33:03   
 * 修改人：
 * 修改时间：2018年5月28日 下午8:33:03   
 * 修改备注：
 */
public class HaulWayDetail {
	
	/**
	 * hualway表主键
	 */
	private Integer haulwayid;
	/**
	 * 出发地id
	 */
	private String srcid;
	/**
	 * 目的地id
	 */
	private String desid;
	/**
	 * 时间间隔
	 */
	private Integer timespan;
	/**
	 * 司机
	 */
	private Integer driver;
	
	private String provid;
	
	private String cityid;
	
	public Integer getHaulwayid() {
		return haulwayid;
	}
	public void setHaulwayid(Integer haulwayid) {
		this.haulwayid = haulwayid;
	}
	public String getSrcid() {
		return srcid;
	}
	public void setSrcid(String srcid) {
		this.srcid = srcid;
	}
	public String getDesid() {
		return desid;
	}
	public void setDesid(String desid) {
		this.desid = desid;
	}
	public Integer getTimespan() {
		return timespan;
	}
	public void setTimespan(Integer timespan) {
		this.timespan = timespan;
	}
	public Integer getDriver() {
		return driver;
	}
	public void setDriver(Integer driver) {
		this.driver = driver;
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
	
}
