package com.fh.common.constant_enum;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：自动分配异常类
 * 类名称：com.fh.common.constant_enum.ALLOT_LOG_CODE     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:12:40   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:12:40   
 * 修改备注：
 */
public enum ALLOT_LOG_CODE {
    
    /**
     * 自动分配成功
     */
    AUTO_ALLOT_SUCCESS("AUTO_ALLOT_SUCCESS", "自动分配成功"),
    /**
     * 校验异常
     */
    CHECK("CHECK", "检验异常"),
    /**
     * 重新分配
     */
    AGAINALLOT("AGAINALLOT", "重新分配"),
    /**
     * 系统异常
     */
    FAIL("FAIL", "系统异常"),
    /**
     * 人工分配成功
     */
    MANUAL_ALLOT_SUCC("MANUAL_ALLOT_SUCC", "人工分配成功"); 

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ALLOT_LOG_CODE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
