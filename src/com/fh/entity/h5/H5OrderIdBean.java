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
}
