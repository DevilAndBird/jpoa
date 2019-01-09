package com.fh.entity.auxiliary.invitation;

import java.util.Date;

public class InvitationCodeInfo {
    private Integer id;

    private Integer cusid;

    private String invitename;

    private String invitecode;

    private Date addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCusid() {
        return cusid;
    }

    public void setCusid(Integer cusid) {
        this.cusid = cusid;
    }

    public String getInvitename() {
        return invitename;
    }

    public void setInvitename(String invitename) {
        this.invitename = invitename == null ? null : invitename.trim();
    }

    public String getInvitecode() {
        return invitecode;
    }

    public void setInvitecode(String invitecode) {
        this.invitecode = invitecode == null ? null : invitecode.trim();
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}