package com.fh.rule.autoallot;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.entity.order.OrderRole;
import com.fh.rule.BaseRule;
import com.fh.rule.Exception.RuleException;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：分配相同区域
 * 类名称：com.fh.rule.autoallot.checkHualWayRule     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:19:54   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:19:54   
 * 修改备注：
 */
@Component
public class AllotSameAreaRule implements BaseRule{
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DaoSupport dao;

	@Override
	@SuppressWarnings("all")
	public void execute(AutoAllotParam autoAllotParam) throws RuleException {
		Integer srcRegionid = autoAllotParam.getSrcRegionid();
		Integer desRegionid = autoAllotParam.getDesRegionid();
		if(srcRegionid == desRegionid){//同区域
				saveSrcOrderRole(autoAllotParam);
				saveDesOrderRole(autoAllotParam,null);
				return;
		}
		Integer srcTransitid = autoAllotParam.getSrcTransitd();
		Integer desTransitid = autoAllotParam.getDesTransitd();
		if(srcRegionid != desRegionid && srcTransitid == desTransitid){//同区域 不同集散中心
		PageData pd = new PageData();
		pd.put("transitCenterid", autoAllotParam.getSrcTransitd());		
			PageData Transit = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findTransitCenterByRegionid", pd);
			String arrivedtime = DateUtil.mulMinute(autoAllotParam.getSendtime(), autoAllotParam.getDestspan().intValue());
			saveSrcOrderRole(autoAllotParam);
			saveTransitSend(autoAllotParam, Transit,arrivedtime);
			saveTransitTake(autoAllotParam, Transit,arrivedtime);
			saveDesOrderRole(autoAllotParam,Transit);
	 }
	}

	
	private void saveSrcOrderRole(AutoAllotParam autoAllotParam){
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getSrcroleid());
		ore.setRoletype(autoAllotParam.getSrcroletype());
		ore.setIsshow(ISVALID_TYPE.VALID.getValue());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setDesttype(autoAllotParam.getSrctype());
		ore.setDestaddrname(autoAllotParam.getSrcaddressname());
		ore.setDestaddrdesc(autoAllotParam.getSrcaddress());
		ore.setDestaddress(autoAllotParam.getSrcaddressid());
		ore.setArrivedtime(DateUtil.getDateTimeStr(autoAllotParam.getTaketime()));
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveDesOrderRole(AutoAllotParam autoAllotParam,PageData desTransit) {
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getDesroleid());
		ore.setRoletype(autoAllotParam.getDesroletype());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		if(desTransit!=null){			
			ore.setSrctype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
			ore.setSrcaddrname(desTransit.get("name").toString());
			ore.setSrcaddress(desTransit.get("id").toString());
			ore.setSrcaddrdesc(desTransit.get("address").toString());
		}
		ore.setDesttype(autoAllotParam.getDesttype());
		ore.setDestaddrname(autoAllotParam.getDesaddressname());
		ore.setDestaddress(autoAllotParam.getDestaddressid());
		ore.setDestaddrdesc(autoAllotParam.getDestaddress());
		ore.setArrivedtime(DateUtil.getDateTimeStr(autoAllotParam.getSendtime()));
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveTransitTake(AutoAllotParam autoAllotParam,PageData Transit,String arrivedtime){
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getDesroleid());
		String roletype = ROLE_TYPE.ROLE_TRANSIT_TASK.getValue();
		ore.setRoletype(roletype);
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.VALID.getValue());
		ore.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(Transit.get("id").toString());
		ore.setDestaddrdesc(Transit.get("address").toString());
		ore.setDestaddrname(Transit.get("name").toString());
		ore.setArrivedtime(arrivedtime);
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveTransitSend(AutoAllotParam autoAllotParam,PageData Transit,String arrivedtime) {
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getSrcroleid());
		String roletype = ROLE_TYPE.ROLE_TRANSIT_SEND.getValue();
		ore.setRoletype(roletype);
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		ore.setSrcaddress(autoAllotParam.getSrcaddressid());
		ore.setSrcaddrname(autoAllotParam.getSrcaddressname());
		ore.setSrcaddrdesc(autoAllotParam.getSrcaddress());
		ore.setSrctype(autoAllotParam.getSrctype());
		ore.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(Transit.get("id").toString());
		ore.setDestaddrdesc(Transit.get("address").toString());
		ore.setDestaddrname(Transit.get("name").toString());
		ore.setArrivedtime(arrivedtime);
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
}
