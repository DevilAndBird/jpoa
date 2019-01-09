package com.fh.common.constant_enum;

/**
 * app接口返回类型枚举
 * @author tangqm
 * @date 2018年2月27日
 */
public enum APP_RESPONSE_CODE {
    
    /**
     * 成功
     */
    SUCCESS("0", "成功"),
    /**
     * 失败
     */
    FAIL("1", "失败"),
    /**
     * 校验异常
     */
    CHECK("2", "校验异常"),
    /**
     * 登陆超时
     */
    LOGINERROR("3", "登陆超时");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private APP_RESPONSE_CODE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
