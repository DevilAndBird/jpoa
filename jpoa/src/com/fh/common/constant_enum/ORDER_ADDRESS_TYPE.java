package com.fh.common.constant_enum;

/**
 * @desc 是否订单类型枚举，用于order_address
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ORDER_ADDRESS_TYPE {
	/** 机场转盘 */
	AIRPORTTURNTABLE("AIRPORTTURNTABLE", "机场转盘"),
	/** 机场柜台 */
	AIRPORTCOUNTER("AIRPORTCOUNTER", "机场柜台"),
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

    private ORDER_ADDRESS_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
