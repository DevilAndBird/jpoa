package com.fh.controller.controlplatfrom.dmanlist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.controlplatfrom.dmanlist.DmanListService;
import com.fh.service.delivery.UserDeliveryManService;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 控制台_取派员列表查询统计
 */
@Controller
@RequestMapping(value = "controlplatform_dmanlist")
public class DmanListController extends BaseController {
	@Autowired
	private UserDeliveryManService userDeliveryManService;
	@Autowired
	private DmanListService dmanListService;
	@Autowired
	private BaiDuMapBO baiDuMapBO;
	
	/**
	 * @desc 取派员列表查询
	 * @auther zhangjj
	 * @date 2018年5月14日
	 */
	@ResponseBody
	@RequestMapping(value = "/findDmanList")
	public List<PageData> findDmanList() throws Exception {
		return userDeliveryManService.findDmanList_contralplatform(this.getPageData());
	}
	
}
