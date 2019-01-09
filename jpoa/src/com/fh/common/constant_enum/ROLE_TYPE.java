package com.fh.common.constant_enum;

public enum ROLE_TYPE {
    /** 去酒店取件 */
    ROLE_HOTEL_TASK("ROLE_HOTEL_TASK", "去酒店取件"),
    /** 去酒店派件 */
    ROLE_HOTEL_SEND("ROLE_HOTEL_SEND", "去酒店送件"),
    /** 去集散中心取件 */
    ROLE_TRANSIT_TASK("ROLE_TRANSIT_TASK", "去集散中心取件"),
    /** 去集散中心送件 */
    ROLE_TRANSIT_SEND("ROLE_TRANSIT_SEND", "去集散中心送件"),
    /** 去机场取件 */
    ROLE_AIRPORT_TASK("ROLE_AIRPORT_TASK", "去机场取件"),
    /** 去机场派件 */
    ROLE_AIRPORT_SEND("ROLE_AIRPORT_SEND", "去机场送件"),
    /** 抵达机场柜台 */
    ROLE_ARRIVE_AIRPORT("ROLE_ARRIVE_AIRPORT", "抵达机场柜台"),
    /** 抵达集散中心 */
    ROLE_ARRIVE_TRANSIT("ROLE_ARRIVE_TRANSIT", "抵达集散中心"),
    /** 抵达酒店 */
    ROLE_ARRIVE_HOTEL("ROLE_ARRIVE_HOTEL", "抵达酒店"),
    /** 机场已取件 */
    ROLE_AIRPORT_TASKED("ROLE_AIRPORT_TASKED", "机场已取件"),
    /** 机场已释放 */
    ROLE_AIRPORT_RELEASEOVER("ROLE_AIRPORT_RELEASEOVER", "机场已释放");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ROLE_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
