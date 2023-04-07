package com.ruoyi.base.vo;


public class SelectExceptionInOutLogListVO {
    private String idCard;
    private String name;
    private int exceptionInTimes;
    private int exceptionOutTimes;
    private Integer personType;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExceptionInTimes() {
        return exceptionInTimes;
    }

    public void setExceptionInTimes(int exceptionInTimes) {
        this.exceptionInTimes = exceptionInTimes;
    }

    public int getExceptionOutTimes() {
        return exceptionOutTimes;
    }

    public void setExceptionOutTimes(int exceptionOutTimes) {
        this.exceptionOutTimes = exceptionOutTimes;
    }

    public Integer getPersonType() {
        return personType;
    }

    public void setPersonType(Integer personType) {
        this.personType = personType;
    }
}
