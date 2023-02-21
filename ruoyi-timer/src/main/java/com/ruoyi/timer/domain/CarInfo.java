package com.ruoyi.timer.domain;

import java.io.Serializable;

public class CarInfo implements Serializable {
    private String carIdno;
    private String carType;
    private String carNm;
    private String carIpltLic;
    private String carPz;

    public String getCarIdno() {
        return carIdno;
    }

    public void setCarIdno(String carIdno) {
        this.carIdno = carIdno;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarNm() {
        return carNm;
    }

    public void setCarNm(String carNm) {
        this.carNm = carNm;
    }

    public String getCarIpltLic() {
        return carIpltLic;
    }

    public void setCarIpltLic(String carIpltLic) {
        this.carIpltLic = carIpltLic;
    }

    public String getCarPz() {
        return carPz;
    }

    public void setCarPz(String carPz) {
        this.carPz = carPz;
    }
}
