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
        // 查询未用过的qr码
        List<String> qrlist = appQRCodeService.findQRCodeLimitPage();
        for (String qr : qrlist){
            QRCodeUtil.encode_("JPQR" + qr,"D:\\QR\\bg.png", "D:\\QR\\logo.png","D:\\QR\\dest");
        }
        // 失效
        appQRCodeService.update(qrlist);
    }

}
