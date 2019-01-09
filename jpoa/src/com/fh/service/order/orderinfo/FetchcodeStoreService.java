package com.fh.service.order.orderinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.order.FetchcodeStore;

/**
 * 
 * 项目名称：oa   
 *
 * 类描述：
 * 类名称：com.fh.service.order.orderinfo.OrderInfoService     
 * 创建人：tqm 
 * 创建时间：2018年1月30日 下午3:49:29   
 * 修改人：
 * 修改时间：2018年1月30日 下午3:49:29   
 * 修改备注：   
 * @version   V1.0
 */
@Service
public class FetchcodeStoreService {
	
	@Autowired
    private DaoSupport dao;
	
	/**
	 * 
	 * @Title: saveFetchcode
	 * @Description: 保存提取码
	 * author：tangqm
	 * 2018年9月26日
	 * @param fetchcodeStore
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public void saveFetchcode(FetchcodeStore fetchcodeStore) throws Exception{
		dao.save("FetchcodeStoreMapper.saveFetchcode", fetchcodeStore);
	}
}
