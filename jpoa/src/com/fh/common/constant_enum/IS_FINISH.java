package com.fh.common.constant_enum;

/**
 * @desc 是否完成枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum IS_FINISH {
	
    /** 未开始 */
	UNFINISHED("UNFINISHED", "未开始"),
	/** 进行中*/
	ONGOING("ONGOING", "进行中"),
    /** 完成 */
	FINISHED("FINISHED", "完成");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private IS_FINISH(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
