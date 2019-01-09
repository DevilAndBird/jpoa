package com.fh.service.auxiliary.invitation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.auxiliary.invitation.InvitationCodeInfo;
import com.fh.util.PageData;

@Service
public class InvitationCodeService {

	@Autowired
	private DaoSupport dao;

	/**
	 * 邀请码核对
	 * 木桐
	 * 2018年1月29日14:16:37
	 * @throws Exception 
	 */
	public InvitationCodeInfo checkInfo( PageData pd ) throws Exception{
		return (InvitationCodeInfo)dao.findForObject("InvitationCodeInfoMapper.checkInfo", pd);
	}
	
	/**
	 * 邀请码修改
	 * 木桐
	 * 2018年1月29日14:16:02
	 * @throws Exception 
	 */
	public void updateInvitation( PageData pd ) throws Exception{
		dao.update("InvitationCodeInfoMapper.updateInvitation", pd);
	}

	/**
	 * 邀请码新增
	 * 木桐
	 * 2018年1月29日11:49:10
	 * @throws Exception 
	 */
	public void insertInvitation( PageData pd ) throws Exception{
		dao.save("InvitationCodeInfoMapper.insertCode", pd);
	}
	
	
	/**
	 * 邀请码列表查询
	 * 木桐
	 * 2018年1月29日10:50:31
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public List<PageData> invitationCodeList( Page page ) throws Exception{
		PageData pd = page.getPd();
		String invitecode = pd.getString("invitecode");
		if( null != invitecode ){
			pd.put("invitecode", invitecode.replace(" ", ""));
		}
		page.setPd(pd);
		return (List<PageData>)dao.findForList("InvitationCodeInfoMapper.invitationlistPage", page);
		
	}
}
