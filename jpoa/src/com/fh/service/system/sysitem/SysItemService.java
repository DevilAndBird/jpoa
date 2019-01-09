package com.fh.service.system.sysitem;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.SysItem;

@Service("sysItemService")
public class SysItemService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<SysItem> listAllItems(Page page) throws Exception {
		return (List<SysItem>) dao.findForList("SysItemMapper.listItemsPage", page);
	}
	/**
	 * 根据itemcode查询sys_item列表
	 * @author cys
	 * 2016年6月17日17:04:01
	 * 
	 * @param itemcode
	 * @return
	 * @throws Exception
	 */
	public List<SysItem> listItemsByItemCode(String itemcode) throws Exception {
		return (List<SysItem>) dao.findForList("SysItemMapper.listItemsByItemCode", itemcode );
	}
}
