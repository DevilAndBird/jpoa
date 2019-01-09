package com.fh.common.constant_enum;
/**
 * 发票类型枚举
 * @author sunqp
 * @date 2018年4月17日
 */
public enum INVOICE_INVOICETYPE {

	/** 个人 */
    PERSONAL("PERSONAL", "个人"),
    
    /** 单位 */
    COMPANY("COMPANY", "单位");
    
	private final String value;
    private final String name;
    
    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private INVOICE_INVOICETYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
