package com.fh.entity.system;

public class Sheet_info {
            private Integer id;
            private String sheetname;
            private Integer type;
            private String typename;
            private String notes;
            private String userid;
            private String username;
            private String createdtime;
            
            
            
			public String getUsername() {
				return username;
			}
			public void setUsername(String username) {
				this.username = username;
			}
			public Integer getId() {
				return id;
			}
			public void setId(Integer id) {
				this.id = id;
			}
			public String getSheetname() {
				return sheetname;
			}
			public void setSheetname(String sheetname) {
				this.sheetname = sheetname;
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
			public String getNotes() {
				return notes;
			}
			public void setNotes(String notes) {
				this.notes = notes;
			}
			public String getUserid() {
				return userid;
			}
			public void setUserid(String userid) {
				this.userid = userid;
			}
			public String getCreatedtime() {
				return createdtime;
			}
			public void setCreatedtime(String createdtime) {
				this.createdtime = createdtime;
			}
			
			@Override
			public String toString() {
				return "Sheet_info [id=" + id + ", sheetname=" + sheetname
						+ ", type=" + type + ", typename=" + typename
						+ ", notes=" + notes + ", userid=" + userid
						+ ", usrname=" + username + ", createdtime="
						+ createdtime + "]";
			}
            
            
            
            
}
