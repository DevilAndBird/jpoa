package com.fh.common.constant_enum;

/**
 * 订单渠道
 * @author tangqm
 * @date 2018年2月27日
 */
public enum ORDER_CHANNEL {
    
    /** 微信_金牌 */
    WINXIN_GS("weixin_gs", "微信_金牌"),
    /** 微信_专车 */
    WINXIN_SC("weixin_sc", "微信_专车"),
    /** app_金牌 */
    APP_GS("app_gs", "app_金牌"),
    /** app_专车 */
    APP_SC("app_sc", "app_专车");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ORDER_CHANNEL(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
