package com.fh.entity.area;

public class AreaCityInfo {
    private String id;

    private String provid;

    private String name;
    /** 省份城市中心点 */
    private String centralpointgps;

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

	public String getCentralpointgps() {
		return centralpointgps;
	}

	public void setCentralpointgps(String centralpointgps) {
		this.centralpointgps = centralpointgps;
	}
}