package com.fh.controller.MUDTD;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.IMGURL_BUSINESS_TYPE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERY_ORERYDETAILS_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.AppOrderDetailsReqData;
import com.fh.entity.app.order.AppOrderDetailsResData;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.customer.*;
import com.fh.entity.h5.*;
import com.fh.entity.order.*;
import com.fh.service.SmsSendService;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.customer.*;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.orderinfo.FetchcodeStoreService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderInvoiceService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.HttpUtils;
import com.fh.util.PageData;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
import javafx.scene.CacheHint;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping(value="MUDTO")
public class MUController extends BaseController{
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private OrderInfoService orderInfoService;

	private String env_Aliyun_oss_url = "http://oss-cn-hangzhou.aliyuncs.com";
	private String env_AliyunOSSKey = "LTAIg8xwvCWb7Trj";
	private String env_AliyunOSSSecret = "ui9Wtn1VtAZRzQqm0YGitOrPk6weMm";
	private String env_MyBucketName = "jingpeioss";
	private String env_imgaliyunpath = "http://jingpeioss.oss-cn-hangzhou.aliyuncs.com";

	@ResponseBody
	@RequestMapping(value="/savaorder", produces = "application/json;charset=UTF-8" )
	public Map<String, Object> doortododrSavaOrder( @RequestBody String orderMain ){
		Map<String, Object> modelMap = new HashMap<String, Object>();

		try {
			// 保存基本信息
			String orderno = orderMainService.doortodoorSavaOrder(new Gson().fromJson(orderMain, H5OrderMain.class));

			modelMap.put("success", true);
			modelMap.put("errMsg", "订单保存成功");
			modelMap.put("orderno", orderno);
		} catch (Exception ex) {
			logger.error("订单保存失败："+ ex.getMessage());
			modelMap.put("success", false);
			modelMap.put("errMsg", "订单保存失败");
		}

		return modelMap;
	}

	@ResponseBody
	@RequestMapping(value="/queryOrderByCusMobile", produces = "application/json;charset=UTF-8" )
	public Map<String, Object> queryOrderListByCusMobile( @RequestBody String h5CusinfoReqBean){
		Map<String, Object> modelMap = new HashMap<String, Object>();

		try {
			List<H5OrderInfoBean> list = orderMainService.queryOrderListByCusMobile(new Gson().fromJson(h5CusinfoReqBean, H5CusinfoReqBean.class).getMobile());

			modelMap.put("success", true);
			modelMap.put("errMsg", "根据客户手机号查询客户订单信息成功");
			modelMap.put("orderlist", list);
		} catch (Exception ex) {
			logger.error("根据客户手机号查询客户订单信息失败："+ ex.getMessage());
			modelMap.put("success", false);
			modelMap.put("errMsg", "根据客户手机号查询客户订单信息失败");
		}

		return modelMap;
	}

	/**
	 * @desc 短信
	 * @auther tangqm
	 * @date 2018年6月2日 21点16分
	 */

	@ResponseBody
    @RequestMapping(value = "/smsSend_in", produces = "application/json;charset=UTF-8")
	public Map<String, Object> smsSend_in(@RequestBody Map<String, String> req) {
		logger.error("发送短信请求入参："+ req);
		// 更改订单状态
		HashMap<String, Object> res = new HashMap<String, Object>();

		String smsRespStr = null;
		try {
			Boolean result = smsSendService.smsTemplateSend1(req);
			logger.error(smsRespStr);
			res.put("success", true);
			res.put("resMag", result);
		} catch (Exception e) {
			logger.error("smsError："+e.getMessage());
			res.put("success", false);
			res.put("resMag", e.getMessage());
		}
		return res;
	}

	/**
	 * @desc 上传照片
	 * @auther zhangjj
	 * @date 2019年12月19日
	 */
	@ResponseBody
	@RequestMapping(value="/uploadImg", produces = "application/json;charset=UTF-8")
	public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();

		OSSClient ossClient = null;

