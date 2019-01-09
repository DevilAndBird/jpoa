package com.fh.service.area;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.area.AreaProvInfo;
import com.fh.util.PageData;

@Service
public class AreaProvService {
	
	@Autowired
	private DaoSupport dao;
	
	/**
	 * 审核name是否存在
	 * 木桐
	 * 2018年1月17日15:50:43
	 * @throws Exception 
	 */
	public AreaProvInfo checkName( PageData pd ) throws Exception{
		
		return (AreaProvInfo)dao.findForObject("AreaProvInfoMapper.checkName", pd);
	}
	
	
	/**
	 * 删除省份
	 * @throws Exception 
	 */
	public void delProv( PageData pd ) throws Exception{
		dao.delete("AreaProvInfoMapper.delProv", pd);
	}
	
	/**
	 * 修改省份
	 * @throws Exception 
	 */
	public void updateProv( PageData pd ) throws Exception{
		dao.save("AreaProvInfoMapper.updateProv", pd);
	}
	
	
	/**
	 * 新增省份
	 * @throws Exception 
	 */
	public void insertProv( PageData pd ) throws Exception{
		dao.save("AreaProvInfoMapper.insertProv", pd);
	}
	
	/**
	 * 审核ID是否存在
	 * 木桐
	 * 2018年1月17日15:50:43
	 * @throws Exception 
	 */
	public AreaProvInfo checkID( PageData pd ) throws Exception{
		return (AreaProvInfo)dao.findForObject("AreaProvInfoMapper.checkID", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年1月10日14:35:53
	 * @throws Exception 
	 */
	public void changeStatus( PageData pd ) throws Exception{
		
		dao.update("AreaProvInfoMapper.changeStatus", pd);
		
	}
	
	
	/**
	 * 省级列表查询
	 * 木桐
	 * 2018年1月16日11:53:05
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> provList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String name = pd.getString("name");
		if( null != name ){
			pd.put("name", name.replace(" ", ""));
		}
		List<PageData> provList = (List<PageData>)dao.findForList("AreaProvInfoMapper.provlistPage", page);
		return provList;
	}
	
	/**
	 * 省级所有
	 * 木桐
	 * 2018年1月18日16:38:32
	 */
	@SuppressWarnings("all")
	public List<PageData> findAll() throws Exception{
		List<PageData> provList = (List<PageData>)dao.findForList("AreaProvInfoMapper.findAll", null);
		return provList;
	}
}
