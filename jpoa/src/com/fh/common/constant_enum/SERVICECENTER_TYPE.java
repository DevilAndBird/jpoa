package com.fh.common.constant_enum;

/**
 * @desc 机场服务中心类型
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum SERVICECENTER_TYPE {
    /** 机场服务中心 */
    AIRPORT("AIRPORT", "机场服务中心"),
    /** 高铁服务中心 */
    HIGHSPEEDTRAIN("HIGHSPEEDTRAIN", "高铁服务中心"),
    /** 服务网点 */
    SERVERBRANCH("SERVERBRANCH", "服务网点");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private SERVICECENTER_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
