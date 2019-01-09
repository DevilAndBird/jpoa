package com.fh.common.constant_enum;

/**
 * @desc 是否使用枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ISSCAN_TYPE {
    
    /** 已扫描 */
    SCANNED("1", "已扫描"),
    /** 解除扫描  */
    REMOVESCAN("0", "解除扫描");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ISSCAN_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
