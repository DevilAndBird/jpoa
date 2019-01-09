package com.fh.entity.order;

import java.io.Serializable;

/**
 * 
 * 项目名称：jpoa   
 *
 * 类描述：
 * 类名称：com.fh.entity.order.ProblemOrderNotes     
 * 创建人：tqm 
 * 创建时间：2018年2月10日 下午9:39:25   
 * 修改人：
 * 修改时间：2018年2月10日 下午9:39:25   
 * 修改备注：   
 * @version   V1.0
 */
public class ProblemNotes implements  Serializable{
    
	/**
	 * @Fields serialVersionUID : 序列化
	 */ 
	private static final long serialVersionUID = 1L;
	
	private Integer   id ;                   //主键  
	private Integer   troubleid ;           //问题件id
	private String   notes ;                //备注信
	private String   addtime ;           // 添加时间 
	private Integer   addby;                //备注人
	private String   addusername;           //备注人
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTroubleid() {
		return troubleid;
	}
	public void setTroubleid(Integer troubleid) {
		this.troubleid = troubleid;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public Integer getAddby() {
		return addby;
	}
	public void setAddby(Integer addby) {
		this.addby = addby;
	}
	public String getAddusername() {
		return addusername;
	}
	public void setAddusername(String addusername) {
		this.addusername = addusername;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ProblemOrderNotes [id=" + id + ", troubleid=" + troubleid
				+ ", notes=" + notes + ", addtime=" + addtime + ", addby="
				+ addby + ", addusername=" + addusername + "]";
	}

	
}