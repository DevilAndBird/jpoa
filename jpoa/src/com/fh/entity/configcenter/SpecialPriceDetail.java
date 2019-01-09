package com.fh.entity.configcenter;

public class SpecialPriceDetail {

	/** 起步价格 */
	private Float startingmoney;
	/** 起步公里 */
	private Float startingkm;
	/** 起步公里后每公里计费价 */
	private Float perkmmoney;
	/** 过夜费 */
	private Float overnight;
	/** 开始计算过夜费用天数 */
	private Integer nightnum;
	/** 额外行李费用 */
	private Float extralugmoney;
	/** 开始计算额外行李数量 */
	private Integer startlugnum;

	public Float getStartingmoney() {
		return startingmoney;
	}

	public void setStartingmoney(Float startingmoney) {
		this.startingmoney = startingmoney;
	}

	public Float getStartingkm() {
		return startingkm;
	}

	public void setStartingkm(Float startingkm) {
		this.startingkm = startingkm;
	}

	public Float getPerkmmoney() {
		return perkmmoney;
	}

	public void setPerkmmoney(Float perkmmoney) {
		this.perkmmoney = perkmmoney;
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

	public Integer getStartlugnum() {
		return startlugnum;
	}

	public void setStartlugnum(Integer startlugnum) {
		this.startlugnum = startlugnum;
	}

	public Float getExtralugmoney() {
		return extralugmoney;
	}

	public void setExtralugmoney(Float extralugmoney) {
		this.extralugmoney = extralugmoney;
	}

}
