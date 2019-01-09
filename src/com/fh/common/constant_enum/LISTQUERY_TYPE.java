package com.fh.common.constant_enum;

/**
 * app_订单列表查询
 * @author zhangjj
 * @date 2018年2月27日
 */
public enum LISTQUERY_TYPE {
    /** 经过集散中心查询 */
	TRANSIT("TRANSIT", "集散中心查询"),
    /** 来自服务中心  */
	COUTNTCENTER_TASK("COUTNTCENTER_TASK", "来自服务中心");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private LISTQUERY_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
