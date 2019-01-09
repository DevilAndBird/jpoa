package com.fh.controller.system.sheetdetail;

import java.io.PrintWriter;
import java.util.List;

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
import com.fh.entity.system.Sheet_detail;
import com.fh.entity.system.WorkFlowSheet;
import com.fh.service.system.sd.SheetDetailService;
import com.fh.service.system.workflowsheet.WorkFlowSheetService;
import com.fh.util.PageData;

/**
 * 
 *@Title:SheetDetailController.java
 *@Package:com.fh.controller.system.sheetdetail
 *@Description:SheetInfo的从表，存放具体的数据
 *@author:唐启铭
 *@date:2016年7月7日 下午3:00:17
 */
@Controller
@RequestMapping(value = "/sheetDetail")
public class SheetDetailController extends BaseController {

	@Resource(name = "sheetDetailService")
	private SheetDetailService sheetdetailservice;
	@Resource(name = "workFlowSheetService")
	private WorkFlowSheetService workflowsheetservice;

	/*
	 * 新增sheetdetail
	 */
	@RequestMapping(value = "/addsd")
	public ModelAndView addsd(@RequestParam("fid") String fid,
			@RequestParam("type") String type,
			@RequestParam("notes") String notes,
			@RequestParam("isnull") String isnull,
			@RequestParam("label") String label,
			@RequestParam("itemcode") String itemcode,
			@RequestParam("size") String size) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_detail sd = new Sheet_detail();
		sd.setFid(Integer.parseInt(fid));
		sd.setNotes(notes);
		sd.setLabel(label);
		sd.setItemcode(itemcode);
		sd.setSize(Integer.parseInt(size));
		sd.setType(Integer.parseInt(type));
		sd.setIsnull(Integer.parseInt(isnull));
		if (type.equals("1")) {
			sd.setTypename("datacontrol");
		} else if (type.equals("2")) {
			sd.setTypename("selectcontrol");
		} else if (type.equals("3")) {
			sd.setTypename("textareacontrol");
		} else {
			sd.setTypename("textcontrol");
		}
		sheetdetailservice.save(sd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/*
	 * 获取修改页面的值
	 */
	@RequestMapping(value = "/goupdate")
	public ModelAndView goupdate(@RequestParam("id") String id)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_detail sd = sheetdetailservice.findObjectById(Integer.parseInt(id));
		mv.addObject("sd", sd);
		mv.setViewName("system/sd/sd_edit2");
		return mv;
	}

	/*
	 * 删除WorkDetail的数值
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/deletesd")
	public void deletesd(@RequestParam("id") String id,
			@RequestParam("fidtemp") String fidtemp, PrintWriter out)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		sheetdetailservice.delete(Integer.parseInt(id));
		out.write("success");
		out.flush();
		out.close();
	}

	/*
	 * 分页
	 */
	@RequestMapping(value = "/sdlistPage")
	public ModelAndView sdlistPage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer fid = Integer.parseInt(pd.getString("fid"));
		pd.put("fid", fid);
		page.setPd(pd);
		List<PageData> userList = sheetdetailservice.sdlistPage(page);
		mv.setViewName("system/sd/sd_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("fidtemp", fid);
		return mv;
	}

	/*
	 * 跳到生成的页面
	 */
	@RequestMapping(value = "/create")
	public ModelAndView create(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer fid = Integer.parseInt(pd.getString("fid"));
		pd.put("fid", fid);
		page.setPd(pd);
		List<PageData> sheetdetailList = sheetdetailservice.sdlistPage(page);
		mv.setViewName("system/sd/create");
		List<WorkFlowSheet> sheetList = workflowsheetservice.findSheetname(fid);
		System.out.println(sheetList);
		mv.addObject("userList", sheetdetailList);
		mv.addObject("sheetList", sheetList);
		mv.addObject("pd", pd);
		mv.addObject("fidtemp", fid);
		return mv;
	}

	/*
	 * 生成最后跳转的页面,到生成页面刷新，此方法主要是测试使用下跨类session取值
	 */
	@RequestMapping(value = "/createend")
	public ModelAndView createend(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String fidtemp = (String) session.getAttribute("fid");
		Integer fid = Integer.parseInt(fidtemp);
		pd.put("fid", fid);
		page.setPd(pd);
		List<PageData> userList = sheetdetailservice.sdlistPage(page);
		mv.setViewName("system/sd/create");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("fidtemp", fid);
		return mv;
	}

	/*
	 * 预览的方法
	 */
	@RequestMapping(value = "/browse")
	public ModelAndView listUsers1(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer fid = Integer.parseInt(pd.getString("fid"));
		pd.put("fid", fid);
		page.setPd(pd);
		List<PageData> userList = sheetdetailservice.sdlistPage(page);
		mv.setViewName("system/sd/browse");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		mv.addObject("fidtemp", fid);
		return mv;
	}

	/*
	 * 跳保存新增的页面
	 */
	@RequestMapping(value = "/gosave")
	public ModelAndView saveU6(@RequestParam("fidtemp") String fidtemp)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("fidtemp", fidtemp);
		mv.setViewName("system/sd/sd_add");
		return mv;
	}

	/*
	 * 保存修改
	 */
	@RequestMapping(value = "/updatesd")
	public ModelAndView saveU7(@RequestParam("id") String id,
			@RequestParam("type") String type,
			@RequestParam("notes") String notes,
			@RequestParam("isnull") String isnull,
			@RequestParam("label") String label,
			@RequestParam("itemcode") String itemcode,
			@RequestParam("size") String size) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_detail sd = new Sheet_detail();
		sd.setId(Integer.parseInt(id));
		sd.setNotes(notes);
		sd.setLabel(label);
		sd.setItemcode(itemcode);
		sd.setSize(Integer.parseInt(size));
		sd.setType(Integer.parseInt(type));
		sd.setIsnull(Integer.parseInt(isnull));
		if (type.equals("1")) {
			sd.setTypename("timecontrol");
		} else if (type.equals("2")) {
			sd.setTypename("selectcontrol");
		} else if (type.equals("3")) {
			sd.setTypename("textareacontrol");
		} else {
			sd.setTypename("textcontrol");
		}
		sheetdetailservice.update(sd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
