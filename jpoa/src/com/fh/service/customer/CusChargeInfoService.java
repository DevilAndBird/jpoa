package com.fh.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.customer.CusChargeInfo;
import com.fh.entity.customer.CusInfo;
import com.fh.entity.h5.H5CusChargeBean;
import com.fh.util.Logger;
import com.fh.util.PageData;

@Service
public class CusChargeInfoService {
    
    Logger logger = Logger.getLogger(CusChargeInfoService.class);

    @Autowired
    private DaoSupport dao;
    
    /**	
     * @desc:H5端的充值功能
     * @author: 陈玉石
     * @history: 2018年3月4日17:07:34
     * 
     * 首先要去查询cus_info一下这个客户的原来的money,然后才能去新增
     * 
     */
    public void doCharge(CusInfo cusInfo,H5CusChargeBean bean) throws Exception {
    	CusChargeInfo charge = new CusChargeInfo();
    	charge.setCusid( bean.getCusId() );
    	charge.setMoney( bean.getMoney() );
    	charge.setSerialno( bean.getSerialno() );
    	charge.setSrcmoney( cusInfo.getMoney() );
    	charge.setFinalmoney(bean.getMoney()  + cusInfo.getMoney() );
        dao.update( "CusChargeInfoMapper.insert", charge );
        cusInfo.setMoney(bean.getMoney()  + cusInfo.getMoney());
        dao.update( "CusInfoMapper.updateFinalmoney", cusInfo );
    }
    
    /**
     *@desc:客户充值记录列表查询
     *@author:陈玉石
     *@history:2018年3月4日17:30:56 
     */
    @SuppressWarnings("unchecked")
	public List<CusChargeInfo> queryListByCusId( Integer cusId ) throws Exception {
        PageData pd = new PageData();
        pd.put( "cusid", cusId );
        return (List<CusChargeInfo>) dao.findForList("CusChargeInfoMapper.queryListByCusId", pd);
    }
    /**	
     * @desc:更新客户信息 
     * @author: zhangjj
     * @history: 2018年1月17日
     */
    public void update(CusChargeInfo chargeInfo) throws Exception {
        dao.update("CusChargeInfoMapper.update", chargeInfo);
    }
    
    /** 
     * @desc:更新客户信息 
     * @author: zhangjj
     * @history: 2018年1月17日
     */
    public Integer save(CusChargeInfo chargeInfo) throws Exception {
        dao.save("CusChargeInfoMapper.insert", chargeInfo);
        return chargeInfo.getId();
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

}
