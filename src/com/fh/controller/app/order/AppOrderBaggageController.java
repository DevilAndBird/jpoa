package com.fh.controller.app.order;

import com.fh.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.order.OrderBaggageReqData;
import com.fh.entity.order.OrderBaggage;
import com.fh.service.order.OrderBaggageService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app行李接口
 * 类名称：com.fh.controller.app.order.AppOrderBaggageController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:00:42   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:00:42   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/appOrderBaggage")
public class AppOrderBaggageController extends BaseController
{
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
    private OrderBaggageService orderBaggageService;
	
	/**
     * @desc QR码校验
     * @auther zhangjj
     * @history 2018年2月28日
     */
    @ResponseBody
    @RequestMapping(value = "/scanQRCode", produces = "application/json;charset=UTF-8" )
    public String scanQRCode(@RequestBody AppRequestBean reqParm) {
        AppResponseBean rtBean = doValidate(reqParm);
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson(rtBean);
        }

        OrderBaggageReqData orderBaggageReqData = new Gson().fromJson(reqParm.getData().toString(), OrderBaggageReqData.class);
        if(orderBaggageReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("SQ码校验失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
        
        try {
            // 校验
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getBaggageid(), "行李QR码必传");
            ExceptionUtil.checkBoolean(!"JPQR".equals(orderBaggageReqData.getBaggageid().substring(0,4)), "二维码格式有误");// TODO qr码前缀配置
            
            // 检查QR码是否被使用过
        	OrderBaggage orderBaggageRes = orderInfoService.findOrderBaggageBybagid(orderBaggageReqData.getBaggageid());
        	ExceptionUtil.checkBoolean(orderBaggageRes != null, "该行李QR码不可用，请重新换一张QR码");
            
            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("QR码校验成功");
            return new Gson().toJson(rtBean);
        } catch (RulesCheckedException e) {
            logger.error("QR码校验校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("QR码校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error("QR码校验码非预期异常:" + e.getLocalizedMessage());
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("非预期异常，请联系IT");
            return new Gson().toJson(rtBean);
        }
    }
	

    /**
     * @desc 关联QR码
     * @auther tangqm
     * @history 2018年2月28日
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrderBaggage", produces = "application/json;charset=UTF-8" )
    public String saveOrderBaggage(@RequestBody AppRequestBean reqParm) {
        AppResponseBean rtBean = doValidate(reqParm);
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson(rtBean);
        }

        OrderBaggageReqData orderBaggageReqData = new Gson().fromJson(reqParm.getData().toString(), OrderBaggageReqData.class);
        LoggerUtil.info("OrderBaggageReqData" + reqParm.getData().toString());
        if(orderBaggageReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("关联SQ码失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
        
        try {
            // 校验
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getBaggageid(), "行李QR码必传");
            ExceptionUtil.checkBoolean(!"JPQR".equals(orderBaggageReqData.getBaggageid().substring(0,4)), "二维码格式有误");
            ExceptionUtil.isFalse(orderBaggageReqData.getOrderid() == null || orderBaggageReqData.getOrderid() == 0, "订单id必传");
            ExceptionUtil.isTrue(orderBaggageReqData.getBaggageid().length()==10||orderBaggageReqData.getBaggageid().length()==12,"二维码长度有误");
            // 检查QR码是否被使用过
        	OrderBaggage orderBaggageRes = orderInfoService.findOrderBaggageBybagid(orderBaggageReqData.getBaggageid());
        	ExceptionUtil.checkBoolean(orderBaggageRes != null, "该行李QR码不可用，请重新换一张QR码");

        	// 关联QR

            if(orderBaggageReqData.getId() == null) {
                // 新增
                OrderBaggage orderBaggage = new OrderBaggage();
                orderBaggage.setOrderid(orderBaggageReqData.getOrderid());
                orderBaggage.setBaggageid(orderBaggageReqData.getBaggageid());
                orderInfoService.linkOrderAndQR(orderBaggage);
            } else {
                // 新增
                OrderBaggage orderBaggage = new OrderBaggage();
                orderBaggage.setId(orderBaggageReqData.getId());
                orderBaggage.setBaggageid(orderBaggageReqData.getBaggageid());
                orderInfoService.updateQR(orderBaggage);
            }

            
            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("关联QR码成功");
            return new Gson().toJson(rtBean);
        } catch (RulesCheckedException e) {
            logger.error("关联QR码校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error("关联QR码非预期异常:" + e.getLocalizedMessage());
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("非预期异常，请联系IT");
            return new Gson().toJson(rtBean);
        }
    }
    
    

    /**
     * @desc 行李照片路径上传
     * @auther zhangjj
     * @history 2018年2月28日
     */
    @ResponseBody
    @RequestMapping(value = "/baggageImgUrlUpload", produces = "application/json;charset=UTF-8" )
    public String baggageImgUrlUpload(@RequestBody AppRequestBean reqParm) {
        AppResponseBean rtBean = doValidate(reqParm);
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson(rtBean);
        }
        OrderBaggageReqData orderBaggageReqData = new Gson().fromJson(reqParm.getData().toString(), OrderBaggageReqData.class);
        if(orderBaggageReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("行李照片路径上传失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
        
        try {
        	// 校验
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getBaggageid(), "行李QR码必传");
            ExceptionUtil.checkBoolean(!"JPQR".equals(orderBaggageReqData.getBaggageid().substring(0,4)), "二维码格式有误");// TODO 代码层面写死，不应该
            ExceptionUtil.isFalse(orderBaggageReqData.getOrderid() == null || orderBaggageReqData.getOrderid() == 0, "订单id必传");
//            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getImgurl(), "行李照片路径不能为空");
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getImgtype(), "行李照片路径业务类型不能为空");
            ExceptionUtil.checkNotCollectNotEmpty(orderBaggageReqData.getImgurlList(), "图片集合不能为空");							
            ExceptionUtil.checkBoolean(orderBaggageReqData.getUploadUserid() == null || orderBaggageReqData.getUploadUserid() == 0, "上传照片用户id不能为空");
            
            // 上传
            orderBaggageService.updateImage(orderBaggageReqData);

            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("行李照片上传成功");
            return new Gson().toJson(rtBean);
        } catch (RulesCheckedException e) {
            logger.error("行李照片路径上传校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error("行李照片路径上传非预期异常:" + e.getLocalizedMessage());
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("非预期异常，请联系IT");
            return new Gson().toJson(rtBean);
        }
    }
    
    /**
     * @desc 删除关联QR码
     * @auther zhangjj
     * @history 2018年2月28日
     */
    @ResponseBody
    @RequestMapping(value = "/deleteLinkQR", produces = "application/json;charset=UTF-8" )
    public String deleteLinkQR(@RequestBody AppRequestBean reqParm) {
        AppResponseBean rtBean = doValidate(reqParm);
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
            return new Gson().toJson(rtBean);
        }

        OrderBaggageReqData orderBaggageReqData = new Gson().fromJson(reqParm.getData().toString(), OrderBaggageReqData.class);
        if(orderBaggageReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("删除关联QR码失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
        
        try {
            // 校验
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getBaggageid(), "行李QR码必传");
            ExceptionUtil.checkBoolean(!"JPQR".equals(orderBaggageReqData.getBaggageid().substring(0,4)), "二维码格式有误");
            ExceptionUtil.isFalse(orderBaggageReqData.getOrderid() == null || orderBaggageReqData.getOrderid() == 0, "订单id必传");
        	
        	// 关联QR
            OrderBaggage orderBaggage = new OrderBaggage();
            orderBaggage.setOrderid(orderBaggageReqData.getOrderid());
            orderBaggage.setBaggageid(orderBaggageReqData.getBaggageid());
            
            orderBaggageService.deleteLinkQR(orderBaggageReqData.getOrderid(), orderBaggageReqData.getBaggageid());
            
            rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
            rtBean.setMsg("删除关联QR码成功");
            return new Gson().toJson(rtBean);
        } catch (RulesCheckedException e) {
            logger.error("删除关联QR码校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error("删除关联QR码非预期异常:" + e.getLocalizedMessage());
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("非预期异常，请联系IT");
            return new Gson().toJson(rtBean);
        }
    }
    
    /**
     * @desc 是否扫描
     * @auther zhangjj
     * @history 2018年2月28日
     */
    @ResponseBody
    @RequestMapping(value="/isScan", produces = "application/json;charset=UTF-8" )
    public String countBaggageNumByQR( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue()))
        {
            return new Gson().toJson( rtBean );
        }
        
        OrderBaggageReqData orderBaggageReqData = new Gson().fromJson( reqParm.getData().toString(), OrderBaggageReqData.class );
        
        try {
            ExceptionUtil.checkBoolean(orderBaggageReqData.getOrderid() == null || orderBaggageReqData.getOrderid() == 0, "订单id不能为空");
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getBaggageid(), "QR码不能为空");
            ExceptionUtil.checkNotEmpty(orderBaggageReqData.getIsscan(), "是否扫描不能为空");
        	
            orderInfoService.updateOrderBaggageIsScan(orderBaggageReqData);
            
            rtBean.setCode( APP_RESPONSE_CODE.SUCCESS.getValue() );
            rtBean.setMsg("已扫描成功");
            return new Gson().toJson( rtBean );
        } catch (RulesCheckedException e) {
            logger.error("是否已扫描校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean); 
        } catch (Exception e) {
            logger.error( "是否已扫描非预期异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
}
