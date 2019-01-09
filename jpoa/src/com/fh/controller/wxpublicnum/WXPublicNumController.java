package com.fh.controller.wxpublicnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.common.constant_enum.ORDER_PAY_STUTAS;
import com.fh.service.order.orderinfo.OrderPayInfoService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant.MsgOfTmpCode;
import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.controller.wxpublicnum.wxpushinfo.AccessToken;
import com.fh.controller.wxpublicnum.wxpushinfo.WeixinUtil;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.h5.H5QRinfoReqBean;
import com.fh.entity.h5.wxpublicnum.SNSUserInfo;
import com.fh.entity.h5.wxpublicnum.WXInfoReqBean;
import com.fh.entity.h5.wxpublicnum.WXInfoResBean;
import com.fh.entity.h5.wxpublicnum.WXInvoiceAccreditReqBean;
import com.fh.entity.h5.wxpublicnum.WXInvoiceAccreditResBean;
import com.fh.entity.h5.wxpublicnum.WXPayReqBean;
import com.fh.entity.h5.wxpublicnum.WXPublicNumReqBean;
import com.fh.entity.h5.wxpublicnum.WXPublicNumResBean;
import com.fh.entity.h5.wxpublicnum.WXRefundReqBean;
import com.fh.entity.wxpublicnum.event.WxPayResData;
import com.fh.entity.wxpublicnum.event.WxRefundResData;
import com.fh.rule.Exception.RuleException;
import com.fh.service.SmsSendService;
import com.fh.service.autoallot.AutoAllotService;
import com.fh.service.customer.CusCouponInfoService;
import com.fh.service.customer.CusInfoService;
import com.fh.service.order.OrderRoleService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.service.wxpublicnum.WXPublicNumService;
import com.fh.util.ExceptionUtil;
import com.fh.util.HttpsClientUtil;
import com.fh.util.LoggerUtil;
import com.fh.util.MapObjUtil;
import com.fh.util.PageData;
import com.fh.util.PropertiesUtils;
import com.fh.util.PropertyConfigurer;
import com.fh.util.RedisUtil;
import com.fh.util.RulesCheckedException;
import com.fh.util.Sign;
import com.fh.util.Tools;
import com.fh.util.WXInfoUtil;
import com.fh.util.WeChatUtil;
import com.fh.util.wxpay.WXPayUtil;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author zhangjj
 * 微信公众号微信接口调用
 */
@Controller
@RequestMapping(value = "/wxpublicnum")
public class WXPublicNumController extends BaseController {

	@Autowired
	private CusInfoService cusInfoService;
	@Autowired
	private OrderMainService orderMainService;
    @Autowired
    private AutoAllotService autoAllotService;
    @Autowired
    private OrderRoleService rderRoleService;
    @Autowired
    private WXPublicNumService wxPublicNumService;
    @Autowired
    private SmsSendService smsSendService;
    @Autowired
    private PropertyConfigurer propertyConfigurer;
    @Autowired
    private CusCouponInfoService couponInfoService;
    @Autowired
    private OrderPayInfoService orderPayInfoService;
    
