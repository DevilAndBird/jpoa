package com.fh.common.constant_enum;

/**
 * @desc 司机任务状态
 * @author zhangjj
 * @history 2018年02月06日
 */
public enum TASKMAINDRIVER_STATUS {
    
    /** 未开始 */
    NOTSTARTED("NOTSTARTED", "未开始"),
    /** 确认发车 */
    STARTED("HASSTARTED", "确认发车"),
    /** 即将到达 */
    ARRIVING("ARRIVING", "即将到达"),
    /** 确认到达 */
    ARRIVED("ARRIVED", "确认到达"),
    /** 已取消 */
    CANCEL("CANCELLED", "已取消");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private TASKMAINDRIVER_STATUS(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
