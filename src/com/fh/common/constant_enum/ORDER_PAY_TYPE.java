package com.fh.common.constant_enum;

/**
 * 支付类型
 * @author tangqm
 * @date 2018年2月27日
 */
public enum ORDER_PAY_TYPE {

    /** 微信 */
    WEIXIN("WEIXIN", "微信"),
    /** 支付宝 */
    ALIPAY("ALIPAY", "支付宝"),
    /** 月结 */
    MONTH("MONTH", "月结"),
    /** 现金 */
    CASH("CASH", "现金");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ORDER_PAY_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
