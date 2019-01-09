package com.fh.common.constant_enum;

/**
 * 问题件类型
 * 
 * @author tangqm
 * @date 2018年3月4日
 */
public enum TOUBLE_CODE {

	/** 丢失行李 */
	LOST("LOST", "丢失行李"),

	/** 无法联系客户  */
	OUTOFCONTACT("OUTOFCONTACT", "无法联系客户"),
	
	/** 行李未准备好  */
	UNRIPE("UNRIPE", "行李未准备好"),
	/** 订单超时  */
	OVERTIME("OVERTIME", "订单超时");
	
	

	private final String value;
	private final String name;

	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	private TOUBLE_CODE(String value, String name) {
		this.value = value;
		this.name = name;
	}

}
