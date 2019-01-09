package com.fh.common.constant_enum;

/**
 * 取派员状态
 * @author tangqm
 * @date 2018年2月27日
 */
public enum DMAN_STATUS {
    /** 暂时无法调度  */
    CANNOTDISPATCH("CANNOTDISPATCH", "暂时无法调度"),
    /** 正常调度 */
    NORMALDISPATCH("NORMALDISPATCH", "正常调度");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private DMAN_STATUS(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
