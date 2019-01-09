package com.fh.rule.autoallot;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fh.common.constant_enum.ALLOT_LOG_CODE;
import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.ROLE_TYPE;
import com.fh.dao.DaoSupport;
import com.fh.entity.app.userdelivery.AppDmanCurrentGpsReqData;
import com.fh.entity.autoallot.AutoAllotParam;
import com.fh.entity.delivery.BaiduCoord;
import com.fh.rule.BaseRule;
import com.fh.rule.Exception.RuleException;
import com.fh.thirdparty.baidu.BaiDuMapBO;
import com.fh.util.PageData;
import com.fh.util.RedisUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：校验订单地址是否超区以及工作人员是否在岗
 * 类名称：com.fh.rule.autoallot.checkHualWayRule     
 * 创建人：tangqm
 * 创建时间：2018年5月28日 下午3:19:54   
 * 修改人：
 * 修改时间：2018年5月28日 下午3:19:54   
 * 修改备注：
 */
@Component
public class CheckOrdrnoAddrAndAttendanceRule implements BaseRule{
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DaoSupport dao;
	@Autowired
	private BaiDuMapBO baiDuMapBO;

	@Override
	@SuppressWarnings("all")
	public void execute(AutoAllotParam autoAllotParam) throws RuleException {
			 PageData pd = new PageData();
			 String scrtype = DESTINSATION_TYPE.HOTEL.getValue();
			 String desttype = DESTINSATION_TYPE.HOTEL.getValue();
			 String srcroletype = ROLE_TYPE.ROLE_HOTEL_TASK.getValue();
			 String desroletype = ROLE_TYPE.ROLE_HOTEL_SEND.getValue();
			 String srcaddressname = autoAllotParam.getScrlandmark();
			 String desaddressname = autoAllotParam.getDestlandmark();
			 pd.put("provid", autoAllotParam.getProvid());
			 pd.put("cityid", autoAllotParam.getCityid());
			 List<PageData>  regionDeliveryList =  (List<PageData>) dao.findForListByAutoAllot("AutoAllotMapper.regionDeliveryManList", pd);
			 
			 //判断出发地,得到出发地集散中心以及区域id
			BaiduCoord srcAddr = baiDuMapBO.getGpsByAddr(autoAllotParam.getSrcaddress());
			if(StringUtils.isBlank(srcAddr.getLat())||StringUtils.isBlank(srcAddr.getLng())){
				logger.error("订单id:"+autoAllotParam.getOrderid()+"出发地地址解析失败");
				throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "出发地地址解析失败");
			}
			PageData srcRegion= getRegionIdAndTransitId(srcAddr, regionDeliveryList);
			if(srcRegion==null){
				srcAddr = baiDuMapBO.getGpsByAddr(autoAllotParam.getSrcprovname()+
						autoAllotParam.getSrccityname()+autoAllotParam.getSrcdistrictname()+autoAllotParam.getSrcaddress());
				srcRegion= getRegionIdAndTransitId(srcAddr, regionDeliveryList);
				if(srcRegion==null){
					logger.error("订单id:"+autoAllotParam.getOrderid()+"得不到所属出发地区域");
					throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "得不到所属出发地区域");
				}
			}
			autoAllotParam.setSrcRegionid(Integer.parseInt(srcRegion.get("id").toString()));
			autoAllotParam.setSrcTransitd(Integer.parseInt(srcRegion.get("transitid").toString()));
			autoAllotParam.setSrctspan(Integer.parseInt(srcRegion.get("timespan").toString()));
			//判断出发地工作人员
			pd.put("regionid", autoAllotParam.getSrcRegionid());
		    List<PageData> srcWorker  = (List<PageData>) dao.findForListByAutoAllot("AutoAllotMapper.findRegionUDM", pd);
			if(CollectionUtils.isEmpty(srcWorker)){
				logger.error("订单id:"+autoAllotParam.getOrderid()+"出发地区域无工作人员");
				throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "出发地区域无工作人员");
			}
		    //选择取件员
			Integer srcroleid = choiceUDM(srcWorker, srcAddr,  autoAllotParam.getCityid());
			if(srcroleid==null){
				logger.error("订单id:"+autoAllotParam.getOrderid()+"选择取件员错误");
				throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "选择取件员错误");
			}
			autoAllotParam.setSrcroleid(srcroleid);
			if(autoAllotParam.getOrdertype().startsWith("AIRPORTCOUNTER")){
				pd.put("regionid",autoAllotParam.getSrcRegionid() );
				PageData counterService  = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findServiceCenter", pd);
				autoAllotParam.setSrcaddressid(counterService.get("id").toString()+"");
				scrtype = DESTINSATION_TYPE.SERVICECERTER.getValue();
				srcroletype = ROLE_TYPE.ROLE_AIRPORT_TASK.getValue();
				srcaddressname = (String) counterService.get("servicecentername");
			}
			autoAllotParam.setSrctype(scrtype);
			autoAllotParam.setSrcroletype(srcroletype);
			autoAllotParam.setSrcaddressname(srcaddressname);
			
			//判断目的地,得到目的地集散中心以及区域id
			BaiduCoord desAddr = baiDuMapBO.getGpsByAddr(autoAllotParam.getDestaddress());
			if(StringUtils.isBlank(desAddr.getLat())||StringUtils.isBlank(desAddr.getLng())){
				logger.error("订单id:"+autoAllotParam.getOrderid()+"目的地地址解析失败");
				throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "目的地地址解析失败");
			}
			PageData desRegion = getRegionIdAndTransitId(desAddr, regionDeliveryList);
			if(desRegion==null){
				 desAddr = baiDuMapBO.getGpsByAddr(autoAllotParam.getDestprovname()+autoAllotParam.getDestcityname()+
						 autoAllotParam.getDestdistrictname()+autoAllotParam.getDestaddress());
				desRegion = getRegionIdAndTransitId(desAddr, regionDeliveryList);
				if(desRegion==null){					
					logger.error("订单id:"+autoAllotParam.getOrderid()+"得不到所属目的地区域");
					throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "得不到所属目的地区域");
				}
			}
			autoAllotParam.setDesRegionid((Integer)desRegion.get("id"));
			autoAllotParam.setDesTransitd((Integer)desRegion.get("transitid"));
			autoAllotParam.setDestspan((Integer)desRegion.get("timespan"));
			//判断目的地工作人员
			pd.put("regionid", autoAllotParam.getDesRegionid());
			List<PageData> desWorker  = (List<PageData>) dao.findForListByAutoAllot("AutoAllotMapper.findRegionUDM", pd);
			if(CollectionUtils.isEmpty(desWorker)){
					logger.error("订单id:"+autoAllotParam.getOrderid()+"目的地区域无工作人员");
					throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "目的地区域无工作人员");
			}
			//选择派送员
			Integer desroleid = choiceUDM(desWorker, desAddr,  autoAllotParam.getCityid());
			if(desroleid==null){
				logger.error("订单id:"+autoAllotParam.getOrderid()+"选择取件员错误");
				throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "选择取件员错误");
			}
			autoAllotParam.setDesroleid(desroleid);
			if(autoAllotParam.getOrdertype().endsWith("AIRPORTCOUNTER")){
				desttype = DESTINSATION_TYPE.SERVICECERTER.getValue();
				desroletype = ROLE_TYPE.ROLE_AIRPORT_SEND.getValue();
				pd.put("regionid",autoAllotParam.getDesRegionid() );
				PageData counterService  = (PageData) dao.findForObjectByAutoAllot("AutoAllotMapper.findServiceCenter", pd);
				if(counterService==null){
					logger.error("机场配置未找到:"+autoAllotParam.getOrderid()+"请核实");
					throw new RuleException(ALLOT_LOG_CODE.AGAINALLOT.getValue(), "机场配置未找到");
				}
				autoAllotParam.setDestaddressid(counterService.get("id")+"");
				desaddressname = (String) counterService.get("servicecentername");
			}
			autoAllotParam.setDesttype(desttype);
			autoAllotParam.setDesroletype(desroletype);
			autoAllotParam.setDesaddressname(desaddressname);
	}
	
	/**
	 * 
	 * @Title: getRegionId
	 * @Description: 得到区域id
	 * author：tangqm
	 * 2018年5月28日
	 * @return
	 */
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
	
	/**
	 * 
	 * @Title: choiceUDM
	 * @Description: 选择取派员
	 * author：tangqm
	 * 2018年5月29日
	 * @param pd
	 * @param lngAndLat
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("all")
	public Integer choiceUDM( List<PageData> udmList,BaiduCoord lngAndLat,String cityid) {
			double arr[] = new double[udmList.size()];  
			for (int i = 0; i < arr.length; i++) {
				Integer  userid = (Integer) udmList.get(i).get("userid");
				Map<String, AppDmanCurrentGpsReqData> udmMap = (Map<String, AppDmanCurrentGpsReqData>) RedisUtil.get(cityid);
				AppDmanCurrentGpsReqData appDmanCurrentGpsReqData = udmMap.get(userid.toString());
				if(appDmanCurrentGpsReqData == null){
					return null;
				}
				String currentgps = appDmanCurrentGpsReqData.getCurrentgps();
				BaiduCoord baiduCoord = new Gson().fromJson(currentgps, BaiduCoord.class);
				double distance = BaiDuMapBO.getDistanceFromTwoPoints(Double.parseDouble(baiduCoord.getLat()),Double.parseDouble(baiduCoord.getLng()),
						Double.parseDouble(lngAndLat.getLat()),Double.parseDouble( lngAndLat.getLng()));
				arr[i]=distance;
			}
			return (Integer) udmList.get(getMinIndex(arr)).get("userid");
	}
	
	/**
	 * 
	 * @Title: getMinIndex
	 * @Description: 得到最小下标
	 * author：tangqm
	 * 2018年5月29日
	 * @param arr
	 * @return
	 */
    public static int getMinIndex(double[] arr){  
        int minIndex = 0;  
        for(int i=0; i<arr.length; i++){  
            if(arr[i] < arr[minIndex]){  
                minIndex = i;  
            }  
        }  
        return minIndex;  
    }

}
