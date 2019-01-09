package com.fh.rule;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：规则工厂类
 * 类名称：com.fh.rule.RuleFactory     
 * 创建人：tangqm
 * 创建时间：2018年5月30日 下午2:18:01   
 * 修改人：
 * 修改时间：2018年5月30日 下午2:18:01   
 * 修改备注：
 */
@Component
public class RuleFactory implements ApplicationContextAware{

	@Autowired
	private ApplicationContext applicationContext;  
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 
	 * @Title: getActualRule
	 * @Description: 得到实际规则类
	 * author：tangqm
	 * 2018年5月30日
	 * @param beanName
	 * @return
	 */
	public BaseRule getActualRule(String beanName){
		if(applicationContext.containsBean(beanName)){
			BaseRule baseRule = applicationContext.getBean(beanName,BaseRule.class);
			return baseRule;
		}
		return null;
	}
	

}
