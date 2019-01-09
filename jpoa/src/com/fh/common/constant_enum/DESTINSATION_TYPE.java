package com.fh.common.constant_enum;

/**
 * 目的地类型,用于order_role
 * @author zhangjj
 * @date 2018年3月09日
 */
public enum DESTINSATION_TYPE {
    /** 服务中心 */
    SERVICECERTER("SERVICECERTER", "服务中心(机场/高铁)"),
    /** 机场柜台 */
	AIRPORTCOUNTER("AIRPORTCOUNTER", "机场柜台"),
    /** 集散中心 */
    TRANSITCERTER("TRANSITCERTER", "集散中心"),
    /** 酒店 */
    HOTEL("HOTEL", "酒店"),
    /** 住宅 */
    RESIDENCE("RESIDENCE", "住宅");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private DESTINSATION_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
