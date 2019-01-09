package com.fh.controller.system.cus;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Cus;
import com.fh.service.system.cus.CusService;
import com.fh.util.Const;
import com.fh.util.PageData;

/** 
 * 类名称：UserController
 * 创建人：FH 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/cus01")
public class CusController extends BaseController {
		
	@Resource(name="cusservice")
	private CusService cusservice;

	
	@RequestMapping(value="/show2")
	public ModelAndView saveU1() throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		cusservice.saveIP(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	//获取修改页面的值
	@RequestMapping(value="/show3")
	public ModelAndView saveU2(
			@RequestParam("id")String id
			) throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		Cus cus= cusservice.listonecus(Integer.parseInt(id));
		mv.addObject("cus",cus);
		mv.setViewName("system/cus/cus_edit2");
		return mv;
	}
	
	@RequestMapping(value="/show4")
	public ModelAndView saveU4(
			@RequestParam("id")String id
	    ) throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		cusservice.deleteU(Integer.parseInt(id));
		mv.addObject("msg","删减成功");
		mv.setViewName("redirect:/cus01/cuslistPage");
		return mv;
	}
	
	@RequestMapping(value="/cuslistPage")
	public ModelAndView listUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		page.setPd(pd);
		List<PageData>userList=cusservice.listAllUser(page);
		
		mv.setViewName("system/cus/cus_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		return mv;
	}
	
	@SuppressWarnings("all")
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	
	@RequestMapping(value="/show5")
	public ModelAndView saveU5() throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		mv.addObject("msg","删减成功");
		mv.setViewName("system/notice/notice_add");
		return mv;
	}
	
	@RequestMapping(value="/show6")
	public ModelAndView saveU6() throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		mv.setViewName("system/cus/cus_edit");
		return mv;
	}
	
	// 修改页面
	@RequestMapping(value="/show7")
	public ModelAndView saveU7() throws Exception{
		ModelAndView mv = this.getModelAndView(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		cusservice.updateLastLogin(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
}
