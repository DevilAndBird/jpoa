package com.fh.service.delivery;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.delivery.UserDriverInfo;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.PageData;

@Service
@SuppressWarnings("all")
public class UserDriverService {

    @Autowired
    private DaoSupport dao;

    /**
     *@desc 更新司机信息
     *@auther zhangjj
     *@history 2018年2月8日
     */
    public void updateUserPicker(PageData pd) throws Exception {
        dao.update("UserDriverInfoMapper.updateUserPicker", pd);
    }

    /**
     * 新增班车司机 木桐 2018年2月5日15:24:55
     * 
     * @throws Exception
     */
    public void insertUserDriver(PageData pd) throws Exception {

        /*User user = getuser();
        if (null != user) {
            pd.put("createdby", user.getUSERNAME());
        }*/

        dao.save("UserDriverInfoMapper.insertUserDriver", pd);
    }

    /**
     * 审核班车 木桐 2018年2月5日15:19:16
     * 
     * @throws Exception
     */
    public UserDriverInfo checkInfo(PageData pd) throws Exception {
        return (UserDriverInfo) dao.findForObject("UserDriverInfoMapper.checkInfo", pd);
    }

    /**
     * 班车司机列表查询 木桐 2018年2月5日11:51:58
     * 
     * @throws Exception
     */
    public List<PageData> findUserDriverlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("UserDriverInfoMapper.userDriverlistPage", page);
    }

    /**
     * 获取当前的登录用户信息 mt
     */
    public User getuser() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        User user = (User) session.getAttribute(Const.SESSION_USER);
        return user;
    }
    
    /**
     *@desc 班车司机查询所有
     *@auther zhangjj
     *@history 2018年2月10日
     */
    public List<PageData> findAll() throws Exception {
        return (List<PageData>) dao.findForList("UserDriverInfoMapper.findAll", null);
    }

}
