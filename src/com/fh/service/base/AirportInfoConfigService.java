package com.fh.service.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.Logger;
import com.fh.util.PageData;

@Service
public class AirportInfoConfigService {
    
    Logger logger = Logger.getLogger(AirportInfoConfigService.class);

    @Autowired
    private DaoSupport dao;

    /**	
     * @desc:机场信息编码
     * @author: zhangjj
     * @history: 2018年2月04日
     */
    public List<PageData> loadAirportInfoConfig() throws Exception {
        return (List<PageData>) dao.findForList("AirportInfoConfigMapper.findAll", null);
    }
    
    /**    
     * @desc:根据id查询飞机场信息
     * @author: zhangjj
     * @history: 2018年2月04日
     */
    public PageData findByid(String id) throws Exception {
        PageData pd = new PageData();
        pd.put("id", id);
        return (PageData) dao.findForObject("AirportInfoConfigMapper.findByid", pd);
    }

}
