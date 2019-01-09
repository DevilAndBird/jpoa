package com.fh.entity.order;

import java.io.Serializable;
import java.util.Date;

public class OrderNotesInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id; // 主键
    private Integer orderid; // 问题件id
    private String notes; // 备注
    private String addusername; // 备注人

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddusername() {
        return addusername;
    }

    public void setAddusername(String addusername) {
        this.addusername = addusername;
    }

}