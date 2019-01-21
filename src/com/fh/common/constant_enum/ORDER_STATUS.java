package com.fh.common.constant_enum;

/**
 * @desc 是否订单状态枚举
 * @author zhangjj
 * @history 2018年01月15日
 */
public enum ORDER_STATUS {
    /** 待支付 */
    WAITPPAY("WAITPAY", "待支付"),
    /** 已支付 */
    PREPAID("PREPAID", "已支付的"),
    /** 待取件 */
    WAITPICK("WAITPICK", "待取件"),
    /** 取件中 */
    TAKEGGOOSING("TAKEGOOGSING", "取件中"),
    /** 已取件 */
    TAKEGOOGSOVER("TAKEGOOGSOVER", "已取件"),
    /** 待接单 */
    WAITORDERRECEIVING("WAITORDERRECEIVING", "待接单"),
    /** 已接单 */
    ORDERRECEIVINGOVER("ORDERRECEIVINGOVER", " 已接单"),
    /** 待装车 */
    WAITTRUCELOADING("WAITTRUCELOADING", "待装车"),
    /** 已装车 */
    TRUCELOADING("TRUCELOADINGOVER", "已装车"),
    /** 已中转 */
    TRANSFEROVER("TRANSFEROVER", "已中转"),
    /** 已达机场 */
    ARRIVEAIRPORT("ARRIVEAIRPORT", "已达机场"),
    /** 已释放 */
    RELEASEOVER("RELEASEOVER", "已释放"),
    /** 已派单 */
    ALLOTDELIVERY("ALLOTDELIVERY", "已派单"),
    /** 派送中 */
    DELIVERYING("DELIVERYING", "派送中"),
    /** 已送达 */
    DELIVERYOVER("DELIVERYOVER", "已送达"),
    /** 问题件处理 */
    TROUBLEDEAL("TROUBLE_DEAL", "问题件处理"),
    /** 已取消 */
    CANCELLED("CANCELLED", "已取消"),
    /**待卸车*/
	WAITINGUNLOAD("WAITINGUNLOAD", "待卸车"), 
	/**已卸车*/
	UNLOAD("UNLOAD", "已卸车"),
    /**配送中*/
    INTRANSIT("INTRANSIT", "配送中");

	private final String value;
	private final String name;

	public String getValue() {
		return this.value;
	}

	public String getName() {
		return this.name;
	}

	private ORDER_STATUS(String value, String name) {
		this.value = value;
		this.name = name;
	}

}
