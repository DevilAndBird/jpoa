package com.fh.common.constant_enum;

/**
 * 订单状态
 * @author tangqm
 * @date 2018年2月27日
 */
public enum ORDER_PAY_STUTAS {

    /** 未支付 */
    WAITPAY("WAITPAY", "未支付"),
    /** 已支付 */
    PREPAID("PREPAID", "已支付"),
    /** 支付失败 */
    PAINFAIL("PAINFAIL", "支付失败");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ORDER_PAY_STUTAS(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
