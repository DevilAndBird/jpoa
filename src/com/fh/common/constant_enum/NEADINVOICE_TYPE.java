package com.fh.common.constant_enum;

/**
 * @desc 是否需要开发票枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum NEADINVOICE_TYPE {
	
    /** 需要开发票 */
    NEEDED("NEEDED", "需要开发票"),
    
    /** 不需要开发票 */
    UNNEEDED("UNNEEDED", "不需要开发票");
    

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private NEADINVOICE_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
