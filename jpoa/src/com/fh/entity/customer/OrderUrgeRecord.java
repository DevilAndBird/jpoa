package com.fh.entity.customer;

import java.util.Date;

public class OrderUrgeRecord {
    private Integer id;

    private Integer cusid;

    private String cusname;

    private Integer orderid;

    private String orderno;

    private Date ordertime;

    private String mobile;

    private Integer dmanid;

    private String dmanname;

    private String dmanmobile;

    private Integer status;

    private Integer processby;

    private String processusername;

    private Date processtime;

    private Date addtime;

    private String urgenotes;

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

    public String getCusname() {
        return cusname;
    }

    public void setCusname(String cusname) {
        this.cusname = cusname == null ? null : cusname.trim();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getDmanid() {
        return dmanid;
    }

    public void setDmanid(Integer dmanid) {
        this.dmanid = dmanid;
    }

    public String getDmanname() {
        return dmanname;
    }

    public void setDmanname(String dmanname) {
        this.dmanname = dmanname == null ? null : dmanname.trim();
    }

    public String getDmanmobile() {
        return dmanmobile;
    }

    public void setDmanmobile(String dmanmobile) {
        this.dmanmobile = dmanmobile == null ? null : dmanmobile.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProcessby() {
        return processby;
    }

    public void setProcessby(Integer processby) {
        this.processby = processby;
    }

    public String getProcessusername() {
        return processusername;
    }

    public void setProcessusername(String processusername) {
        this.processusername = processusername == null ? null : processusername.trim();
    }

    public Date getProcesstime() {
        return processtime;
    }

    public void setProcesstime(Date processtime) {
        this.processtime = processtime;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getUrgenotes() {
        return urgenotes;
    }

    public void setUrgenotes(String urgenotes) {
        this.urgenotes = urgenotes == null ? null : urgenotes.trim();
    }
}