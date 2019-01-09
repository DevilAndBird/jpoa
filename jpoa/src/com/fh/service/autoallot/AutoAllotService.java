package com.fh.service.autoallot;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.entity.order.OrderRole;
import com.fh.rule.BaseRule;
import com.fh.rule.RuleFactory;
import com.fh.rule.Exception.RuleException;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.service.order.OrderRoleService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.DateUtil;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class AutoAllotService {

	@Autowired
	private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	@Autowired
	private RuleFactory ruleFactory;
	@Autowired
	private PushInfoService pushInfoService;
	@Autowired
	private OrderRoleService orderRoleService;

	public Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @Title: saveAutoAllotWay
	 * @Description: 保存自动分配路线
	 * author：tangqm
	 * 2018年9月26日
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public void saveAutoAllotWay(PageData pd) throws Exception {
		PageData saveAutoAllotDetail = new PageData();
		Integer srcid = null;
		Integer desid = null;
		Integer timespan = null;
		dao.save("AutoAllotMapper.insertHaulWay", pd);
		Integer haulwayid = (Integer) pd.get("id");
		List<String> transitidList = new Gson().fromJson(pd.get("transitArr")
				.toString(), new TypeToken<List<String>>() {
		}.getType());
		List<String> spanList = new Gson().fromJson(pd.get("spanArr")
				.toString(), new TypeToken<List<String>>() {
		}.getType());
		saveAutoAllotDetail.put("haulwayid", haulwayid);
		for (int i = 0; i < transitidList.size(); i++) {
			if (i == 0) {
				desid = Integer.parseInt(pd.getString("startTransit")
						.toString());
			} else {
				srcid = Integer.parseInt(transitidList.get(i - 1));
				desid = Integer.parseInt(transitidList.get(i));
				timespan = Integer.parseInt(spanList.get(i).toString());
			}
			saveAutoAllotDetail.put("srcid", srcid);
			saveAutoAllotDetail.put("desid", desid);
			saveAutoAllotDetail.put("timespan", timespan);
			dao.save("AutoAllotMapper.insertHaulWayDetail", saveAutoAllotDetail);
		}
	}

	@SuppressWarnings("all")
	public List<PageData> haulwaylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"AutoAllotMapper.haulwaylistPage", page);
	}

	@SuppressWarnings("all")
	public void deleteHualWay(PageData pd) throws Exception {
		dao.delete("AutoAllotMapper.deleteHualWay", pd);
		dao.delete("AutoAllotMapper.deleteHualWayDetail", pd);
	}

	@SuppressWarnings("all")
	public List<PageData> findPreviewMap(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"AutoAllotMapper.findPreviewMap", pd);
	}

	@SuppressWarnings("all")
	public List<PageData> findAutoAllotRule() throws Exception {
		return (List<PageData>) dao.findForList(
				"AutoAllotMapper.findAutoAllotRule", null);
	}

	/**
	 * 
	 * @Title: autoAllot
	 * @Description: 自动分配 author：tangqm 2018年5月30日
	 * @param orderid
	 * @throws RuleException
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public void cbAutoAllot(PageData pd) throws RuleException {
		String code = ALLOT_LOG_CODE.AUTO_ALLOT_SUCCESS.getValue();
		String remark = "自动分配成功";
		List<PageData> autoAllotRuleList = getAllAutoAllotRule();
		AutoAllotParam autoAllotParam = (AutoAllotParam) dao
				.findForObjectByAutoAllot("AutoAllotMapper.findOrderinfo", pd);
		for (PageData pageData : autoAllotRuleList) {
			BaseRule actualRule = ruleFactory.getActualRule(pageData
					.getString("rulename"));
			actualRule.execute(autoAllotParam);
		}
	}

	@SuppressWarnings("all")
	private List<PageData> getAllAutoAllotRule() {
		List<PageData> autoAllotRuleList = (List<PageData>) RedisUtil
				.get("autoAllotRuleList");
		if (CollectionUtils.isEmpty(autoAllotRuleList)) {
			autoAllotRuleList = (List<PageData>) dao.findForListByAutoAllot(
					"AutoAllotMapper.findAutoAllotRule", null);
		}
		return autoAllotRuleList;
	}

	@SuppressWarnings("all")
	public List<PageData> findAutoAllotOrder() throws Exception {
		return (List<PageData>) dao.findForList(
				"AutoAllotMapper.findAutoAllotOrder", null);
	}

	// 添加日志
	public void insertAutoAllotLog(Integer orderid, String type, String remark) {
		try {
			PageData pd = new PageData();
			pd.put("orderid", orderid);
			pd.put("type", type);
			pd.put("remark", remark);
			dao.save("AutoAllotMapper.insertAutoAllotLog", pd);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	@SuppressWarnings("all")
	public PageData getOrderIdByOrderNo(PageData pd) throws Exception {
		return (PageData) dao.findForObject(
				"AutoAllotMapper.getOrderIdByOrderNo", pd);
	}

	public String cbSemiAllot(PageData pd) throws Exception {
		AutoAllotParam autoAllotParam = (AutoAllotParam) dao
				.findForObjectByAutoAllot("AutoAllotMapper.findOrderinfo", pd);
		if (existRoleForOrder(autoAllotParam.getOrderid())) {
			return "FAIL:取派员已存在";
		}
		int userid = Integer.parseInt((String) pd.get("userid"));
		pushInfoService.allocNewOrderDman(userid, autoAllotParam.getOrderid());
		autoAllotParam.setSrcroleid(userid);
		autoAllotParam.setDesroleid(userid);
		saveSrcOrderRole(autoAllotParam);
		saveDesOrderRole(autoAllotParam);
		return "SUCCESS:分配成功!";
	}

	/**
	 * 判断订单是否已存在取派员
	 * 
	 * @param orderId
	 *            订单id
	 */
	private boolean existRoleForOrder(Integer orderId) {
		PageData orderDMain = null;
		try {
			orderDMain = (PageData) dao.findForObject(
					"OrderRoleMapper.findOrderlinkdman", orderId);
			if (null == orderDMain
					|| StringUtils.isBlank(String.valueOf(orderDMain
							.get("orderfid")))) {
				return false;
			} else {
				return true;
			}
		} catch (TooManyResultsException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void saveSrcOrderRole(AutoAllotParam autoAllotParam) {
		if (ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(
				autoAllotParam.getSrcroletype())) {
			autoAllotParam.setSrcroletype(ROLE_TYPE.ROLE_AIRPORT_TASK
					.getValue());
			autoAllotParam.setSrctype(DESTINSATION_TYPE.SERVICECERTER
					.getValue());
		} else if (ORDER_ADDRESS_TYPE.HOTEL.getValue().equals(
				autoAllotParam.getSrcroletype())) {
			autoAllotParam.setSrcroletype(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
			autoAllotParam.setSrctype(DESTINSATION_TYPE.HOTEL.getValue());
		} else {
			autoAllotParam.setSrcroletype(ROLE_TYPE.ROLE_HOTEL_TASK.getValue());
			autoAllotParam.setSrctype(DESTINSATION_TYPE.RESIDENCE.getValue());
		}
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
		ore.setDestgps(autoAllotParam.getSrc_gps());
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}

	private void saveDesOrderRole(AutoAllotParam autoAllotParam) {
		if (ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(
				autoAllotParam.getDesroletype())) {
			autoAllotParam.setDesroletype(ROLE_TYPE.ROLE_AIRPORT_SEND
					.getValue());
			autoAllotParam.setDesttype(DESTINSATION_TYPE.SERVICECERTER
					.getValue());
		} else if (ORDER_ADDRESS_TYPE.HOTEL.getValue().equals(
				autoAllotParam.getDesroletype())) {
			autoAllotParam.setDesroletype(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
			autoAllotParam.setDesttype(DESTINSATION_TYPE.HOTEL.getValue());
		} else {
			autoAllotParam.setDesroletype(ROLE_TYPE.ROLE_HOTEL_SEND.getValue());
			autoAllotParam.setDesttype(DESTINSATION_TYPE.RESIDENCE.getValue());
		}
		OrderRole ore = new OrderRole();
		ore.setOrderid(autoAllotParam.getOrderid());
		ore.setSrcaddrname(autoAllotParam.getSrcaddressname());
		ore.setSrcaddress(autoAllotParam.getSrcaddressid());
		ore.setSrcaddrdesc(autoAllotParam.getSrcaddress());
		ore.setSrcgps(autoAllotParam.getSrc_gps());
		ore.setRoleid(autoAllotParam.getDesroleid());
		ore.setRoletype(autoAllotParam.getDesroletype());
		ore.setIsfinish(IS_FINISH.UNFINISHED.getValue());
		ore.setIsshow(ISVALID_TYPE.INVALID.getValue());
		ore.setDesttype(autoAllotParam.getDesttype());
		ore.setDestaddrname(autoAllotParam.getDesaddressname());
		ore.setDestaddress(autoAllotParam.getDestaddressid());
		ore.setDestaddrdesc(autoAllotParam.getDestaddress());
		ore.setArrivedtime(DateUtil.getDateTimeStr(autoAllotParam.getSendtime()));
		ore.setDestgps(autoAllotParam.getDest_gps());
		dao.saveByAutoAllot("OrderRoleMapper.insert", ore);
	}

}
