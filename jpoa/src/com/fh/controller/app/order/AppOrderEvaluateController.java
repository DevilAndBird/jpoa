package com.fh.controller.app.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.AppOrderEvaluateReqData;
import com.fh.entity.app.order.AppOrderEvaluateResData;
import com.fh.entity.order.OrderEvaluate;
import com.fh.service.order.OrderEvaluateService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app订单评论接口(废弃)
 * 类名称：com.fh.controller.app.order.AppOrderEvaluateController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:03:30   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:03:30   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "/orderEvaluate")
public class AppOrderEvaluateController extends BaseController {
	@Autowired
	private OrderEvaluateService orderEvaluateService;
	
	/**
	 * @desc 发表评论
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrderEvaluate", produces = "application/json;charset=UTF-8")
	public String saveOrderEvaluate(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderEvaluateReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderEvaluateReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		
		try {
			ExceptionUtil.checkBoolean(reqData.getOrderid() == null || reqData.getOrderid() == 0, "订单id不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getStaffattitude(), "人员服务不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getLogisticsservice(), "物流服务不能为空");
			
			// 转换
			OrderEvaluate orderEvaluate = evaluateDataToEntity(reqData);
			
			// 发表评论
			orderEvaluateService.save(orderEvaluate);
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("发表评论成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
		    logger.error("发表评论校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("发表评论校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("发表评论非预期保存失败:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("发表评论非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
	}
	
	/**
	 * @desc 追加评论
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/addToEvaluate", produces = "application/json;charset=UTF-8")
	public String addToEvaluate(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderEvaluateReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderEvaluateReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		try {
			ExceptionUtil.checkBoolean(reqData.getId() == null || reqData.getId() == 0, "评论id不能为空");
			ExceptionUtil.checkNotEmpty(reqData.getEvaluatedesc(), "评价描述不能为空");
			
			// 转换
			OrderEvaluate orderEvaluate = evaluateDataToEntity(reqData);
			
			// 追加评论
			orderEvaluateService.updateEvaluatedesc(orderEvaluate);
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("追加评论成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
		    logger.error("追加评论校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("追加评论校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("追加评论非预期保存失败:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("追加评论非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
	}
	
	/**
	 * @desc 查看评论
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/selectEvaluate", produces = "application/json;charset=UTF-8")
	public String selectEvaluate(@RequestBody AppRequestBean reqParm) {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderEvaluateReqData reqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderEvaluateReqData.class);
		if (reqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		try {
			ExceptionUtil.checkBoolean(reqData.getOrderid() == null || reqData.getOrderid() == 0, "订单id不能为空");
			
			// 查询评论
			OrderEvaluate orderEvaluate = orderEvaluateService.findbyid(reqData.getOrderid());
			
			if(orderEvaluate != null) {
				// 转换
				AppOrderEvaluateResData resData = entityToEvaluateData(orderEvaluate);
				// 返回
				rtBean.setJsonData(new Gson().toJson(resData));
			}
			
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("查看评论成功");
			return new Gson().toJson(rtBean);
		}catch (RulesCheckedException e) {
		    logger.error("查看评论校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("查看评论校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("查看评论非预期保存失败:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("查看评论非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
		
	}
	
	/**
	 * @desc 评论reqData转实体类
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	private OrderEvaluate evaluateDataToEntity(AppOrderEvaluateReqData reqData) {
		OrderEvaluate orderEvaluate = new OrderEvaluate();
		orderEvaluate.setId(reqData.getId());
		orderEvaluate.setOrderid(reqData.getOrderid());
		orderEvaluate.setStaffattitude(reqData.getStaffattitude());
		orderEvaluate.setLogisticsservice(reqData.getLogisticsservice());
		orderEvaluate.setEvaluatedesc(reqData.getEvaluatedesc());
		return orderEvaluate;
	}
	
	/**
	 * @desc 实体类转评论resData
	 * @auther zhangjj
	 * @date 2018年4月10日
	 */
	private AppOrderEvaluateResData entityToEvaluateData(OrderEvaluate entity) {
		AppOrderEvaluateResData resData = new AppOrderEvaluateResData();
		resData.setId(entity.getId());
		resData.setOrderid(entity.getOrderid());
		resData.setStaffattitude(entity.getStaffattitude());
		resData.setLogisticsservice(entity.getLogisticsservice());
		resData.setEvaluatedesc(entity.getEvaluatedesc());
		return resData;
	}
	
}
