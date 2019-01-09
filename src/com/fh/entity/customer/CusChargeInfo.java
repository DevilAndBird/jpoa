package com.fh.entity.customer;

import java.util.Date;

public class CusChargeInfo {
    
    private Integer id;

    private Integer cusid;
    
    private Date addtime;

    private Float money;

    private Float srcmoney;

    private Float finalmoney;
    
    private String serialno;		//序列号

    public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCusid() {
        return cusid;
    }

    public void setCusid(Integer cusid) {
        this.cusid = cusid;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Float getSrcmoney() {
        return srcmoney;
    }

    public void setSrcmoney(Float srcmoney) {
        this.srcmoney = srcmoney;
    }

    public Float getFinalmoney() {
        return finalmoney;
    }

    public void setFinalmoney(Float finalmoney) {
        this.finalmoney = finalmoney;
    }
}