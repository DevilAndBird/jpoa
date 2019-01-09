package com.fh.service.delivery;



import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.common.constant_enum.TASKMAINDRIVER_STATUS;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.app.userdelivery.UserDeliveryReqData;
import com.fh.entity.app.userdelivery.UserDeliveryResData;
import com.fh.entity.delivery.TaskMainDriver;
import com.fh.entity.system.User;
import com.fh.service.auxiliary.push.PushInfoService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.PageData;

@Service
@SuppressWarnings("all")
public class TaskMainDriverService {

	@Autowired
	private DaoSupport dao;
	@Autowired 
	private PushInfoService pushInfoService; 
	
	/**
	 * 获取班车司机的任务列表
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public List<TaskMainDriver> queryTasksByUserId( Integer userId ) throws Exception
	{
		Date curDate = new Date();
		Date date = DateUtil.addDate( curDate, -30 );
		String dateStr = DateUtil.getDefaultDateStr( date );
		PageData pd = new PageData();
		pd.put( "timeParam" , dateStr );
		pd.put( "driverid" , userId );
		return (List<TaskMainDriver>)dao.findForList( "TaskMainDriverMapper.queryAppTasksByUserId", pd );
	}
	/**
	 * 确认出发
	 * 陈玉石
	 * 2018年3月3日15:26:37
	 * @throws Exception 
	 */
	public void confirmStart( Integer taskid  ) throws Exception{
		PageData pd = new PageData();
		pd.put( "id", taskid );
		pd.put( "status", TASKMAINDRIVER_STATUS.STARTED );
		dao.update("TaskMainDriverMapper.confirmStart", pd);
	}
	
	
	/**
	 * @desc 即将到达 
	 * @auther zhangjj
	 * @date 2018年3月21日
	 */
	public void cbConfirmArriving(UserDeliveryReqData reqData) throws Exception{// TODO 根据id阔以更改
		PageData pd = new PageData();
		pd.put( "roleid", reqData.getRoleid());
		pd.put( "roletype", reqData.getRoletype());
		pd.put( "isfinish", reqData.getIsfinish());
		pd.put( "desttype", reqData.getDesttype());
		pd.put( "destaddress", reqData.getDestaddress());
		pd.put( "travelstatus", TASKMAINDRIVER_STATUS.ARRIVING.getValue());
		dao.update("OrderRoleMapper.updateTravelstatus", pd);
		
		// 推送信息
		pushInfoService.dmanArrivingPush(reqData.getRoleid(), reqData.getRoletype(),  reqData.getIsfinish(), reqData.getDesttype(), reqData.getDestaddress());
	}
	
	/**
	 * @desc 确认到达
	 * @auther zhangjj
	 * @date 2018年3月22日
	 */
	public void cbConfirmArrived(UserDeliveryReqData reqData) throws Exception{
		PageData pd = new PageData();
		pd.put( "roleid", reqData.getRoleid());
		pd.put( "roletype", reqData.getRoletype());
		pd.put( "isfinish", reqData.getIsfinish());
		pd.put( "desttype", reqData.getDesttype());
		pd.put( "destaddress", reqData.getDestaddress());
		pd.put( "travelstatus", TASKMAINDRIVER_STATUS.ARRIVED.getValue());
		dao.update("OrderRoleMapper.updateTravelstatus", pd);
		
		// 推送信息
		pushInfoService.dmanArrivedPush(reqData.getRoleid(), reqData.getRoletype(),  reqData.getIsfinish(), reqData.getDesttype(), reqData.getDestaddress());
	}
	
	/**
	 * 导出
	 * 木桐
	 * 2018年2月2日11:12:17
	 * @throws Exception 
	 */
	public List<PageData> findAll( PageData pd ) throws Exception{
		return (List<PageData>)dao.findForList("TaskMainDriverMapper.findAll", pd);
	}
	
	/**
	 * 修改状态
	 * 木桐
	 * 2018年2月5日16:09:37
	 * @throws Exception 
	 */
	public void updateStatus( PageData pd ) throws Exception{
		dao.update("TaskMainDriverMapper.updateStatus", pd);
	}
	
	/**
	 * 修改取件员状态
	 * tangqm
	 * 2018年3月5日 01:01:53
	 * @throws Exception 
	 */
	public void updatePickerStatus( PageData pd ) throws Exception{
		dao.update("TaskMainDriverMapper.updatePickerStatus", pd);
	}
	
	/**
	 * 新增班车任务
	 * @throws Exception 
	 * */
	public List<PageData> findAlikeTransit(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TaskMainDriverMapper.findAlikeTransit", pd);
	}
	
	/**
	 * 新增班车任务
	 * @throws Exception 
	 * */
	public void saveTaskMainDrive(PageData pd) throws Exception {
		User user = getuser();
		if( null != user ){
			pd.put("createdby", user.getUSER_ID());
			pd.put("modifiedby", user.getUSER_ID());
		}
		dao.save("TaskMainDriverMapper.insertTask", pd);
	}
	
	/**
	 * 新增取件员任务
	 * @throws Exception 
	 * */
	public void saveTaskPickerDrive(PageData pd) throws Exception {
		User user = getuser();
		if( null != user ){
			pd.put("createdby", user.getUSER_ID());
			pd.put("modifiedby", user.getUSER_ID());
		}
		dao.save("TaskMainDriverMapper.insertPickerTask", pd);
	}
	
	/**
	 * 审核班车司机任务
	 * 木桐
	 * 2018年2月5日18:47:55
	 * @throws Exception 
	 */
	public TaskMainDriver checkInfo( PageData pd ) throws Exception{
		return (TaskMainDriver)dao.findForObject("TaskMainDriverMapper.checkInfo", pd);
	}
	
	
	/**
	 * 查询班车任务
	 * @throws Exception
	 * */
	public List<PageData> findTaskMainDriverlistPage( Page page ) throws Exception {
		return (List<PageData>) dao.findForList("TaskMainDriverMapper.findTaskMainDriverlistPage", page);
	}
	
	/**
	 * 查询取件员任务
	 * @throws Exception
	 * */
	public List<PageData> findTaskPickerDriverlistPage( Page page ) throws Exception {
		return (List<PageData>) dao.findForList("TaskMainDriverMapper.findTaskPickerDriverlistPage", page);
	}
			
	/**
	 * 派送员列表查询接口
	 * @throws Exception
	 * */
	public List<UserDeliveryResData> findTaskMainDriverlist( UserDeliveryReqData userDeliveryReqData ) throws Exception {
		return (List<UserDeliveryResData>) dao.findForList("OrderRoleMapper.findDMANListByDest", userDeliveryReqData);
	}

	/**
	 * 获取当前的登录用户信息
	 * mt
	 */
	public User getuser(){
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute( Const.SESSION_USER );
		return user;
	}
}
