package com.fh.service.customer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.customer.CusCompliantInfo;
import com.fh.util.PageData;

@Service
public class CusCompliantService {

	@Autowired
	private DaoSupport dao;
	
	/**
	 * 新增客户投诉信息
	 * 陈玉石
	 * 2018年3月4日16:13:46
	 * @throws Exception 
	 */
	public void insert( CusCompliantInfo obj ) throws Exception{
		dao.save( "CusCompliantInfoMapper.insert", obj );
	}
	
	/**
	 * 导出
	 * 木桐
	 * 2018年2月2日11:12:17
	 * @throws Exception 
	 */
	public List<PageData> findAll( PageData pd ) throws Exception{
		return (List<PageData>)dao.findForList("CusCompliantInfoMapper.findAll", pd);
	}
	
	/**
	 * 投诉反馈
	 * 木桐
	 * 2018年2月2日10:31:31
	 * @throws Exception 
	 */
	public List<PageData> compliantList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String cusid = pd.getString("cusid");
		String cusname = pd.getString("cusname");
		String mobile = pd.getString("mobile");
		String beginDate = pd.getString("beginDate");
		String endDate = pd.getString("endDate");
		
		if( null != cusid ){
			pd.put("cusid", cusid.replace(" ", ""));
		}
		if( null != cusname ){
			pd.put("cusname", cusname.replace(" ", ""));
		}	
		if( null != mobile ){
			pd.put("mobile", mobile.replace(" ", ""));
		}
		if( StringUtils.isNotEmpty(beginDate) ){
			pd.put("beginDate", beginDate+" 00:00:00");
		}
		if( StringUtils.isNotEmpty(endDate) ){
			pd.put("endDate", endDate+" 23:59:59");
		}
		page.setPd(pd);
		return (List<PageData>)dao.findForList("CusCompliantInfoMapper.cusCompliantlistPage", page);
	}
	
}
