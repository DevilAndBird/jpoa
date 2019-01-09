package com.fh.entity.configcenter;

public class GoldPriceDetail {
	/** 基础服务费 */
	private Float basemoney;
	/** 过夜费 */
	private Float overnight;
	/** 开始计算过夜费用天数 */
	private Integer nightnum;

	public Float getBasemoney() {
		return basemoney;
	}

	public void setBasemoney(Float basemoney) {
		this.basemoney = basemoney;
	}

	public Float getOvernight() {
		return overnight;
	}

	public void setOvernight(Float overnight) {
		this.overnight = overnight;
	}

	public Integer getNightnum() {
		return nightnum;
	}

	public void setNightnum(Integer nightnum) {
		this.nightnum = nightnum;
	}

}
