package com.fh.service.order.orderinfo;

import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.*;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Point;
import com.fh.entity.app.dm.AppOrderMain;
import com.fh.entity.app.dm.AppOrders;
import com.fh.entity.app.order.AppSaveOrderInfoReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanResData;
import com.fh.entity.app.transitcenter.TransitOrderReqData;
import com.fh.entity.app.transitcenter.TransitOrderResData;
import com.fh.entity.customer.CusCouponInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.delivery.TaskMainDriver;
import com.fh.entity.delivery.UserDeliveryMan;
import com.fh.entity.h5.H5OrderInfo;
import com.fh.entity.h5.H5OrderInfoBean;
import com.fh.entity.h5.H5OrderMain;
import com.fh.entity.order.*;
import com.fh.service.SmsSendService;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.base.BaseService;
import com.fh.service.customer.CusCouponInfoService;
import com.fh.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 对订单主表的查询操作
 * 2018年3月3日17:02:16
 * @author chenyushi
 *
 */
@Service
public class OrderPayInfoService
{
	@Autowired
    private DaoSupport dao;

	/**
	 * 修改支付状态
	 * @param orderid
	 * @throws Exception
	 */
	public void updateIsvalidByid( Integer orderid,String stutas) throws Exception {
		PageData pd = new PageData();
		pd.put( "orderid", orderid );
		pd.put( "status", stutas);
		dao.update("OrderPayInfoMapper.updateOrderPayInfo", pd);
	}
	
}
