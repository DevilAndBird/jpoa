package com.fh.common.constant_enum;

/**
 * @desc 是否失效枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ISVALID_TYPE {  
    
    /** 有效 */
    VALID("Y", "有效"),
    
    /** 无效 */
    INVALID("N", "无效");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ISVALID_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
