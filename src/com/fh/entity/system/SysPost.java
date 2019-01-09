package com.fh.entity.system;

public class SysPost {
        
	    private Integer id;
        private Integer fid;
        private String postname;
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
		public String getPostname() {
			return postname;
		}
		public void setPostname(String postname) {
			this.postname = postname;
		}
		
		@Override
		public String toString() {
			return "SysPost [id=" + id + ", fid=" + fid + ", postname="
					+ postname + "]";
		}
}
