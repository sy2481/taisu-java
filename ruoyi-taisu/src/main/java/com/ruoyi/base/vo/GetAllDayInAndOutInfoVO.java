package com.ruoyi.base.vo;

public class GetAllDayInAndOutInfoVO {
    private String areaName;
    private Integer personCount;
    private Integer workCount;
    private Integer carCount;
    private String oprEnvts;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Integer workCount) {
        this.workCount = workCount;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public String getOprEnvts() {
        return oprEnvts;
    }

    public void setOprEnvts(String oprEnvts) {
        this.oprEnvts = oprEnvts;
    }
}
