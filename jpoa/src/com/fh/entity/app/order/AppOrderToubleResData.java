package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

import com.fh.entity.order.ProblemNotes;
import com.fh.entity.order.ProblemOrder;

/**
 * 返回问题件出参
 * @author tangqm
 * @date 2018年3月4日
 */
public class AppOrderToubleResData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<ProblemOrder> problemOrderList;
    
    public List<ProblemOrder> getProblemOrderList() {
        return problemOrderList;
    }
    
    public void setProblemOrderList(List<ProblemOrder> problemOrderList) {
        this.problemOrderList = problemOrderList;
    }
    
    
}
