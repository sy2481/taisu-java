package com.ruoyi.base.service;


import com.ruoyi.base.domain.BaseCarCardBind;

/**
 * @author mi
 * @description 针对表【base_car_card_bind】的数据库操作Service
 * @createDate 2022-09-05 20:47:03
 */
public interface CarCardBindService {

    void addCarCardBind(BaseCarCardBind baseCarCardBind);

    int getCountByCarCardNo(String carCarNo);

    void deleteByPrimarykey(String carCarNo, String bindNo);
}
