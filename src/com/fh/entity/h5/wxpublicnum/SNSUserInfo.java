package com.fh.entity.h5.wxpublicnum;

import java.util.List;

/**
 * @desc 通过网页授权获取的用户信息
 * @auther zhangjj
 * @date 2018年4月20日
 */
public class SNSUserInfo {
    // 用户标识
    private String openId;
    // 用户昵称
    private String nickname;
    // 性别（1是男性，2是女性，0是未知）
    private int sex;
    // 国家
    private String country;
    // 省份
    private String province;
    // 城市
    private String city;
    // 用户头像链接
    private String headImgUrl;
    // 用户特权信息
    private List<String> privilegeList;
    //用户id  tanqm start
    private Integer cusid;
    // 身份证
    private String idno;
    // 手机号码
    private String mobile;
    // 用户姓名 tanqm end
    private String name;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public List<String> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<String> privilegeList) {
        this.privilegeList = privilegeList;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCusid() {
		return cusid;
	}

	public void setCusid(Integer cusid) {
		this.cusid = cusid;
	}

	@Override
	public String toString() {
		return "SNSUserInfo [openId=" + openId + ", nickname=" + nickname
				+ ", sex=" + sex + ", country=" + country + ", province="
				+ province + ", city=" + city + ", headImgUrl=" + headImgUrl
				+ ", privilegeList=" + privilegeList + ", cusid=" + cusid
				+ ", idno=" + idno + ", mobile=" + mobile + ", name=" + name
				+ "]";
	}
	
}