package com.fh.common.constant_enum;

/**
 * app接口_订单详情查询类型
 * @author tangqm
 * @date 2018年2月27日
 */
public enum QUERY_ORERYDETAILS_TYPE {
    /** 查询寄件收件人信息 */
    ORDERSENDERRECEIVER("ORDERSENDERRECEIVER", "查询寄件收件人信息"),
    /** 查询QR码+图片路径 */
    ORDERBAGAGE("ORDERBAGGAGE", "查询QR码+图片路径"),
    /** 订单备注信息 */
    ORDERNOTES("ORDERNOTES", "订单备注信息查询"),
    /** 订单地址信息 */
    ORDERADDRESS("ORDERADDRESS", "订单地址信息查询"),
    /** 订单航班信息 */
    ORDERFLIGHT("ORDERFLIGHT", "订单航班信息"),
    /** 订单客户信息 */
    ORDERCUS("ORDERCUS", "订单客户信息"),
    /** 订单是否分配了取派员信息 */
    ORDERDMAN("ORDERDMAN", "取派员信息"),
    /** 订单是否分配到集散中心 */
    ORDERTRANSIT("ORDERTRANSIT", "是否分配去集散中心送件"),
    /** 订单费用详情 */
    ORDER_PRICE_DETAIL("ORDER_PRICE_DETAIL", "订单费用详情"),
    /** 订单动作类型 */ 
    ACTION_DETAILS("ACTION_DETAILS", "订单动作类型");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private QUERY_ORERYDETAILS_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
