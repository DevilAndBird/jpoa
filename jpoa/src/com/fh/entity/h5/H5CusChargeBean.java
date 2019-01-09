package com.fh.entity.h5;

public class H5CusChargeBean
{
	private Integer cusid;
	private String serialno;	//流水号
	private float money;		//充值金额
	
	public Integer getCusId() {
		return cusid;
	}
	public void setCusId(Integer cusid) {
		this.cusid = cusid;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
}
