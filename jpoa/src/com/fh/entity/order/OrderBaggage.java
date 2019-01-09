package com.fh.entity.order;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单行李关系请求
 *
 * @author tangqm
 * @date 2018年2月27日
 */
public class OrderBaggage implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单id
     */
    private Integer orderid;
    /**
     * 订单号
     */
    private String orderno;
    /**
     * 行李id
     */
    private String baggageid;
    /**
     * 图片路径
     */
    private String imgurl;
    /**
     * 订单图片
     */
    private OrderImgurl orderImgurl;

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

    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public OrderImgurl getOrderImgurl() {
        return orderImgurl;
    }

    public void setOrderImgurl(OrderImgurl orderImgurl) {
        this.orderImgurl = orderImgurl;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
