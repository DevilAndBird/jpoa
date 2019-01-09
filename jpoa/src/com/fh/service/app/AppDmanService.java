package com.fh.service.app;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.DMAN_STATUS;
import com.fh.common.constant_enum.ISSCAN_TYPE;
import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.transitcenter.ApploadDonReqData;
import com.fh.entity.order.OrderRole;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;

@Service
public class AppDmanService {

    @Autowired
    private DaoSupport dao;
    
    
   /**
    * 集散中心取派员装车完毕 
    * @param apploadDonReqData
    * @return
    * @throws Exception
    */
    public String cbTransitLoadDone(ApploadDonReqData apploadDonReqData) throws Exception{
        
        List<Integer> orderidList = apploadDonReqData.getOrderidList();
        
        for (Integer orderid : orderidList) {
            // 查询所有订单是否在待装车状态或者是已经扫描件
            PageData pd = new PageData();
            pd.put("status", ORDER_STATUS.WAITTRUCELOADING.getValue());
            pd.put("orderid", orderid);
            Integer check1 = (Integer)dao.findForObject("OrderMainMapper.checkOrderStatus", pd);
            // 如果该订单不是待装车状态则异常
            ExceptionUtil.checkBoolean(check1 == null || check1 == 0, "订单id为：" + orderid + "订单状态不在待装车状态");
            
            // 检查QR码必须是扫描过的
            pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("isscan", ISSCAN_TYPE.REMOVESCAN.getValue());
            @SuppressWarnings("all")
			List<PageData> check2 = (List<PageData>) dao.findForList("OrderMainMapper.checkQRIsScan", pd);
            ExceptionUtil.checkBoolean(check2 != null && check2.size() > 0, "订单id为：" + orderid + "未扫描，请扫描");
            
            // 如果所有订单是待装车状态,更新这些订单信息已装车
            pd = new PageData();
            pd.put("status",  ORDER_STATUS.TRUCELOADING.getValue());
            pd.put("orderid", orderid);
            dao.update("OrderMainMapper.updateStatusByOrderid", pd);
            
            // 更新取派员角色动作节点已完成  这个更新不安全，如果经过两个集散
            OrderRole orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoletype(ROLE_TYPE.ROLE_TRANSIT_TASK.getValue());
            orderRole.setDesttype(DESTINSATION_TYPE.TRANSITCERTER.getValue());
            orderRole.setDestaddress(apploadDonReqData.getDestaddress());
            orderRole.setRoleid(apploadDonReqData.getRoleid());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            dao.update("OrderRoleMapper.updateIsfinishByOrderidAndRoleType", orderRole);
            
            //切换任务列表显示
            dao.update("OrderRoleMapper.updateIsShow", orderRole);
            
            // 解除扫描
            pd = new PageData();
            pd.put("isscan", "0");
            pd.put("orderid", orderid);
            dao.save("OrderBaggageMapper.updateIsScanByOrderid", pd);
        }
        return APP_RESPONSE_CODE.SUCCESS.getValue();
    }
    
    /**
     * 
     * @Title: findTransitArrived
     * @Description: 判断是否抵达集散中心
     * author：tangqm
     * 2018年6月6日
     * @param pd
     * @return
     * @throws Exception
     */
	@SuppressWarnings("all")
	public String findTransitArrived(PageData pd) throws Exception{
			List<OrderRole> gotoTransitList  = (List<OrderRole>) dao.findForList("OrderRoleMapper.findTransitArrived", pd);
       	  	if(CollectionUtils.isEmpty(gotoTransitList)){
       	  		return ISVALID_TYPE.INVALID.getValue();
       	  	} 
       	  	return ISVALID_TYPE.VALID.getValue();
	}
    
    /**
     * 装车完毕功能
     */
    @SuppressWarnings("all")
    public void cbServiceCenterLoadDone(ApploadDonReqData apploadDonReqData) throws Exception {
        
        
        List<Integer> orderidList = apploadDonReqData.getOrderidList();
        for (Integer orderid : orderidList) {
            // 查询所有订单是否在待装车状态
            PageData pd = new PageData();
            pd.put("status", ORDER_STATUS.WAITTRUCELOADING.getValue());
            pd.put("orderid", orderid);
            Integer checkOrderStatus = (Integer) dao.findForObject("OrderMainMapper.checkOrderStatus", pd);
            // 如果这些订单有一个不是待裝車状态则无法确认交接
            ExceptionUtil.checkBoolean(checkOrderStatus == null || checkOrderStatus == 0, "等待对方申请装车");
            
            // 如果所有订单是待卸货状态,更新这些订单信息已卸货
            pd = new PageData();
            pd.put("status", ORDER_STATUS.TRUCELOADING.getValue());
            pd.put("orderid", orderid);
            dao.update("OrderMainMapper.updateStatusByOrderid", pd);
            
            // 更新取派员角色动作节点已完成
            OrderRole orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoleid(apploadDonReqData.getRoleid());
            orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_TASK.getValue());
            orderRole.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
            orderRole.setDestaddress(apploadDonReqData.getDestaddress());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            dao.update("OrderRoleMapper.updateIsfinishByOrderidAndRoleType", orderRole);
            
            //切换任务列表显示
            dao.update("OrderRoleMapper.updateIsShow", orderRole);
            
//            // TODO 如果有两个集散中心就完了
//            dao.update("OrderRoleMapper.updateRoleAirportTSendNext_ISFINISH", orderid);
            
            // 解除限制
            PageData saveDTO = new PageData();
            saveDTO.put("userid", apploadDonReqData.getRoleid());
            saveDTO.put("status", DMAN_STATUS.NORMALDISPATCH.getValue());
            dao.update("UserDeliveryManMapper.updateUserDeliveryStatusById", saveDTO);
        }
        
    }

}