	/**
	 * @desc 公众账号用户授权返回用户信息
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/userAccredit", produces = "application/json;charset=UTF-8")
	public String checkAddressUsable(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate(reqParm);
		WXPublicNumReqBean reqBean = (WXPublicNumReqBean) new Gson().fromJson(
				reqParm.getData(), WXPublicNumReqBean.class);
		if (reqBean == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}

		try {
			// 校验
			ExceptionUtil.checkNotEmpty(reqBean.getCode(), "code不能为空");

			// 返回用户信息
			SNSUserInfo sNSUserInfo = getOauth2AccessToken(reqBean.getCode());

			// 返回组装
			WXPublicNumResBean resBean = new WXPublicNumResBean();
			resBean.setsNSUserInfo(sNSUserInfo);

			rtBean.setJsonData(new Gson().toJson(resBean));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("返回微信客户信息成功");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("返回微信客户信息校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("返回微信客户信息校验异常：" + e.getMessage());
			return new Gson().toJson(rtBean);
		} catch (Exception ex) {
			logger.error("返回微信客户信息非预期异常:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("返回微信客户信息非预期异常");
			return new Gson().toJson(rtBean);
		}
	}


	/**
	 * @desc 公众账号的唯一标识
	 * @auther zhangjj
	 * @date 2018年4月20日
	 */
	@SuppressWarnings("all")
	public SNSUserInfo getOauth2AccessToken(String code) throws Exception {
		// 通过code换取网页授权access_token
		String requestUrl1 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl1 = requestUrl1.replace("APPID", WeChatConst.WX_APPID);
		requestUrl1 = requestUrl1.replace("SECRET",WeChatConst.WX_APP_SECRET);
		requestUrl1 = requestUrl1.replace("CODE", code);
		JSONObject jsonObject1 = HttpsClientUtil.httpsRequest(requestUrl1,
				"GET", null);

		// 校验
		ExceptionUtil.checkNotNull(jsonObject1, "获取网页授权凭证出错");

		// 拉取用户信息(需scope为 snsapi_userinfo)
		String requestUrl2 = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl2 = requestUrl2.replace("ACCESS_TOKEN",
				jsonObject1.getString("access_token"));
		requestUrl2 = requestUrl2.replace("OPENID",
				jsonObject1.getString("openid"));
		JSONObject jsonObject2 = HttpsClientUtil.httpsRequest(requestUrl2,
				"GET", null);

		// 获取用户
		SNSUserInfo snsUserInfo = null;

		if (jsonObject2 == null) {
			return snsUserInfo;
		}
		snsUserInfo = new SNSUserInfo();
		String openid = jsonObject2.getString("openid");
		CusInfo cusinfo = cusInfoService.findByOpenid(openid);
		// 用户的标识
		snsUserInfo.setOpenId(openid);
		// 昵称
		snsUserInfo.setNickname(jsonObject2.getString("nickname"));
		// 性别（1是男性，2是女性，0是未知）
		snsUserInfo.setSex(jsonObject2.getInt("sex"));
		// 用户所在国家
		snsUserInfo.setCountry(jsonObject2.getString("country"));
		// 用户所在省份
		snsUserInfo.setProvince(jsonObject2.getString("province"));
		// 用户所在城市
		snsUserInfo.setCity(jsonObject2.getString("city"));
		// 用户头像
		snsUserInfo.setHeadImgUrl(jsonObject2.getString("headimgurl"));
		// 用户特权信息
		snsUserInfo.setPrivilegeList(JSONArray.toList(
				jsonObject2.getJSONArray("privilege"), List.class));
		Integer cusid = null;
		String name = "";
		String mobile = "";
		String idno = "";
		if (cusinfo != null) {
			cusid = cusinfo.getId();
			name = cusinfo.getName();
			mobile = cusinfo.getMobile();
			idno = cusinfo.getIdno();
		} else {
			CusInfo saveCusinfo = new CusInfo();
			saveCusinfo.setOpenid(openid);
			saveCusinfo.setChannel("weixin");
			cusid = cusInfoService.saveCusinfoByOpenid(saveCusinfo);
		}
		snsUserInfo.setCusid(cusid);
		// 用户姓名
		snsUserInfo.setName(name);
		// 手机号码
		snsUserInfo.setMobile(mobile);
		// 身份证
		snsUserInfo.setIdno(idno);

		return snsUserInfo;
	}

