package com.fh.service.system.auditinfo;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.AuditInfo;
import com.fh.util.PageData;

/**
 * 
 * @Title:AuditInfoService.java
 * @Package:com.fh.service.system.auditinfo
 * @Description:这是流程AuditInfoService
 * @author:唐启铭
 * @date:2016年7月5日 上午00:30:23
 */
@Service("auditInfoService")
public class AuditInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@SuppressWarnings("all")
	
	/*
	 * 查询所有
	 */
	public List<AuditInfo> listPage(String auditby) throws Exception {
		return (List<AuditInfo>) dao.findForList("auditInfoMapper.auditlist",auditby);
	}

	/*
	 * 保存新增
	 */
	public void save(AuditInfo ai) throws Exception {
		dao.update("auditInfoMapper.addaim", ai);
	}

	/*
	 * 查找单行数据
	 */
	public boolean findObjectByWorkid(Page page) throws Exception {
		AuditInfo ai = (AuditInfo) dao.findForObject("auditInfoMapper.findlastaudit", page);
		System.out.println(ai);
		if (ai.getStatus() == 2) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 保存修改
	 */
	public void update(AuditInfo ai) throws Exception {
		dao.update("auditInfoMapper.update", ai);
	}

	/*
	 * 判断审核是否结束
	 */
	public boolean judgementEnd(AuditInfo ai) throws Exception {
		@SuppressWarnings("unchecked")
		List<AuditInfo>aiList = (List<AuditInfo>) dao.findForList("auditInfoMapper.judgementend", ai);
		if (aiList.size()>0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * auditlist分页查询
	 */
	public List<PageData> ailistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("auditInfoMapper.ailistPage",page);
	}
}
