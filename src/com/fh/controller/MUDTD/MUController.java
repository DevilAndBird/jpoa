package com.fh.controller.MUDTD;

import com.aliyun.oss.OSSClient;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.customer.*;
import com.fh.entity.h5.*;
import com.fh.entity.order.FetchcodeStore;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.entity.order.OrderRole;
import com.fh.entity.order.OrderRoleResBean;
import com.fh.service.SmsSendService;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.customer.*;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.orderinfo.FetchcodeStoreService;
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
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(value="MUDTO")
public class MUController extends BaseController{
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private SmsSendService smsSendService;

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


}
