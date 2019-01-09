package com.fh.entity.auxiliary.smstemplate;

import java.util.Date;

public class SmsIdentifyingInfo {
    private Integer id;

    private Integer cusid;

    private Integer userid;

    private String ip;

    private String type;

    private String smsmsg;

    private Date addtime;

    private String mobile;

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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getSmsmsg() {
        return smsmsg;
    }

    public void setSmsmsg(String smsmsg) {
        this.smsmsg = smsmsg == null ? null : smsmsg.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}