		try{
			// 创建OSSClient实例。
			ossClient = new OSSClient(env_Aliyun_oss_url, env_AliyunOSSKey, env_AliyunOSSSecret);

			// 图片上传
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Thumbnails.of(file.getInputStream()).scale(1f).outputQuality(0.1f).toOutputStream(out);
			byte[] smallImage = out.toByteArray();

			// 上传字符串
			String imgname = System.currentTimeMillis() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

			ossClient.putObject(env_MyBucketName, imgname, new ByteArrayInputStream(smallImage));

			modelMap.put("success", true);
			modelMap.put("errMsg", "照片批量上传成功");
			modelMap.put("imgurl", env_imgaliyunpath+ "/" + imgname);
		} catch (Exception e) {
			logger.error("照片批量上传失败："+e.getMessage());
			modelMap.put("success", false);
			modelMap.put("errMsg", "照片批量上传失败");
		} finally {
			if(ossClient != null) {
				// 关闭OSSClient。
				ossClient.shutdown();
			}
		}

		return modelMap;
	}

	// 后台
	/**
	 *
	 * @Title: listOrderInfo
	 * @Description: 查询订单列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "listOrderInfo_dtd")
	public ModelAndView listOrderInfo(Page page) throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		page.setPd(pd);
		List<PageData> orderInfoList = orderInfoService.orderMainlistPage(page);
		if (CollectionUtils.isNotEmpty(orderInfoList)) {
			mv.addObject("orderInfoList", orderInfoList);
		}
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/order/ordermain/jsp/ordermain_list_dtd");
		return mv;
	}

	/**
	 * @desc 后台明细页面
	 * @auther zhangjj
	 * @date 2018年8月24日
	 */
	@RequestMapping({ "listOrderDetail_dtd" } )
	public ModelAndView listOrderDetail() throws Exception {
		PageData pd = this.getPageData();

		//组装参数
		AppOrderDetailsReqData reqDetails = new AppOrderDetailsReqData();
		Float.parseFloat((String)pd.get("id"));
		reqDetails.setOrderid(Integer.parseInt((String)pd.get("id")));
		List<String> queryDetailsType = new ArrayList<String>();
		// 客户信息
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERCUS.getValue());
		// 地址
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERADDRESS.getValue());
		// 行李qr码
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERBAGAGE.getValue());
		// 寄件收件
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERSENDERRECEIVER.getValue());
		// 备注
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDERNOTES.getValue());
		// 订单价格详情
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ORDER_PRICE_DETAIL.getValue());
		// 动作类型详情
		queryDetailsType.add(QUERY_ORERYDETAILS_TYPE.ACTION_DETAILS.getValue());
		reqDetails.setQueryDetailsType(queryDetailsType);
		// 查询订单详情
		AppOrderDetailsResData resDetails = orderInfoService.findAppAppOrderDetails(reqDetails);

		// mv 返回
		ModelAndView mv = new ModelAndView();
		// 订单上传图片特殊处理
		List<OrderBaggage> orderBaggageList = resDetails.getOrderBaggageList();
		if(CollectionUtils.isNotEmpty(orderBaggageList)) {
			List<Map<String, Object>> lugQRAndImginfo = new ArrayList<Map<String, Object>>();
			for (OrderBaggage luginfo : orderBaggageList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("QR", luginfo.getBaggageid());
				if(StringUtils.isNotBlank(luginfo.getImgurl())) {
					Map<String, Object> parseObject = JSONObject.parseObject(luginfo.getImgurl(), Map.class);

					Iterator<String> iterator = parseObject.keySet().iterator();
					while (iterator.hasNext()) {
						String key = iterator.next();

						if(IMGURL_BUSINESS_TYPE.COOLECT.getValue().equals(key) || IMGURL_BUSINESS_TYPE.RELEASE.getValue().equals(key)) {
							String value = parseObject.get(key).toString();
							map.put(key, JSONArray.parseArray(value, String.class));
						}
					}
				}

				lugQRAndImginfo.add(map);
			}
			mv.addObject("lugQRAndImginfo", lugQRAndImginfo);
		}

		mv.addObject("resDetails", resDetails);
		mv.addObject("pd", pd);
		mv.setViewName("jpoa/order/ordermain/jsp/ordermain_detail_dtd");
		return mv;
	}


}
