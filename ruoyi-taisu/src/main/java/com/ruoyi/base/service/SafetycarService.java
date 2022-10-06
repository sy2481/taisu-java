package com.ruoyi.base.service;


import com.ruoyi.base.domain.BaseSafetycar;

import java.util.List;

/**
 * @author mi
 * @description 针对表【base_safetyCar】的数据库操作Service
 * @createDate 2022-08-29 09:39:08
 */
public interface SafetycarService {
    /**
     * 判断是否已存在
     */
    BaseSafetycar isExist(String ipltlic);

    /**
     * 插入当日车辆信息
     */
    void insertCarlist(BaseSafetycar baseSafetycar);

    /**
     * 获取拉取的车辆数据
     */
    List<BaseSafetycar> getSafetycarByCarno(String carParam);
}
