package com.fh.controller.system.syspost;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.SysPost;
import com.fh.service.system.syspost.SysPostService;

/**
 * 
 * @Title:SysPostController.java
 * @Package:com.fh.controller.system.syspost
 * @Description:岗位管理
 * @author:唐启铭
 * @date:2016年7月7日 上午1:38:14
 */
@Controller
@RequestMapping(value = "/sp")
public class SysPostController extends BaseController {

	@Resource(name = "sysPostService")
	private SysPostService syspostservice;

	/*
	 * 跳到岗位页面的，通过json直接携带具体的值
	 */
	@RequestMapping(value = "/goZtree")
	public ModelAndView goZtree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = this.getModelAndView();
		JSONArray ja = new JSONArray();
		List<SysPost> list = syspostservice.syspostlistPage(null);
		for (SysPost sp : list) {
			JSONObject jo = new JSONObject();
			jo.put("id", sp.getId());
			jo.put("fid", sp.getFid());
			jo.put("name", sp.getPostname());
			jo.put("noRemoveBtn", "true");
			ja.add(jo);
		}
		mv.addObject("json", ja);
		mv.setViewName("system/syspost/post");
		return mv;
	}

	/*
	 * 进行新增的方法
	 */
	@RequestMapping(value = "/goAdd")
	public ModelAndView addsyspost(@RequestParam("postFid") String fid,
			@RequestParam("postName") String postname) throws Exception {
		ModelAndView mv = this.getModelAndView();
		SysPost sp = new SysPost();
		sp.setFid(Integer.parseInt(fid));
		sp.setPostname(postname);
		syspostservice.save(sp);
		mv.setViewName("redirect:/sp/goZtree");
		return mv;
	}

	/*
	 * 进行修改的方法
	 */
	@RequestMapping(value = "/goUpdate")
	public ModelAndView updatesyspost(@RequestParam("postid") String id,
			@RequestParam("editpostName") String postname) throws Exception {
		ModelAndView mv = this.getModelAndView();
		SysPost sp = new SysPost();
		sp.setId(Integer.parseInt(id));
		sp.setPostname(postname);
		syspostservice.update(sp);
		mv.setViewName("redirect:/sp/goZtree");
		return mv;
	}

	/*
	 * 进行删除的方法
	 */
	@RequestMapping(value = "/godelete")
	public ModelAndView deletessyspost(@RequestParam("postid") String id)
			throws Exception {
		ModelAndView mv = this.getModelAndView();
		syspostservice.delete(Integer.parseInt(id));
		mv.setViewName("redirect:/sp/goZtree");
		return mv;
	}

}
