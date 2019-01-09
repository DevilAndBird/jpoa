package com.fh.rule.Exception;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：规则异常类
 * 类名称：com.fh.rule.Exception.RuleException     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:08:05   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:08:05   
 * 修改备注：
 */
public class RuleException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String code ;  //异常对应的返回码
    private String message;  //异常对应的描述信息
	
	public RuleException(String code,String message) {  
        super(message);
        this.code = code;
        this.message = message;
    }
    
	public RuleException(String message, Throwable cause)
    {
	         super(message, cause);
	         this.message = message;
	 }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
