package com.fh.test.wx;

import java.math.BigDecimal;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.INVICE_STATUS;
import com.fh.common.constant_enum.INVOICE_INVOICETYPE;
import com.fh.controller.wxpublicnum.wxpushinfo.AccessToken;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.h5.wxpublicnum.WXPayReqBean;
import com.fh.entity.h5.wxpublicnum.WXPublicNumReqBean;
import com.fh.entity.h5.wxpublicnum.WXRefundReqBean;
import com.fh.entity.order.OrderInvoiceInfo;
import com.fh.util.ExceptionUtil;
import com.fh.util.HttpClientUtil;
import com.fh.util.IdWorker;
import com.fh.util.LoggerUtil;
import com.fh.util.MD5;
import com.fh.util.PropertiesUtils;
import com.fh.util.RedisUtil;
import com.fh.util.WeixinUtil;
import com.google.gson.Gson;

public class WeiXinJunitTest {
	
	public   String urlHeader = "http://localhost/jpoa/";// 地址头
//	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	/**
	 * 微信支付
	 */
	@Test
	public void wxPay() {
		urlHeader+="wxpublicnum/wxPay";
		
		
		WXPayReqBean data = new WXPayReqBean();
		data.setOpenId("okV0d1WLWKPwS4T0Oxe1lp7eTh5s");//okV0d1WLWKPwS4T0Oxe1lp7eTh5s
		data.setOrderno("JPWX20180503180507213");//JPWX20180503180507213
		data.setTotalFee(1);
//		data.setClientIp("yhair.info");
//		data.setClientIp("192.168.195.57");
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	/**
	 * 微信退款
	 */
	@Test
	public void rufund() {
		urlHeader+="wxpublicnum/refund";
		
		WXRefundReqBean data = new WXRefundReqBean();
		data.setOut_trade_no("ozb5fjjxbwag1******08makqhsdmx");
		data.setRefund_fee(1f);
		data.setTotal_fee(1f);
		
		commonTest(urlHeader,new Gson().toJson(data));
	}
	
	
	
	/**
	 * 用户授权
	 */
    @Test
    public void userAccredit() {
    	urlHeader+="wxpublicnum/userAccredit";
    	WXPublicNumReqBean data  = new WXPublicNumReqBean();
    	data.setCode("021GasZq0iTMFp1hON0r0P02Zq0GasZK");
    	commonTest(urlHeader,new Gson().toJson(data));
    }
    
	/**
	 * 
	 */
    @Test
    public void linkMchidAndSPappidTest() {
    	urlHeader+="wxpublicnum/linkMchidAndSPappid";
    	commonTest(urlHeader,new Gson().toJson(null));
    }
	
	public void commonTest(String url,String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("130354218011");
		bean.setKey("admin444");
		bean.setTimestamp("" + System.currentTimeMillis());
		bean.setSign(MD5.md5("jingpei" + bean.getUser() + bean.getKey()
				+ bean.getTimestamp()));
		bean.setData(data);
		Gson gson = new Gson();
		String json = gson.toJson(bean);
		System.out.println(json);
		try {
			String res = HttpClientUtil.doPostJson(url, json);
			System.out.println(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @desc 关联商户号与开票平台（本步骤为一次性设置，后续一般在遇到开票平台识别号变更，或者商户更换开票平台时才需要调用本接口重设对应关系）
	 * @auther zhangjj
	 * @date 2018年8月28日
	 */
	@Test
	public void linkMchidAndSPappid() {
		AppResponseBean rtBean = new AppResponseBean();

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
			JSONObject resObject = WeixinUtil.httpRequest(requestUrl, "POST", reqparam.toString());

			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("关联商户号与开票平台成功");
			if (!"0".equals(resObject.get("errcode"))) {
				rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
				rtBean.setMsg("关联商户号与开票平台失败");
			}
			LoggerUtil.info("关联商户号与开票平台返回信息：" + resObject.toString());
		} catch (Exception ex) {
			LoggerUtil.error("关联商户号与开票平台非预期异常:", ex);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("关联商户号与开票平台非预期异常");
		}
	}
	
	/**
	 * @desc 查询商户号与开票平台关联情况
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
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
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
	}
	
	/**
	 * @desc  设置商户联系方式
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void set_contact() {
		try {
			String requestUrl = "https://api.weixin.qq.com/card/invoice/setbizattr?action=set_contact&access_token=ACCESS_TOKEN";
			String access_token = "13_FLi8hQsQqLBweu88q3TqyXwfWkKRC4kGjA_5CH9KfOzERVQ3XH4-PjulZUBrm7hP_fAvoH4usfQa_hcKw-6acnAKSQXzVd-tCYwEcaA0cF0aiFRSFXMqLk5YccZ-9IvSysaywQx5X-_PQkjyVROiAJAYUR"; 
			requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
			// 请求参数
			JSONObject reqparam = new JSONObject();
			reqparam.put("phone", "18752066145");
			reqparam.put("time_out", 6000000);
			JSONObject contact = new JSONObject();
			contact.put("contact", reqparam);
			
			JSONObject resJsonObject = WeixinUtil.httpRequest(requestUrl,"POST", contact.toString());
			LoggerUtil.info(resJsonObject.toString());
		} catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
	}
	
	/**
	 * @desc  查询授权完成状态
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void getauthdata() {
		OrderInvoiceInfo orderInvoiceInfo = new OrderInvoiceInfo();
		
		try {
			String requestUrl = "https://api.weixin.qq.com/card/invoice/getauthdata?access_token=ACCESS_TOKEN";
			String access_token = "13_ObWYPxNolLeKoi3fY4xhNr-OSj9YplGua8a1RPvy54O5RY6kOL-1D2BgoupfviaA6NvVCcw_mSrZ0FQ4Ep3uIhCxX0_hspSpUOMSAdCCgf0jbBO5aXnTQcQ6saOEVmjs8YkVumwmVE3Bkw0BIPNjAHAVCA"; 
			requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
			// 请求参数
			JSONObject reqparam = new JSONObject();
			reqparam.put("s_pappid", "d3hmNjkxYzI5YzBkMjlmZjZkX5qbQ2DLjgykH6xd9wU6RJANme3SGp22dg0jsQRJUCgB");
			reqparam.put("order_id", "JPWX20180905001925193");
			
			// 调用
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_req：\n" + reqparam.toString());
	    	JSONObject res = WeixinUtil.httpRequest(requestUrl, "POST", reqparam.toString());
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_res：\n" + res.toString());
			
	    	// 电子发票授权失败
	    	ExceptionUtil.checkBoolean((Integer) res.get("errcode") != 0 || !"auth success".equals(res.getString("invoice_status")), "查询电子发票授权状态返回异常, 异常码：" + res.getString("errmsg"));
			
			JSONObject userAuthInfo = JSONObject.fromObject(res.getString("user_auth_info"));
			// 企业级发票信息
			if(userAuthInfo.get("biz_field") != null) {
				JSONObject bizField = (JSONObject) userAuthInfo.get("biz_field");
				orderInvoiceInfo.setTitle((String) bizField.get("title"));
				orderInvoiceInfo.setTaxno((String) bizField.get("tax_no"));
				orderInvoiceInfo.setInvoicetype(INVOICE_INVOICETYPE.COMPANY.getValue());
				orderInvoiceInfo.setInvoicetime(new Date());
				orderInvoiceInfo.setEmail((String) bizField.get("email"));
				orderInvoiceInfo.setBanktype((String) bizField.get("bank_type"));
				orderInvoiceInfo.setBankno((String) bizField.get("bank_no"));
				orderInvoiceInfo.setAddr((String) bizField.get("addr"));
				orderInvoiceInfo.setPhone((String) bizField.get("phone"));
				orderInvoiceInfo.setStatus(INVICE_STATUS.APPLYED.getValue());
			}
			// 个人发票信息
			if( userAuthInfo.get("user_field") != null ) {
				JSONObject userfield = (JSONObject) userAuthInfo.get("user_field");
				orderInvoiceInfo.setTitle((String) userfield.get("title"));
				orderInvoiceInfo.setInvoicetype(INVOICE_INVOICETYPE.PERSONAL.getValue());
				orderInvoiceInfo.setEmail((String) userfield.get("email"));
				orderInvoiceInfo.setInvoicetime(new Date());
				orderInvoiceInfo.setStatus(INVICE_STATUS.APPLYED.getValue());
			}
			
		} catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
		
	}
	
	/**
	 * @desc  查询授权完成状态
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void makeoutinvoice() {
		try {
			Float money = 49.00f;
			String openid = "okV0d1aBu5waTha4A_7pYiiRJkMQ";
			String orderno = "JPWX20180905001925193";
			
	    	String reqUrl = "https://api.weixin.qq.com/card/invoice/makeoutinvoice?access_token=ACCESS_TOKEN";
	    	String access_token = "13_4KuiWJJbU4eCFP5oRMO6extfphj6xtqsZqvQJ3mcCeFOKXukkmBjcxxfh5k5KX7f7rJSKSktK6X0YNTiNsciT74h4wsCxbvawGpu_0Ty-_WZAiyp5mr2zJbVh20uEnPcBk9P2_KqgliCsrUwFIKhAGAPSU";
	    	reqUrl = reqUrl.replace("ACCESS_TOKEN", access_token);
	    	
	    	// 税额 单位元 两位小数 
	    	Float se = (float) (money * 0.06);
	    	BigDecimal seTemp = new BigDecimal(se);
	    	float seTemp2 = seTemp.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	    	
	    	
	    	
	    	// 组装参数
	    	JSONArray invoicedetail_list = new JSONArray();
	    	JSONObject invoicedetail = new JSONObject();
	    	invoicedetail.put("fphxz", "0");// 发票行性质 0 正常 1折扣 2 被折扣
	    	invoicedetail.put("spbm", "3040409030000000000");// 	19位税收分类编码
	    	invoicedetail.put("xmmc", "派送服务");// 项目名称 
	    	invoicedetail.put("xmsl", "1");// 项目数量
	    	invoicedetail.put("xmdj", "46.23");// 项目金额 不含税，单位元 两位小数
	    	invoicedetail.put("xmje", "46.23");// 项目金额 不含税，单位元 两位小数
	    	invoicedetail.put("sl", "0.06");// 税率 精确到两位小数 如0.01 
	    	invoicedetail.put("se", "2.77");
	    	invoicedetail_list.add(invoicedetail);
	    	
	    	JSONObject invoiceinfo = new JSONObject();
	    	invoiceinfo.put("wxopenid", openid);	// 用户的openid 用户知道是谁在开票
	    	invoiceinfo.put("ddh", orderno);	// 订单号，企业自己内部的订单号码
	    	// 生成发票请求流水号
	    	IdWorker worker = IdWorker.getInstance(); 
	    	String invoiceSerial = "2ff836" + "80905001925193";
	    	invoiceinfo.put("fpqqlsh", invoiceSerial);	// 发票请求流水号，唯一查询发票的流水号
	    	
	    	invoiceinfo.put("nsrmc", "上海景沛网络科技有限公司");	// 	纳税人名称
	    	invoiceinfo.put("nsrsbh", "91310107MA1G0J7C8P");	// 纳税人识别码
	    	invoiceinfo.put("nsrdz", "上海市普陀区金通路999号A座611室");	// 	纳税人地址
	    	invoiceinfo.put("nsrdh", "56057681");	// 纳税人电话
	    	invoiceinfo.put("nsrbank", "工商银行普陀真南支行"); // 纳税人开户行
	    	invoiceinfo.put("nsrbankid", "1001151209000236539"); // 纳税人银行账号
	    	
	    	invoiceinfo.put("ghfmc", "上海景沛网络科技有限公司");	// 购货方名称
	    	invoiceinfo.put("ghfnsrsbh", "91310107MA1G0J7C8P");	// 购货方识别号
	    /*	invoiceinfo.put("ghfdz", orderInvoiceInfo.getAddr());	// 	购货方地址
	    	invoiceinfo.put("ghfdh", orderInvoiceInfo.getPhone());	//	购货方电话
	    	invoiceinfo.put("ghfbank", orderInvoiceInfo.getBanktype()); // 购货方开户行
	    	invoiceinfo.put("ghfbankid", orderInvoiceInfo.getBankno()); // 购货方银行帐号*/
	    	
	    	invoiceinfo.put("kpr", "张晶晶"); // 	开票人
	    	invoiceinfo.put("jshj", (49.00) + ""); // 价税合计
	    	invoiceinfo.put("hjje", (46.23) + ""); // 合计金额
	    	invoiceinfo.put("hjse", "2.77"); // 合计税额
	    	invoiceinfo.put("hylx", "0"); // 合计金额
	    	
	    	invoiceinfo.put("invoicedetail_list", invoicedetail_list); // 发票行项目数据
	    	
	    	JSONObject makeOutAnInvoice = new JSONObject();
	    	makeOutAnInvoice.put("invoiceinfo", invoiceinfo);
	    	
	    	// 调用
	    	LoggerUtil.info("\n开具蓝票makeoutinvoice_req：\n" + makeOutAnInvoice.toString());
	    	LoggerUtil.info("\n开具蓝票makeoutinvoice_url：\n" + reqUrl);
	    	JSONObject res = WeixinUtil.httpRequest(reqUrl, "POST", makeOutAnInvoice.toString());
	    	LoggerUtil.info("\n开具蓝票makeoutinvoice_res：\n" + res.toString());
	    	
	    	ExceptionUtil.checkNotNull(res, "开具蓝票异常，请检查原因！");
	    } catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
	}

	/**
	 * @desc  查询电子发票开具情况
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void queryinvoceinfo() {
		
		try {
			String requestUrl = "https://api.weixin.qq.com/card/invoice/queryinvoceinfo?access_token=ACCESS_TOKEN";
			String access_token = "13_ObWYPxNolLeKoi3fY4xhNr-OSj9YplGua8a1RPvy54O5RY6kOL-1D2BgoupfviaA6NvVCcw_mSrZ0FQ4Ep3uIhCxX0_hspSpUOMSAdCCgf0jbBO5aXnTQcQ6saOEVmjs8YkVumwmVE3Bkw0BIPNjAHAVCA"; 
			requestUrl = requestUrl.replace("ACCESS_TOKEN", access_token);
			// 请求参数
			JSONObject reqparam = new JSONObject();
			reqparam.put("fpqqlsh", "2ff83620180906145347857");
			reqparam.put("nsrsbh", "91310107MA1G0J7C8P");
			
			// 调用
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_req：\n" + reqparam.toString());
	    	JSONObject res = WeixinUtil.httpRequest(requestUrl, "POST", reqparam.toString());
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_res：\n" + res.toString());
			
		} catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
		
	}
	
	/**
	 * @desc  查询电子发票开具情况
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void calc() {
		
		try {
//			// 税率
//	    	Float taxRate = 0.06f;
//	    	// 合计金额
//			Float amountInTotal = new BigDecimal(49.00f/(taxRate + 1)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//	    	// 合计税额
//			Float taxInTotal = new BigDecimal(49.00f - amountInTotal).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();;
			String orderno = "JPWX20180905001925193";
			System.out.println(orderno.substring(8, orderno.length()));
		} catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
	}
	
	
	/**
	 * @desc  查询电子发票开具情况
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@Test
	public void batchget() {
		
		try {
			
			String reqUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
			String access_token = "13_UAkzIPkLpyMrEcmmos_wGmXWTk_T7mnE5IlDHhSJYAjsosrrT8zNad4Nv_FuQ7PlCDohzytymgRjDWcrawenYcnIVBQC68gMJgWAlDTCgjbyggkI-ZWTVQJV_IPYjoShtqjAE0HgOZK3tALYVTWfAEAHFI";
			reqUrl = reqUrl.replace("ACCESS_TOKEN", access_token);
			
			
			/*JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("openid", "okV0d1ePY8gYXqTc7qKKE2iDqpMA");
			jsonObject1.put("lang", "zh_CN");*/
			JSONObject jsonObject2 = new JSONObject();
			jsonObject2.put("openid", "okV0d1TT3J97e25DNwQYxjN473co");
			jsonObject2.put("lang", "zh_CN");
			
			JSONArray jsonArray = new JSONArray();
//			jsonArray.add(jsonObject1);
			jsonArray.add(jsonObject2);
			
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("user_list", jsonArray);
			
			// 调用
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_req：\n" + jsonObject3.toString());
	    	JSONObject res = WeixinUtil.httpRequest(reqUrl, "POST", jsonObject3.toString());
	    	LoggerUtil.info("\n查询电子发票授权状态getauthdata_res：\n" + res.toString());
			
		} catch (Exception ex) {
			LoggerUtil.error(" 查询商户号与开票平台关联情况非预期异常:", ex);
		}
	}
}
