package com.ruoyi.system.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.system.domain.ZhongUser;
import com.ruoyi.system.mapper.ZhongUserMapper;
import com.ruoyi.system.service.IZhongUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZhongUserServiceImpl implements IZhongUserService {
    @Autowired
    private ZhongUserMapper zhongUserMapper;

    @DataSource(value = DataSourceType.SLAVE)
    @Override
    public List<ZhongUser> findAll() {
        return zhongUserMapper.findAll();
    }
}
