package com.fh.service.order;

import java.util.List;


import com.fh.common.constant_enum.MAILING_WAY;
import com.fh.common.constant_enum.ORDER_ADDRESS_TYPE;
import com.fh.entity.order.OrderMainSpec;
import com.fh.entity.order.OrderRole;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Point;
import com.fh.entity.h5.HistoryAddress;
import com.fh.entity.order.OrderAddress;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.MapUtil;
import com.fh.util.PageData;

/**
 * QR码+图片路径
 */
@Service
public class OrderAddressService {
	
	@Autowired
    private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;

    /**
     * @desc 根据订单id查询地址
     * @auther zhangjj
     * @date 2018年4月12日
     */
    public OrderAddress findByOrderid(Integer orderid) throws Exception {
    	return (OrderAddress) dao.findForObject("orderAddressMapper.findByOrderid", orderid);
    }
    
    /**
     * @desc 查询客户下历史寄件收件地址
     * @auther zhangjj
     * @date 2018年4月12日
     */
    @SuppressWarnings("unchecked")
    public List<HistoryAddress> findHistoryAddress(String provid, String cityid, String openid, String addresstype) throws Exception {
    	PageData pd = new PageData();
    	pd.put("provid", provid);
    	pd.put("cityid", cityid);
    	pd.put("openid", openid);
    	pd.put("addresstype", addresstype);
		return (List<HistoryAddress>) dao.findForList("orderAddressMapper.findHistoryAddr", pd);
    }
    
    /**
     * @desc 查询客户下历史寄件收件地址
     * @auther zhangjj
     * @date 2018年4月12日
     */
    @SuppressWarnings("unchecked")
    public boolean checkAddrIsuse(String provid, String cityid, String byCheckGps, boolean istransitgps) throws Exception {
    	// 获取高德坐标
    	JSONObject gaode_json = JSONObject.fromObject(byCheckGps);
    	
    	PageData orderLocation = new PageData();
    	if(istransitgps) {// 是否转换坐标
    		Point point = new Point(Double.parseDouble((String) gaode_json.get("lng")), Double.parseDouble((String) gaode_json.get("lat")));
        	// 高德转换百度
        	Point pointtemp = MapUtil.GD_TRANS_BD(point);
        	
        	orderLocation.put("lng", pointtemp.getLng() + "");
        	orderLocation.put("lat", pointtemp.getLat() + "");
    	} else {
    		// 组装参数坐标点
        	orderLocation.put("lng", gaode_json.get("lng"));
        	orderLocation.put("lat", gaode_json.get("lat"));
    	}
    	
    	
    	// 获取开通区域
    	PageData pd = new PageData();
    	pd.put("loginperson_provid", provid);
    	pd.put("loginperson_cityid", cityid);
    	List<PageData> gpslist = (List<PageData>) dao.findForList("RegionDeliveryManMapper.findAllRegionDman", pd);
    	// 未获取不做校验
    	if(CollectionUtils.isEmpty(gpslist)) {
    		return false;
    	}
    	for(PageData temp : gpslist) {
    		JSONArray jsonArray = JSONArray.fromObject(temp.get("gps"));
    		List<PageData> partitionLocation = (List<PageData>) JSONArray.toCollection(jsonArray, PageData.class);
    		boolean res = baiDuMapBO.isInPolygon(orderLocation, partitionLocation);
    		if(res) {
    			return res;
    		}
    	}
		
        return false;
    }
    
    /**
     * @desc 更新地址信息
     * @auther zhangjj
     * @date 2018年11月19日
     */
    public void updateOrderAddr(OrderAddress orderAddress) throws Exception {
        dao.update("orderAddressMapper.updatedAddr", orderAddress);

		// 修改寄件方式 or 收件方式
		OrderMainSpec orderMainSpec = new OrderMainSpec();
		orderMainSpec.setId(orderAddress.getOrderid());
		orderMainSpec.setMailingway(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(orderAddress.getSrcaddrtype()) ? MAILING_WAY.AIRPOSTCOUNTER.getValue() : ORDER_ADDRESS_TYPE.HOTEL.getValue().equals(orderAddress.getSrcaddrtype())? MAILING_WAY.FRONTDESK.getValue() : "");
		orderMainSpec.setBackway(ORDER_ADDRESS_TYPE.AIRPORTCOUNTER.getValue().equals(orderAddress.getDestaddrtype()) ? MAILING_WAY.AIRPOSTCOUNTER.getValue() : ORDER_ADDRESS_TYPE.HOTEL.getValue().equals(orderAddress.getDestaddrtype())? MAILING_WAY.FRONTDESK.getValue() : "");
		dao.update("OrderMainMapper.updatMailingOrbackway", orderMainSpec);

		// 更改动作信息寄送收件地址
		OrderRole orderRole = new OrderRole();
		orderRole.setOrderid(orderAddress.getOrderid());
		if(StringUtils.isNotBlank(orderAddress.getSrcaddrtype())) {
            orderRole.setDesttype(orderAddress.getSrcaddrtype());
            orderRole.setDestaddress(orderAddress.getSrcaddressid()+"");
            orderRole.setDestaddrname(orderAddress.getScrlandmark());
            orderRole.setDestaddrdesc(orderAddress.getSrcaddress());
            orderRole.setDestgps(orderAddress.getSrcgps());
			dao.update("OrderRoleMapper.updateAddrByTask", orderRole);
		} else if(StringUtils.isNotBlank(orderAddress.getDestaddrtype())) {

            orderRole.setDesttype(orderAddress.getDestaddrtype());
            orderRole.setDestaddress(orderAddress.getDestaddressid() + "");
            orderRole.setDestaddrname(orderAddress.getDestlandmark());
            orderRole.setDestaddrdesc(orderAddress.getDestaddress());
            orderRole.setDestgps(orderAddress.getDestgps());
			dao.update("OrderRoleMapper.updateAddrBySend", orderRole);
		}
    }

}
