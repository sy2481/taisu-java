package com.ruoyi.timer.domain;

import java.io.Serializable;

/**
 * 
 * @TableName SafetyMan
 */
public class Safetyman implements Serializable {
    /**
     * 
     */
    private String vhno;

    /**
     * 
     */
    private String egno;

    /**
     * 
     */
    private String idno;

    /**
     * 
     */
    private String smmk;

    /**
     * 
     */
    private String nm;

    /**
     * 
     */
    private String ipltlic;

    /**
     * 
     */
    private String pz;

    /**
     * 
     */
    private String aeipltlic;

    /**
     * 
     */
    private String aepz;

    /**
     * 
     */
    private String vndabr;

    /**
     * 
     */
    private String mngnm;

    /**
     * 
     */
    private String mngnm2;

    /**
     * 
     */
    private String mngtm;

    /**
     * 
     */
    private String fvid;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getVhno() {
        return vhno;
    }

    /**
     * 
     */
    public void setVhno(String vhno) {
        this.vhno = vhno;
    }

    /**
     * 
     */
    public String getEgno() {
        return egno;
    }

    /**
     * 
     */
    public void setEgno(String egno) {
        this.egno = egno;
    }

    /**
     * 
     */
    public String getIdno() {
        return idno;
    }

    /**
     * 
     */
    public void setIdno(String idno) {
        this.idno = idno;
    }

    /**
     * 
     */
    public String getSmmk() {
        return smmk;
    }

    /**
     * 
     */
    public void setSmmk(String smmk) {
        this.smmk = smmk;
    }

    /**
     * 
     */
    public String getNm() {
        return nm;
    }

    /**
     * 
     */
    public void setNm(String nm) {
        this.nm = nm;
    }

    /**
     * 
     */
    public String getIpltlic() {
        return ipltlic;
    }

    /**
     * 
     */
    public void setIpltlic(String ipltlic) {
        this.ipltlic = ipltlic;
    }

    /**
     * 
     */
    public String getPz() {
        return pz;
    }

    /**
     * 
     */
    public void setPz(String pz) {
        this.pz = pz;
    }

    /**
     * 
     */
    public String getAeipltlic() {
        return aeipltlic;
    }

    /**
     * 
     */
    public void setAeipltlic(String aeipltlic) {
        this.aeipltlic = aeipltlic;
    }

    /**
     * 
     */
    public String getAepz() {
        return aepz;
    }

    /**
     * 
     */
    public void setAepz(String aepz) {
        this.aepz = aepz;
    }

    /**
     * 
     */
    public String getVndabr() {
        return vndabr;
    }

    /**
     * 
     */
    public void setVndabr(String vndabr) {
        this.vndabr = vndabr;
    }

    /**
     * 
     */
    public String getMngnm() {
        return mngnm;
    }

    /**
     * 
     */
    public void setMngnm(String mngnm) {
        this.mngnm = mngnm;
    }

    /**
     * 
     */
    public String getMngnm2() {
        return mngnm2;
    }

    /**
     * 
     */
    public void setMngnm2(String mngnm2) {
        this.mngnm2 = mngnm2;
    }

    /**
     * 
     */
    public String getMngtm() {
        return mngtm;
    }

    /**
     * 
     */
    public void setMngtm(String mngtm) {
        this.mngtm = mngtm;
    }

    /**
     * 
     */
    public String getFvid() {
        return fvid;
    }

    /**
     * 
     */
    public void setFvid(String fvid) {
        this.fvid = fvid;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Safetyman other = (Safetyman) that;
        return (this.getVhno() == null ? other.getVhno() == null : this.getVhno().equals(other.getVhno()))
            && (this.getEgno() == null ? other.getEgno() == null : this.getEgno().equals(other.getEgno()))
            && (this.getIdno() == null ? other.getIdno() == null : this.getIdno().equals(other.getIdno()))
            && (this.getSmmk() == null ? other.getSmmk() == null : this.getSmmk().equals(other.getSmmk()))
            && (this.getNm() == null ? other.getNm() == null : this.getNm().equals(other.getNm()))
            && (this.getIpltlic() == null ? other.getIpltlic() == null : this.getIpltlic().equals(other.getIpltlic()))
            && (this.getPz() == null ? other.getPz() == null : this.getPz().equals(other.getPz()))
            && (this.getAeipltlic() == null ? other.getAeipltlic() == null : this.getAeipltlic().equals(other.getAeipltlic()))
            && (this.getAepz() == null ? other.getAepz() == null : this.getAepz().equals(other.getAepz()))
            && (this.getVndabr() == null ? other.getVndabr() == null : this.getVndabr().equals(other.getVndabr()))
            && (this.getMngnm() == null ? other.getMngnm() == null : this.getMngnm().equals(other.getMngnm()))
            && (this.getMngnm2() == null ? other.getMngnm2() == null : this.getMngnm2().equals(other.getMngnm2()))
            && (this.getMngtm() == null ? other.getMngtm() == null : this.getMngtm().equals(other.getMngtm()))
            && (this.getFvid() == null ? other.getFvid() == null : this.getFvid().equals(other.getFvid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVhno() == null) ? 0 : getVhno().hashCode());
        result = prime * result + ((getEgno() == null) ? 0 : getEgno().hashCode());
        result = prime * result + ((getIdno() == null) ? 0 : getIdno().hashCode());
        result = prime * result + ((getSmmk() == null) ? 0 : getSmmk().hashCode());
        result = prime * result + ((getNm() == null) ? 0 : getNm().hashCode());
        result = prime * result + ((getIpltlic() == null) ? 0 : getIpltlic().hashCode());
        result = prime * result + ((getPz() == null) ? 0 : getPz().hashCode());
        result = prime * result + ((getAeipltlic() == null) ? 0 : getAeipltlic().hashCode());
        result = prime * result + ((getAepz() == null) ? 0 : getAepz().hashCode());
        result = prime * result + ((getVndabr() == null) ? 0 : getVndabr().hashCode());
        result = prime * result + ((getMngnm() == null) ? 0 : getMngnm().hashCode());
        result = prime * result + ((getMngnm2() == null) ? 0 : getMngnm2().hashCode());
        result = prime * result + ((getMngtm() == null) ? 0 : getMngtm().hashCode());
        result = prime * result + ((getFvid() == null) ? 0 : getFvid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", vhno=").append(vhno);
        sb.append(", egno=").append(egno);
        sb.append(", idno=").append(idno);
        sb.append(", smmk=").append(smmk);
        sb.append(", nm=").append(nm);
        sb.append(", ipltlic=").append(ipltlic);
        sb.append(", pz=").append(pz);
        sb.append(", aeipltlic=").append(aeipltlic);
        sb.append(", aepz=").append(aepz);
        sb.append(", vndabr=").append(vndabr);
        sb.append(", mngnm=").append(mngnm);
        sb.append(", mngnm2=").append(mngnm2);
        sb.append(", mngtm=").append(mngtm);
        sb.append(", fvid=").append(fvid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}