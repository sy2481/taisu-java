package com.ruoyi.base.vo;

import java.util.List;

public class GetDayInAndOutInfoByAreaVO {
    private List<GetDayInAndOutInfoByAreaBaseErp> getDayInAndOutInfoByAreaBaseErps;
    private List<GetDayInAndOutInfoByAreaPerson> getDayInAndOutInfoByAreaPersons;
    private List<GetDayInAndOutInfoByAreaCar> getDayInAndOutInfoByAreaCars;

    public List<GetDayInAndOutInfoByAreaBaseErp> getGetDayInAndOutInfoByAreaBaseErps() {
        return getDayInAndOutInfoByAreaBaseErps;
    }

    public void setGetDayInAndOutInfoByAreaBaseErps(List<GetDayInAndOutInfoByAreaBaseErp> getDayInAndOutInfoByAreaBaseErps) {
        this.getDayInAndOutInfoByAreaBaseErps = getDayInAndOutInfoByAreaBaseErps;
    }

    public List<GetDayInAndOutInfoByAreaPerson> getGetDayInAndOutInfoByAreaPersons() {
        return getDayInAndOutInfoByAreaPersons;
    }

    public void setGetDayInAndOutInfoByAreaPersons(List<GetDayInAndOutInfoByAreaPerson> getDayInAndOutInfoByAreaPersons) {
        this.getDayInAndOutInfoByAreaPersons = getDayInAndOutInfoByAreaPersons;
    }

    public List<GetDayInAndOutInfoByAreaCar> getGetDayInAndOutInfoByAreaCars() {
        return getDayInAndOutInfoByAreaCars;
    }

    public void setGetDayInAndOutInfoByAreaCars(List<GetDayInAndOutInfoByAreaCar> getDayInAndOutInfoByAreaCars) {
        this.getDayInAndOutInfoByAreaCars = getDayInAndOutInfoByAreaCars;
    }
}
