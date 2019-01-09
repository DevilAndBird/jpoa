package com.fh.service.auxiliary.smstemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
public class SmsIdentifyingService {

	@Autowired
	private DaoSupport dao;
	
	/**
	 * 短信验证码列表查询
	 * 木桐
	 * 2018年1月29日15:34:08
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> smsIdentifyList( Page page ) throws Exception{
		
		PageData pd = page.getPd();
		String mobile = pd.getString("mobile");
		if( null != mobile ){
			pd.put("mobile", mobile.replace(" ", ""));
		}
		page.setPd(pd);
		return (List<PageData>)dao.findForList("SmsIdentifyingInfoMapper.smsIdentifylistPage", page);
	}
}
