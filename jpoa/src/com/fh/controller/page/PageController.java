package com.fh.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("page")
public class PageController {
	/**
	 * 页面跳转
	 * */
	@RequestMapping(value="{pageName}",method=RequestMethod.GET)
	private String toPage(@PathVariable("pageName")String pageName) {
		String name = "kuaidi/drive/"+pageName;
		return name;
	}
}
