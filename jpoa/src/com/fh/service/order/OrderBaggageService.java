package com.fh.service.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.order.OrderBaggage;
import com.fh.util.DateUtil;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * QR码+图片路径
 */
@Service
public class OrderBaggageService {
	
	@Autowired
    private DaoSupport dao;
    /**
     * 
     * @Title: updateImage
     * @Description: 覆盖图片
     * author：tangqm
     * 2018年9月26日
     * @param orderBaggageReqData
     * @throws Exception
     */
    public void updateImage(OrderBaggageReqData orderBaggageReqData) throws Exception {
    	// 检查qr码和订单id是否关联
        PageData checkQRInfoExist = this.checkQRInfoExist(orderBaggageReqData.getOrderid(), orderBaggageReqData.getBaggageid());
        ExceptionUtil.checkNotNull(checkQRInfoExist, "请先关联QR码信息");
        
        // 将图片压缩成 json形式;
        String imgurl = checkQRInfoExist.getString("imgurl");
        Map<String, Object> map = null;
        if(StringUtils.isBlank(imgurl)) {
        	map = new HashMap<String, Object>();
        } else {
        	map = JSONObject.parseObject(imgurl, Map.class);
        }
        map.put(orderBaggageReqData.getImgtype(), orderBaggageReqData.getImgurlList());

		// 更新照片的人
		map.put(orderBaggageReqData.getImgtype() + "Userid", orderBaggageReqData.getUploadUserid());
		// 更新照片的时间
		map.put(orderBaggageReqData.getImgtype() + "Modeifytime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
        
        
        // 保存数据
        OrderBaggage orderBaggage = new OrderBaggage();
        orderBaggage.setOrderid(orderBaggageReqData.getOrderid());
        orderBaggage.setBaggageid(orderBaggageReqData.getBaggageid());
        orderBaggage.setImgurl(JSONObject.toJSONString(map));
        dao.update("OrderBaggageMapper.updateImgurl", orderBaggage);
    }

	/**
	 * @desc 检查QR信息是否存在
	 * @auther zhangjj
	 * @date 2018年3月15日
	 */
	public PageData  checkQRInfoExist(Integer orderid, String baggageid) throws Exception {
		PageData pd = new PageData();
		pd.put("orderid", orderid);
		pd.put("baggageid", baggageid);
		return (PageData) dao.findForObject("OrderBaggageMapper.checkQRInfoExist", pd);
	}

	/**
	 * @desc 删除关联QR码
	 * @auther zhangjj
	 * @date 2018年3月15日
	 */
	public void deleteLinkQR(Integer orderid, String baggageid) throws Exception {
		PageData pd = new PageData();
		pd.put("orderid", orderid);
		pd.put("baggageid", baggageid);
		dao.delete("OrderBaggageMapper.deleteLinkQR", pd);
	}
	
	/**
	 * @desc 根据订单id查询行李信息
	 * @auther tqm
	 * @date 2018年7月9日
	 */
	@SuppressWarnings("all")
	public List<OrderBaggage> findByOrderid(Integer orderid) throws Exception{
		return  (List<OrderBaggage>)  dao.findForList("OrderBaggageMapper.findByOrderid", orderid);
	}

	
	/**
     * @desc json 转 map
     * @auther zhangjj
     * @date 2018年8月23日
     */
    private Map<String, Object> jsonToMap(String json){
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	Map<String, Object> parseObject = JSONObject.parseObject(json, Map.class);
    	
    	Iterator<String> iterator = parseObject.keySet().iterator();
    	while (iterator.hasNext()) {
    		String key = iterator.next();
    		String value = parseObject.get(key).toString();
    		map.put(key, JSONArray.parseArray(value, String.class));
		}
    	return map;
    }


	/**
	 * @desc 保存qr + 图片信息
	 * @auther zhangjj
	 * @date 2019年1月2日
	 */
	public void insertQRAndImgUrl(OrderBaggageReqData orderBaggageReqData) throws Exception {

		synchronized (orderBaggageReqData.getBaggageid()) {
			// 检查qr 码是否使用过
            List<OrderBaggage> orderBaggagetemp = (List<OrderBaggage>) dao.findForList("OrderBaggageMapper.findBybagid", orderBaggageReqData.getBaggageid());
            ExceptionUtil.isFalse(orderBaggagetemp !=null && orderBaggagetemp.size() > 0, "qr码已存在，重新换取：" + orderBaggageReqData.getBaggageid());

			// 将图片压缩成 json形式;
            Map<String, Object> map = map = new HashMap<String, Object>();
            map.put(orderBaggageReqData.getImgtype(), orderBaggageReqData.getImgurlList());
            // 更新照片的人
            map.put(orderBaggageReqData.getImgtype() + "Userid", orderBaggageReqData.getUploadUserid());
            // 更新照片的时间
            map.put(orderBaggageReqData.getImgtype() + "Modeifytime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm"));
            // 保存数据
            OrderBaggage orderBaggage = new OrderBaggage();
            orderBaggage.setOrderid(orderBaggageReqData.getOrderid());
            orderBaggage.setBaggageid(orderBaggageReqData.getBaggageid());
            orderBaggage.setImgurl(JSONObject.toJSONString(map));
            dao.update("OrderBaggageMapper.insertQRAndImgUrl", orderBaggage);
		}
	}



}



