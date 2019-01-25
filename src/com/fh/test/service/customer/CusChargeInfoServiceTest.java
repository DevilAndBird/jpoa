package com.fh.test.service.customer;

import org.springframework.beans.factory.annotation.Autowired;

import com.fh.entity.customer.CusChargeInfo;
import com.fh.service.customer.CusChargeInfoService;
import com.fh.service.order.orderinfo.OrderInfoService;
import com.fh.test.base.BaseTest;
import com.fh.util.PageData;

public class CusChargeInfoServiceTest extends BaseTest {
    
    @Autowired
    private CusChargeInfoService cusChargeInfoService;
    @Autowired
    private OrderInfoService orderInfoService;
    
    
    public void save() throws Exception {
        CusChargeInfo cusChargeInfo = new CusChargeInfo();
        cusChargeInfo.setCusid(1111);
        cusChargeInfo.setFinalmoney(111f);
        cusChargeInfo.setMoney(444f);
        cusChargeInfo.setSrcmoney(333f);
        System.out.println(cusChargeInfoService.save(cusChargeInfo));
    }
    
    @org.junit.Test
    public void update() throws Exception {
        CusChargeInfo cusChargeInfo = new CusChargeInfo();
        cusChargeInfo.setCusid(1111);
        cusChargeInfo.setFinalmoney(222f);
        cusChargeInfo.setMoney(44422f);
        cusChargeInfo.setSrcmoney(333f);
        cusChargeInfoService.update(cusChargeInfo);
    }
    
    @org.junit.Test
    public void insertQRCode() throws Exception {
        PageData pd = new PageData();
    	for (int i = 0; i < 100; i++) {
    	    Integer temp = 100000;
            pd.put("qrcode", temp ++);
    		orderInfoService.insertQRCode(pd);
		}
    }
    
    
    
}
