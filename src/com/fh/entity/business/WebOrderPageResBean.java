package com.fh.entity.business;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public class WebOrderPageResBean {

    /** 查询集合 */
    private List<PageData> pageList;
    /** 翻页数据 */
    private Page page;

    public List<PageData> getPageList() {
        return pageList;
    }

    public void setPageList(List<PageData> pageList) {
        this.pageList = pageList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
