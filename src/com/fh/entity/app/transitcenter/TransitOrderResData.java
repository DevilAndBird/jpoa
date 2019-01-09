package com.fh.entity.app.transitcenter;

public class TransitOrderResData {
    /** 取派员userid */
    private Integer dmanid;
    /** 取派员名称 */
    private String dmanname;
    /** 服务中心id */
    private String counterserviceid;
    /** 服务中心名称 */
    private String countercentername;
    /** 取派员行李总数 */
    private Integer bagnum;

    public Integer getDmanid() {
        return dmanid;
    }

    public void setDmanid(Integer dmanid) {
        this.dmanid = dmanid;
    }

    public String getDmanname() {
        return dmanname;
    }

    public void setDmanname(String dmanname) {
        this.dmanname = dmanname;
    }

    public String getCounterserviceid() {
        return counterserviceid;
    }

    public void setCounterserviceid(String counterserviceid) {
        this.counterserviceid = counterserviceid;
    }

    public String getCountercentername() {
        return countercentername;
    }

    public void setCountercentername(String countercentername) {
        this.countercentername = countercentername;
    }

    public Integer getBagnum() {
        return bagnum;
    }

    public void setBagnum(Integer bagnum) {
        this.bagnum = bagnum;
    }

}
