package com.fh.entity.autoallot;


public class HaulWay {
	
	private String startTransit;
	
	private String endTransit;
	
	private String endspan;
	
	private String transitArr;
	
	private String spanArr;

	public String getStartTransit() {
		return startTransit;
	}

	public void setStartTransit(String startTransit) {
		this.startTransit = startTransit;
	}

	public String getEndTransit() {
		return endTransit;
	}

	public void setEndTransit(String endTransit) {
		this.endTransit = endTransit;
	}

	public String getEndspan() {
		return endspan;
	}

	public void setEndspan(String endspan) {
		this.endspan = endspan;
	}

	public String getTransitArr() {
		return transitArr;
	}

	public void setTransitArr(String transitArr) {
		this.transitArr = transitArr;
	}

	public String getSpanArr() {
		return spanArr;
	}

	public void setSpanArr(String spanArr) {
		this.spanArr = spanArr;
	}

	@Override
	public String toString() {
		return "HaulWay [startTransit=" + startTransit + ", endTransit="
				+ endTransit + ", endspan=" + endspan + ", transitArr="
				+ transitArr + ", spanArr=" + spanArr + "]";
	}

}
