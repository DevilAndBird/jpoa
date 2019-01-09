package com.fh.controller.system.sheetinfo;

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
import com.fh.entity.system.Sheet_info;
import com.fh.entity.system.User;
import com.fh.service.system.si.SheetInfoService;
import com.fh.util.Const;
import com.fh.util.PageData;

/**
 * 
 * @Title:SIController.java
 * @Package:com.fh.controller.system.si
 * @Description:这是SheetInfo控制层，本层生成功能和生成页面统一用create单词替代
 * @author:唐启铭
 * @date:2016年7月4日 下午5:36:20
 */
@Controller
@RequestMapping(value = "/sheetInfo")
public class SheetInfoController extends BaseController {

	@Resource(name = "sheetInfoService")
	private SheetInfoService sheetinfoservice;

	/*
	 * 保存的方法
	 */
	@RequestMapping(value = "/save")
	public ModelAndView save(@RequestParam("sheetname") String sheetname,
			@RequestParam("type") String type,
			@RequestParam("notes") String notes) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_info si = new Sheet_info();
		si.setSheetname(sheetname);
		si.setType(Integer.parseInt(type));
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String userid = user.getUSER_ID();
		String username = user.getUSERNAME();
		si.setUsername(username);
		si.setUserid(userid);
		if (type.equals("1")) {
			si.setTypename("work");
		} else if (type.equals("2")) {
			si.setTypename("finance");
		} else {
			si.setTypename("3");
		}
		si.setNotes(notes);
		sheetinfoservice.save(si);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/*
	 * 获取修改页面具体的值
	 */
	@RequestMapping(value = "/goupdate")
	public ModelAndView goupdate(@RequestParam("id") String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_info sd = sheetinfoservice.findObjectById(Integer.parseInt(id));
		mv.addObject("sd", sd);
		mv.setViewName("system/si/si_edit");
		return mv;
	}

	/*
	 * 删除
	 */
	@RequestMapping(value = "/deletesheet")
	public ModelAndView deletesheet(@RequestParam("id") String id) throws Exception {
		ModelAndView mv = this.getModelAndView();
		sheetinfoservice.delete(Integer.parseInt(id));
		mv.addObject("msg", "删减成功");
		mv.setViewName("redirect:/sheetInfo/silistPage");
		return mv;
	}

	/*
	 * 分页
	 */
	@RequestMapping(value = "/silistPage")
	public ModelAndView silistPage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sheetname = pd.getString("sheetname");
		if (null != sheetname && !"".equals(sheetname)) {
			sheetname = sheetname.trim();
			pd.put("sheetname", sheetname);
		}
		String typename = pd.getString("typename");
		if (null != typename && !"".equals(typename)) {
			typename = typename.trim();
			pd.put("typename", typename);
		}
		page.setPd(pd);
		List<PageData> userList = sheetinfoservice.silistPage(page);
		mv.setViewName("system/si/si_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		return mv;
	}

	/*
	 * 跳转到分页(生成)页面
	 */
	@RequestMapping(value = "/gocreatePage")
	public ModelAndView gocreatePage(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String sheetname = pd.getString("sheetname");
		if (null != sheetname && !"".equals(sheetname)) {
			sheetname = sheetname.trim();
			pd.put("sheetname", sheetname);
		}
		String typename = pd.getString("typename");
		if (null != typename && !"".equals(typename)) {
			typename = typename.trim();
			pd.put("typename", typename);
		}
		page.setPd(pd);
		List<PageData> userList = sheetinfoservice.silistPage(page);
		mv.setViewName("tablesystem/create_list");
		mv.addObject("userList", userList);
		mv.addObject("pd", pd);
		return mv;
	}

	/*
	 *  跳转到新增页面
	 */
	@RequestMapping(value = "/gosave")
	public ModelAndView gosave() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/si/si_add");
		return mv;
	}

	/*
	 * 保存修改
	 */
	@RequestMapping(value = "/updatesi")
	public ModelAndView updatesi(@RequestParam("id") String id,
			@RequestParam("sheetname") String sheetname,
			@RequestParam("type") String type,
			@RequestParam("notes") String notes) throws Exception {
		ModelAndView mv = this.getModelAndView();
		Sheet_info si = new Sheet_info();
		si.setSheetname(sheetname);
		si.setType(Integer.parseInt(type));
		if (type.equals("1")) {
			si.setTypename("work");
		} else if (type.equals("2")) {
			si.setTypename("finance");
		} else {
			si.setTypename("3");
		}
		si.setNotes(notes);
		si.setId(Integer.parseInt(id));
		sheetinfoservice.update(si);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

}