	/**
	 * 点击确认付费 统一下单,获得预付id(prepay_id)
	 * tangqm
	 */
	@ResponseBody
	@RequestMapping(value = "/wxPay", produces = "application/json;charset=UTF-8")
	public String prePay(HttpServletRequest request,HttpServletResponse response,@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}
		WXPayReqBean reqBean = (WXPayReqBean) new Gson().fromJson(
				reqParm.getData(), WXPayReqBean.class);
		WxPayResData result = new WxPayResData();
		try {
			String out_trade_no = reqBean.getOrderno();//实际业务单号
			// 产品价格,单位：分
			Integer total_fee = reqBean.getTotalFee();
			// 客户端ip
//			String ip = reqBean.getClientIp();
			String ip = Tools.getIpAddress(request);
			// 支付成功后回调的url地址
			String notify_url = propertyConfigurer.getProperty("notify_url");
			// 统一下单
			String strResult = WeChatUtil.unifiedorder("包给我行李托运", out_trade_no,
					total_fee, ip, notify_url, reqBean.getOpenId());
			// 解析xml
			XStream stream = new XStream(new DomDriver());
			stream.alias("xml", WxPayResData.class);
			WxPayResData wxReturnData = (WxPayResData) stream
					.fromXML(strResult);
			// 两者都为SUCCESS才能获取prepay_id
			if ("SUCCESS".equals(wxReturnData.getResult_code())
					&& "SUCCESS".equals(wxReturnData.getReturn_code())) {
				// 业务逻辑，写入支付日志 TODO
				String timeStamp = WeChatUtil.getTimeStamp();// 时间戳
				String nonce_str = WeChatUtil.getNonceStr();// 随机字符串
				// 注：上面这两个参数，一定要拿出来作为后续的value，不能每步都创建新的时间戳跟随机字符串，不然H5调支付API，会报签名参数错误
				result.setResult_code(wxReturnData.getResult_code());
				result.setAppid(WeChatConst.WX_APPID);
				result.setTimeStamp(timeStamp);
				result.setNonce_str(nonce_str);
				result.setPackageStr("prepay_id=" + wxReturnData.getPrepay_id());
				result.setSignType("MD5");

				// 下单操作中，也有签名操作，那个只针对统一下单，要区别于下面的paySign
				// 第二次签名,将微信返回的数据再进行签名
				SortedMap<Object, Object> signMap = new TreeMap<Object, Object>();
				signMap.put("appId", WeChatConst.WX_APPID);
				signMap.put("timeStamp", timeStamp);
				signMap.put("nonceStr", nonce_str);
				signMap.put("package",
						"prepay_id=" + wxReturnData.getPrepay_id());
				signMap.put("signType", "MD5");
				String paySign = WeChatUtil
						.createSign(signMap, WeChatConst.KEY);// 支付签名

				result.setSign(paySign);
			} else {
				rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
				rtBean.setMsg(" 微信支付失败 ");
				result.setResult_code("fail");
			}
		} catch (Exception ex) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg(" 微信支付非预期异常 ");
			logger.error("返回微信支付非预期异常:" + ex.getLocalizedMessage());
		}
		rtBean.setJsonData(new Gson().toJson( result ));
		return new Gson().toJson( rtBean );

	}

	/**
	 * 微信退款
	 * @auther sqp
	 */
	@ResponseBody
	@RequestMapping(value = "/refund", produces = "application/json;charset=UTF-8")
	public String refund(HttpServletRequest request,HttpServletResponse response,@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}

		WXRefundReqBean reqBean = (WXRefundReqBean) new Gson().fromJson(
				reqParm.getData(), WXRefundReqBean.class);

		WxRefundResData result = new WxRefundResData();
		String out_trade_no = reqBean.getOut_trade_no();//订单号


		try {
			boolean checkRefund = rderRoleService.checkRefund(out_trade_no);
			if(checkRefund){
				rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
				rtBean.setMsg("取派员已火速为您服务，请联系客服取消此单");
				return new Gson().toJson( rtBean );
			}
			Integer id = reqBean.getId();
			WxRefundResData wxRefundResData = wxPublicNumService.refund(out_trade_no, reqBean.getRefund_fee(), reqBean.getTotal_fee());
			if ( wxRefundResData!=null  ||("SUCCESS".equals(wxRefundResData.getResult_code()) && "SUCCESS".equals(wxRefundResData.getReturn_code()))) {
				orderMainService.updateCancle(id);
				rtBean.setMsg("微信退款成功");
				try {
					PageData ordermain = orderMainService.findOpenidByOrderid(id);
					String status = orderMainService.getOrderStatusByOrderid(id);
					if(ORDER_PAY_STUTAS.PREPAID.getValue().equals(status)){
						smsSendService.smsTemplateSend("{'orderno':'"+ (String) ordermain.getString("orderno") +"','mobile':'"+ (String) ordermain.getString("mobile") +"','header': '"+ MsgOfTmpCode.SMS_HEADER +"', 'smscode':'X022','refund_fee':'"+ reqBean.getRefund_fee() +"'}");
					}
				} catch(Exception e) {
					LoggerUtil.warn("消息发送失败哦，消息编码：X022", e);
				}


				// 撤回使用优惠卷
				couponInfoService.recallUseCoupon(out_trade_no);
			}else{
				rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
				rtBean.setMsg("微信退款失败 ");
			}

		} catch (Exception ex) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("微信退款非预期异常 ");
			logger.error("微信退款非预期异常:" + ex.getLocalizedMessage());
		}
		rtBean.setJsonData(new Gson().toJson( result ));
		return new Gson().toJson( rtBean );
	}

	/**
	 *
	 * @Title: getQRInfo
	 * @Description: 得到扫描认证信息
	 * author：tangqm
	 * 2018年10月23日
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getQRInfo", produces = "application/json;charset=UTF-8")
	public String getQRInfo(HttpServletRequest request,HttpServletResponse response,@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}
		H5QRinfoReqBean qrInfo = (H5QRinfoReqBean) new Gson().fromJson(
				reqParm.getData(), H5QRinfoReqBean.class);
		Map<String, String> sign = null;
		try {
			 sign = Sign.sign(qrInfo.getUrl());
		} catch (Exception ex) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg(" 得到扫描认证信息非预期异常 ");
			logger.error("返回得到扫描认证信息非预期异常:" + ex.getLocalizedMessage());
		}
		rtBean.setJsonData(new Gson().toJson( sign ));
		return new Gson().toJson( rtBean );

	}


	/**
	 * 支付回调接口
	 * tangqm
	 * 2018年12月26日
	 */
	@ResponseBody
	@RequestMapping(value = "/callback", produces = "application/json;charset=UTF-8")
	public void callBack(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/xml;charset=UTF-8");
		try {
			InputStream is = request.getInputStream();
			String result = IOUtils.toString(is, "UTF-8");
			if ("".equals(result)) {
				response.getWriter()
						.write("<xm><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数错误！]]></return_msg></xml>");
				return;
			}
			// 使用微信sdk解析xml 需使用最新的sdk(修复微信支付回调函数存在的bug)
			Map<String, String> xmlToMap = WXPayUtil.xmlToMap(result);
			WxPayResData wxPaySendData = (WxPayResData) MapObjUtil.mapToObject(xmlToMap,WxPayResData.class);
			// 解析xml
//			XStream stream = new XStream(new DomDriver());
//			stream.alias("xml", WxPayResData.class);
//			WxPayResData wxPaySendData = (WxPayResData) stream
//					.fromXML(result);
			logger.error("支付回调接口:" + wxPaySendData.toString());
            //此为微信回调传给我们的参数
			String appid = wxPaySendData.getAppid();
			String mch_id = wxPaySendData.getMch_id();
			String nonce_str = wxPaySendData.getNonce_str();
			String out_trade_no = wxPaySendData.getOut_trade_no();
			String total_fee = wxPaySendData.getTotal_fee();
			// double money =
			// DBUtil.getDBDouble(DBUtil.getDBInt(wxPaySendData.getTotal_fee())/100.0);
			String trade_type = wxPaySendData.getTrade_type();
			String openid = wxPaySendData.getOpenid();
			String return_code = wxPaySendData.getReturn_code();
			String result_code = wxPaySendData.getResult_code();
			String bank_type = wxPaySendData.getBank_type();
			Integer cash_fee = wxPaySendData.getCash_fee();
			String fee_type = wxPaySendData.getFee_type();
			String is_subscribe = wxPaySendData.getIs_subscribe();
			String time_end = wxPaySendData.getTime_end();
			String transaction_id = wxPaySendData.getTransaction_id();
			String sign = wxPaySendData.getSign();

			// 签名验证
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			/*parameters.put("receipt", "Y");// 支付成功后返回消息显示发票按钮使用 */
			parameters.put("nonce_str", nonce_str);
			parameters.put("out_trade_no", out_trade_no);
			parameters.put("total_fee", total_fee);
			parameters.put("trade_type", trade_type);
			parameters.put("openid", openid);
			parameters.put("return_code", return_code);
			parameters.put("result_code", result_code);
			parameters.put("bank_type", bank_type);
			parameters.put("cash_fee", cash_fee);
			parameters.put("fee_type", fee_type);
			parameters.put("is_subscribe", is_subscribe);
			parameters.put("time_end", time_end);
			parameters.put("transaction_id", transaction_id);
			// 以下4个参数针对优惠券(鼓励金之类的)
			parameters.put("coupon_count", wxPaySendData.getCoupon_count());
			parameters.put("coupon_fee", wxPaySendData.getCoupon_fee());
			parameters.put("coupon_id_0", wxPaySendData.getCoupon_id_0());
			parameters.put("coupon_fee_0", wxPaySendData.getCoupon_fee_0());

			String sign2 = WeChatUtil.createSign(parameters, WeChatConst.KEY);

			if (sign.equals(sign2)) {// 校验签名，两者需要一致
				if ("SUCCESS".equals(return_code)
						&& "SUCCESS".equals(result_code)) {
					logger.info(" callBack,订单编号:" + out_trade_no);
					PageData pd = new PageData();
					pd.put("orderno", out_trade_no);
					PageData orderIdByOrderNo = autoAllotService.getOrderIdByOrderNo(pd);
					orderIdByOrderNo.put("openid", openid);
					WeixinUtil.orderGenerate(orderIdByOrderNo);
					//修改订单支付状态
					orderPayInfoService.updateIsvalidByid((Integer)orderIdByOrderNo.get("orderid"), ORDER_PAY_STUTAS.PREPAID.getValue());
					// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
					response.getWriter()
							.write("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
				} else {
					response.getWriter()
							.write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[交易失败]]></return_msg></xml>");
				}
			} else {
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				response.getWriter()
						.write("<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名校验失败]]></return_msg></xml>");
			}
			logger.error(" 支付回调成功,订单编号:" + out_trade_no);
			response.getWriter().flush();
			response.getWriter().close();
			return;
		} catch (IOException e) {
			logger.error(" 支付回调失败:" + e.getLocalizedMessage());
		}
	}
	
	/**
     * @desc  自动分配
     * @auther tangqm
     * @date 2018年6月2日 21点16分
     */
	public String autoAllot(PageData pd) {
		String code = ALLOT_LOG_CODE.AUTO_ALLOT_SUCCESS.getValue();
		String remark = "自动分配成功";
    	try {
    		autoAllotService.cbAutoAllot(pd);
		} catch (RuleException e) {
			logger.error(e);
			code = e.getCode();
			remark = e.getMessage();
		} catch (Exception e) {
			logger.error(e);
			code = ALLOT_LOG_CODE.FAIL.getValue();
			remark = "系统异常";
		}finally{
			pd.put("remark", remark);
			autoAllotService.insertAutoAllotLog((Integer)pd.get("orderid"),code, remark);
			if(code.equals(ALLOT_LOG_CODE.AGAINALLOT.getValue())){					
				WeixinUtil.autoFail(pd);
			}
		}
    	return code;
    }
	
	/**
	 * 微信查询个人信息(头像)
	 * @auther sqp
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", produces = "application/json;charset=UTF-8")
	public String getUserInfo(HttpServletRequest request,HttpServletResponse response,@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}
		
		WXInfoReqBean reqBean = (WXInfoReqBean) new Gson().fromJson(
				reqParm.getData(), WXInfoReqBean.class);				
		
		try {

			AccessToken accessToken = WXInfoUtil.getAccessToken(WeChatConst.WX_APPID, WeChatConst.WX_APP_SECRET);
			String tokenString = accessToken.getToken();
			WXInfoResBean wxInfoResBean = wxPublicNumService.getUserInfo(tokenString, reqBean.getOpenId());

			rtBean.setJsonData(new Gson().toJson(wxInfoResBean));		
		} catch (Exception ex) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("微信查询个人信息异常 ");
			logger.error("微信查询个人信息异常:" + ex.getLocalizedMessage());
		}
		return new Gson().toJson( rtBean );
	}
	
	
	/**
	 * @desc 关联商户号与开票平台（本步骤为一次性设置，后续一般在遇到开票平台识别号变更，或者商户更换开票平台时才需要调用本接口重设对应关系）
	 * @auther zhangjj
	 * @date 2018年8月28日
	 */
	@RequestMapping(value = "/linkMchidAndSPappid")
	public void linkMchidAndSPappid() {
		try {
			String requestUrl = "https://api.weixin.qq.com/card/invoice/setbizattr?action=set_pay_mch&access_token=ACCESS_TOKEN";
			String access_token = "13_MMJKcVr_3geFlx_HaVhBaw16_-DvzeLkOxFYDB11h9fGA3s5yQ8ZNxEUCIiTXW1fFN37iTpJ7LPSlHY_3hH0-ykQB0yaV_c09FAL-0YnLRNJpK6eL9oepf1psab86O73um18qU7dskNQ3yQzXJCbAGAGSL";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
			// 请求参数传入
			JSONObject reqparam_value = new JSONObject();
			reqparam_value.put("mchid", "1502576941");
			reqparam_value.put("s_pappid", "d3hmNjkxYzI5YzBkMjlmZjZkX5qbQ2DLjgykH6xd9wU6RJANme3SGp22dg0jsQRJUCgB");
			JSONObject reqparam = new  JSONObject();
			reqparam.put("paymch_info", reqparam_value);
			// 调用参数组装 
			JSONObject resObject = WeixinUtil.httpRequest(requestUrl, "POST", reqparam.toString());

			LoggerUtil.info("关联商户号与开票平台返回信息：" + resObject.toString());
		} catch (Exception ex) {
			logger.error("关联商户号与开票平台非预期异常:" + ex.getLocalizedMessage());
		}
	}
	
	/**
	 * @desc 查询商户号与开票平台关联情况
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@RequestMapping(value = "/findMchidAndSPappid")
	public void findMchidAndSPappid() {

		try {
			String requestUrl = "https://api.weixin.qq.com/card/invoice/setbizattr?action=get_pay_mch&access_token=ACCESS_TOKEN";
			String access_token = "13_MMJKcVr_3geFlx_HaVhBaw16_-DvzeLkOxFYDB11h9fGA3s5yQ8ZNxEUCIiTXW1fFN37iTpJ7LPSlHY_3hH0-ykQB0yaV_c09FAL-0YnLRNJpK6eL9oepf1psab86O73um18qU7dskNQ3yQzXJCbAGAGSL"; 
			requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
			// 请求参数
			JSONObject reqparam = new JSONObject();
			JSONObject resJsonObject = WeixinUtil.httpRequest(requestUrl,"POST", reqparam.toString());
			LoggerUtil.info(resJsonObject.toString());
		} catch (Exception ex) {
			logger.error(" 查询商户号与开票平台关联情况非预期异常:" + ex.getLocalizedMessage());
		}
	}
	
	
	/**
	 * 获取发票授权链接
	 */
	@ResponseBody
	@RequestMapping(value = "/getInvoiceAccredit")
	public String getInvoiceAccredit(HttpServletRequest request,HttpServletResponse response,@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doH5Validate( reqParm );
		if( rtBean.getCode().equals(APP_RESPONSE_CODE.FAIL.getValue()) ){
			return new Gson().toJson( rtBean );
		}
		
		WXInvoiceAccreditReqBean reqBean = (WXInvoiceAccreditReqBean) new Gson().fromJson(reqParm.getData(), WXInvoiceAccreditReqBean.class);
		LoggerUtil.info("获取发票授权链接入参:" + reqBean.toString());
		
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
			AccessToken access_token = (AccessToken) RedisUtil.get("accessToken");
			url = url.replace("ACCESS_TOKEN", access_token.getToken());
			JSONObject resJsonObject = HttpsClientUtil.httpsRequest(url, "GET", null);
			
			// 获取ticket是否失败校验
			LoggerUtil.info("获取发票授权链接ticket返回参数:" + resJsonObject.toString());
			ExceptionUtil.checkBoolean((Integer) resJsonObject.get("errcode") != 0, resJsonObject.getString("errmsg"));
			
			// 获取token
			String ticket = resJsonObject.getString("ticket");
			
			// 获取开票前授权链接
			String accrediturl = "https://api.weixin.qq.com/card/invoice/getauthurl?access_token=ACCESS_TOKEN";
			accrediturl = accrediturl.replace("ACCESS_TOKEN", access_token.getToken());
			// 组装请求参数
			JSONObject reqAccedit = new JSONObject();
			reqAccedit.put("s_pappid", PropertiesUtils.getString("WX_S_PAPPID"));
			reqAccedit.put("order_id", reqBean.getOrderno());
			reqAccedit.put("money", reqBean.getMoney());
			reqAccedit.put("timestamp", new Date().getTime());
			reqAccedit.put("source", "web");
			reqAccedit.put("redirect_url", PropertiesUtils.getString("WX_INVOICE_ACCREDIT_REDIRECT_URL"));
			reqAccedit.put("ticket", ticket);
			reqAccedit.put("type", 1);
			JSONObject res_JsonObj_Accedit = HttpsClientUtil.httpsRequest(accrediturl, "POST", reqAccedit.toString());
			// 获取发票授权页面链接是否失败
			LoggerUtil.info("获取发票授权链接返回参数:" + res_JsonObj_Accedit.toString());
			ExceptionUtil.checkBoolean((Integer) res_JsonObj_Accedit.get("errcode") != 0, res_JsonObj_Accedit.getString("errmsg"));
			
			// 组装返回参数
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg(resJsonObject.getString("errmsg"));
			rtBean.setJsonData(new Gson().toJson(new WXInvoiceAccreditResBean(res_JsonObj_Accedit.getString("auth_url"))));
			return new Gson().toJson( rtBean );
		} catch (Exception ex) {
			logger.error("发票授权页页面链接:" + ex.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("获取发票授权页面链接失败" + ex.getMessage());
			return new Gson().toJson( rtBean );
		}
	}
	
}