package com.fh.entity.app.order;

import java.io.Serializable;

/**
 * @desc 订单信息详情
 * @auther zhangjj
 * @history 2018年2月6日
 */
public class CountBaggageNumResData implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 订单内部编码 */
    private Integer baggageNum;

    public Integer getBaggageNum() {
        return baggageNum;
    }

    public void setBaggageNum(Integer baggageNum) {
        this.baggageNum = baggageNum;
    }
}
