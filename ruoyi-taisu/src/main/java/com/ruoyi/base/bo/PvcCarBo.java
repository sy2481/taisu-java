package com.ruoyi.base.bo;

public class PvcCarBo {
    /** 車牌 */
    private String lcensePlate;

    private String name;
    /** 廠商名稱 */
    private String factoryName;

    /**
     * 发证厂区编号
     */
    private String factoryNo;
    /** 入場證（定位卡編號） */
    private String ipLtLic;
    /**
     * 有效时间
     */
    private String datetime;

    public String getLcensePlate() {
        return lcensePlate;
    }

    public void setLcensePlate(String lcensePlate) {
        this.lcensePlate = lcensePlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getIpLtLic() {
        return ipLtLic;
    }

    public void setIpLtLic(String ipLtLic) {
        this.ipLtLic = ipLtLic;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
