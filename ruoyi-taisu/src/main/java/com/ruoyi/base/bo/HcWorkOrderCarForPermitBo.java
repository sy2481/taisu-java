package com.ruoyi.base.bo;

import com.ruoyi.base.domain.BaseDoor;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class HcWorkOrderCarForPermitBo extends BaseEntity implements Serializable {

    private List<HcWorkOrderCar> hcCarList;
    private List<BaseDoor> doorList;

    public List<HcWorkOrderCar> getHcCarList() {
        return hcCarList;
    }

    public void setHcCarList(List<HcWorkOrderCar> hcCarList) {
        this.hcCarList = hcCarList;
    }

    public List<BaseDoor> getDoorList() {
        return doorList;
    }

    public void setDoorList(List<BaseDoor> doorList) {
        this.doorList = doorList;
    }
}
