package com.fh.service.ConfigCenter;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
public class BusinessHoursService {
	
	@Resource
	private DaoSupport dao;
	
	/**
	 * @desc 营业时间列表查询
	 * @auther tqm
	 * @date 2018年10月26日
	 */
	@SuppressWarnings("all")
	public List<PageData> getBusinessHourslistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("BusinessHoursMapper.findBusinessHourslistPage", page);
	}
	
	/**
	 * @desc 保存营业时间
	 * @auther tqm
	 * @date 2018年10月26日
	 */
	@SuppressWarnings("all")
	public void insertBusinessHours(PageData pd) throws Exception {
		dao.save("BusinessHoursMapper.insertBusinessHours", pd);
	}
	
	/**
	 * @desc 删除营业时间
	 * @auther tqm
	 * @date 2018年10月26日
	 */
	@SuppressWarnings("all")
	public void deleteBusinessHours(PageData pd) throws Exception {
		dao.delete("BusinessHoursMapper.deleteBusinessHours", pd);
	}
	
	/**
	 * @desc 修改营业时间
	 * @auther tqm
	 * @date 2018年10月26日
	 */
	@SuppressWarnings("all")
	public void updateBusinessHours(PageData pd) throws Exception {
		dao.update("BusinessHoursMapper.updateBusinessHours", pd);
	}
	
	
}
