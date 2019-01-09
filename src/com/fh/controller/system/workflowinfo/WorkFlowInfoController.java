package com.fh.controller.system.workflowinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Sheet_info;
import com.fh.entity.system.WorkFlowInfo;
import com.fh.service.system.wfi.WorkFlowInfoService;
import com.fh.util.PageData;

/**
 * 
 *@Title:WFIController.java
 *@Package:com.fh.controller.system.wfi
 *@Description:WorkFlowInfo的CRUD控制层
 *@author:唐启铭
 *@date:2016年7月4日 下午10:48:02
 */
@Controller
@RequestMapping(value = "/wfi")
public class WorkFlowInfoController extends BaseController {

	@Resource(name = "workFlowInfoService")
	private WorkFlowInfoService workFlowInfoService;

	/*
	 * 保存新增
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(@RequestParam("flowname") String flowname,
			@RequestParam("notes") String notes) throws Exception {
		ModelAndView mv = this.getModelAndView();
		WorkFlowInfo wfi = new WorkFlowInfo();
		wfi.setNotes(notes);
		wfi.setFlowname(flowname);
		workFlowInfoService.save(wfi);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/*
	 * 获取修改页面的值
	 */
	@RequestMapping(value = "/goupdate")
	public ModelAndView goupdate(@RequestParam("id") String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/si/si_edit");
		return mv;
	}

	/*
	 * 分页
	 */
	@RequestMapping(value = "/wfilistPage")
	public ModelAndView wfilistPage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String flowname = pd.getString("flowname");
		if (null != flowname && !"".equals(flowname)) {
			flowname = flowname.trim();
			pd.put("flowname", flowname);
		}
		page.setPd(pd);
		List<PageData> userList = workFlowInfoService.wfilistPage(page);
		mv.setViewName("system/wfi/wfi_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		return mv;
	}

	/*
	 * 跳转到保存页面
	 */
	@RequestMapping(value = "/gosave")
	public ModelAndView gosave() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/wfi/wfi_add");
		return mv;
	}

	/*
	 * 保存修改的值
	 */
	@RequestMapping(value = "/updatesi")
	public ModelAndView updatesi(@RequestParam("id") String id,
			@RequestParam("sheetname") String sheetname,
			@RequestParam("type") String type,
			@RequestParam("notes") String notes,
			@RequestParam("userid") String userid,
			@RequestParam("username") String username) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_info si = new Sheet_info();
		si.setSheetname(sheetname);
		si.setUsername(username);
		si.setType(Integer.parseInt(type));
		si.setUserid(userid);
		if (type.equals("1")) {
			si.setTypename("work");
		} else if (type.equals("2")) {
			si.setTypename("finance");
		} else {
			si.setTypename("3");
		}
		si.setNotes(notes);
		si.setId(Integer.parseInt(id));
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
