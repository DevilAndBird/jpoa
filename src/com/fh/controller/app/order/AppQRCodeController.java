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
import com.fh.entity.app.order.AppQRCodeReqData;
import com.fh.service.app.AppQRCodeService;
import com.google.gson.Gson;
/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：app二维码接口
 * 类名称：com.fh.controller.app.order.AppQRCodeController     
 * 创建人：tangqm
 * 创建时间：2018年9月26日 下午3:04:38   
 * 修改人：
 * 修改时间：2018年9月26日 下午3:04:38   
 * 修改备注：
 */
@Controller
@RequestMapping(value="/qrCode")
public class AppQRCodeController extends BaseController
{
	@Autowired
	private AppQRCodeService appQRCodeService;
	
 /**
  * 
  * @Title: 
  * @Description: 获取QR码
  * author：tangqm
  * 2018年7月23日
  * @param reqParm
  * @return
  * @throws Exception
  */
	@ResponseBody
	@RequestMapping(value = "/findQRCodeLimitPage", produces = "application/json;charset=UTF-8" )
	public String findQRCodeLimitPage(@RequestBody AppRequestBean reqParm) throws Exception {
		AppResponseBean rtBean = new AppResponseBean();
//		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
//			return new Gson().toJson(rtBean);
//		}
		AppQRCodeReqData appQRCodeReqData = new Gson().fromJson(reqParm.getData().toString(), AppQRCodeReqData.class);
		if(appQRCodeReqData == null) {
            rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
            rtBean.setMsg("QR码生成失败，原因是请求参数转换异常");
            return new Gson().toJson(rtBean);
        }
		try {
			List<String> findQRCodeLimitPage = appQRCodeService.findQRCodeLimitPage(appQRCodeReqData);
			rtBean.setJsonData(new Gson().toJson(findQRCodeLimitPage));
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("QR码生成成功！");
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("扫描异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
	/**
	 * 
	 * @Title: 
	 * @Description: 修改QRCode
	 * author：tangqm
	 * 2018年7月23日
	 * @param reqParm
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateQRcode", produces = "application/json;charset=UTF-8" )
	public String updateQRCode(@RequestBody AppRequestBean reqParm) throws Exception {
//		AppResponseBean rtBean = doValidate(reqParm);
//		if (!rtBean.getCode().equals(APP_RESPONSE_CODE.SUCCESS.getValue())) {
//			return new Gson().toJson(rtBean);
//		}
		AppResponseBean rtBean = new AppResponseBean();
		AppQRCodeReqData appQRCodeReqData = new Gson().fromJson(reqParm.getData().toString(), AppQRCodeReqData.class);
		if(appQRCodeReqData == null) {
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("QR码生成失败，原因是请求参数转换异常");
			return new Gson().toJson(rtBean);
		}
		try {
			List<String> qrcodeList = appQRCodeReqData.getQrcodeList();
			appQRCodeService.update(qrcodeList);
			rtBean.setCode(APP_RESPONSE_CODE.SUCCESS.getValue());
			rtBean.setMsg("QR码生成成功！");
			return new Gson().toJson(rtBean);
		} catch (Exception e) {
			logger.error("扫描异常:" + e.getLocalizedMessage());
			rtBean.setCode(APP_RESPONSE_CODE.FAIL.getValue());
			rtBean.setMsg("非预期异常，请联系IT");
			return new Gson().toJson(rtBean);
		}
	}
	
}
