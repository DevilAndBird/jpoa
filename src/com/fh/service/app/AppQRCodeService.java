package com.fh.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.app.order.AppQRCodeReqData;
import com.fh.util.PageData;

@Service
public class AppQRCodeService {
    @Autowired
    private DaoSupport dao;
    
    @SuppressWarnings("all")
	public List<String> findQRCodeLimitPage(AppQRCodeReqData appQRCodeReqData) throws Exception{
		return  (List<String>) dao.findForList( "QRCodeMapper.findQRCodeLimitPage" , (appQRCodeReqData.getLimit()-1)*200 );
    }
    
    @SuppressWarnings("all")
    public void update(List<String> qrcodeList) throws Exception{
    	   PageData pd = new PageData();
    	   pd.put("qrcodeList", qrcodeList);
    	   dao.update( "QRCodeMapper.update" , pd );
    }
    
}
