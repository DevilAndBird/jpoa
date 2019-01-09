package com.fh.service.order.overtimeorder;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.order.OrderOverTime;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：oa
 * 
 * 类描述：超时订单列表 类名称：com.fh.service.order.overtimeorderservice.OverTimeOrderService
 * 创建人：tangqm 修改备注：
 * 
 * @version V1.0
 */
@Service
public class OverTimeOrderService {

	@Autowired
	private DaoSupport overtimeorderdao;

	/**
	 * 
	 * @Title: overTimeOrderList
	 * @Description: 查询超时订单
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> overTimeOrderList(Page page) throws Exception {
		return (List<PageData>) overtimeorderdao.findForList(
				"OrderOverTimeMapper.orderOverTimelistPage", page);
	}

	/**
	 * 
	 * @Title: overTimeDetailList
	 * @Description: 超时明细
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> overTimeDetailList(Page page) throws Exception {
		return (List<PageData>) overtimeorderdao.findForList(
				"OrderOverTimeMapper.overTimeDetaillistPage", page);
	}

	/**
	 * 
	 * @Title: saveOverTimeOrder
	 * @Description: 新增超时订单
	 * @param pd
	 * @throws Exception
	 */
	public void saveOverTimeOrder(PageData pd) throws Exception {
		OrderOverTime orderOverTime = new OrderOverTime();
		orderOverTime.setOrderno(pd.getString("orderno"));
		String arrivetime = pd.getString("arrivetime");
		if(StringUtils.isNotBlank(arrivetime)){			
			orderOverTime.setArrivetime(DateUtil.fomatDate(arrivetime, "yyyy-MM-dd HH:mm"));
		}
		overtimeorderdao.save("OrderOverTimeMapper.insert", orderOverTime);
	}
	
}
