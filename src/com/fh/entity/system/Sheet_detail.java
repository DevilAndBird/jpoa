package com.fh.entity.system;

public class Sheet_detail {
	
	 private Integer id;
	 private Integer fid;
     private Integer type;
     private String typename;
     private Integer size;
     private Integer isnull;
     private String label;
     private String itemcode;
     private String notes;
     
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getIsnull() {
		return isnull;
	}
	public void setIsnull(Integer isnull) {
		this.isnull = isnull;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getItemcode() {
		return itemcode;
	}
	public void setItemcode(String itemcode) {
		this.itemcode = itemcode;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return "Sheet_detail [id=" + id + ", fid=" + fid + ", type=" + type
				+ ", typename=" + typename + ", size=" + size + ", isnull="
				+ isnull + ", label=" + label + ", itemcode=" + itemcode
				+ ", notes=" + notes + "]";
	}
     
     
     
     
}
