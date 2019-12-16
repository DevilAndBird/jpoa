package com.fh.test.service.app;

import com.fh.service.app.AppQRCodeService;
import com.fh.service.auxiliary.coupon.CouponInfoService;
import com.fh.test.base.BaseTest;
import com.fh.util.QRCodeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/* 生成qr码步骤 */
public class AppQRCodeServiceTest extends BaseTest {
    @Autowired
    private AppQRCodeService appQRCodeService;

    /* 步骤：1 */
    @Test
    public void insert() throws Exception {
        for (int a = 102101; a<=103100; a++) {
            try {
                appQRCodeService.insert(a +"");
            } catch(Exception e) {

            }
        }
        /**/

    }

    /* 步骤：2 */
    @Test
    public void create() throws Exception {
        for (int a = 101001; a<=102000; a++){
            QRCodeUtil.encode_("JPQR" + a,"D:\\QR\\bg.png", "D:\\QR\\logo.png","D:\\QR\\dest");
        }
    }

}
