package com.fh.rule.autoallot;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.dao.DaoSupport;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.entity.autoallot.HaulWayDetail;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.rule.BaseRule;
import com.fh.rule.Exception.RuleException;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：校验物流路线是否以及存在
 * 类名称：com.fh.rule.autoallot.checkHualWayRule     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:19:54   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:19:54   
 * 修改备注：
 */
@Component
public class CheckHaulWayRule implements BaseRule{
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;

	@Override
	@SuppressWarnings("all")
	public void execute(AutoAllotParam autoAllotParam) throws RuleException {
			//判断物流路线
		     if(autoAllotParam.getSrcTransitd()==autoAllotParam.getDesTransitd()){
		    	 return;
		     }
			 PageData isExistRegion = (PageData)dao.findForObjectByAutoAllot("AutoAllotMapper.isExistRegion", autoAllotParam);
			 Integer trantspan = 0;
			 if(isExistRegion == null){
				 logger.error("订单id:"+autoAllotParam.getOrderid()+"物流路线未提前规划");
				 throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "物流路线未提前规划");
			 }
			 autoAllotParam.setHaulwayid(Integer.parseInt(isExistRegion.get("id").toString()));
			
			 //判断集散中心中转工作人员
			List<HaulWayDetail> detailList =  (List<HaulWayDetail>) dao.findForListByAutoAllot("AutoAllotMapper.findHualWayDetail", autoAllotParam);
			for (HaulWayDetail haulWayDetail : detailList) {
				if(haulWayDetail.getSrcid()==null || haulWayDetail.getDesid()==null){
					continue;
				}
				haulWayDetail.setProvid(autoAllotParam.getProvid());
				haulWayDetail.setCityid(autoAllotParam.getCityid());
				 PageData taskMainDrvier = (PageData)dao.findForObjectByAutoAllot("AutoAllotMapper.findTaskMainDrvier", haulWayDetail);
				 if(taskMainDrvier==null){
					 logger.error("订单id:"+autoAllotParam.getOrderid()+"集散中心中转人员未配置");
					 throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "物流路线未提前规划");
				 }
				 haulWayDetail.setDriver( (Integer) taskMainDrvier.get("driverid"));
				 trantspan+=haulWayDetail.getTimespan();
			}
			autoAllotParam.setTrantspan(trantspan);
			autoAllotParam.setHualWayDetail(detailList);//集散中心中转人员
	}
	
	/**
	 * 
	 * @Title: getRegionId
	 * @Description: 得到区域id
	 * author：tangqm
	 * 2018年5月28日
	 * @return
	 */
	@SuppressWarnings("all")
	public PageData getRegionIdAndTransitId(BaiduCoord addr,List<PageData>  regionDeliveryList){
		for (PageData pageData : regionDeliveryList) {
			pageData.put("lng", addr.getLng());
			pageData.put("lat", addr.getLat());
			String gps =  (String) pageData.get("gps");
			List<PageData> gpsList = new Gson().fromJson(gps, new TypeToken<List<PageData>>(){}.getType());
			if(baiDuMapBO.isInPolygon(pageData, gpsList)){
	    		return  pageData;
			}
		}
		return null;
	}

}
