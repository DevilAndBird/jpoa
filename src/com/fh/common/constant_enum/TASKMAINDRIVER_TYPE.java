package com.fh.common.constant_enum;

/**
 * @desc 司机任务类型枚举
 * @author zhangjj
 * @history 2018年02月06日
 */
public enum TASKMAINDRIVER_TYPE {
    
    /** 定时任务 */
    TIMED_TASK("TIMED_TASK", "定时任务"),
    /** 临时任务 */
    TEMP_TASK("TEMP_TASK", "临时任务");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private TASKMAINDRIVER_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
