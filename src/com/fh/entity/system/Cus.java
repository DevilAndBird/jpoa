package com.fh.entity.system;

public class Cus {
	
	   private Integer id;
	   private String cusname;
	   private Integer gender;
	   private Integer age;
	   private String tel;
	   private String mobile;
	   private String email;
	   private String address;
	   private String compayname;
	   private Integer userid;
	   private String username;
	   private String notes;
	   private Integer status;
	   private String statusname;
	   private String createdtime;
	   private String freezetime;
	   private Integer type;
	   private String typename;
	   
	
	
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCusname() {
		return cusname;
	}
	public void setCusname(String cusname) {
		this.cusname = cusname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompayname() {
		return compayname;
	}
	public void setCompayname(String compayname) {
		this.compayname = compayname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public String getFreezetime() {
		return freezetime;
	}
	public void setFreezetime(String freezetime) {
		this.freezetime = freezetime;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	@Override
	public String toString() {
		return "Cus [id=" + id + ", cusname=" + cusname + ", gender=" + gender
				+ ", age=" + age + ", tel=" + tel + ", mobile=" + mobile
				+ ", email=" + email + ", address=" + address + ", compayname="
				+ compayname + ", userid=" + userid + ", username=" + username
				+ ", notes=" + notes + ", status=" + status + ", createdtime="
				+ createdtime + ", freezetime=" + freezetime + ", type=" + type
				+ ", typename=" + typename + "]";
	}
	

	   
   
   
}
