package com.ruoyi.base.domain;

import java.io.Serializable;

/**
 * @TableName base_safetyCar
 */
public class BaseSafetycar implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 关联编号 - 车辆(车牌号码)
     */
    private String idno;

    /**
     * 工作名称
     */
    private String workName;

    /**
     * 合约卡号
     */
    private String ipLtLic;

    /**
     * 厂区编号
     */
    private String pz;

    /** 車輛類型（1 大 2小） */
    private Long carType;

    /** 車輛類型名稱 */
    private String carTypeName;


    /**
     * 十进制码
     */
    private String decimalCode;

    public String getDecimalCode() {
        return decimalCode;
    }

    public void setDecimalCode(String decimalCode) {
        this.decimalCode = decimalCode;
    }


    /**
     * 关联编号 - 车辆(车牌号码)
     */
    public String getIdno() {
        return idno;
    }

    /**
     * 关联编号 - 车辆(车牌号码)
     */
    public void setIdno(String idno) {
        this.idno = idno;
    }

    /**
     * 工作名称
     */
    public String getWorkName() {
        return workName;
    }

    /**
     * 工作名称
     */
    public void setWorkName(String workName) {
        this.workName = workName;
    }

    /**
     * 合约卡号
     */
    public String getIpLtLic() {
        return ipLtLic;
    }

    /**
     * 合约卡号
     */
    public void setIpLtLic(String ipLtLic) {
        this.ipLtLic = ipLtLic;
    }

    /**
     * 厂区编号
     */
    public String getPz() {
        return pz;
    }

    /**
     * 厂区编号
     */
    public void setPz(String pz) {
        this.pz = pz;
    }

    public Long getCarType() {
        return carType;
    }

    public void setCarType(Long carType) {
        this.carType = carType;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }
}