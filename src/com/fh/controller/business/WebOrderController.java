package com.fh.controller.business;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.constant.ConfigCenterKeys;
import com.fh.common.constant_enum.AIRPORTORDER_TYPE;
import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.UPDATE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.counterservice.CheckAddrReqBean;
import com.fh.entity.app.counterservice.CheckAddrResBean;
import com.fh.entity.app.order.*;
import com.fh.entity.configcenter.GoldPriceDetail;
import com.fh.entity.configcenter.SpecialPriceDetail;
import com.fh.entity.h5.H5CheckAddrResBean;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.service.order.OrderAddressService;
import com.fh.service.order.OrderBaggageService;
import com.fh.service.order.OrderEvaluateService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.PageData;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业下单接口
 * tangqm
 * 2019年3月25日
 */
@Controller
@RequestMapping(value = "weborder")
public class WebOrderController extends BaseController {
	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * @desc web_订单列表查询
	 * @auther tangqm
	 * @history 2019年3月25日
	 */
	@ResponseBody
	@RequestMapping(value = "/findWebOrderMainList", produces = "application/json;charset=UTF-8")
	public String findAppOrderMainList(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return gson.toJson(rtBean);
		}
		Page page = new Gson().fromJson(reqParm.getData().toString(), Page.class);
		if (page == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查询订单信息成功失败，原因是请求参数转换异常");
			return gson.toJson(rtBean);
		}
		try {
			List<PageData> pageData = orderInfoService.orderMainlistPage(page);
			rtBean.setJsonData(new Gson().toJson(pageData));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查询订单列表信息成功");
			return gson.toJson(rtBean);
		} catch (Exception e) {
			logger.error("查询订单列表信息非预期异常:" + e);
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return gson.toJson(rtBean);
		}
	}
	
}
