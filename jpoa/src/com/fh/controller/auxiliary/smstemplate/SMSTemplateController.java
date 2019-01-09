package com.fh.controller.auxiliary.smstemplate;

import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.auxiliary.smstemplate.SMSTemplate;
import com.fh.service.auxiliary.smstemplate.SMSTemplateService;
import com.fh.util.PageData;
import com.mysql.jdbc.StringUtils;
/**
 * 短信模板
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author 木桐
 * 短信模板
 */
@Controller
@RequestMapping(value="smstemplate")
public class SMSTemplateController extends BaseController{

	@Autowired
	private SMSTemplateService smsTemplateService;
	
	
	/**
	 * 返回首页
	 * 木桐
	 * 2018年1月22日11:07:27
	 */
	@RequestMapping(value="/backPage")
	public String backPage( Page page ){
		return "redirect:http:listSmsTemplate";
	}
	
	/**
	 * 删除模板
	 * 木桐
	 * 2018年1月10日14:57:50
	 */
	@RequestMapping(value="/delTemplate")
	public void delTemplate(PrintWriter out){
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			smsTemplateService.delTemplate(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:34:44
	 */
	@RequestMapping(value="/chgStatus")
	public String changeStatus(){
		PageData pd = this.getPageData();
		try {
			smsTemplateService.changeStatus(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:listSmsTemplate";
	}
	
	/**
	 * 修改短信模板
	 * 木桐
	 * 2018年1月10日11:56:30
	 */
	@RequestMapping(value="/updateSms")
	public String updateSmsTemplate(){
		PageData pd = this.getPageData();
		smsTemplateService.updateSmsTemplate(pd);
		return "redirect:http:listSmsTemplate";
	}
	
	/**
	 * 判断smscode是否存在
	 * 木桐
	 * 2018年1月9日20:28:00
	 */
	@RequestMapping(value="/checkCode")
	public String checkCode(){
		PageData pd = this.getPageData();
		SMSTemplate smstemplate = smsTemplateService.checkCode(pd.getString("smscode"));
		if( null != smstemplate ){
			return "1";
		}else{
			return "2";
		}
	}
	
	/**
	 * 新增短信模板
	 * 木桐
	 * 2018年1月9日16:26:39
	 */
	@RequestMapping(value="/saveSms")
	public String saveSmsTemplate(){
		PageData pd = this.getPageData();
		smsTemplateService.saveTemplate(pd);
		return "redirect:http:listSmsTemplate";
	}
	
	/**
	 * 跳转至新增修改页面
	 * 木桐
	 * 2018年1月8日17:54:54
	 */
	@RequestMapping(value="/goback")
	public ModelAndView goback(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auxiliary/smsTemplate/smstemplate_msg");
		PageData pd = this.getPageData();
		if( !StringUtils.isEmptyOrWhitespaceOnly( pd.getString("id") ) ){
			SMSTemplate smsTemplate = smsTemplateService.queryById(pd);
			if( null != smsTemplate ){
				mv.addObject("smsTemplate", smsTemplate);
			}
		}
		return mv;
	}
	
	/**
	 * 短信模板列表
	 * 木桐
	 * 2018年1月8日10:40:55
	 */
	@RequestMapping(value="listSmsTemplate")
	public ModelAndView listSmsTemplate( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auxiliary/smsTemplate/smstemplate_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		try {
			List<PageData> smsTemplateList = smsTemplateService.listSmsTemplate(page);
			if( null != smsTemplateList && smsTemplateList.size()>0 ){
				mv.addObject("smsTemplateList", smsTemplateList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	
	
	
}
