package com.fh.common.constant_enum;

/**
 * @desc 查询范围
 * @author zhangjj
 * @history 2018年02月06日
 */
public enum QUERYSCOPE_TYPE {
    
    /** 去往市内（酒店或其他） */
    CITYSCOPE("CITYSCOPE", "去往市内"),
    /** 去往服务中心(包括柜台/机场)  */
    COUNTERCENTER("COUNTERCENTER", "(包括柜台/机场)");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private QUERYSCOPE_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
