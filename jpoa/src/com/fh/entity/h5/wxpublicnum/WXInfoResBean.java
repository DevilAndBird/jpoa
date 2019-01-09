package com.fh.entity.h5.wxpublicnum;

import java.util.List;

/**
 * 类描述: 微信用户的基本信息
 */
public class WXInfoResBean {
	
		// 用户的标识
	    private String openid;
	    // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	    private int subscribe;
	    // 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	    private String subscribe_time;
	    // 昵称
	    private String nickname;
	    // 用户的性别（1是男性，2是女性，0是未知）
	    private int sex;
	    // 用户所在国家
	    private String country;
	    // 用户所在省份
	    private String province;
	    // 用户所在城市
	    private String city;
	    // 用户的语言，简体中文为zh_CN
	    private String language;
	    // 用户头像
	    private String headimgurl;
	    // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	    private String unionid;
	    // 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	    private String remark;
	    // 用户所在的分组ID（兼容旧的用户分组接口）
	    private int groupid; 
	    // 用户被打上的标签ID列表
	    private List<Integer> tagid_list; 
	    // 错误编码
	    private int errcode;
	    // 错误提示
	    private String errmsg="ok";

	    public String getOpenid() {
	        return openid;
	    }
	    public void setOpenid(String openid) {
	        this.openid = openid;
	    }
	    public int getSubscribe() {
	        return subscribe;
	    }
	    public void setSubscribe(int subscribe) {
	        this.subscribe = subscribe;
	    }
	    public String getSubscribe_time() {
	        return subscribe_time;
	    }
	    public void setSubscribe_time(String subscribe_time) {
	        this.subscribe_time = subscribe_time;
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
	    public String getLanguage() {
	        return language;
	    }
	    public void setLanguage(String language) {
	        this.language = language;
	    }
	    public String getHeadimgurl() {
	        return headimgurl;
	    }
	    public void setHeadimgurl(String headimgurl) {
	        this.headimgurl = headimgurl;
	    }
	    public int getErrcode() {
	        return errcode;
	    }
	    public void setErrcode(int errcode) {
	        this.errcode = errcode;
	    }
	    public String getErrmsg() {
	        return errmsg;
	    }
	    public void setErrmsg(String errmsg) {
	        this.errmsg = errmsg;
	    }
	    public String getUnionid() {
	        return unionid;
	    }
	    public void setUnionid(String unionid) {
	        this.unionid = unionid;
	    }
	    public String getRemark() {
	        return remark;
	    }
	    public void setRemark(String remark) {
	        this.remark = remark;
	    }
	    public int getGroupid() {
	        return groupid;
	    }
	    public void setGroupid(int groupid) {
	        this.groupid = groupid;
	    }
	    public List<Integer> getTagid_list() {
	        return tagid_list;
	    }
	    public void setTagid_list(List<Integer> tagid_list) {
	        this.tagid_list = tagid_list;
	    }
	    
	    @Override
		public String toString() {
			return "WeixinUserInfo [openid=" + openid + ", subscribe=" + subscribe
					+ ", subscribe_time=" + subscribe_time + ", nickname="
					+ nickname + ", sex=" + sex + ", country=" + country
					+ ", province=" + province + ", city=" + city + ", language="
					+ language + ", headimgurl=" + headimgurl + ", unionid="
					+ unionid + ", remark=" + remark + ", groupid=" + groupid
					+ ", tagid_list=" + tagid_list + ", errcode=" + errcode
					+ ", errmsg=" + errmsg + "]";
		}

}
