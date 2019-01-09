package com.fh.entity.area;

public class AreaDistrictInfo {
    private String id;

    private String provid;

    private String cityid;

    private String name;

    private String isvalid;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvid() {
		return provid;
	}

	public void setProvid(String provid) {
		this.provid = provid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }
    
}