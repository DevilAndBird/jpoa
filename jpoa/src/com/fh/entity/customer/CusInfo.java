package com.fh.entity.customer;

import java.io.Serializable;

public class CusInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 客户内部主键 */
    private Integer id;
    /** 客户名称 */
    private String name;
    /** 名字拼音 */
    private String namespell;
    /** 名字拼音首字母 */
    private String namespellinitial;
    /** 客户身份证号 */
    private String idno;
    /** 客户手机号 */
    private String mobile;
    private String password;
    /** 省份ID */
    private String provid;
    /** 城市id */
    private String cityid;
    /** 县id */
    private String districtid;
    /** 地址在 */
    private String address;
    /** 渠道 */
    private String channel;
    /** 账户余额 */
    private Float money;
    /** 发票公司名称 */
    private String taxtitle;
    /** 发票号 */
    private String taxno;
    /** 推荐人编号 */
    private String recdid;
    /** 邮箱 */
    private String email;
    /** 最后一次登陆时间 */
    private String lastlogintime;
    /** 创建人 */
    private String createdby;
    /** 是否有效 */
    private String isvalid;

    /** 添加时间 */
    private String addtime;

    /** 对当前公众号唯一标识  */
    private String openid;
    /** 用户关注时间 */
    private String subscribetime;
    /** 验证码 */
    private String verify;

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

    public String getNamespell() {
        return namespell;
    }

    public void setNamespell(String namespell) {
        this.namespell = namespell;
    }

    public String getNamespellinitial() {
        return namespellinitial;
    }

    public void setNamespellinitial(String namespellinitial) {
        this.namespellinitial = namespellinitial;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDistrictid() {
        return districtid;
    }

    public void setDistrictid(String districtid) {
        this.districtid = districtid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getTaxtitle() {
        return taxtitle;
    }

    public void setTaxtitle(String taxtitle) {
        this.taxtitle = taxtitle;
    }

    public String getTaxno() {
        return taxno;
    }

    public void setTaxno(String taxno) {
        this.taxno = taxno;
    }

    public String getRecdid() {
        return recdid;
    }

    public void setRecdid(String recdid) {
        this.recdid = recdid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}





	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getSubscribetime() {
		return subscribetime;
	}

	public void setSubscribetime(String subscribetime) {
		this.subscribetime = subscribetime;
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}
}