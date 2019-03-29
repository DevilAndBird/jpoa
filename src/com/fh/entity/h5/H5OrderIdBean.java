package com.fh.entity.h5;

public class H5OrderIdBean{
	/**
	 * 订单id
	 */
	private Integer orderid;
	
	/**
	 * 订单qr码
	 */
	private String baggageid;
	/**
	 * 订单号
	 */
	private String orderno;

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
    }

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
}
