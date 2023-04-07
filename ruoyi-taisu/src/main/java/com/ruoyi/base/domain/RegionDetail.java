package com.ruoyi.base.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class RegionDetail extends BaseEntity implements Serializable {
    /**
     * 厂商编号
     */
    private String no;

    /**
     * 厂商名称
     */
    private String name;

    /**
     * 人数
     */
    private Long people;


    /**
     * 车数
     */
    private Long car;
    /**
     * 工单号
     */
    private String vhNo;
    /**
     * 作业类别
     */
    private List<EgsTsOprEnvt> oprEnvtList;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPeople() {
        return people;
    }

    public void setPeople(Long people) {
        this.people = people;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }

    public String getVhNo() {
        return vhNo;
    }

    public void setVhNo(String vhNo) {
        this.vhNo = vhNo;
    }

    public List<EgsTsOprEnvt> getOprEnvtList() {
        return oprEnvtList;
    }

    public void setOprEnvtList(List<EgsTsOprEnvt> oprEnvtList) {
        this.oprEnvtList = oprEnvtList;
    }

}

