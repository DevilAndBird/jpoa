package com.fh.common.constant_enum;

/**
 * @desc 是否订单类型枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ORDER_TYPE {
	
    /** 机场到酒店(柜台到酒店) */
    AIRPORTCOUNTER_TO_HOTEL("AIRPORTCOUNTERTOHOTEL", "机场柜台到酒店"),
    /** 机场到酒店(轮盘到酒店) */
    AIRPORTTURNTABLE_TO_HOTEL("AIRPORTTURNTABLETOHOTEL", "机场转盘到酒店"),
    /** 酒店到机场 */
    HOTEL_TO_AIRPORT("HOTELTOAIRPORT", "酒店到机场");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {  
        return this.name;
    }

    private ORDER_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
