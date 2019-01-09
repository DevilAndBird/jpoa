package com.fh.rule;

import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.rule.Exception.RuleException;

/**
 * 基础规则类
 * @author tangqm
 *
 */
public interface  BaseRule {
	
	/**
	 * 基础执行函数
	 */
	public abstract void execute(AutoAllotParam autoAllotParam) throws RuleException;
	
}
