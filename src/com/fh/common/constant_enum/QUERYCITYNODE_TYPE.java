package com.fh.common.constant_enum;

/**
 * @desc 查询服务中心节点/集散中心节点
 * @author zhangjj
 * @history 2018年02月06日
 */
public enum QUERYCITYNODE_TYPE {
    
    /** 集散中心列表 */
	TRANSITCENTERLIST("TRANSITCENTERLIST", "集散中心列表"),
    /** 服务中心列表  */
	COUNTERCENTERLIST("COUNTERCENTERLIST", "服务中心列表");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private QUERYCITYNODE_TYPE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
