package com.fh.service.system.wfd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.WorkFlowDetail;
import com.fh.util.PageData;
/**
 * 
 *@Title:WorkFlowDetailService.java
 *@Package:com.fh.service.system.wfd
 *@Description:这是WorkFlowInfo的从表，记录具体的流程步骤
 *@author:唐启铭
 *@date:2016年7月7日 下午2:31:28
 */
@Service("workFlowDetailService")
public class WorkFlowDetailService {
	@Resource(name = "daoSupport")
	private  DaoSupport dao;
	
	@SuppressWarnings("all")
	/*
	 * 分页查询
	 */
	public List<PageData> wfdlistPage(Page page)throws Exception{
		return (List<PageData>) dao.findForList("workFlowDetailMapper.wfdlistPage", page);
	}
	
	/*
	 * 通过WorkFlowInfo的id来查询从表的数据
	 */
	public List<WorkFlowDetail> listBywfiid(Integer wfiid)throws Exception{
		return (List<WorkFlowDetail>) dao.findForList("workFlowDetailMapper.listbywfiid", wfiid);
	}
	
	/*
	 * 保存新增
	 */
	public void save(PageData pd)throws Exception{
		dao.update("workFlowDetailMapper.addwfd", pd);
	}
	
	/*
	 * 保存修改
	 */
	public void update(WorkFlowDetail wfd)throws Exception{
		dao.update("workFlowDetailMapper.updatewfd", wfd);
	}
	
	/*
	 * 查询从表具体某自定义流程的顺序
	 */
	public  List<WorkFlowDetail> ordersList(WorkFlowDetail wfd)throws Exception{
		return( List<WorkFlowDetail>) dao.findForList("workFlowDetailMapper.orderslist", wfd);
	}
	
	/*
	 * 删除某步骤时，更改下一行的顺序
	 */
	public void updateOrders(WorkFlowDetail wfd)throws Exception{
		dao.update("workFlowDetailMapper.updateorders", wfd);
	}
	
	/*
	 * 删除
	 */
	public void delete(Integer id)throws Exception{
		dao.delete("workFlowDetailMapper.deletewfd", id);
	}
	
	/*
	 * 查询当行数据
	 */
	public WorkFlowDetail findObject(Integer id)throws Exception{
		return  (WorkFlowDetail)dao.findForObject("workFlowDetailMapper.wfdfindone", id);
	}
	
	/*
	 * 根据wfiid查出当前行有无，给出下一行的具体步骤和顺序
	 */
	public WorkFlowDetail getNextOrders(Integer wfiid)throws Exception{
		List<WorkFlowDetail> wfd =	 (List<WorkFlowDetail>)dao.findForList("workFlowDetailMapper.getnextorders", wfiid);	
		if(wfd.size()>0){
			wfd.get(0).setOrders(wfd.get(0).getOrders()+1);
			wfd.get(0).setStepname("第"+wfd.get(0).getOrders()+"步");
			return wfd.get(0);			
		}else{
			WorkFlowDetail orderswfd = new WorkFlowDetail();
			orderswfd.setOrders(1);
			orderswfd.setStepname("第1步");
			orderswfd.setPostname("待定");
			return orderswfd;
		}
		 
	}
	
}
