package com.fh.common.constant_enum;

public enum INVICE_STATUS {

	/** 已申请 */
    APPLYED("APPLYED", "已申请"),
    
    /** 已邮寄 */
    MAILED("MAILED", "已邮寄");
    
	private final String value;
    private final String name;
    
    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private INVICE_STATUS(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
