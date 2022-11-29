package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.BaseCarCardBind;
import com.ruoyi.base.mapper.CarCardBindMapper;
import com.ruoyi.base.service.CarCardBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mi
 * @description 针对表【base_car_card_bind】的数据库操作Service实现
 * @createDate 2022-09-05 20:47:03
 */
@Service
public class CarCardBindServiceImpl implements CarCardBindService{

    @Autowired
    private CarCardBindMapper carCardBindMapper;

    @Override
    public void addCarCardBind(BaseCarCardBind baseCarCardBind) {
        carCardBindMapper.addCarCardBind(baseCarCardBind);
    }

    @Override
    public int getCountByCarCardNo(String carCarNo) {
        return carCardBindMapper.getCountByCarCardNo(carCarNo);
    }

    @Override
    public void deleteByPrimarykey(String carCarNo, String bindNo) {
        carCardBindMapper.deleteByPrimarykey(carCarNo,bindNo);
    }
}
