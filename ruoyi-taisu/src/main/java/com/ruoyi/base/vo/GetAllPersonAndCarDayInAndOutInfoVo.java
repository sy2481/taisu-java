package com.ruoyi.base.vo;

import java.util.List;

public class GetAllPersonAndCarDayInAndOutInfoVo {

    private List<GetDayInAndOutInfoByAreaPerson> getDayInAndOutInfoByAreaPersons;
    private List<GetDayInAndOutInfoByAreaCar> getDayInAndOutInfoByAreaCars;

    private List<GetDayInAndOutInfoUser> getDayInAndOutInfoUsers;


    public List<GetDayInAndOutInfoUser> getGetDayInAndOutInfoUsers() {
        return getDayInAndOutInfoUsers;
    }

    public void setGetDayInAndOutInfoUsers(List<GetDayInAndOutInfoUser> getDayInAndOutInfoUsers) {
        this.getDayInAndOutInfoUsers = getDayInAndOutInfoUsers;
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
