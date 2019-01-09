package com.fh.controller.auxiliary.invitation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.auxiliary.invitation.InvitationCodeInfo;
import com.fh.service.auxiliary.invitation.InvitationCodeService;
import com.fh.util.PageData;
/**
 * 邀请码管理
 * @author tqm
 * @date 2018年11月5日
 */
/**
 * @author zhangjj
 * 邀请码处理
 */
@Controller
@RequestMapping(value="/invitation")
public class InvitationCodeController extends BaseController{

	@Autowired
	private InvitationCodeService invitationCodeService;
	
	
	/**
	 * 邀请码修改
	 * 木桐
	 * 2018年1月29日14:13:22
	 */
	@ResponseBody
	@RequestMapping(value="/updateInvitation")
	public String update(HttpServletRequest request){
		PageData pd = this.getPageData();
		String modelInvitename = pd.getString("modelInvitename");//邀请人姓名
		try {
			modelInvitename = new String(modelInvitename.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelInvitename", modelInvitename);
			
			InvitationCodeInfo invitationCodeInfo = invitationCodeService.checkInfo(pd);
			if( null != invitationCodeInfo ){
				if( !(invitationCodeInfo.getId()+"").equalsIgnoreCase(pd.getString("modelId")) ){
					return "1";
				}
			}
			invitationCodeService.updateInvitation(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "2";
	}
	
	/**
	 * 邀请码新增
	 * 木桐
	 * 2018年1月29日11:56:56
	 */
	@RequestMapping(value="/addInvitation")
	public String addInvitation(HttpServletRequest request){
		PageData pd =this.getPageData();
		String modelInvitename = pd.getString("modelInvitename");//邀请人姓名
		try {
			modelInvitename = new String(modelInvitename.getBytes("iso-8859-1"),"utf-8");
			pd.put("modelInvitename", modelInvitename);
			
			invitationCodeService.insertInvitation(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http:list";
	}
	
	/**
	 * 邀请码列表查询
	 * 木桐
	 * 2018年1月29日10:59:02
	 */
	@RequestMapping(value="/list")
	public ModelAndView invitationList( Page page ){
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("auxiliary/invitation/invitationcode_list");
		try {
			page.setPd(pd);
			List<PageData> invitationCodeList = invitationCodeService.invitationCodeList(page);
			if( null != invitationCodeList && invitationCodeList.size()>0 ){
				mv.addObject("invitationCodeList", invitationCodeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("pd", pd);
		return mv;
	}
	
}
