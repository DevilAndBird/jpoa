package com.fh.rule.autoallot;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.rule.BaseRule;
import com.fh.rule.Exception.RuleException;
import com.fh.util.DateUtil;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：检验取件时间规则
 * 类名称：com.fh.rule.autoallot.checkTakeTimeRule     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:01:16   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:01:16   
 * 修改备注：
 */
@Component
public class CheckTakeTimeRule implements BaseRule{

	public Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	@SuppressWarnings("all")
	public void execute(AutoAllotParam autoAllotParam) throws RuleException {
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
		Date  taketime = autoAllotParam.getTaketime();
		if(taketime == null){
			logger.error("订单id:"+autoAllotParam.getOrderid()+",取件时间为空");			
			throw new RuleException(ALLOT_LOG_CODE.CHECK.getValue(),"取件时间为空");
		}
		Date now = DateUtil.fomatDate(fmt.format(new Date()));
		taketime = DateUtil.fomatDate(fmt.format(taketime));
		if(taketime.after(now)){
			logger.error("订单id:"+autoAllotParam.getOrderid()+"非当日取派订单");			
			throw new RuleException(ALLOT_LOG_CODE.CHECK.getValue(),"取件时间非今日");
		}
	}
}
