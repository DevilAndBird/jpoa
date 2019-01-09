package com.fh.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.IS_FINISH;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.counterservice.AppAffirmConnectReqData;
import com.fh.entity.order.OrderAddress;
import com.fh.entity.order.OrderRole;
import com.fh.service.SmsSendService;
import com.fh.util.DateUtil;
import com.fh.util.ExceptionUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.PageData;

@Service
public class AppAffirmConnectService {

    @Autowired
    private DaoSupport dao;
    @Autowired
    private SmsSendService smsSendService;
    
    public void cbAffirmConnect(AppAffirmConnectReqData reqData) throws Exception{
        
        List<Integer> orderidList = reqData.getOrderidList();
        
        for (Integer orderid : orderidList) {
            // 查询所有订单是否在待卸货状态
            PageData pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("status",  ORDER_STATUS.WAITINGUNLOAD.getValue());
            Integer checkOrderStatus = (Integer) dao.findForObject("OrderMainMapper.checkOrderStatus", pd);
            // 如果这些订单有一个不是待卸货状态则无法确认交接
            ExceptionUtil.checkBoolean(checkOrderStatus == null || checkOrderStatus == 0, "等待对方申请卸车");
          
            
            // 如果所有订单是待卸货状态,更新这些订单信息已卸货
            pd = new PageData();
            pd.put("orderid", orderid);
            pd.put("status", ORDER_STATUS.UNLOAD.getValue());
            dao.update("OrderMainMapper.updateStatusByOrderid", pd);
            
            // 取派员角色动作完成更新
            OrderRole orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoleid(reqData.getUnloadBeforeRoleid());
            orderRole.setDestaddress(reqData.getDestaddress());
            orderRole.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
            orderRole.setRoletype(ROLE_TYPE.ROLE_AIRPORT_SEND.getValue());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            dao.update("OrderRoleMapper.updateIsfinish", orderRole);
            
            // 查询柜台服务中心信息
            pd = new PageData();
            pd.put("id", reqData.getDestaddress());
            PageData res = (PageData) dao.findForObject("CounterServiceCenterMapper.findByid", pd);
            ExceptionUtil.checkNotNull(res, "根据柜台服务中心id+" + reqData.getDestaddress() + "未能查询到柜台信息请检查");
            // 机场角色节点添加
            orderRole = new OrderRole();
            orderRole.setOrderid(orderid);
            orderRole.setRoleid(reqData.getUnloadLaterRoleid());
            orderRole.setRoletype(ROLE_TYPE.ROLE_ARRIVE_AIRPORT.getValue());
            orderRole.setIsfinish(IS_FINISH.FINISHED.getValue());
            orderRole.setDesttype(DESTINSATION_TYPE.SERVICECERTER.getValue());
            orderRole.setDestaddress(reqData.getDestaddress());
            orderRole.setDestaddrname(res.getString("servicecentername"));
            orderRole.setDestaddrdesc(res.getString("address"));
            dao.save("OrderRoleMapper.insert", orderRole);
           
          
            try {
            	 // 行李已经到机场，发送短信通知柜台人员
            	 PageData ordermain =   (PageData) dao.findForObject("OrderMainMapper.findOpenidByOrderid", orderid);  
                 ordermain.put("actionfinishtime", DateUtil.getTime());
                 ordermain.put("servicecentername", res.get("servicecentername"));
                 WeixinUtil.arrivedesc(ordermain);
            	// 短信通知
                OrderAddress orderAddress = (OrderAddress) dao.findForObject("orderAddressMapper.findByOrderid", orderid);
    			smsSendService.smsTemplateSend("{'mobile':'"+ (String) ordermain.getString("mobile") +"','header': '"+ MsgOfTmpCode.SMS_HEADER +"', 'destlandmark':'"+ orderAddress.getDestlandmark() +"','smscode':'X021'}");
            } catch(Exception e) {
            	LoggerUtil.error("消息发送失败：消息编码：X021", e);
            }
            
        }
        
    }
}
