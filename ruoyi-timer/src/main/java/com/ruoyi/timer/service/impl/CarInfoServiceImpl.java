package com.ruoyi.timer.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.CarInfo;
import com.ruoyi.timer.mapper.CarInfoMapper;
import com.ruoyi.timer.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DataSource(value = DataSourceType.IEMDG)
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    public List<CarInfo> getCarList(String carId) {
        return carInfoMapper.getCarInfoList(carId);
    }
}