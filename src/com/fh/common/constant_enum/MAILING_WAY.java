package com.fh.common.constant_enum;
/**
 * 寄件/收件方式
 * @author tqm
 * @date 2018年11月5日
 */
public enum MAILING_WAY {
	/** 柜台 */
	AIRPOSTCOUNTER("AIRPOSTCOUNTER", "柜台"),
    /** 本人 */
	ONESELF("ONESELF", "本人"),
    /** 酒店前台 */
	FRONTDESK("FRONTDESK", "酒店前台"),
    /** 他人 */
	OTHER("OTHER", "他人");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private MAILING_WAY(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
