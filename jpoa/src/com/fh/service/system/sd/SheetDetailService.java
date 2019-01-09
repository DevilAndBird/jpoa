package com.fh.service.system.sd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Sheet_detail;
import com.fh.util.PageData;
/**
 * 
 *@Title:SheetDetailService.java
 *@Package:com.fh.service.system.sd
 *@Description:这是SheetDetail的从表
 *@author:唐启铭
 *@date:2016年7月7日 下午2:55:03
 */
@Service("sheetDetailService")
public class SheetDetailService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("all")
	/*
	 * 分页查询
	 */
	public List<PageData> sdlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("sheetDetailMapper.sdlistPage", page);
	}

	/*
	 * SheetInfo通过id查询在从表有多少相关数据
	 */
	public List<Sheet_detail> findListSD(Integer id) throws Exception {
		return (List<Sheet_detail>) dao.findForList("sheetDetailMapper.sdfindlist", id);
	}

	/*
	 * 保存新增
	 */
	public void save(Sheet_detail sd) throws Exception {
		dao.update("sheetDetailMapper.addsd", sd);
	}

	/*
	 * 保存修改
	 */
	public void update(Sheet_detail sd) throws Exception {
		dao.update("sheetDetailMapper.updatesd", sd);
	}

	/*
	 * 删除
	 */
	public void delete(Integer id) throws Exception {
		dao.delete("sheetDetailMapper.deletesd", id);
	}

	/*
	 * 查询单行数据
	 */
	public Sheet_detail findObjectById(int id) throws Exception {
		return (Sheet_detail) dao.findForObject("sheetDetailMapper.sdfindone", id);
	}

}
