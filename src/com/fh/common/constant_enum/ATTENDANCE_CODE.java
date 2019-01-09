package com.fh.common.constant_enum;

/**
 * 在岗/离岗
 * @author tangqm
 *
 */
public enum ATTENDANCE_CODE {
    
    /**
     * 在岗
     */
	WORK("WORK", "在岗"),
    /**
     * 离岗
     */
	REST("REST", "离岗");

    private final String value;
    private final String name;

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    private ATTENDANCE_CODE(String value, String name) {
        this.value = value;
        this.name = name;
    }

}
