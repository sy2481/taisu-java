package com.ruoyi.timer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcInOutLog {
    /**
     * 刷卡机IP
     */
    private String ip;
    /**
     * 刷卡时间
     */
    private String dateTime;
    /**
     * 刷卡类别：1--人员入厂；2--人员出厂；3--车辆入厂；4--车辆出厂
     */
    private String type;
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 0未上傳
     */
    private int uploaded;
    /**
     * 厂门代号
     */
    private String fctDorNm;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public String getFctDorNm() {
        return fctDorNm;
    }

    public void setFctDorNm(String fctDorNm) {
        this.fctDorNm = fctDorNm;
    }
}
