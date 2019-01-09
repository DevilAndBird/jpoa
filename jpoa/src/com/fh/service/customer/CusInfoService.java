package com.fh.service.customer;

import com.fh.common.constant_enum.ISVALID_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.customer.CusInfo;
import com.fh.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CusInfoService {
    
    Logger logger = Logger.getLogger( this.getClass() );

    @Autowired
    private DaoSupport dao;
    
    /**
     * 根据ID获取客户对象
     * 陈玉石
     * 2018年3月4日16:51:04
     * @param cusId
     * @return
     */
    public CusInfo getById( Integer cusId )throws Exception
    {
    	PageData pd = new PageData();
    	pd.put( "id", cusId );
    	return (CusInfo)dao.findForObject( "CusInfoMapper.getById", pd );
    }
    /**	
     * @desc:客戶信息查詢
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    public List<PageData> cusInfoList(Page page) throws Exception {
        PageData pd = page.getPd();
        String name = pd.getString("name");
        String mobile = pd.getString("mobile");
        // 是否是邀请客户
        String isInvite = pd.getString("isInvite");
        // 客户号
        String cusid = pd.getString("cusid");

        if (null != name) {
            pd.put("name", name.replace(" ", ""));
        }
        if (null != mobile) {
            pd.put("mobile", mobile.replace(" ", ""));
        }
        if (null != isInvite) {
            pd.put("isInvite", isInvite.replace(" ", ""));
        }
        if (null != cusid) {
            pd.put("cusid", cusid.replace(" ", ""));
        }
        
        page.setPd(pd);

        return (List<PageData>) dao.findForList("CusInfoMapper.cusInfolistPage", page);
    }
    
    /**	
     * @desc:更新客户信息 
     * @author: zhangjj
     * @history: 2018年1月17日
     */
    public String updateCusInfo(PageData pd) {
        try {
            dao.update("CusInfoMapper.updateCusInfo", pd);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改客户信息失败",e);
            return "error";
        }
    }
    
    /**	
     * @desc:新增客户信息
     * @author: 陈玉石 张晶晶 唐启铭
     * @throws Exception 
     * @history: 2018年3月4日11:24:31
     */
    public void insertCusInfo(CusInfo cusInfo) throws Exception {
    	// 验证码校验
    	PageData pd = new PageData();
    	pd.put("mobile", cusInfo.getMobile());
    	pd.put("valicode", cusInfo.getVerify());
    	PageData res = (PageData) dao.findForObject("valicodemapper.findByValicode", pd);
    	ExceptionUtil.checkNotNull(res, "验证码错误");
    	long beforeDate = DateUtil.fomatDate((String) res.get("addtime"), "yyyy-MM-dd HH:mm:ss").getTime();
    	long currentDate = DateUtil.fomatDate(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss").getTime();
    	ExceptionUtil.checkBoolean((currentDate - beforeDate)/(60*1000) > 30, "验证码超时");
    	
    	cusInfo.setNamespell( PingYinUtil.getPingYin( cusInfo.getName() ) );
    	cusInfo.setNamespellinitial( PingYinUtil.getFirstSpell( cusInfo.getName() ).toUpperCase() );
    	cusInfo.setIsvalid( ISVALID_TYPE.VALID.getValue() );
    	dao.save("CusInfoMapper.updateCusInfoByCusid", cusInfo);
    }

    public Integer saveCusinfoByOpenid(CusInfo cusInfo) throws Exception{
    	cusInfo.setIsvalid( ISVALID_TYPE.VALID.getValue() );
    	dao.save("CusInfoMapper.insert", cusInfo);
    	return cusInfo.getId();
    }
    
    /**
     *@desc:客户充值记录列表查询
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    public List<PageData> cusChargeList(Page page) throws Exception {
        PageData pd = page.getPd();
        String cusid = pd.getString("cusid");

        if (null != cusid) {
            pd.put("cusid", cusid.replace(" ", ""));
        }
        page.setPd(pd);

        return (List<PageData>) dao.findForList("CusChargeInfoMapper.cusChargelistPage", page);
    }
    
    
    /**
     *@desc:客户优惠卷列表查询_分页
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    public List<PageData> cusCouponlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CusCouponInfoMapper.cusCouponlistPage", page);
    }
    
    /**
     *@desc:客户订单列表查询
     *@author:zhangjj
     *@history:2018年1月18日 
     */
    public List<PageData> cusOrderList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("OrderMainMapper.orderMainlistPage", page);
    }
    
    /**
     * @desc 根据openid查询客户信息
     * @auther zhangjj
     * @date 2018年4月12日
     */
    public CusInfo findByOpenid(String openid)throws Exception {
    	return (CusInfo)dao.findForObject( "CusInfoMapper.findByOpenid", openid);
    }

    /**
     * @desc 根据openid查询客户信息
     * @auther zhangjj
     * @date 2018年4月12日
     */
    public CusInfo findByMobile(String mobile)throws Exception {
    	return (CusInfo)dao.findForObject( "CusInfoMapper.findByMobile", mobile);
    }

    /**
     * @desc: 更改客户信息
     * @author: zhangjj
     * @history: 2018年1月17日
     */
    public void updateNameAndIdnoById(PageData pd) throws Exception {
        dao.update("CusInfoMapper.updateNameAndIdnoById", pd);
    }


}
