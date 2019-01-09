package com.fh.service.customer;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

/** 
 * 说明： 验证码表
 * 创建人：
 * 创建时间：2018-04-13
 * @version
 */
@Service("valicodeService")
public class ValiCodeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/** 新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("valicodemapper.save", pd);
	}
	
}

