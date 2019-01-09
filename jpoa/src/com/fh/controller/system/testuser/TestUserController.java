package com.fh.controller.system.testuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.TestUser;
import com.fh.service.system.testuser.TestUserService;

@Controller
@RequestMapping(value="/testuser")
public class TestUserController extends BaseController {

	@Resource(name="testUserService")
	private TestUserService testUserService;
	
	/**
	 * 显示用户列表(tab方式)
	 */
	@RequestMapping(value="/listTestUser")
	public ModelAndView listtabUsers(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
	
		List<TestUser> list  = testUserService.findTestUser(null);			//列出用户列表
		
		mv.setViewName("system/testuser/list");
		mv.addObject("userList", list);
		mv.addObject("msg", "aaaaaaaaaaaaaaaaaaa" );
		
		return mv;
	}
}
