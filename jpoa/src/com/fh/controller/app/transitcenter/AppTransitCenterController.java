package com.fh.controller.app.transitcenter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.common.constant_enum.APP_RESPONSE_CODE;
import com.fh.common.constant_enum.ORDER_STATUS;
import com.fh.common.constant_enum.QUERYSCOPE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.AppResponseBean;
import com.fh.entity.app.transitcenter.AppUnloadDonReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanReqData;
import com.fh.entity.app.transitcenter.AppUnloadScanResData;
import com.fh.entity.app.transitcenter.TransitOrderReqData;
import com.fh.entity.app.transitcenter.TransitOrderResData;
import com.fh.service.app.AppTransitCenterService;
import com.fh.service.order.orderinfo.OrderMainService;
import com.fh.util.ExceptionUtil;
import com.fh.util.RulesCheckedException;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app端集散中心(订单列表在AppOrderController包里)
 * 类名称：com.fh.controller.app.transitcenter.AppTransitCenterController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:41:05   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:41:05   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/appTransitCenter")
public class AppTransitCenterController extends BaseController {
	@Autowired
	private OrderMainService orderMainService;
	@Autowired
    private AppTransitCenterService appTransitCenterService;
	
	/**
	 * @DESC 集散中心卸车扫描加载列表功能
	 * @author zhangjj
	 * @history 2018年03月04日
	 */
	@ResponseBody
    @RequestMapping(value="/unloadScan", produces = "application/json;charset=UTF-8" )
    public String unloadScan( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue()))
        {
            return new Gson().toJson( rtBean );
        }
        
        AppUnloadScanReqData appUnloadScanReqData = new Gson().fromJson( reqParm.getData().toString(), AppUnloadScanReqData.class );
        if(appUnloadScanReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
        
        try {
             List<AppUnloadScanResData> resList = orderMainService.findAppOrderBaggageid(appUnloadScanReqData);
             
             // 等待对方申请卸车校验
             for (AppUnloadScanResData res : resList) {
          	   ExceptionUtil.isTrue(ORDER_STATUS.WAITINGUNLOAD.getValue().equals(res.getStatus()), "等待对方申请卸车");
             }
             
             rtBean.setJsonData(new Gson().toJson( resList )); 
             rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
             rtBean.setMsg("查询成功");
             return new Gson().toJson( rtBean );
        } catch (RulesCheckedException e) {
		    logger.error("集散中心卸车扫描加载列表校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error( "集散中心卸车扫描加载列表非预期异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
	
	/**
     * @DESC 集散中心卸车完毕
     * @author zhangjj
     * @history 2018年03月04日
     */
    @ResponseBody
    @RequestMapping(value="/unloadDone", produces = "application/json;charset=UTF-8" )
    public String unloadDone( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue()))
        {
            return new Gson().toJson( rtBean );
        }
        
        AppUnloadDonReqData reqData = new Gson().fromJson( reqParm.getData().toString(), AppUnloadDonReqData.class );
        if(reqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
        
        try {
        	// 校验
        	ExceptionUtil.checkNotCollectNotEmpty(reqData.getOrderidList(), "订单Id列表不能为空");
        	ExceptionUtil.checkBoolean(reqData.getUnloadBeforeRoleid() == null && reqData.getUnloadBeforeRoleid() == 0, "取派员id不能为空");
        	ExceptionUtil.checkBoolean(reqData.getUnloadLaterRoleid() == null && reqData.getUnloadLaterRoleid() == 0, "集散中心人员id不能为空");
        	ExceptionUtil.checkNotEmpty(reqData.getDestaddress(), "集散中心id为空");
        	
        	appTransitCenterService.cbNnloadDone(reqData);
        	
             rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
             rtBean.setMsg("卸货完毕");
             return new Gson().toJson( rtBean );
             
        } catch (RulesCheckedException e) {
		    logger.error("集散中心卸车完毕校验异常:" + e.getMessage());
            rtBean.setCode(APP_RESPONSE_CODE.CHECK.getValue());
            rtBean.setMsg("校验异常：" + e.getMessage());
            return new Gson().toJson(rtBean);
        } catch (Exception e) {
            logger.error( "卸车完毕非预期异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
	
    
    /**
     * @DESC 集散中心查询订单接口
     * @author zhangjj
     * @history 2018年03月04日
     */
    @ResponseBody
    @RequestMapping(value="/transitFindOrderList", produces = "application/json;charset=UTF-8" )
    public String transitFindOrderList( @RequestBody AppRequestBean reqParm ){
        AppResponseBean rtBean = doValidate( reqParm );
        if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue()))
        {
            return new Gson().toJson( rtBean );
        }
        
        TransitOrderReqData transitOrderReqData = new Gson().fromJson( reqParm.getData().toString(), TransitOrderReqData.class );
        if(transitOrderReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
        
        try {
            
            List<TransitOrderResData> cbTransitFindOrderList = null;
            
            if(QUERYSCOPE_TYPE.CITYSCOPE.getValue().equals(transitOrderReqData.getQueryScope())) {
                 cbTransitFindOrderList = orderMainService.cbTransitFindOrderListCityScope(transitOrderReqData);
            }
            
            if(QUERYSCOPE_TYPE.COUNTERCENTER.getValue().equals(transitOrderReqData.getQueryScope())) {
                 cbTransitFindOrderList = orderMainService.cbTransitFindOrderListAirPortScope(transitOrderReqData);
            }
             
             rtBean.setJsonData(new Gson().toJson(cbTransitFindOrderList));
             rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
             rtBean.setMsg("集散中心查询订单信息完毕");
             return new Gson().toJson( rtBean );
        } catch (Exception e) {
            logger.error( "集散中心查询订单信息异常:"+ e.getLocalizedMessage() );
            rtBean.setCode( APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg( "非预期异常，请联系IT" );
            return new Gson().toJson( rtBean );
        }
    }
	
}
