package com.fh.service.order.orderinfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.NEADINVOICE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.customer.CusInvoiceInfo;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.util.PageData;

@Service
public class OrderInvoiceService {

	@Autowired
	private DaoSupport dao;

	/**
     * @DESC 新增发票
     * @author sunqp
     * @history 2018年04月17日
     */
	public void insertInvoice(OrderInvoiceInfo orderInvoiceInfo)throws Exception{
		// 插入
		orderInvoiceInfo.setStatus(INVICE_STATUS.APPLYED.getValue());
		dao.save("OrderInvoiceInfoMapper.addInvoice", orderInvoiceInfo);
		
		// 更新订单是否开发票
		PageData pd = new PageData();
		pd.put( "orderid", orderInvoiceInfo.getOrderid());		
		pd.put( "neadinvoice", NEADINVOICE_TYPE.NEEDED.getValue());
		dao.update("OrderMainMapper.updateInvoiceNeadinvoice", pd);		
		
	}
	
	/**
     * @DESC 查询发票信息
     * @author sunqp
     * @history 2018年04月17日
     */
	public OrderInvoiceInfo queryInvoiceByOrderId( Integer orderid ) throws Exception {
        return (OrderInvoiceInfo) dao.findForObject("OrderInvoiceInfoMapper.queryInvoiceByOrderId", orderid);
    }
	
	/**
     * @DESC 修改发票
     * @author sunqp
     * @history 2018年04月17日
     */
	public void updateInvoiceById( PageData pd ) throws Exception {		
        dao.update("OrderInvoiceInfoMapper.updateInvoiceEmailById", pd);
    }
	
	
	/**
	 * @desc 发票列表查询
	 * @auther zhangjj
	 * @date 2018年7月21日
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> orderInvoicelistPage( Page page ) throws Exception{
		return (List<PageData>)dao.findForList("OrderInvoiceInfoMapper.orderInvoicelistPage", page);
	}
	
	/**
	 * @desc 发票列表查询
	 * @auther zhangjj
	 * @date 2018年7月21日
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryOrderInvoiceList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("OrderInvoiceInfoMapper.queryOrderInvoiceList", pd);
	}
	
	/**
	 * 插入发票
	 * 木桐
	 * 2018年2月2日18:28:33
	 * @throws Exception 
	 */
	public void insertInvoice( PageData pd ) throws Exception{
		pd.put("status", 1);
		dao.save("OrderInvoiceInfoMapper.insertInvoice", pd);
	}
	
	/**
	 * 审核发票是否存在
	 * 木桐
	 * 2018年2月2日18:17:20
	 * @throws Exception 
	 */
	public CusInvoiceInfo checkInfo( PageData pd ) throws Exception{
		return (CusInvoiceInfo)dao.findForObject("OrderInvoiceInfoMapper.checkInfo", pd);
	}
	
	
}
