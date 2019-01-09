package com.fh.common.constant_enum;

/**
 * 极光推送消息类型
 * @author sqp
 * @date 2018年5月21日
 */
public enum PUSHINFO_TYPE {
    
    /**
     * 订单消息
     */
	ORDERMSG("ORDERMSG", "订单消息"),
    /**
     * 企业消息
     */   
	SYSTEMMSG("SYSTEMMSG", "企业消息");
    
    

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private PUSHINFO_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
