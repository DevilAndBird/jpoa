package com.fh.entity.app.order;

import java.util.Map;

public class OrderPriceDetatils {
	/** 行李基础费用 */
	private Map<String, String> lugBaseCostMap;
	/** 基础费用 */
	private Float baseMoney;
	/** 保费 */
	private Float prem;
	/** 额外费用 */
	private Float extraMoney;
	/** 实际支付费用 */
	private Float actualMoney;
	/** 优惠金额 */
	private Float cutMoney;
	/** 总金额 */
	private Float totalMoney;

	public Float getBaseMoney() {
		return baseMoney;
	}

	public void setBaseMoney(Float baseMoney) {
		this.baseMoney = baseMoney;
	}

	public Float getPrem() {
		return prem;
	}

	public void setPrem(Float prem) {
		this.prem = prem;
	}

	public Float getExtraMoney() {
		return extraMoney;
	}

	public void setExtraMoney(Float extraMoney) {
		this.extraMoney = extraMoney;
	}

	public Float getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Float actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Map<String, String> getLugBaseCostMap() {
		return lugBaseCostMap;
	}

	public void setLugBaseCostMap(Map<String, String> lugBaseCostMap) {
		this.lugBaseCostMap = lugBaseCostMap;
	}

	public Float getCutMoney() {
		return cutMoney;
	}

	public void setCutMoney(Float cutMoney) {
		this.cutMoney = cutMoney;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}
}
