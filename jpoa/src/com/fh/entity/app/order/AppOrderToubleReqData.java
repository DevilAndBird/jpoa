package com.fh.entity.app.order;

import java.io.Serializable;

import com.fh.entity.order.ProblemOrder;

/**
 * @desc 订单问题件反馈入参
 * @auther zhangjj
 * @history 2018年2月6日
 */
public class AppOrderToubleReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单号
     */
    private String orderno;
    
    /**
     * 反馈信息
     */
    private ProblemOrder problemOrder;

    public ProblemOrder getProblemOrder() {
        return problemOrder;
    }

    public void setProblemOrder(ProblemOrder problemOrder) {
        this.problemOrder = problemOrder;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
