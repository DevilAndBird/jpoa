package com.fh.entity.h5.wxpublicnum;

public class WXInvoiceAccreditReqBean {
	/** 订单号 */
	private String orderno;
	/** 订单金额 */
	private Integer money;

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "WXInvoiceAccreditReqBean [orderno=" + orderno + ", money=" + money + "]";
	}
}
