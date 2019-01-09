package com.fh.common.constant_enum;

/**
 * @desc 推送语音类型
 * @author sunqp
 * @history 2018年06月27日
 */
public enum VOICEINFO_TYPE {
	
    /** 即将到达 */
	COMINGSOON("COMINGSOON", "即将到达"),
	
	/** 分配或更改取派员 */
	
	NEWROLE("NEWROLE", "新任务"),	
	/** 分配或更改取派员 */
	UPDATEROLE("UPDATEROLE", "改派订单"),	
	
	/** 专车订单 */
	SPECIALCAR("SPECIALCAR", "专车订单"),	
	
	/** 已经到达 */
	ARRIVED("ARRIVED", "已经到达"),
	
	/** 已取消 */
	CANCEL("CANCEL", "取消订单"),

	/** 反馈 */
    FEEDBACK("FEEDBACK", "订单反馈");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private VOICEINFO_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
