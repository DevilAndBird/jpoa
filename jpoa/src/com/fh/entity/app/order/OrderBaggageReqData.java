package com.fh.entity.app.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单行李关系请求
 * 
 * @author tangqm
 * @date 2018年2月27日
 */
/**
 * @author hasee
 * 
 */
public class OrderBaggageReqData implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 订单id  */
	private Integer orderid;
	/** 行李id */
	private String baggageid;
	/** 添加时间 */
	private Date addtime;
	/** 图片路径 */
	private String imgurl;
	/** 扫描状态 */
	private String isscan;
	/** 图片类型 */
	private String imgtype;
	/** 图片集合 */
	private List<String> imgurlList;
	/** 更新照片的人 */
	private Integer uploadUserid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getBaggageid() {
		return baggageid;
	}

	public void setBaggageid(String baggageid) {
		this.baggageid = baggageid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getIsscan() {
		return isscan;
	}

	public void setIsscan(String isscan) {
		this.isscan = isscan;
	}

	public String getImgtype() {
		return imgtype;
	}

	public void setImgtype(String imgtype) {
		this.imgtype = imgtype;
	}

	public List<String> getImgurlList() {
		return imgurlList;
	}

	public void setImgurlList(List<String> imgurlList) {
		this.imgurlList = imgurlList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getUploadUserid() {
		return uploadUserid;
	}

	public void setUploadUserid(Integer uploadUserid) {
		this.uploadUserid = uploadUserid;
	}

}
