package com.fh.common.constant_enum;

/**
 * @desc 是否使用枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ISUSED_TYPE {
    
    /** 已使用 */
    USED("USED", "已使用"),
    
    /** 未使用 */
    UNUSED("UNUSED", "未使用");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ISUSED_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
