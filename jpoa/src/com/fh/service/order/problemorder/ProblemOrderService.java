package com.fh.service.order.problemorder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.order.ProblemNotes;
import com.fh.entity.order.ProblemOrder;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：oa
 * 
 * 类描述：超时订单列表 
 * 类名称：com.fh.service.order.overtimeorderservice.OverTimeOrderService
 * 创建人：tangqm
 * 修改备注：
 * 
 * @version V1.0
 */
@Service
public class ProblemOrderService {

	@Autowired
	private DaoSupport problemorderdao;

	/**
	 * 
	 * @Title: orderInfoList
	 * @Description: 查询问题件列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> findProblemOrderlistPage(Page page) throws Exception {
		return (List<PageData>) problemorderdao.findForList("ProblemOrderMapper.findProblemOrderlistPage", page);
	}
	
	/**
	 * 
	 * @Title: saveOverTimeOrder
	 * @Description: 新增超时订单
	 * @param pd
	 * @throws Exception
	 */
	public int saveProblemOrder(ProblemOrder problemOrder) throws Exception {
		return (int) problemorderdao.save("ProblemOrderMapper.insert", problemOrder);
	}
	
	/**
	 * 
	 * @Title: updateProcessStatus
	 * @Description: 修改处理状态
	 * @param problemOrder
	 * @return
	 * @throws Exception
	 */
	public int updateProcessStatus(ProblemOrder problemOrder) throws Exception {
		return (int) problemorderdao.update("ProblemOrderMapper.update", problemOrder);
	}

	/**
	 * 
	 * @Title: saveProblemNote
	 * @Description: 保存问题备注
	 * @param problemOrder
	 * @return
	 * @throws Exception
	 */
	public int saveProblemNote(ProblemNotes problemOrderNote) throws Exception {
		return (int) problemorderdao.save("ProblemNoteMapper.insert", problemOrderNote);
	}
	
	/**
	 * 
	 * @Title: findProblemDetaillistPage
	 * @Description: 查询问题明细
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public PageData findProblemDetaillistPage(Page page) throws Exception {
		return (PageData) problemorderdao.findForObject("ProblemOrderMapper.findProblemDetailList", page);
	}
	
	/**
	 * 
	 * @Title: findProblemNoteslistPage
	 * @Description: 查询备注列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> findProblemNoteslistPage(Page page) throws Exception {
		return (List<PageData>) problemorderdao.findForList("ProblemNoteMapper.findProblemNoteslistPage", page);
	}

}
