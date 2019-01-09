package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：QR码
 * 类名称：com.fh.entity.app.order.AppQRCodeReqData     
 * 创建人：tangqm
 * 创建时间：2018年7月23日 下午4:40:42   
 * 修改人：
 * 修改时间：2018年7月23日 下午4:40:42   
 * 修改备注：
 */
public class AppQRCodeReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer limit;
    /** qr码集合  */
    private List<String>qrcodeList;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<String> getQrcodeList() {
		return qrcodeList;
	}

	public void setQrcodeList(List<String> qrcodeList) {
		this.qrcodeList = qrcodeList;
	}
    

}
