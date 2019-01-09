package com.fh.service.delivery;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Logger;
import com.fh.util.PageData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings("all")
public class ServiceCenterService {
    
    Logger logger = Logger.getLogger(ServiceCenterService.class);

    @Autowired
    private DaoSupport dao;

    /**	
     * @desc:服务中心分页查询
     * @author: zhangjj
     * @history: 2018年02月02日
     */
    public List<PageData> findServiceCenterlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CounterServiceCenterMapper.findServiceCenterlistPage", page);
    }
    
    /**
     *@desc 根据主键更新柜台服务中心信息
     *@auther zhangjj
     *@history 2018年2月5日
     */
    public Object updateCounterServiceCenterById(PageData pageData) throws Exception {
        return dao.update("CounterServiceCenterMapper.updateCounterServiceCenterById", pageData);
    }
    
    /**
     *@desc 插入柜台服务中心信息
     *@auther zhangjj
     *@history 2018年2月5日
     */
    public int saveServiceCenter(PageData pd) throws Exception {
        return (int) dao.update("CounterServiceCenterMapper.saveServiceCenter", pd);
    }
    
    /** 
     * @desc:服务中心管理_分页
     * @author: zhangjj
     * @history: 2018年02月02日
     */
    public List<PageData> findAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CounterServiceCenterMapper.findAll", pd);
    }
    
    /**
     * 根据用户userid查询机场/高铁信息
     * zhangjj
     */
    public CounterServiceCenter findByUserid(Integer userid) throws Exception {
        return (CounterServiceCenter) dao.findForObject("CounterServiceCenterMapper.findByUserid", userid);
    }

    /**
     * 根据服务中心id查询机场/高铁信息
     * zhangjj
     */
    public PageData findByid(Integer id) throws Exception {
        PageData pd = new PageData();
        pd.put("id", id);
        return (PageData) dao.findForObject("CounterServiceCenterMapper.findByid", pd);
    }
    
    /**
     * 根据地区查询在这个地区的节点（服务中心节点列表）
     * zhangjj
     */
    public List<PageData> findByProvidAndCityid(String provid, String cityid) throws Exception {
        PageData pd = new PageData();
        pd.put("provid", provid);
        pd.put("cityid",cityid);
        return (List<PageData>) dao.findForList("CounterServiceCenterMapper.findByProvidAndCityid", pd);
    }
    
    /**
     * 服务中心节点列表包括叠加
     * zhangjj
     */
    public List<PageData> findCounterAll(String provid, String cityid, String transmittype) throws Exception {
        List<PageData> temp = new ArrayList<PageData>();

        PageData pd = new PageData();
        pd.put("provid", provid);
        pd.put("cityid",cityid);
        List<PageData> countertemp = (List<PageData>) dao.findForList("CounterServiceCenterMapper.findByProvidAndCityid", pd);

        // 柜台特殊查询   只管寄/只管送地址查询
        for (int i = 0; i< countertemp.size(); i++) {
            temp.add(countertemp.get(i));

            PageData req = new PageData();
            req.put("codeType", "COUNTER_AUX");
            req.put("code", countertemp.get(i).getString("servicecentercode") + "_" + transmittype);
            List<PageData> res = (List<PageData>) dao.findForList("BaseConfigMapper.findBycodeTypeAndCode", req);

            if(CollectionUtils.isNotEmpty(res)) {
                for(PageData pdtemp2 : res) {
                    JSONObject temp1 = JSONObject.parseObject((String) pdtemp2.get("remark"));
                    pdtemp2.put("id", countertemp.get(i).get("id"));
                    pdtemp2.put("servicecentername",  countertemp.get(i).get("servicecentername"));
                    pdtemp2.put("businesstimeflag", temp1.get("businesstimeflag"));
                    pdtemp2.put("remark", temp1.get("remark"));
                    pdtemp2.put("gps", countertemp.get(i).get("gps"));
                }
                temp.addAll(res);
            }
        }

        return temp;
    }

    /**
     * 服务网点查询
     * zhangjj
     */
    public List<PageData> findServerBranch(String provid, String cityid, String type) throws Exception {
        List<PageData> temp = new ArrayList<PageData>();
        PageData pd = new PageData();
        pd.put("provid", provid);
        pd.put("cityid",cityid);
        pd.put("type",type);
        return (List<PageData>) dao.findForList("CounterServiceCenterMapper.findByProvidAndCityid", pd);
    }
    
    /**
     * @desc 获取机场当前位置
     * @auther zhangjj
     * @date 2018年3月14日下午7:18:57
     */
    public List<PageData> findServiceCentergps(PageData pd) throws Exception {
    	return (List<PageData>) dao.findForList("CounterServiceCenterMapper.findServiceCentergps", pd);
    }

}
