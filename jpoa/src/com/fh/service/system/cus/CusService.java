package com.fh.service.system.cus;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Cus;
import com.fh.util.PageData;
@Service("cusservice")
public class CusService {
	@Resource(name = "daoSupport")
	private  DaoSupport dao;
	
	@SuppressWarnings("all")
	public List<PageData> listAllUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("cusmapper.cuslistPage", page);
	}
	
	public void saveIP(PageData pd)throws Exception{
		dao.update("cusmapper.addcus", pd);
	}
	
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("cusmapper.updatecus", pd);
	}
	
	public void deleteU(Integer id)throws Exception{
		dao.delete("cusmapper.deletecus", id);
	}
	
	public Cus listonecus(int id)throws Exception{
		return  (Cus)dao.findForObject("cusmapper.cusfindone", id);
	}
	
}
