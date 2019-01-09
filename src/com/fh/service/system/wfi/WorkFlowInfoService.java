package com.fh.service.system.wfi;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Sheet_info;
import com.fh.entity.system.WorkFlowInfo;
import com.fh.util.PageData;

/**
 * 
 * @Title:WorkFlowInfoService.java
 * @Package:com.fh.service.system.wfi
 * @Description:流程审核是根据数据库来设计，数据库采用主分表，这是主表的Service
 * @author:唐启铭
 * @date:2016年7月7日 下午2:24:19
 */
@Service("workFlowInfoService")
public class WorkFlowInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("all")
	/*
	 * 分页查询
	 */
	public List<PageData> wfilistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(	"workFlowInfoMapper.wfilistPage", page);
	}

	/*
	 * 保存新增
	 */
	public void save(WorkFlowInfo wfi) throws Exception {
		dao.update("workFlowInfoMapper.addwfi", wfi);
	}

	public void update(WorkFlowInfo wfi) throws Exception {
		dao.update("workFlowInfoMapper.updatewfi", wfi);
	}

	/*
	 * 删除数据
	 */
	public void delete(Integer id) throws Exception {
		dao.delete("workFlowInfoMapper.deletewfi", id);
	}

	/*
	 * 查询WorkFlowInfo关于Sheet_Info的数据
	 */
	public Sheet_info findOneById(Integer id) throws Exception {
		return (Sheet_info) dao.findForObject("workFlowInfoMapper.wfifindone",id);
	}

	/*
	 * 查询WorkFlowInfo所有数据
	 */
	@SuppressWarnings("unchecked")
	public List<WorkFlowInfo> queryList() throws Exception {
		return (List<WorkFlowInfo>) dao.findForList(	"workFlowInfoMapper.wfifindall", null);
	}

}
