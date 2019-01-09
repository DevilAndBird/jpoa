package com.fh.entity.app.transitcenter;

public class AppTransitCenter {
	/** 去往集散中心顺序 */
	private String seq;
	/** 主键ID */
	private Integer id;
	/** 集散中心名 */
	private String name;
	/** 是否已经送往集散中心 */
	private String isfinist;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsfinist() {
		return isfinist;
	}

	public void setIsfinist(String isfinist) {
		this.isfinist = isfinist;
	}
}
