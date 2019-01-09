package com.fh.service.customer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.h5.H5ContactsReqBean;
import com.fh.util.PageData;

@Service
public class ContactsService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
     * @DESC 新增联系人
     * @author sunqp
     * @history 2018年04月16日
     */
	public void insertContact(PageData pd)throws Exception{
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		dao.save("ContactsMapper.addContact", pd);
	}
	
	/**
     * @DESC 删除联系人
     * @author sunqp
     * @history 2018年04月16日
     */
	public void deleteContactById( Integer contactid ) throws Exception {
        PageData pd = new PageData();
        pd.put( "contactid", contactid );         
        pd.put( "isvalid", ISVALID_TYPE.INVALID.getValue());
        dao.update("ContactsMapper.deleteContactById", pd);
    }
	
	/**
     * @DESC 修改联系人
     * @author sunqp
     * @history 2018年04月16日
     */
	public void updateContactById( PageData pd ) throws Exception {                             
        dao.update("ContactsMapper.updateContactByid", pd);
    }
	
	/**
     * @DESC 通过客户id获取联系人列表
     * @author sunqp
     * @history 2018年04月16日
     */
	@SuppressWarnings("unchecked")
	public List<H5ContactsReqBean> queryContactsListByOpenId(String openid)throws Exception{
		PageData pd = new PageData();
		pd.put( "isvalid", ISVALID_TYPE.VALID.getValue());
		pd.put("openid", openid);
		return (List<H5ContactsReqBean>)dao.findForList("ContactsMapper.queryContactsListByOpenId", pd);
	}
	
}

