package com.fh.service.weixin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.delivery.AppUser;
import com.fh.util.LoggerUtil;
import com.fh.util.MessageUtil;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：微信公众号业务类
 * 类名称：com.fh.service.wxpublicnum.WXPublicNumService     
 * 创建人：tangqm
 * 创建时间：2018年6月8日 下午1:58:13   
 * 修改人：
 * 修改时间：2018年6月8日 下午1:58:13   
 * 修改备注：
 */
@Service
public class WXPublicNumCondigService {

    @Autowired
    private DaoSupport dao;
    
    public static final Map<String, String> PUBLICNUMCONFIGMAP = new HashMap<String, String>();
    
    
    /**
     *@desc appUser 列表分页查询
     *@auther zhangjj
     *@history 2018年2月6日
     */
    @SuppressWarnings("unchecked")
	public List<PageData> findPublicNumConfiglistPage(Page page) throws Exception {
         return (List<PageData>) dao.findForList("WXPublicNumConfigMapper.publicnumconfiglistPage", page);
    }
    
    @SuppressWarnings("unchecked")
    @PostConstruct// 初始化
    public void initPublicNumConfigtoMap() throws Exception {
    	try {
    		// 清空
    		PUBLICNUMCONFIGMAP.clear();
    		List<PageData> list = (List<PageData>) dao.findForList("WXPublicNumConfigMapper.findAll", null);
    		if(CollectionUtils.isNotEmpty(list)) {
    			for (PageData pd : list) {
    				if(MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(pd.getString("businesskey"))) {// 文本
    					JSONObject businessValueJsonObj = JSONObject.fromObject((String) pd.get("businessvalue"));
                    	String reqcontent = (String) businessValueJsonObj.get("reqcontent");
                    	String rescontent = (String) businessValueJsonObj.get("rescontent");
    					PUBLICNUMCONFIGMAP.put((String) pd.get("publicnum") + (String) pd.get("businesskey") + reqcontent, rescontent);
    				} else if(MessageUtil.SERVICE_LIST.equals(pd.getString("businesskey"))) {
    					JSONObject businessValueJsonObj = JSONObject.fromObject(pd.getString("businessvalue"));
                    	String rescontent = (String) businessValueJsonObj.get("rescontent");
    					PUBLICNUMCONFIGMAP.put((String) pd.get("publicnum") + pd.getString("businesskey"), rescontent);
    				} else {// 事件
    					PUBLICNUMCONFIGMAP.put(pd.getString("publicnum") + pd.getString("businesskey"),  pd.getString("businessvalue"));
    				}
    			}
    		}
    	} catch (Exception e) {
    		LoggerUtil.error("公众号配置信息加入缓存错误", e);
    	}
    }
    
    /**
     *@desc 更新公众号配置
     *@auther zhangjj
     *@history 2018年2月9日
     */
    public void updatePubileNumConfig(PageData pd) throws Exception {
        dao.update("WXPublicNumConfigMapper.updatePubileNumConfig", pd);
    }
    
    /**
	 *@desc 新增公众号配置
 	 *@auther zhangjj 
	 *@history 2018年2月1日 2018年5月17日
	 */
	public void save(PageData pd) throws Exception {
		dao.save("WXPublicNumConfigMapper.save", pd);
	}
	
	/**
	 *@desc delete公众号配置
 	 *@auther zhangjj 
	 *@history 2018年2月1日 2018年5月17日
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("WXPublicNumConfigMapper.delete", pd);
	}
	
}
