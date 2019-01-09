package com.fh.common.constant_enum;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：
 * 类名称：com.fh.common.constant_enum.PROCESSSTATUS_TYPE     
 * 创建人：tqm
 * 创建时间：2018年2月12日 上午2:22:53   
 * 修改人：
 * 修改时间：2018年2月12日 上午2:22:53   
 * 修改备注：   
 * @version   V1.0
 */
public enum PROCESSSTATUS_TYPE {
    
    /** 未解决 */
	UNSOLVED("UNSOLVED", "未解决"),
    
    /** 已解决 */
	SOLVED("SOLVED", "已解决");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private PROCESSSTATUS_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
