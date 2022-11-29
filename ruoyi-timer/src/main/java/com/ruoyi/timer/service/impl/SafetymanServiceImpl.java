package com.ruoyi.timer.service.impl;


import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.Safetyman;
import com.ruoyi.timer.mapper.SafetymanMapper;
import com.ruoyi.timer.service.SafetymanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mi
 * @description 针对表【SafetyMan】的数据库操作Service实现
 * @createDate 2022-08-25 16:41:37
 */
@Service
@DataSource(value = DataSourceType.IEMDG)
public class SafetymanServiceImpl implements SafetymanService {

    @Autowired
    private SafetymanMapper safetymanMapper;

    @Override
    public List<Safetyman> getCarList() {
        return safetymanMapper.getCarList();
    }
}
