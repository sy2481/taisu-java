package com.ruoyi.timer.service;

import com.ruoyi.timer.domain.CarInfo;

import java.util.List;

public interface CarInfoService {
    List<CarInfo> getCarList(String carId);
}
