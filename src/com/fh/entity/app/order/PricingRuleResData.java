package com.fh.entity.app.order;

import com.fh.entity.configcenter.GoldPriceDetail;
import com.fh.entity.configcenter.SpecialPriceDetail;

public class PricingRuleResData {
	/** 金牌计价规则 */
	private GoldPriceDetail goldPriceDetail;
	/** 专车计价规则 */
	private SpecialPriceDetail specialPriceDetail;
	
	public GoldPriceDetail getGoldPriceDetail() {
		return goldPriceDetail;
	}
	
	public void setGoldPriceDetail(GoldPriceDetail goldPriceDetail) {
		this.goldPriceDetail = goldPriceDetail;
	}

	public SpecialPriceDetail getSpecialPriceDetail() {
		return specialPriceDetail;
	}

	public void setSpecialPriceDetail(SpecialPriceDetail specialPriceDetail) {
		this.specialPriceDetail = specialPriceDetail;
	}
	
}
