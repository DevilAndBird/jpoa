package com.fh.common.constant_enum;

public enum UPDATE_TYPE {
    /** 订单ID更新 */
    ORDERID("ORDERID", "订单ID更新"),
    /** 订单编码 */
    ORDERNO("ORDERNO", "订单编码"),
    /** 订单id集合 */
    ORDERIDLIST("ORDERIDLIST", "订单id集合"),
    /** 订单编码集合 */
    ORDERNOLIST("ORDERNOLIST", "订单编码集合");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private UPDATE_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
