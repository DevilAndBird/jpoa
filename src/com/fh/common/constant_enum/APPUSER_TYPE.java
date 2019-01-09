package com.fh.common.constant_enum;

/**
 * @desc 登陆用户类型枚举
 * @author zhangjj
 * @history 2018年02月06日
 */
public enum APPUSER_TYPE {
    
    /** 取派员 */
    DELIVERY_MAN("DELIVERY_MAN", "取派员"),
    /** 集散中心 */
    TRANSIT_CENTER("TRANSIT_CENTER", "集散中心"),
    /** 班车司机 */
    REGULAR_DRIVER("REGULAR_DRIVER", "班车司机"),
    /** 机场取件员 */
    AIRPORT_PICKER("AIRPORT_PICKER", "机场取件员"),
    /** 柜台服务中心 */
    SERVICE_CENTER("SERVICE_CENTER", "柜台服务中心");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private APPUSER_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
