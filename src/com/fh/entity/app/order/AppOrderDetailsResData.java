package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.app.transitcenter.AppTransitCenter;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.delivery.UserDeliveryMan;
import com.fh.entity.order.OrderAddress;
import com.fh.entity.order.OrderBaggage;
import com.fh.entity.order.OrderFlight;
import com.fh.entity.order.OrderNotesInfo;
import com.fh.entity.order.OrderSenderReceiver;
import com.fh.util.PageData;

/**
 * @desc 订单信息列表查询
 * @auther zhangjj
 * @history 2018年2月6日
 */
public class AppOrderDetailsResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单内部
     */
    private Integer id;
    /**
     * 订单编码
     */
    private String orderno;
    /** 订单类型：机-酒  酒-机 */
    /**
     * 渠道类型
     */
    private String channel;
    private String type;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 航班号
     */
    private String flightno;
    /**
     * 寄件方式
     */
    private String mailingway;
    /**
     * 收件方式
     */
    private String backway;
    /**
     * 取件时间
     */
    private String taketime;
    /**
     * 送件时间
     */
    private String sendtime;
    /**
     * 寄件人姓名
     */
    private String sendername;
    /**
     * 寄件人号码
     */
    private String senderphone;
    /**
     * 收件人姓名
     */
    private String receivername;
    /**
     * 收件人号码
     */
    private String receiverphone;
    /**
     * 行李数
     */
    private Integer num;
    /**
     * 客户号
     */
    private Integer cusid;
    /**
     * 客户信息
     */
    private CusInfo cusInfo;
    /**
     * 二维码+图片路径
     */
    private List<OrderBaggage> orderBaggageList;
    /**
     * 备注信息
     */
    private List<OrderNotesInfo> orderNotesInfoList;
    /**
     * 航班信息
     */
    private OrderFlight orderFlight;
    /**
     * 订单地址信息
     */
    private OrderAddress orderAddress;
    /**
     * 订单寄件收件地址
     */
    private OrderSenderReceiver orderSenderReceiver;
    /**
     * 取派员
     */
    private List<UserDeliveryMan> userDeliveryManList;
    /**
     * 去往的集散中心
     */
    private List<AppTransitCenter> appTransitCenterList;
    /**
     * 优惠卷
     */
    private Float cutmoney;
    /**
     * 实际支付金额
     */
    private Float actualmoney;
    /**
     * 总金额
     */
    private Float totalmoney;
    /**
     * 备注
     */
    private String remark;
    private OrderPriceDetatils orderPriceDetatils;
    /**
     * 订单状态描述
     */
    private String statusDesc;
    /**
     * 动作类型
     */
    private List<PageData> actiondetail;
    /**
     * 案件编号
     */
    private String casenum;

    private String casetime;

    public String getCasetime() {
        return casetime;
    }

    public void setCasetime(String casetime) {
        this.casetime = casetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCusid() {
        return cusid;
    }

    public void setCusid(Integer cusid) {
        this.cusid = cusid;
    }

    public List<OrderNotesInfo> getOrderNotesInfoList() {
        return orderNotesInfoList;
    }

    public void setOrderNotesInfoList(List<OrderNotesInfo> orderNotesInfoList) {
        this.orderNotesInfoList = orderNotesInfoList;
    }

    public CusInfo getCusInfo() {
        return cusInfo;
    }

    public void setCusInfo(CusInfo cusInfo) {
        this.cusInfo = cusInfo;
    }

    public List<OrderBaggage> getOrderBaggageList() {
        return orderBaggageList;
    }

    public void setOrderBaggageList(List<OrderBaggage> orderBaggageList) {
        this.orderBaggageList = orderBaggageList;
    }

    public OrderFlight getOrderFlight() {
        return orderFlight;
    }

    public void setOrderFlight(OrderFlight orderFlight) {
        this.orderFlight = orderFlight;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public List<UserDeliveryMan> getUserDeliveryManList() {
        return userDeliveryManList;
    }

    public void setUserDeliveryManList(List<UserDeliveryMan> userDeliveryManList) {
        this.userDeliveryManList = userDeliveryManList;
    }

    public OrderSenderReceiver getOrderSenderReceiver() {
        return orderSenderReceiver;
    }

    public void setOrderSenderReceiver(OrderSenderReceiver orderSenderReceiver) {
        this.orderSenderReceiver = orderSenderReceiver;
    }

    public List<AppTransitCenter> getAppTransitCenterList() {
        return appTransitCenterList;
    }

    public void setAppTransitCenterList(List<AppTransitCenter> appTransitCenterList) {
        this.appTransitCenterList = appTransitCenterList;
    }

    public String getMailingway() {
        return mailingway;
    }

    public void setMailingway(String mailingway) {
        this.mailingway = mailingway;
    }

    public String getTaketime() {
        return taketime;
    }

    public void setTaketime(String taketime) {
        this.taketime = taketime;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getBackway() {
        return backway;
    }

    public void setBackway(String backway) {
        this.backway = backway;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getSenderphone() {
        return senderphone;
    }

    public void setSenderphone(String senderphone) {
        this.senderphone = senderphone;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public String getReceiverphone() {
        return receiverphone;
    }

    public void setReceiverphone(String receiverphone) {
        this.receiverphone = receiverphone;
    }

    public String getFlightno() {
        return flightno;
    }

    public void setFlightno(String flightno) {
        this.flightno = flightno;
    }

    public float getActualmoney() {
        return actualmoney;
    }

    public void setActualmoney(float actualmoney) {
        this.actualmoney = actualmoney;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OrderPriceDetatils getOrderPriceDetatils() {
        return orderPriceDetatils;
    }

    public void setOrderPriceDetatils(OrderPriceDetatils orderPriceDetatils) {
        this.orderPriceDetatils = orderPriceDetatils;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Float getCutmoney() {
        return cutmoney;
    }

    public void setCutmoney(Float cutmoney) {
        this.cutmoney = cutmoney;
    }

    public void setActualmoney(Float actualmoney) {
        this.actualmoney = actualmoney;
    }

    public Float getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(Float totalmoney) {
        this.totalmoney = totalmoney;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public List<PageData> getActiondetail() {
        return actiondetail;
    }

    public void setActiondetail(List<PageData> actiondetail) {
        this.actiondetail = actiondetail;
    }

    public String getCasenum() {
        return casenum;
    }

    public void setCasenum(String casenum) {
        this.casenum = casenum;
    }
}




