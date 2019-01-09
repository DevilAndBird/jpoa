package com.fh.controller.system.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.log.LogService;
import com.fh.util.PageData;

/**
 * 
 *@Title:LogController.java
 *@Package:com.fh.controller.system.log
 *@Description:这是登录日志控制层
 *@author:唐启铭
 *@date:2016年7月7日 上午1:03:55
 */
@Controller
@RequestMapping(value="/log01")
public class LogController extends BaseController {
		
	@Resource(name="logeservice")
	private LogService logeservice;

	@RequestMapping(value="/show1")
	public ModelAndView listloginfo(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>list=logeservice.loglistPage(page);
		mv.addObject("list",list);
		mv.addObject("pd", pd);
		mv.setViewName("system/log/log_list");
		return mv;
	}
	
	
	
}
