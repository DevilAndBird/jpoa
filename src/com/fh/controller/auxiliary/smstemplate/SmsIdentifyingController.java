package com.fh.controller.auxiliary.smstemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.auxiliary.smstemplate.SmsIdentifyingService;
import com.fh.util.PageData;
/**
 *短信验证码
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 短信验证码
 */
@Controller
@RequestMapping(value="/smsidentifying")
public class SmsIdentifyingController extends BaseController{

	@Autowired
	private SmsIdentifyingService smsIdentifyingService;
	
	/**
	 * 短信验证码列表查询呢
	 * 木桐2018年1月29日15:42:29
	 */
	@RequestMapping(value="/list")
	public ModelAndView smsidentifyList( Page page ){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auxiliary/smsTemplate/smsidentifying_list");
		PageData pd = this.getPageData();
		page.setPd(pd);
		
		try {
			List<PageData> smsIdentifyList = smsIdentifyingService.smsIdentifyList(page);
			if( null != smsIdentifyList && smsIdentifyList.size()>0 ){
				mv.addObject("smsIdentifyList", smsIdentifyList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
}
