package com.fh.service.system.log;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Log;
import com.fh.util.PageData;
@Service("logeservice")
public class LogService {
	@Resource(name = "daoSupport")
	private  DaoSupport dao;
	
	@SuppressWarnings("all")
	public List<PageData> loglistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("LogMapper.loglistPage", page);
	}
	
	public void save(Log lg)throws Exception{
		dao.update("LogMapper.addlog", lg);
	}
	
	
}
