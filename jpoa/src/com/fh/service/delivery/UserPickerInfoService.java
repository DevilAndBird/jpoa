package com.fh.service.delivery;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
@SuppressWarnings("all")
public class UserPickerInfoService {
    @Resource
    private DaoSupport dao;

    /**
     * @desc 机场取件员查询_分頁
     * @auther zhangjj
     * @history 2018年2月3日
     */
    public List<PageData> findUserPickerlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("UserPickerInfoMapper.findUserPickerlistPage", page);
    }
    
    /**
     * @desc 机场取件员查询全部
     * @auther zhangjj
     * @history 2018年2月3日
     */
    public List<PageData> findAll() throws Exception {
        return (List<PageData>) dao.findForList("UserPickerInfoMapper.findAll", null);
    }
    
    /**
     *@return 
     *@desc 行李提取员根据主键更新
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public Object updateUserPickerInfoById(PageData pd) throws Exception {
        return dao.update("UserPickerInfoMapper.updateUserPickerInfoById", pd);
    }
    
    /**
     *@desc 增加机场取件员
     *@auther zhangjj
     *@history 2018年2月1日
     */
    public void savePicker(PageData pd) throws Exception {
         dao.save("UserPickerInfoMapper.savePicker", pd);
    }
    
}
