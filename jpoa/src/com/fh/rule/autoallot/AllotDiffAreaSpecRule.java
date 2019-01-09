package com.fh.rule.autoallot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.entity.autoallot.HaulWayDetail;
import com.fh.entity.order.OrderRole;
import com.fh.rule.BaseRule;
import com.fh.rule.Exception.RuleException;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：集散中心运输人员与出发地取派员为同一人
 * 类名称：com.fh.rule.autoallot.AllotDiffAreaSpecRule     
 * 创建人：tangqm
 * 创建时间：2018年6月8日 下午2:39:49   
 * 修改人：
 * 修改时间：2018年6月8日 下午2:39:49   
 * 修改备注：
 */
@Component
public class AllotDiffAreaSpecRule implements BaseRule{
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DaoSupport dao;

	@Override
	public void execute(AutoAllotParam autoAllotParam) throws RuleException {
		PageData pd = new PageData();
		String arrivedtime = "";
		List<HaulWayDetail> haulWayList = autoAllotParam.getHualWayDetail();
		Integer srcRegionid = autoAllotParam.getSrcRegionid();
		Integer desRegionid = autoAllotParam.getDesRegionid();
		Integer srcTransitid = autoAllotParam.getSrcTransitd();
		Integer desTransitid = autoAllotParam.getDesTransitd();
		PageData desTransit = null;//获取最后一个集散中心
		if(srcRegionid == desRegionid || srcTransitid ==desTransitid){
			return;
		}
		if(!autoAllotParam.getSrcroleid().equals(autoAllotParam.getHualWayDetail().get(1).getDriver())){
			return;
		}
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
		Date now = DateUtil.fomatDate(fmt.format(new Date()));
		Date sendtime = DateUtil.fomatDate(fmt.format(autoAllotParam.getSendtime()));
		if(sendtime.after(now)){
			arrivedtime = DateUtil.addMinute(autoAllotParam.getTaketime(),autoAllotParam.getSrctspan());
		}else{
			arrivedtime = DateUtil.mulMinute(autoAllotParam.getSendtime(), (autoAllotParam.getDestspan().intValue()+autoAllotParam.getTrantspan().intValue()));
		}
		for (int i=1;i<haulWayList.size();i++) {
				HaulWayDetail haulWayDetail = haulWayList.get(i);
				if(i==1){//分配出发地
					pd.put("transitCenterid", autoAllotParam.getDesTransitd());
					PageData Transit = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findTransitCenterByRegionid", pd);
					saveSrcOrderRole(autoAllotParam);
					saveTransitSend(autoAllotParam, Transit,arrivedtime);
					continue;
				}
				//分配出发地集散中心
				pd.put("transitCenterid", haulWayDetail.getSrcid());
				PageData srcTransit = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findTransitCenterByRegionid", pd);
				saveTransitTake(autoAllotParam,srcTransit,haulWayDetail.getDriver(),arrivedtime);
				//分配目的地集散中心
				pd.put("transitCenterid", haulWayDetail.getDesid());
				desTransit = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findTransitCenterByRegionid", pd);//得到目的地集散中心
				arrivedtime = DateUtil.addMinute(DateUtil.fomatDate(arrivedtime,"yyyy-MM-dd HH:mm"),haulWayDetail.getTimespan());
				saveTransitSendByTransit(autoAllotParam,srcTransit,desTransit,haulWayDetail.getDriver(),arrivedtime);
			}
		if(desTransit==null){
			pd.put("transitCenterid", autoAllotParam.getDesTransitd());
			desTransit = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findTransitCenterByRegionid", pd);//得到目的地集散中心
		}
			saveTransitTake(autoAllotParam,desTransit,autoAllotParam.getDesroleid(),arrivedtime);
			saveDesOrderRole(autoAllotParam,desTransit);
	}
	
	private void saveSrcOrderRole(AutoAllotParam autoAllotParam){
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getSrcroleid());
		ore.setRoletype(autoAllotParam.getSrcroletype());
		ore.setIsshow(ISVALID_TYPE.VALID.getValue());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setDesttype(autoAllotParam.getSrctype());
		ore.setDestaddress(autoAllotParam.getSrcaddressid());
		ore.setDestaddrname(autoAllotParam.getSrcaddressname()); 
		ore.setDestaddrdesc(autoAllotParam.getSrcaddress());
		ore.setArrivedtime(DateUtil.getDateTimeStr(autoAllotParam.getTaketime()));
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveTransitSend(AutoAllotParam autoAllotParam,PageData Transit,String arrivedtime) {
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getSrcroleid());
		ore.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		ore.setSrcaddrdesc(autoAllotParam.getSrcaddressid());
		ore.setSrcaddrdesc(autoAllotParam.getSrcaddress());
		ore.setSrctype(autoAllotParam.getSrctype());
		ore.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(Transit.get("id").toString());
		ore.setDestaddrname(Transit.get("name").toString());
		ore.setDestaddrdesc(Transit.get("address").toString());
		ore.setArrivedtime(arrivedtime);
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveDesOrderRole(AutoAllotParam autoAllotParam,PageData desTransit) {
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(autoAllotParam.getDesroleid());
		ore.setRoletype(autoAllotParam.getDesroletype());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		ore.setSrctype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setSrcaddrname(desTransit.get("name").toString());
		ore.setSrcaddress(desTransit.get("id").toString());
		ore.setSrcaddrdesc(desTransit.get("address").toString());
		ore.setDesttype(autoAllotParam.getDesttype());
		ore.setDestaddrname(autoAllotParam.getDesaddressname());
		ore.setDestaddress(autoAllotParam.getDestaddressid());
		ore.setDestaddrdesc(autoAllotParam.getDestaddress());
		ore.setArrivedtime(DateUtil.getDateTimeStr(autoAllotParam.getSendtime()));
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveTransitTake(AutoAllotParam autoAllotParam,PageData Transit,Integer roleid,String arrivedtime){
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(roleid);
		String roletype = ROLE_TYPE.ROLE_TRANSIT_TASK.getValue();
		ore.setRoletype(roletype);
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.VALID.getValue());
		ore.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(Transit.get("id")+"");
		ore.setDestaddrdesc(Transit.get("address").toString());
		ore.setDestaddrname(Transit.get("name").toString());
		ore.setArrivedtime(arrivedtime);
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
	private void saveTransitSendByTransit(AutoAllotParam autoAllotParam,PageData srcTransit,PageData desTransit,Integer roleid,String arrivedtime) {
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setRoleid(roleid);
		String roletype = ROLE_TYPE.ROLE_TRANSIT_SEND.getValue();
		ore.setRoletype(roletype);
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		ore.setSrcaddrdesc(srcTransit.get("address").toString());
		ore.setSrcaddress(srcTransit.get("id").toString());
		ore.setSrcaddrname(srcTransit.get("name").toString());
		ore.setSrctype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(srcTransit.get("id").toString());
		ore.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
		ore.setDestaddress(desTransit.get("id").toString());
		ore.setDestaddrname(desTransit.get("name").toString());
		ore.setDestaddrdesc(desTransit.get("address").toString());
		ore.setArrivedtime(arrivedtime);
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}
	
}
