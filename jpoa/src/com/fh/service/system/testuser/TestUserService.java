package com.fh.service.system.testuser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.TestUser;
import com.fh.util.PageData;

@Service("testUserService")
public class TestUserService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	public List<TestUser> findTestUser(PageData pd)throws Exception{
		List<TestUser> list = (List<TestUser>)dao.findForList("TestUserMapper.getTestUser", null);
		return list;
	}	 
}
