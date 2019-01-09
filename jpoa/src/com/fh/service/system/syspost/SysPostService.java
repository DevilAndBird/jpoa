package com.fh.service.system.syspost;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.SysPost;
import com.fh.entity.system.User;

/**
 * 
 * @Title:SysPostService.java
 * @Package:com.fh.service.system.syspost
 * @Description:这是岗位管理的Service层
 * @author:唐启铭
 * @date:2016年7月7日 下午2:38:48
 */
@Service("sysPostService")
public class SysPostService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@SuppressWarnings("all")
	/*
	 * 查询所有的数据
	 */
	public List<SysPost> syspostlistPage(Page page) throws Exception {
		return (List<SysPost>) dao.findForList("sysPostMapper.findall", null);
	}

	/*
	 * 查询单行数据
	 */
	public SysPost listOneSP(Integer id) throws Exception {
		return (SysPost) dao.findForObject("sysPostMapper.findone", id);
	}

	/*
	 * 判断下拉菜单是否正确
	 */
	public boolean getSelectBoolean(Integer postid) throws Exception {
		List<User> list = (List<User>) dao.findForList("UserXMapper.getboolean", postid);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 新增的方法
	 */
	public void save(SysPost sp) throws Exception {
		dao.update("sysPostMapper.addsyspost", sp);
	}

	/*
	 * 修改的方法
	 */
	public void update(SysPost sp) throws Exception {
		dao.update("sysPostMapper.updatesyspost", sp);
	}

	/*
	 * 删除的方法
	 */
	public void delete(Integer id) throws Exception {
		dao.delete("sysPostMapper.deletesyspost", id);
	}

}
