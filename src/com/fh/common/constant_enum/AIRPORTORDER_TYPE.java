package com.fh.common.constant_enum;

public enum AIRPORTORDER_TYPE {
    /** 去酒店取件 */
    HOSTING("HOSTING", "寄存件"),
    /** 去酒店派件 */
    FINISH("FINISH", "已完成件"),
    /** 去集散中心取件 */
    TAKING("TAKING", "待取件"),
    /** 去集散中心送件 */
    SENDING("SENDING", "待派件"),
    /** 待支付 */
    WAITPAY("WAITPAY", "待支付");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private AIRPORTORDER_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }
}

