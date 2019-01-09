package com.fh.service.base;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.base.BaseConfig;
import com.fh.util.PageData;

@Service
public class BaseService {
    

    @Autowired
    private DaoSupport dao;

    /**	
     * @desc:基础配置表加载
     * @author: zhangjj
     * @history: 2018年1月15日
     */
    @SuppressWarnings("unchecked")
	public List<BaseConfig> loadBaseConfig() throws Exception {
        return (List<BaseConfig>) dao.findForList("BaseConfigMapper.findAll", new PageData());
    }
    
    /**	
     * @desc:根据codetype获取基础配置
     * @author: tangqm
     * @history: 2018年03月06日
     */
    @SuppressWarnings("all")
	public List<BaseConfig> getBaseConfigByCodeType(String  codetype,String code) throws Exception {
    	PageData pd = new PageData();
    	pd.put("codeType", codetype);
    	pd.put("code", code);
    	return (List<BaseConfig>) dao.findForList("BaseConfigMapper.baseConfiglist", pd);
    }
	/**
	 * 
	 * @Title: findBycodeType
	 * @Description: 根据codetype获取基础配置集
	 * author：tangqm
	 * 2018年10月31日
	 * @param codetype
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("all")
    public List<BaseConfig> findBycodeType(String  codetype) throws Exception {
    	PageData pd = new PageData();
    	pd.put("codeType", codetype);
    	return (List<BaseConfig>) dao.findForList("BaseConfigMapper.findBycodeType", pd);
    }
    
    public void saveBaseConfig(BaseConfig bc) throws Exception{
    	List<BaseConfig> baseConfigByCodeType = getBaseConfigByCodeType(bc.getCodeType(), bc.getCode());
    	if(CollectionUtils.isEmpty(baseConfigByCodeType)){
    		dao.save("BaseConfigMapper.saveBaseConfig", bc);
    	}
    }

}
