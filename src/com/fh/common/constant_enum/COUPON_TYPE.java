package com.fh.common.constant_enum;

public enum COUPON_TYPE {
    /** 折扣 */
    DISCOUNT("discount", "折扣"),
    /** 直减 */
    DIRECT_REDUCE("directreduce", "直减"),
    /** 满减 */
    FULL_REDUCE("fullreduce", "满减");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private COUPON_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
