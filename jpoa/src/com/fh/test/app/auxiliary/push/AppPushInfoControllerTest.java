package com.fh.test.app.auxiliary.push;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fh.entity.app.AppRequestBean;
import com.fh.entity.app.auxiliary.push.req.AppPushReqData;
import com.fh.test.base.BaseTest;
import com.fh.thirdparty.jiguangpush.JiGuangPushBO;
import com.fh.util.HttpClientUtil;
import com.fh.util.MD5;
import com.google.gson.Gson;

public class AppPushInfoControllerTest  extends BaseTest{

//	public   String urlHeader = "http://localhost:8080/jpoa/";// 地址头
	public  String urlHeader = "http://47.96.186.145/jpoa/";//测试服务器
	
	@Autowired
	private JiGuangPushBO bo;
	
//	@Test
//	public void a() {
//		List<Integer> list = new ArrayList<Integer>();
//		list.add(84);
//		bo.pushRest(list, "误会误会", null);
//	}
	
	/**
     * app_查询推送信息userid
     */
    @Test
    public void findPushInfoByUserid() {
    	 urlHeader+="push/findPushInfoByUserid";
    	 AppPushReqData reqData = new AppPushReqData();
    	 reqData.setUserid(77);
    	 reqData.setType("ORDERMSG");
         commonTest(urlHeader,new Gson().toJson(reqData));
    }
    
    /**
     * app_查询推送条数未读
     */
    @Test
    public void countIsread_N() {
    	 urlHeader+="push/countIsread_N";
    	 AppPushReqData reqData = new AppPushReqData();
    	 reqData.setUserid(71);
    	 
         commonTest(urlHeader,new Gson().toJson(reqData));
    }
    
    /**
     * app_更新未读为已读
     */
    @Test
    public void updateIsreadToY() {
    	 urlHeader+="push/updateIsreadToY";
    	 AppPushReqData reqData = new AppPushReqData();
    	 reqData.setUserid(76);
    	 reqData.setId(782);
         commonTest(urlHeader,new Gson().toJson(reqData));
    }
	
	public void commonTest(String url,String data) {
		AppRequestBean bean = new AppRequestBean();
		bean.setUser("wojiaotangqiming");
		bean.setKey("JPWEIXIN");
		bean.setTimestamp("" + System.currentTimeMillis());
		
		bean.setSign(MD5.md5("jingpei" + bean.getUser() + bean.getKey()
				+ bean.getTimestamp()));
		bean.setData(data);
		Gson gson = new Gson();
		String json = gson.toJson(bean);
		System.out.println(json);
		try {
			String res = HttpClientUtil.doPostJson(url, json);
			System.out.println(res);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	

	@Test
	public void test() {
		
	}

}
