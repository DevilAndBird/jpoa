package com.fh.common.constant_enum;

/**
 * @desc 评价等级
 * @author zhangjj
 * @history 2018年04月10日
 */
public enum EVALUATE_CLASS {
	
    /** 非常差 */
	VERYBAD("VERYBAD", "非常差"),
	/** 差 */
	BAD("BAD", "差"),
    /** 一般 */
	COMMON("COMMON", "一般"),
	/** 好 */
	GOOD("GOOD", "好"),
	/** 非常好 */
	VERYGOOD("VERYGOOD", "非常好");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private EVALUATE_CLASS(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
