package com.ruoyi.base.service.impl;

import com.ruoyi.base.service.SafetycarService;
import com.ruoyi.base.domain.BaseSafetycar;
import com.ruoyi.base.mapper.SafetycarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author mi
* @description 针对表【base_safetyCar】的数据库操作Service实现
* @createDate 2022-08-29 09:39:08
*/
@Service
public class SafetycarServiceImpl implements SafetycarService {

    @Autowired
    private SafetycarMapper safetycarMapper;

    @Override
    public BaseSafetycar isExist(String ipltlic) {
        return safetycarMapper.isExist(ipltlic);
    }

    @Override
    public void insertCarlist(BaseSafetycar baseSafetycar) {
        safetycarMapper.insertCarlist(baseSafetycar);
    }

    @Override
    public List<BaseSafetycar> getSafetycarByCarno(String carParam) {
        return safetycarMapper.getSafetycarByCarno(carParam);
    }
}
