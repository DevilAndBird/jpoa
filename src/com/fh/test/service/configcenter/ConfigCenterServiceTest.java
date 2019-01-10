package com.fh.test.service.configcenter;

import com.fh.entity.customer.CusChargeInfo;
import com.fh.service.ConfigCenter.ConfigCenterService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.test.base.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;

class ConfigCenterServiceTest extends BaseTest {


    @Autowired
    private ConfigCenterService configCenterService;


    public void getConfigTest() throws Exception {
        System.out.println(configCenterService.getConfig("310000", "specialPriceDetail"));
    }
}
