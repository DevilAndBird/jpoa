package com.fh.common.constant_enum;

/**
 * @desc 保费编码
 * @auther zhangjj
 * @date 2018年10月31日
 */
public enum INSURE_TYPE {
    /** 0001-0 */
	_0001("0001", 0, 1000,"保费0元"),
    /** 0002-6  */
	_0002("0002" , 6, 2000,"保费6元"),
    /** 0003-12 */
	_0003("0003", 12, 4000,"保费12元");

    /** 保费编码 */
    private final String insurancecode;
    /** 保费 */
    private final Integer premium;
    /** 保额 */
    private final Integer coverage;
    /** 描述 */
    private final String insurancedesc;
	
    public String getInsurancecode() {
		return insurancecode;
	}
	public Integer getPremium() {
		return premium;
	}
	
	public Integer getCoverage() {
		return coverage;
	}
	
	public String getInsurancedesc() {
		return insurancedesc;
	}
	
	private INSURE_TYPE(String insurancecode, Integer premium, Integer coverage, String insurancedesc) {
		this.insurancecode = insurancecode;
		this.premium = premium;
		this.coverage = coverage;
		this.insurancedesc = insurancedesc;
	}
	
	/**
	 * @desc 根据保费编码查询相应保费
	 * @auther zhangjj
	 * @date 2018年10月31日
	 */
	public static INSURE_TYPE getPrem(String insurancecode) {
		for ( INSURE_TYPE insureType: INSURE_TYPE.values()) {
			if(insureType.getInsurancecode().equals(insurancecode)) {
				return insureType;
			}
		}
		
		return INSURE_TYPE._0001;
	}
  
}
