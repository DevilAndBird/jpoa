package com.fh.controller.h5;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.aliyun.oss.OSSClient;
import com.fh.util.HttpUtils;
import net.coobird.thumbnailator.Thumbnails;
import oracle.net.aso.h;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.AppOrderReqData;
import com.fh.entity.customer.CusChargeInfo;
import com.fh.entity.customer.CusCompliantInfo;
import com.fh.entity.customer.CusCouponInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.customer.OrderUrgeRecord;
import com.fh.entity.h5.CusGenerateVerify;
import com.fh.entity.h5.H5CheckAddrReqBean;
import com.fh.entity.h5.H5CheckAddrResBean;
import com.fh.entity.h5.H5ContactIdBean;
import com.fh.entity.h5.H5ContactsReqBean;
import com.fh.entity.h5.H5CusChargeBean;
import com.fh.entity.h5.H5CusIdBean;
import com.fh.entity.h5.H5CusOrderCouponBean;
import com.fh.entity.h5.H5CusinfoReqBean;
import com.fh.entity.h5.H5CusinfoResBean;
import com.fh.entity.h5.H5FindCounterReqBean;
import com.fh.entity.h5.H5FindCounterResBean;
import com.fh.entity.h5.H5HistoryAddrReqBean;
import com.fh.entity.h5.H5HistoryAddrResBean;
import com.fh.entity.h5.H5OrderIdBean;
import com.fh.entity.h5.H5OrderInfo;
import com.fh.entity.h5.H5OrderInfoBean;
import com.fh.entity.h5.H5OrderInvoiceInfoReqBean;
import com.fh.entity.h5.H5OrderInvoiceInfoResBean;
import com.fh.entity.h5.H5OrderMain;
import com.fh.entity.h5.H5OrderNoBean;
import com.fh.entity.h5.H5UseCouponReqBean;
import com.fh.entity.h5.H5UseCouponResBean;
import com.fh.entity.h5.HistoryAddress;
import com.fh.entity.order.FetchcodeStore;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.entity.order.OrderRole;
import com.fh.entity.order.OrderRoleResBean;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.service.customer.ContactsService;
import com.fh.service.customer.CusChargeInfoService;
import com.fh.service.customer.CusCompliantService;
import com.fh.service.customer.CusCouponInfoService;
import com.fh.service.customer.CusInfoService;
import com.fh.service.customer.OrderUrgeRecordService;
import com.fh.service.customer.ValiCodeService;
import com.fh.service.delivery.ServiceCenterService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.orderinfo.FetchcodeStoreService;
import com.fh.service.order.orderinfo.OrderInvoiceService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zhangjj
 * 微信公众号下单所有接口
 */
@Controller
@RequestMapping(value="h5order")
public class H5OrderController extends BaseController{

	@Autowired
	private CusInfoService cusInfoService;
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
	private OrderUrgeRecordService orderUrgeRecordService;
	@Autowired
	private CusCompliantService cusCompliantService;
	@Autowired
	private CusCouponInfoService cusCouponInfoService;
	@Autowired
	private CusChargeInfoService cusChargeInfoService;
	@Autowired
 	private OrderInvoiceService orderInvoiceService;
	@Autowired
	private OrderAddressService orderAddressService;
	@Autowired
	private ValiCodeService valiCodeService;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private FetchcodeStoreService fetchcodeStoreService;
	@Autowired
	private ServiceCenterService serviceCenterService;
	@Autowired
	private CouponInfoService couponInfoService;
	private String env_Aliyun_oss_url = "http://oss-cn-hangzhou.aliyuncs.com";
	private String env_AliyunOSSKey = "LTAIg8xwvCWb7Trj";
	private String env_AliyunOSSSecret = "ui9Wtn1VtAZRzQqm0YGitOrPk6weMm";
	private String env_MyBucketName = "jingpeioss";
	private String env_imgaliyunpath = "http://jingpeioss.oss-cn-hangzhou.aliyuncs.com";
	
