package com.fh.entity.app.transitcenter;

public class AppUnloadScanResData {
    /** 订单内部 */
    private Integer id;
    /** 订单编码 */
    private String orderno;
    /** 订单行李QR码 */
    private String baggageid;
    /** 订单类型：机-酒 酒-机 */
    private String type;
    /** 订单状态 */
    private String status;
    /** 行李数 */
    private Integer num;
    /** 客户姓名 */
    private String name;
    /** 客户名字拼音首字母 */
    private String namespellinitial;
    /** 客户名字拼音 */
    private String namespell;
    /** 出发地类型 */
    private String srctype;
    /** 出发地id */
    private String srcaddress;
    /** 出发地简称 */
    private String srcaddrname;
    /** 出发地地址详情 */
    private String srcaddrdesc;
    /** 目的地类型 */
    private String desttype;
    /** 目的地id */
    private String destaddress;
    /** 目的地简称 */
    private String destaddrname;
    /** 目的地详情地址 */
    private String destaddrdesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getBaggageid() {
        return baggageid;
    }

    public void setBaggageid(String baggageid) {
        this.baggageid = baggageid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getNamespellinitial() {
        return namespellinitial;
    }

    public void setNamespellinitial(String namespellinitial) {
        this.namespellinitial = namespellinitial;
    }

    public String getNamespell() {
        return namespell;
    }

    public void setNamespell(String namespell) {
        this.namespell = namespell;
    }

	public String getSrctype() {
		return srctype;
	}

	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}

	public String getSrcaddress() {
		return srcaddress;
	}

	public void setSrcaddress(String srcaddress) {
		this.srcaddress = srcaddress;
	}

	public String getSrcaddrname() {
		return srcaddrname;
	}

	public void setSrcaddrname(String srcaddrname) {
		this.srcaddrname = srcaddrname;
	}

	public String getDesttype() {
		return desttype;
	}

	public void setDesttype(String desttype) {
		this.desttype = desttype;
	}

	public String getDestaddress() {
		return destaddress;
	}

	public void setDestaddress(String destaddress) {
		this.destaddress = destaddress;
	}

	public String getDestaddrname() {
		return destaddrname;
	}

	public void setDestaddrname(String destaddrname) {
		this.destaddrname = destaddrname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSrcaddrdesc() {
		return srcaddrdesc;
	}

	public void setSrcaddrdesc(String srcaddrdesc) {
		this.srcaddrdesc = srcaddrdesc;
	}

	public String getDestaddrdesc() {
		return destaddrdesc;
	}

	public void setDestaddrdesc(String destaddrdesc) {
		this.destaddrdesc = destaddrdesc;
	}
}
