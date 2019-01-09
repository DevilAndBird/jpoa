package com.fh.service.system.si;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Sheet_info;
import com.fh.util.PageData;

/**
 * 
 *@Title:SheetInfoService.java
 *@Package:com.fh.service.system.si
 *@Description:这是自定义表单的主表 SheetInfo的Service层
 *@author:唐启铭
 *@date:2016年7月7日 下午2:49:08
 */
@Service("sheetInfoService")
public class SheetInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@SuppressWarnings("all")
	
	/*
	 * 分页查询
	 */
	public List<PageData> silistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("sheetInfoMapper.silistPage", page);
	}
   
	/*
	 * 查询SheetInfo表所有数据
	 */
	public List<Sheet_info> querySheetInfoList() throws Exception {
		return (List<Sheet_info>) dao.findForList("sheetInfoMapper.sifindall", null);
	}

	/*
	 * 保存新增
	 */
	public void save(Sheet_info si) throws Exception {
		dao.update("sheetInfoMapper.addsi", si);
	}

	/*
	 * 保存修改
	 */
	public void update(Sheet_info si) throws Exception {
		dao.update("sheetInfoMapper.updatesi", si);
	}

	/*
	 * 删除
	 */
	public void delete(Integer id) throws Exception {
		dao.delete("sheetInfoMapper.deletesi", id);
		dao.delete("sheetDetailMapper.deletefid", id);
	}

	/*
	 * 查询单行数据
	 */
	public Sheet_info findObjectById(Integer id) throws Exception {
		return (Sheet_info) dao.findForObject("sheetInfoMapper.sifindone", id);
	}

}
