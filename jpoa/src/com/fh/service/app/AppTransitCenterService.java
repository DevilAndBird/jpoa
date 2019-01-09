package com.fh.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ISSCAN_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.transitcenter.AppUnloadDonReqData;
import com.fh.entity.order.OrderRole;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;

@Service
public class AppTransitCenterService {

    @Autowired
    private DaoSupport dao;
    
    
    /**
     * 卸货完毕功能
     */
    @SuppressWarnings("all")
    public String cbNnloadDone(AppUnloadDonReqData appUnloadDonReqData) throws Exception{
        
        List<Integer> orderidList = appUnloadDonReqData.getOrderidList();
        
        for (Integer orderid : orderidList) {
            // 检查所有订单是否在待卸货状态
            PageData pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("status", ORDER_STATUS.WAITINGUNLOAD.getValue());
            Integer check1 = (Integer)dao.findForObject("OrderMainMapper.checkOrderStatus", pd);
            // 如果该订单不是待装车状态则异常
            ExceptionUtil.checkBoolean(check1 == null || check1 == 0, "订单id为：" + orderid + "订单状态不在待卸车状态");
            
            // 检查QR码必须是扫描过的
            pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("isscan", ISSCAN_TYPE.REMOVESCAN.getValue());
			List<PageData> check2 = (List<PageData>) dao.findForList("OrderMainMapper.checkQRIsScan", pd);
            ExceptionUtil.checkBoolean(check2 != null && check2.size() > 0, "订单id为：" + orderid + "未扫描，请扫描");
            
            // 如果所有订单是待卸货状态,更新这些订单信息已卸货
            pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("status", ORDER_STATUS.UNLOAD.getValue());
            dao.update("OrderMainMapper.updateStatusByOrderid", pd);
            
            // 更新取派员去机场送件已经角色动作节点已完成
            OrderRole orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoleid(appUnloadDonReqData.getUnloadBeforeRoleid());
            orderRole.setRoletype(ROLE_TYPE.ROLE_TRANSIT_SEND.getValue());
            orderRole.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
            orderRole.setDestaddress(appUnloadDonReqData.getDestaddress());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            dao.update("OrderRoleMapper.updateIsfinishByOrderidAndRoleType", orderRole);
            
            // 查询集散中心信息
            PageData pdReqTemp = new PageData();
            pdReqTemp.put("transitCenterid", appUnloadDonReqData.getDestaddress());
            PageData resTemp = (PageData) dao.findForObject("TransitCenterMapper.findTransitCenterById", pdReqTemp);
            // 增加抵达集散中心角色动作节点
            orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoleid(appUnloadDonReqData.getUnloadLaterRoleid());
            orderRole.setRoletype(ROLE_TYPE.ROLE_ARRIVE_TRANSIT.getValue());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            orderRole.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
            orderRole.setDestaddress(appUnloadDonReqData.getDestaddress());
            orderRole.setDestaddrname(resTemp.getString("name"));
            orderRole.setDestaddrdesc(resTemp.getString("address"));
            dao.save("OrderRoleMapper.insert", orderRole);
            
            // 解除扫描
            pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("isscan", ISSCAN_TYPE.REMOVESCAN.getValue());
            dao.save("OrderBaggageMapper.updateIsScanByOrderid", pd);
        }
        
        
        return APP_RESPONSE_CODE.SUCCESS.getValue();
    }
}
