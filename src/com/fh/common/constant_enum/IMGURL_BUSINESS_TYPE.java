package com.fh.common.constant_enum;

public enum IMGURL_BUSINESS_TYPE {
    /** 收取行李拍照 */
	COOLECT("COOLECT", "收取行李拍照"),
    /** 释放行李拍照使用 */
	RELEASE("RELEASE", "释放行李拍照使用");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private IMGURL_BUSINESS_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
