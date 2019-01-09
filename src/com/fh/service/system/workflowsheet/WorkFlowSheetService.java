package com.fh.service.system.workflowsheet;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.WorkFlowSheet;
import com.fh.util.PageData;

/**
 * 
 * @Title:WorkFlowSheetService.java
 * @Package:com.fh.service.system.workflowsheet
 * @Description: WorkFlowInfo和SheetInfo关系表的Service层
 * @author:唐启铭
 * @date:2016年7月4日 下午8:53:51
 */
@Service("workFlowSheetService")
public class WorkFlowSheetService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@SuppressWarnings("all")
	
	/*
	 * 分页查询
	 */
	public List<PageData> wfslistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("workFlowSheetMapper.wfslistPage", page);
	}

	/*
	 * 查询单行数据
	 */
	public WorkFlowSheet findObject(Integer id) throws Exception {
		return (WorkFlowSheet) dao.findForObject("workFlowSheetMapper.findone",id);
	}

	/*
	 * 保存新增
	 */
	public void save(WorkFlowSheet wfs) throws Exception {
		dao.update("workFlowSheetMapper.addwfs", wfs);
	}

	/*
	 * 修改的方法
	 */
	public void update(WorkFlowSheet wfs) throws Exception {
		dao.update("workFlowSheetMapper.updatewfs", wfs);
	}

	/*
	 *  删除的方法
	 */
	public void delete(Integer id) throws Exception {
		dao.delete("workFlowSheetMapper.deletewfs", id);
	}
	
	/*
	 * 在WorkFlowSheet查询sheetname
	 */
	public List<WorkFlowSheet> findSheetname(Integer sheetid) throws Exception {
		return (List<WorkFlowSheet>)dao.findForList("workFlowSheetMapper.findsheetname", sheetid);
	}

}