 	/**
	 * @desc 取消订单接口
     * @auther mutong
     * @history 2018年3月4日
     */
	@ResponseBody
	@RequestMapping(value="/orderCancle", produces = "application/json;charset=UTF-8" )
	public String orderCancle( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}
		AppOrderReqData appOrderReqData = new Gson().fromJson( reqParm.getData().toString(), AppOrderReqData.class ); 
		Integer id = appOrderReqData.getId();
		try {
			PageData ordermain = orderMainService.getById(id);
			if( null != ordermain ){
				if( ORDER_STATUS.WAITPPAY.getValue().equalsIgnoreCase(ordermain.getString("paystatus")) ||
						ORDER_STATUS.PREPAID.getValue().equalsIgnoreCase(ordermain.getString("paystatus"))){
					orderMainService.updateCancle(id);
					
					rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue());
		            rtBean.setMsg( "订单取消成功" );
		            logger.error( "订单:" + ordermain.getString("orderno") + "取消成功");
				}else{
					rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
		            rtBean.setMsg( "此订单的状态不符合取消规则" );
		            logger.error( "订单:" + ordermain.getString("orderno") + "的状态不符合取消规则");
				}
			}else{
				rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
	            rtBean.setMsg( "没有此订单的，无法取消" );
			}
		} catch (Exception e) {
			logger.error( "订单取消异常:"+e.getLocalizedMessage() );
			e.printStackTrace();
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "订单取消异常" );
		}
		
		return new Gson().toJson( rtBean );
	}

	/**
	 * @desc 上传照片
	 * @auther zhangjj
	 * @date 2018年10月26日
	 */
	@ResponseBody
	@RequestMapping(value="/uploadImg", produces = "application/json;charset=UTF-8")
	public Map<String, Object> uploadImg(@RequestParam("file") MultipartFile file) throws Exception{
		Map<String, Object> modelMap = new HashMap<String, Object>();

		OSSClient ossClient = null;

		try{
			// 创建OSSClient实例。
			ossClient = new OSSClient(env_Aliyun_oss_url, env_AliyunOSSKey, env_AliyunOSSSecret);

			BufferedImage thumbnail = Thumbnails.of(new ByteArrayInputStream(file.getBytes())).outputQuality(0.3f).asBufferedImage();

			// 上传字符串
			String imgname = System.currentTimeMillis() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

			modelMap.put("success", true);
			modelMap.put("errMsg", "照片批量上传成功");
			modelMap.put("imgurl", env_imgaliyunpath+ "/" + imgname);
		} catch (Exception e) {
			logger.error("照片批量上传失败："+e.getLocalizedMessage());
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
	
	/**
	 * @desc 删除订单（根据订单id）
     * @auther sunqp
     * @history 2018年4月10日
     */
	@ResponseBody
	@RequestMapping(value="/deleteOrder", produces = "application/json;charset=UTF-8" )
	public String deleteOrder( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5OrderIdBean reqBean = (H5OrderIdBean)gson.fromJson( data, H5OrderIdBean.class );
		if( reqBean == null )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "删除订单失败" );
			return new Gson().toJson(rtBean);
		}
		try {			
//			String status = orderMainService.getOrderStatusByOrderid( reqBean.getOrderid());
//			ExceptionUtil.checkBoolean(!ORDER_STATUS.CANCELLED.getValue().equals(status) && !ORDER_STATUS.WAITPPAY.getValue().equals(status), "订单为取消状态才能删除");
			orderMainService.updateIsvalidByid( reqBean.getOrderid());
		}catch (RulesCheckedException e) {
            logger.error("订单状态校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("订单状态校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error( "删除订单状态使用接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "删除订单状态使用接口异常" );
		}
		
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
 	
	/**
	 * 获取客户充值记录，最多100条
	 * 陈玉石
	 * 2018年3月4日16:18:03
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCusChargeList", produces = "application/json;charset=UTF-8" )
	public String getCusChargeList( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusIdBean obj = (H5CusIdBean)gson.fromJson( data, H5CusIdBean.class );
		if( obj == null )
		{
			rtBean.setCode( "1" );
			rtBean.setMsg( "获取客户充值记录失败" );
			return new Gson().toJson(rtBean);
		}
		try {
			List<CusChargeInfo> chargeList = cusChargeInfoService.queryListByCusId( obj.getCusId() );
			rtBean.setJsonData( new Gson().toJson( chargeList ) );
		} catch (Exception ex) {
			logger.error( "客户充值使用接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( "1" );
			rtBean.setMsg( "客户充值优惠券使用接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * 客户充值
	 * 陈玉石
	 * 2018年3月4日16:18:03
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cusCharge", produces = "application/json;charset=UTF-8" )
	public String cusCharge( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusChargeBean obj = (H5CusChargeBean)gson.fromJson( data, H5CusChargeBean.class );
		if( obj == null )
		{
			rtBean.setCode( "1" );
			rtBean.setMsg( "客户充值接口失败" );
			return new Gson().toJson(rtBean);
		}
		try {
			CusInfo cusInfo = cusInfoService.getById( obj.getCusId() );
			if( cusInfo == null )
			{
				rtBean.setCode( "1" );
				rtBean.setMsg( "未获取到对应的客户信息!" );
			}else
			{
				cusChargeInfoService.doCharge( cusInfo, obj );
			}
			
		} catch (Exception ex) {
			logger.error( "客户充值使用接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( "1" );
			rtBean.setMsg( "客户充值优惠券使用接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
//	/**
//	 * 更新订单的优惠券信息
//	 * 陈玉石
//	 * 2018年3月4日16:18:03
//	 * @param reqParm
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value="/updateCusOrderCoupon", produces = "application/json;charset=UTF-8" )
//	public String updateCusOrderCoupon( @RequestBody AppRequestBean reqParm ){
//		AppResponseBean rtBean = doH5Validate(reqParm);
//		String data = reqParm.getData();
//		Gson gson = new Gson();
//		H5CusOrderCouponBean obj = (H5CusOrderCouponBean)gson.fromJson( data, H5CusOrderCouponBean.class );
//		if( obj == null )
//		{
//			rtBean.setCode( "1" );
//			rtBean.setMsg( "更新客户优惠券使用接口失败" );
//			return new Gson().toJson(rtBean);
//		}
//		
//		try {
//			cusCouponInfoService.useCoupon( obj.getCouponId(), obj.getOrderId() );
//		} catch (Exception ex) {
//			logger.error("更新客户优惠券使用接口异常:" + ex.getLocalizedMessage());
//			rtBean.setCode( "1" );
//			rtBean.setMsg( "更新客户优惠券使用接口异常" );
//		}
//		String json = new Gson().toJson(rtBean);
//		
//		return json;
//	}
	
	/**
	 * 获取用户的优惠券列表
	 * 陈玉石
	 * 2018年3月4日16:18:03
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findCusCouponList", produces = "application/json;charset=UTF-8" )
	public String findCusCouponList( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5CusIdBean obj = (H5CusIdBean)new Gson().fromJson( reqParm.getData(), H5CusIdBean.class );
		if(obj == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "获取用户优惠券列表失败" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			List<CusCouponInfo> findCusCouponList = cusCouponInfoService.findCusCouponList( obj.getCusId() );
			rtBean.setJsonData(new Gson().toJson(findCusCouponList));
		
		} catch (Exception ex) {
			logger.error("获取用户优惠券列表接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "获取用户优惠券列表接口异常" );
		}
		
		String json = new Gson().toJson(rtBean);
		return json;
	}
	
	/**
	 * H5端客户投诉
	 * 陈玉石
	 * 2018年3月4日16:18:03
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/h5CusComplaint", produces = "application/json;charset=UTF-8" )
	public String h5CusComplaint( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		CusCompliantInfo obj = (CusCompliantInfo)gson.fromJson( data, CusCompliantInfo.class );
		if( obj == null )
		{
			rtBean.setCode( "1" );
			rtBean.setMsg( "客户投诉失败" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			cusCompliantService.insert( obj );
		} catch (Exception ex) {
			logger.error("客户投诉接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( "1" );
			rtBean.setMsg( "客户投诉接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * H5端催单
	 * 陈玉石
	 * 2018年3月4日16:18:10
	 */
	@ResponseBody
	@RequestMapping(value="/h5UrgeOrder", produces = "application/json;charset=UTF-8" )
	public String h5UrgeOrder( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		OrderUrgeRecord urgeRecord = (OrderUrgeRecord)gson.fromJson( data, OrderUrgeRecord.class );
		if( urgeRecord == null ) 
		{
			rtBean.setCode( "1" );
			rtBean.setMsg( "催单失败" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			orderUrgeRecordService.insert( urgeRecord );
		} catch (Exception ex) {
			logger.error("创催单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( "1" );
			rtBean.setMsg( "催单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 确认下单
	 * @auther zhangjj
	 * @date 2018年4月17日
	 */
	@ResponseBody
	@RequestMapping(value="/h5SaveOrder", produces = "application/json;charset=UTF-8" )
	public String h5SaveOrder( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5OrderMain orderMain = (H5OrderMain)new Gson().fromJson( reqParm.getData(), H5OrderMain.class );
		if( orderMain == null){
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("确认下单接口请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		
		try {
			// TODO 基础校验
			
			// 保存基本信息
			String orderno = orderMainService.saveH5Order(orderMain);
			
			H5OrderNoBean bean = new H5OrderNoBean();
			bean.setOrderno( orderno );
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("确认下单成功");
			rtBean.setJsonData( new Gson().toJson( bean ) );
		}catch (RulesCheckedException e) {
            logger.error("确认下单校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("确认下单校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("确认下单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "确认下单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * 保存客户
	 * 陈玉石
	 * 2018年2月25日16:56:31
	 */
	@ResponseBody
	@RequestMapping(value="/h5SaveCusInfo", produces = "application/json;charset=UTF-8" )
	public String h5SaveCusInfo( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		CusInfo cusInfo = (CusInfo)new Gson().fromJson( reqParm.getData(), CusInfo.class );
		if( cusInfo == null ) {
			rtBean.setCode( "1" );
			rtBean.setMsg( "保存或修改客户信息失败" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 基础校验
			ExceptionUtil.checkNotEmpty(cusInfo.getName(), "姓名为空");
			ExceptionUtil.checkNotEmpty(cusInfo.getIdno(), "身份证为空");
			ExceptionUtil.checkNotEmpty(cusInfo.getMobile(), "手机号码为空");
			ExceptionUtil.checkNotEmpty(cusInfo.getVerify(), "验证码为空");
			ExceptionUtil.checkNotEmpty(cusInfo.getOpenid(), "openid为空");
			
			cusInfoService.insertCusInfo( cusInfo );
		
		}catch (RulesCheckedException e) {
            logger.error("保存或修改客户信息校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("保存或修改客户信息校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("保存或修改客户信息接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( "1" );
			rtBean.setMsg( "保存或修改客户信息接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}

	/**
	 * @desc 查询订单列表（根据客户id）
     * @auther sunqp
     * @history 2018年4月10日
     */
	@ResponseBody
	@RequestMapping(value="/h5getOrderByCusId", produces = "application/json;charset=UTF-8" )
	public String h5getOrderByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)gson.fromJson( data, H5CusinfoReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {			
			List<H5OrderInfoBean> list = orderMainService.queryOrderListByCusId( reqBean.getCusid());
			
			//组装返回
//			H5OrderInfoResBean resBean = new H5OrderInfoResBean();
//			resBean.setH5OrderInfoBeanList(list);
			
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询订单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}	
	
	/**
	 * @desc 查询未支付订单列表（根据客户id）
     * @auther sunqp
     * @history 2018年4月18日
     */
	@ResponseBody
	@RequestMapping(value="/h5getWaitOrderByCusId", produces = "application/json;charset=UTF-8" )
	public String h5getWaitpayOrderByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)gson.fromJson( data, H5CusinfoReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {			
			List<H5OrderInfoBean> list = orderMainService.queryWaitpayOrderListByCusId( reqBean.getCusid());
			
			//组装返回
//			H5OrderInfoResBean resBean = new H5OrderInfoResBean();
//			resBean.setH5OrderInfoBeanList(list);
			
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询订单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 查询退款订单列表（根据客户id）
     * @auther sunqp tangqm
     * @history 2018年4月18日
     */
	@ResponseBody
	@RequestMapping(value="/h5getRefundOrderByCusId", produces = "application/json;charset=UTF-8" )
	public String h5getRefundOrderByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)gson.fromJson( data, H5CusinfoReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {			
			List<H5OrderInfoBean> list = orderMainService.queryRefundOrderListByCusId( reqBean.getCusid());
			
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询订单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 查询已完成订单列表（根据客户id）
	 * @auther sunqp
	 * @history 2018年4月18日
	 */
	@ResponseBody
	@RequestMapping(value="/h5getFinishOrderByCusId", produces = "application/json;charset=UTF-8" )
	public String h5getFinishOrderByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)gson.fromJson( data, H5CusinfoReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {			
			List<H5OrderInfoBean> list = orderMainService.queryFinishOrderListByCusId( reqBean.getCusid());		
			
			//组装返回
//			H5OrderInfoResBean resBean = new H5OrderInfoResBean();
//			resBean.setH5OrderInfoBeanList(list);
			
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询订单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 查询进行中订单列表（根据客户id）
	 * @auther sunqp
	 * @history 2018年4月18日
	 */
	@ResponseBody
	@RequestMapping(value="/h5getOngoingOrderByCusId", produces = "application/json;charset=UTF-8" )
	public String h5getOngoingOrderByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)gson.fromJson( data, H5CusinfoReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {			
			List<H5OrderInfoBean> list = orderMainService.queryOngoingOrderListByCusId( reqBean.getCusid());	
			
			//组装返回
//			H5OrderInfoResBean resBean = new H5OrderInfoResBean();
//			resBean.setH5OrderInfoBeanList(list);
			
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询订单接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
		
	/**
	 * @desc 查询订单角色动作（根据订单id）
     * @auther sunqp
     * @history 2018年4月10日
     */
	@ResponseBody
	@RequestMapping(value="/getH5OrderRole", produces = "application/json;charset=UTF-8" )
	public String getH5OrderRole( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5OrderIdBean reqBean = (H5OrderIdBean)gson.fromJson( data, H5OrderIdBean.class );
		if( reqBean == null ){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单状态失败" );
			return new Gson().toJson(rtBean);
		}
		try {
			List<OrderRole> orderRole = orderMainService.queryStatusByOrderId( reqBean.getOrderid(), reqBean.getOrderno());
			
			OrderRoleResBean orderRoleResBean = new OrderRoleResBean();
			orderRoleResBean.setOrderRole(orderRole);
			rtBean.setJsonData( new Gson().toJson( orderRoleResBean ) );
		} catch (Exception ex) {
			logger.error( "订单状态使用接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "订单状态使用接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}	
	
	/**
	 * @desc 客户领取优惠卷
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/getCoupon", produces = "application/json;charset=UTF-8" )
	public String getCoupon( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5CusOrderCouponBean reqBean = (H5CusOrderCouponBean)new Gson().fromJson( reqParm.getData(), H5CusOrderCouponBean.class );
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkBoolean(reqBean.getCusid() == null || reqBean.getCusid()== 0, "客户id不能为空");
			ExceptionUtil.checkBoolean(reqBean.getCouponId() == null || reqBean.getCouponId()== 0, "优惠卷id不能为空");
			
			// 转换
			CusCouponInfo cusCouponInfo = cusCouponReqBeanToEntity(reqBean);
			
			// 不添加同种类型优惠卷
			List<PageData> pdList = cusCouponInfoService.checkRepeatCoupon(cusCouponInfo);
			if(CollectionUtils.isEmpty(pdList)) {
				// 领取优惠卷
				cusCouponInfoService.getCoupon(cusCouponInfo);
			}
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("客户领取优惠卷成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("客户领取优惠卷校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("客户领取优惠卷校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("客户领取优惠卷非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "客户领取优惠卷非预期异常" );
			 return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 客户使用优惠卷
	 * @auther zhangjj
	 * @date 2018年10月23日
	 */
	@ResponseBody
	@RequestMapping(value="/useCoupon", produces = "application/json;charset=UTF-8" )
	public String useCoupon( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		
		H5UseCouponReqBean reqBean = (H5UseCouponReqBean)new Gson().fromJson( reqParm.getData(), H5UseCouponReqBean.class );
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getCouponcode(), "优惠码不能为空");
			ExceptionUtil.checkBoolean(reqBean.getTotalmoney() == null || reqBean.getTotalmoney() <= 0f, "总金额不能为空");
			
			// 优惠金额计算
			Float discountamount = couponInfoService.useCoupon(reqBean.getCouponcode(), reqBean.getTotalmoney());
			H5UseCouponResBean h5UseCouponResBean = new H5UseCouponResBean();
			h5UseCouponResBean.setActualmoney(new BigDecimal((double)discountamount).setScale(1, BigDecimal.ROUND_UP).floatValue());
			h5UseCouponResBean.setDiscountamount(new BigDecimal((double)(reqBean.getTotalmoney()- discountamount)).setScale(1, BigDecimal.ROUND_UP).floatValue());
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("客户使用优惠卷成功");
            rtBean.setJsonData(new Gson().toJson(h5UseCouponResBean));
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("客户使用优惠卷,校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg(e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("客户使用优惠卷，非预期异常:" + ex.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "客户使用优惠卷，非预期异常" );
			return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 根据openid查询客户信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/findCusinfoByOpenid", produces = "application/json;charset=UTF-8" )
	public String findCusinfoByOpenid( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5CusinfoReqBean reqBean = (H5CusinfoReqBean)new Gson().fromJson( reqParm.getData(), H5CusinfoReqBean.class );
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getOpenid(), "openid不能为空");
			// 查询客户信息
			CusInfo cusinfo = cusInfoService.findByOpenid(reqBean.getOpenid());
			// 根据openid 未查询出来
			if(cusinfo == null) {// 未查出则重新保存
				cusinfo = new CusInfo();
				cusinfo.setOpenid(reqBean.getOpenid());
				cusinfo.setChannel("weixin");
				Integer cusid = cusInfoService.saveCusinfoByOpenid(cusinfo);
			}
			
			// 转换
			H5CusinfoResBean res = cusinfoEntityToReqBean(cusinfo);
			
			rtBean.setJsonData(new Gson().toJson(res));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("根据openid查询客户信息成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("根据openid查询客户信息校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("根据openid查询客户信息校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("根据openid查询客户信息非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "根据openid查询客户信息非预期异常" );
			 return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 查询历史地址信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/findHistoryAddress", produces = "application/json;charset=UTF-8" )
	public String findHistoryAddress( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5HistoryAddrReqBean reqBean = (H5HistoryAddrReqBean)new Gson().fromJson(reqParm.getData(), H5HistoryAddrReqBean.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getProvid(), "省份id不能为空");
			ExceptionUtil.checkNotEmpty(reqBean.getCityid(), "城市id不能为空");
			ExceptionUtil.checkNotEmpty(reqBean.getOpenid(), "openid不能为空");
			ExceptionUtil.checkNotEmpty(reqBean.getAddresstype(), "地址类型不能为空");
			// 查询历史订单地址记录
			List<HistoryAddress> historyAddressList = orderAddressService.findHistoryAddress(reqBean.getProvid(), reqBean.getCityid(), reqBean.getOpenid(), reqBean.getAddresstype());
			
			// 组装返回
			H5HistoryAddrResBean resBean = new H5HistoryAddrResBean();
			resBean.setHistoryAddressList(historyAddressList);
			
			rtBean.setJsonData(new Gson().toJson(resBean));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("查询历史地址信息成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("查询历史地址信息校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("查询历史地址信息校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("查询历史地址信息非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "查询历史地址信息非预期异常" );
			 return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 校验寄件/收件 地址是否可用
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/checkAddressUsable", produces = "application/json;charset=UTF-8" )
	public String checkAddressUsable( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5CheckAddrReqBean reqBean = (H5CheckAddrReqBean)new Gson().fromJson(reqParm.getData(), H5CheckAddrReqBean.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getProvid(), "省份id不能为空");
			ExceptionUtil.checkNotEmpty(reqBean.getCityid(), "市级不能为空");
			ExceptionUtil.checkNotEmpty(reqBean.getByCheckgps(), "地址坐标不能为空");
			
			// 检查寄件/收件 地址是否在允许范围内
			boolean checkAddrIsuse = orderAddressService.checkAddrIsuse(reqBean.getProvid(), reqBean.getCityid(), reqBean.getByCheckgps(), reqBean.getIstransitgps());
			
			H5CheckAddrResBean resBean = new H5CheckAddrResBean();
			resBean.setCheckAddrIsuse(checkAddrIsuse);
			
			rtBean.setJsonData(new Gson().toJson(resBean));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("检查寄件/收件 地址是否可用成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("检查寄件/收件 地址是否可用校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("检查寄件/收件 地址是否可用校验异常：" + e.getMessage());
            H5CheckAddrResBean resBean = new H5CheckAddrResBean();
			resBean.setCheckAddrIsuse(true);
			rtBean.setJsonData(new Gson().toJson(resBean));
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("检查寄件/收件 地址是否可用非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "检查寄件/收件 地址是否可用非预期异常" );
			H5CheckAddrResBean resBean = new H5CheckAddrResBean();
			resBean.setCheckAddrIsuse(true);
			rtBean.setJsonData(new Gson().toJson(resBean));
		    return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 新增联系人
     * @auther sunqp
     * @history 2018年4月16日
     */
	@ResponseBody
	@RequestMapping(value="/addContacts", produces = "application/json;charset=UTF-8" )
	public String insertContact( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5ContactsReqBean reqBean = (H5ContactsReqBean)new Gson().fromJson(reqParm.getData(), H5ContactsReqBean.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		try {
            PageData pd = new PageData();
            pd.put("name", reqBean.getName());
            pd.put("mobile", reqBean.getMobile());
            pd.put("openid", reqBean.getOpenid());           
			contactsService.insertContact(pd);
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("新增成功");
		} catch (RulesCheckedException e) {
            logger.error("校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            rtBean.setJsonData(new Gson().toJson(true));
		} catch (Exception ex) {
			logger.error("调用addContacts:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "新增联系人接口异常" );
			rtBean.setJsonData(new Gson().toJson(true));
		}
		 return new Gson().toJson(rtBean);
	}
	
	/**
	 * @desc 查询联系人列表（根据客户id）
     * @auther sunqp
     * @history 2018年4月16日
     */
	@ResponseBody
	@RequestMapping(value="/h5getContactsByOpenId", produces = "application/json;charset=UTF-8" )
	public String h5getContactsByCusId( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5ContactsReqBean reqBean = (H5ContactsReqBean)gson.fromJson( data, H5ContactsReqBean.class );
		
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}
		
		try {		
			List<H5ContactsReqBean> list = contactsService.queryContactsListByOpenId( reqBean.getOpenid());
			rtBean.setJsonData( new Gson().toJson( list ) );
		} catch (Exception ex) {
			logger.error("查询联系人接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询联系人接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
		
		
		
	}
	
	/**
	 * @desc 删除联系人（根据联系人id）
     * @auther sunqp
     * @history 2018年4月16日
     */
	@ResponseBody
	@RequestMapping(value="/deleteContacts", produces = "application/json;charset=UTF-8" )
	public String deleteContactsById( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5ContactIdBean reqBean = (H5ContactIdBean)gson.fromJson( data, H5ContactIdBean.class );
		if( reqBean == null )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "删除联系人失败" );
			return new Gson().toJson(rtBean);
		}
		try {			
			contactsService.deleteContactById( reqBean.getContactid());
		} catch (Exception ex) {
			logger.error( "删除联系人接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "删除联系人接口异常" );
		}
		
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 修改联系人（根据联系人id）
     * @auther sunqp
     * @history 2018年4月16日
     */
	@ResponseBody
	@RequestMapping(value="/updateContact", produces = "application/json;charset=UTF-8" )
	public String updateContactsByid( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5ContactsReqBean reqBean = (H5ContactsReqBean)gson.fromJson( data, H5ContactsReqBean.class );
		if( reqBean == null )
		{
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "修改联系人失败" );
			return new Gson().toJson(rtBean);
		}
		try {		
			PageData pd = new PageData();	
			pd.put("id", reqBean.getId());
            pd.put("name", reqBean.getName());
            pd.put("mobile", reqBean.getMobile());           
            contactsService.updateContactById(pd);			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("修改成功");
			
		} catch (Exception ex) {
			logger.error( "修改联系人接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "修改联系人接口异常" );
		}
		
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	
	/**
	 * @desc 手机注册验证
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/cusGenerateVerify", produces = "application/json;charset=UTF-8" )
	public String cusGenerateVerify( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		CusGenerateVerify reqBean = (CusGenerateVerify)new Gson().fromJson(reqParm.getData(), CusGenerateVerify.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getMobile(), "手机号不能为空");
			
			// 随机生成四位验证码
			Integer num = (int)(Math.random()*9000)+1000; 
			
			PageData pd = new PageData();
			pd.put("mobile", reqBean.getMobile());
			pd.put("valicode", num);
			valiCodeService.save(pd);
			
			rtBean.setJsonData(new Gson().toJson(num));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("手机注册验证");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("手机注册验证校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("手机注册验证校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("手机注册验证非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "手机注册验证非预期异常" );
		    return new Gson().toJson(rtBean);
		}
	}
	/**
	 * @desc 语音验证码
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/h5VoiceNotification", produces = "application/json;charset=UTF-8" )
	public String h5VoiceNotification( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		CusGenerateVerify reqBean = (CusGenerateVerify)new Gson().fromJson(reqParm.getData(), CusGenerateVerify.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}

		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getMobile(), "手机号不能为空");
			// 随机生成四位验证码
			Integer num = (int)(Math.random()*9000)+1000;
			PageData pd = new PageData();
			pd.put("mobile", reqBean.getMobile());
			pd.put("valicode", num);
			valiCodeService.save(pd);
			//发送验证码
			voiceAuthenticationCode(reqBean.getMobile(),num+"");
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("语音验证码发送成功");
            return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
            logger.error("语音验证码校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("语音验证码校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("语音验证码证非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "语音验证码非预期异常" );
		    return new Gson().toJson(rtBean);
		}
	}
	
	/**
	 * @desc 手动存储提取码
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value="/batchSaveFetch", produces = "application/json;charset=UTF-8" )
	public void batchSaveFetch( @RequestBody AppRequestBean reqParm ){
		
		try {
			for (int i = 0; i < 20000; i++) {
    			// 随机六位数
    			String rom = getItemID(6);
				FetchcodeStore fetchcodeStore = new FetchcodeStore();
				fetchcodeStore.setFetchcode(rom);
				fetchcodeStoreService.saveFetchcode(fetchcodeStore);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @desc 新增发票
     * @auther sunqp
     * @history 2018年4月17日
     */
	@ResponseBody
	@RequestMapping(value="/addInvoice ", produces = "application/json;charset=UTF-8" )
	public String insertInvoice( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		H5OrderInvoiceInfoReqBean reqBean = (H5OrderInvoiceInfoReqBean)new Gson().fromJson(reqParm.getData(), H5OrderInvoiceInfoReqBean.class);
		if(reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "请求参数转换异常" );
			return new Gson().toJson(rtBean);
		}
		try {
			// 校验
			
            // 转换
			OrderInvoiceInfo orderInvoiceInfo = invoiceReqBeanToEntity(reqBean);
            
			orderInvoiceService.insertInvoice(orderInvoiceInfo);
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("新增发票成功");
		} catch (RulesCheckedException e) {
            logger.error("校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            rtBean.setJsonData(new Gson().toJson(true));
		} catch (Exception ex) {
			logger.error("调用addInvoice:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg( "新增发票接口异常" );
			rtBean.setJsonData(new Gson().toJson(true));
		}		
		String json = new Gson().toJson(rtBean);		
		return json;
	}
	
	/**
	 * @desc 查询发票信息（根据订单id）
     * @auther sunqp
     * @history 2018年4月17日
     */
	@ResponseBody
	@RequestMapping(value="/queryInvoiceByOrderid", produces = "application/json;charset=UTF-8" )
	public String queryInvoiceByOrderid( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5OrderIdBean reqBean = (H5OrderIdBean)gson.fromJson( data, H5OrderIdBean.class );	
		if( reqBean == null){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询请求失败" );
			return new Gson().toJson(rtBean);	
		}		
		try {		
			// 校验
			
			// 查询
			OrderInvoiceInfo h5OrderInvoiceInfo = orderInvoiceService.queryInvoiceByOrderId( reqBean.getOrderid());
			// 转换
			H5OrderInvoiceInfoResBean invoiceEntityToResBean = invoiceEntityToResBean(h5OrderInvoiceInfo);
			
			rtBean.setJsonData( new Gson().toJson( invoiceEntityToResBean ) );
		} catch (Exception ex) {
			logger.error("查询发票接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询发票接口异常" );
		}
		String json = new Gson().toJson(rtBean);		
		return json;
	}

	/**
	 * @desc 修改发票（根据发票id）
     * @auther sunqp
     * @history 2018年4月17日
     */
	@ResponseBody
	@RequestMapping(value="/updateInvoiceById", produces = "application/json;charset=UTF-8" )
	public String updateInvoiceById( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5OrderInvoiceInfoReqBean reqBean = (H5OrderInvoiceInfoReqBean)gson.fromJson( data, H5OrderInvoiceInfoReqBean.class );
		if( reqBean == null ){
			
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "修改发票失败" );
			return new Gson().toJson(rtBean);
		}
		try {		
			PageData pd = new PageData();
			pd.put("id", reqBean.getId());
			pd.put("sendname", reqBean.getSendname());
			pd.put("sendphone", reqBean.getSendphone());
			pd.put("sendaddr", reqBean.getSendaddr());
			pd.put("housenum", reqBean.getHousenum());
			pd.put("title", reqBean.getTitle());
			pd.put("taxno", reqBean.getTaxno());
            orderInvoiceService.updateInvoiceById(pd);			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("修改成功");			
		} catch (Exception ex) {
			logger.error( "修改发票接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "修改发票接口异常" );
		}		
		String json = new Gson().toJson(rtBean);		
		return json;
	}
	
	/**
	 * @desc 查询订单信息（根据订单id）
     * @auther sunqp
     * @history 2018年4月10日
     */
	@ResponseBody
	@RequestMapping(value="/getH5OrderInfo", produces = "application/json;charset=UTF-8" )
	public String getH5OrderInfo( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5OrderIdBean reqBean = (H5OrderIdBean)gson.fromJson( data, H5OrderIdBean.class );
		if( reqBean == null ){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询订单信息失败" );
			return new Gson().toJson(rtBean);
		}
		try {
			H5OrderInfo h5OrderInfo = orderMainService.getH5OrderInfo( reqBean.getOrderid(), reqBean.getOrderno());
			
			rtBean.setJsonData( new Gson().toJson( h5OrderInfo ) );
		} catch (Exception ex) {
			logger.error( "订单信息使用接口异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "订单信息使用接口异常" );
		}
		String json = new Gson().toJson(rtBean);
		
		return json;
	}
	
	/**
	 * @desc 查询城市下所有柜台
     * @auther sunqp
     * @history 2018年4月10日
     */
	@ResponseBody
	@RequestMapping(value="/findCountersByCity", produces = "application/json;charset=UTF-8" )
	public String findCountersByCity( @RequestBody AppRequestBean reqParm ){
		AppResponseBean rtBean = doH5Validate(reqParm);
		String data = reqParm.getData();
		Gson gson = new Gson();
		H5FindCounterReqBean reqBean = (H5FindCounterReqBean)gson.fromJson( data, H5FindCounterReqBean.class );
		if( reqBean == null ){
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询城市下所有柜台，请求参数解析异常" );
			return new Gson().toJson(rtBean);
		}
		
		try {
			ExceptionUtil.checkNotEmpty( reqBean.getProvid(), "省份id不能为空");
			ExceptionUtil.checkNotEmpty( reqBean.getCityid(), "市级id不能为空");
			
			List<PageData> counterList = serviceCenterService.findCounterAll(reqBean.getProvid(), reqBean.getCityid(), reqBean.getTransmittype());
			
			H5FindCounterResBean h5FindCounterResBean = new H5FindCounterResBean();
			h5FindCounterResBean.setCounterList(counterList);
			rtBean.setJsonData( new Gson().toJson( h5FindCounterResBean ) );
		} catch (RulesCheckedException e) {
            logger.error("查询城市下所有柜台校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("查询城市下所有柜台校验异常：" + e.getMessage());
		} catch (Exception ex) {
			logger.error( "查询城市下所有柜台系统异常:" + ex.getLocalizedMessage());
			rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue() );
			rtBean.setMsg( "查询城市下所有柜台系统异常" );
		}
		String json = new Gson().toJson(rtBean);
		return json;
	}

	/**
	 * @desc 客户领取优惠卷 reqBean转实体
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	private CusCouponInfo cusCouponReqBeanToEntity(H5CusOrderCouponBean reqBean) {
		CusCouponInfo cusCouponInfo = new CusCouponInfo();
		cusCouponInfo.setCusid(reqBean.getCusid());
		cusCouponInfo.setCouponid(reqBean.getCouponId());
		return cusCouponInfo;
	}
	
	/**
	 * @desc 客户领取优惠卷 reqBean转实体
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	private H5CusinfoResBean cusinfoEntityToReqBean(CusInfo cusInfo) throws IllegalAccessException, InvocationTargetException {
		H5CusinfoResBean resBean = new H5CusinfoResBean();
		resBean.setCusid(cusInfo.getId());
		resBean.setName(cusInfo.getName());
		resBean.setMobile(cusInfo.getMobile());
		resBean.setIdno(cusInfo.getIdno());
		return resBean;
	}
	
	/**
	 * @desc 客户领取优惠卷 reqBean转实体
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	private HistoryAddress checkAddrReqBeanToEntity(H5CheckAddrReqBean H5CheckAddrReqBean) throws IllegalAccessException, InvocationTargetException {
		HistoryAddress resBean = new HistoryAddress();
		BeanUtils.copyProperties(resBean, H5CheckAddrReqBean);
		return resBean;
	}
	
	/**
	 * @desc 税基生成6位数
	 * @auther zhangjj
	 * @date 2018年4月18日
	 */
	private static String getItemID(int num) {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < num ; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt( 10 ) );
            }
        }
        return val;
    }
	
	/**
	 * @desc 发票reqBean转实体
	 * @auther sunqp
	 * @date 2018年4月17日
	 */
	private OrderInvoiceInfo invoiceReqBeanToEntity(H5OrderInvoiceInfoReqBean h5OrderInvoiceInfoReqBean) throws IllegalAccessException, InvocationTargetException {
		OrderInvoiceInfo orderInvoiceInfo = new OrderInvoiceInfo();
		BeanUtils.copyProperties(orderInvoiceInfo, h5OrderInvoiceInfoReqBean);
		return orderInvoiceInfo;
	}
	
	/**
	 * @desc 发票实体类转resBean
	 * @auther sunqp
	 * @date 2018年4月17日
	 */
	private H5OrderInvoiceInfoResBean invoiceEntityToResBean(OrderInvoiceInfo orderInvoiceInfo) throws IllegalAccessException, InvocationTargetException {
		H5OrderInvoiceInfoResBean resBean = new H5OrderInvoiceInfoResBean();
		BeanUtils.copyProperties(resBean, orderInvoiceInfo);
		return resBean;
	}

	/**
	 * 唐启铭
	 * 语音验证码
	 * 2019年1月25日
	 */
	private void voiceAuthenticationCode(String mobile,String valicode){
		String host = "https://yyyzm.market.alicloudapi.com";
		String path = "/chuangxin/yyyzm";
		String method = "POST";
		String appcode = "7ea52ec1d6144aa58ccd2094c212773a";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("content", valicode);
		querys.put("mobile", mobile);
		Map<String, String> bodys = new HashMap<String, String>();
		try {
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
