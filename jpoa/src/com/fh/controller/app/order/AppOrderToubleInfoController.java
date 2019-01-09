package com.fh.controller.app.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.AppOrderToubleReqData;
import com.fh.entity.app.order.AppOrderToubleResData;
import com.fh.entity.order.ProblemOrder;
import com.fh.service.app.AppOrderToubleInfoService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app问题接口(暂未使用)
 * 类名称：com.fh.controller.app.order.AppOrderToubleInfoController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:03:57   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:03:57   
 * 修改备注：
 */
@Controller
@RequestMapping(value = "/orderToubleInfo")
public class AppOrderToubleInfoController extends BaseController {

	@Autowired
	private AppOrderToubleInfoService appOrderToubleInfoService;
 	@Autowired
 	private OrderMainService orderMainService;
	
	/**
	 * @desc 订单问题件反馈
	 * @auther zhangjj
	 * @history 2018年12月10日
	 */
	@ResponseBody
	@RequestMapping(value = "/orderToubleFeedback", produces = "application/json;charset=UTF-8" )
	public String orderToubleFeedback(@RequestBody AppRequestBean reqParm) throws Exception {
		AppResponseBean rtBean = doValidate(reqParm);
		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
			return new Gson().toJson(rtBean);
		}
		AppOrderToubleReqData appOrderToubleReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderToubleReqData.class);
		if(appOrderToubleReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("订单问题件反馈失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
		
		try {
		    // 校验
		    exceptionToubleReqData(appOrderToubleReqData.getProblemOrder());
		    
			appOrderToubleInfoService.feedback(appOrderToubleReqData.getProblemOrder());
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("问题已成功反馈！");
			return new Gson().toJson(rtBean);
		} catch (RulesCheckedException e) {
			logger.error("问题反馈，校验异常:" + e.getMessage());
			rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
			rtBean.setMsg("问题反馈，校验异常s");
			return new Gson().toJson(rtBean);
    	} catch (Exception e) {
    	    logger.error("问题反馈，非预期异常:" + e.getLocalizedMessage());
    	    rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
    	    rtBean.setMsg("问题反馈，非预期异常，请联系IT");
    	    return new Gson().toJson(rtBean);
    	}
	}
	
	/**
     * @desc 订单反馈列表查询
     * @auther zhangjj
     * @history 2018年12月10日
     */
    @ResponseBody
    @RequestMapping(value = "/findOrderToubleList", produces = "application/json;charset=UTF-8" )
    public String findOrderToubleList(@RequestBody AppRequestBean reqParm) throws Exception {
        AppResponseBean rtBean = doValidate(reqParm);
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson(rtBean);
        }

        AppOrderToubleReqData appOrderToubleReqData = new Gson().fromJson(reqParm.getData().toString(), AppOrderToubleReqData.class);
        if(appOrderToubleReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("订单问题件反馈失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
        
        try {
            // 校验
            ExceptionUtil.checkNotNull(appOrderToubleReqData.getProblemOrder(), "请求参数不能空");
            ExceptionUtil.checkNotNull(appOrderToubleReqData.getProblemOrder().getOrderid(), "orderid不能空");
            
            List<ProblemOrder> findOrderTouble = appOrderToubleInfoService.findOrderTouble(appOrderToubleReqData.getProblemOrder().getOrderid());
            AppOrderToubleResData appOrderToubleResData = new AppOrderToubleResData();
            appOrderToubleResData.setProblemOrderList(findOrderTouble);
            rtBean.setJsonData(new Gson().toJson(appOrderToubleResData));
            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("反馈查询成功！");
            return new Gson().toJson(rtBean);
        } catch (RulesCheckedException e) {
            logger.error("查询反馈信息，校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("查询反馈信息，校验异常");
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error("问题反馈，非预期异常:" + e.getLocalizedMessage());
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("问题反馈，非预期异常，请联系IT");
            return new Gson().toJson(rtBean);
        }
    }
	
	/**
	 * 反馈入参校验
	 */
	private void exceptionToubleReqData(ProblemOrder problemOrder) {
	    ExceptionUtil.checkNotNull(problemOrder.getOrderid(), "订单id不能为空");
	    ExceptionUtil.checkNotNull(problemOrder.getFeedbackuserid(), "反馈人id不能为空");
	    ExceptionUtil.checkNotEmpty(problemOrder.getFeedbackusername(), "反馈人姓名不能为空");
	    ExceptionUtil.checkNotEmpty(problemOrder.getFeedbackusermobile(), "反馈人电话不能为空");
	    ExceptionUtil.checkNotEmpty(problemOrder.getFeedbackdesc(), "反馈描述不能为空");
	}

}
