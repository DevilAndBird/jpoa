package com.fh.service.delivery;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.delivery.AppUser;
import com.fh.util.PageData;


@Service
@SuppressWarnings("all")
public class AppUserService {
	@Resource
	private DaoSupport dao;
	
    /**
     *@desc appUser 列表分页查询
     *@auther zhangjj
     *@history 2018年2月6日
     */
    public List<PageData> findAppUserlistPage(Page page) throws Exception {
         return (List<PageData>) dao.findForList("AppUserMapper.findAppUserlistPage", page);
    }
	
	/**
	 *@desc 新增用户
 	 *@auther zhangjj tangqm
	 *@history 2018年2月1日 2018年5月17日
	 */
	public String saveAppUser(AppUser appUser) throws Exception {
		List<PageData> pdList = (List<PageData>) dao.findForList("AppUserMapper.findTheSameNumberAndName", appUser);
		if(CollectionUtils.isEmpty(pdList)){			
			dao.save("AppUserMapper.saveAppUser", appUser);
			return "SUCCESS:"+appUser.getId();
		}
		 return "FAIL:手机号码或者用户名已注册，请重新输入";
	}
	
	/**
     *@desc 根据主键更新角色登陆信息
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public Object updateAppUserById(PageData pageData) throws Exception {
        return dao.update("AppUserMapper.updateAppUserById", pageData);
    }
    
    /**
     *@desc 根据主键更新角色登陆信息
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public PageData findByMobile(String mobile) throws Exception {
        return (PageData) dao.findForObject("AppUserMapper.findByMobile", mobile);
    }
    
    /**
     * 根据手机号码和密码获取对象
     * 陈玉石
     * 2018年2月25日19:43:39
     * @param mobile
     * @param pwd
     * @return
     * @throws Exception 
     */
    public AppUser getByMobileAndPwd( String mobile,String pwd ) throws Exception
    {
    	PageData pd = new PageData();
    	pd.put( "mobile", mobile );
    	pd.put( "password", pwd );
    	return (AppUser)dao.findForObject( "AppUserMapper.getByMobileAndPwd", pd );
    }

}
