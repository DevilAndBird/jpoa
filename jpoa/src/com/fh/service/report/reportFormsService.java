package com.fh.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：报表SQL
 * 类名称：com.fh.service.report.reportFormsService     
 * 创建人：tangqm
 * 创建时间：2018年8月13日 下午8:42:22   
 * 修改人：
 * 修改时间：2018年8月13日 下午8:42:22   
 * 修改备注：
 */
@Service
public class reportFormsService {
	
	@Autowired
    private DaoSupport dao;

    /**
     * 
     * @Title: reportFormslistPage
     * @Description: 查询汇总分页
     * author：tangqm
     * 2018年8月13日
     * @param page
     * @return
     * @throws Exception
     */
	@SuppressWarnings("all")
	public List<PageData> reportFormslistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"reportFormsMapper.reportFormslistPage", page);
	}
	/**
	 * 
	 * @Title: reportFormslistPage
	 * @Description: 查询汇总报表
	 * author：tangqm
	 * 2018年8月13日
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> reportFormslist( PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"reportFormsMapper.reportFormslist", pd);
	}
	/**
	 * 
	 * @Title: reportFormslistPage
	 * @Description: 查询明细分页
	 * author：tangqm
	 * 2018年8月13日
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> reportFormsDetaillistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"reportFormsMapper.reportFormsDetaillistPage", page);
	}
	/**
	 * 
	 * @Title: reportFormslistPage
	 * @Description: 查询明细报表
	 * author：tangqm
	 * 2018年8月13日
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public List<PageData> reportFormsDetaillist(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"reportFormsMapper.reportFormsDetaillist", pd);
	}
    

}
