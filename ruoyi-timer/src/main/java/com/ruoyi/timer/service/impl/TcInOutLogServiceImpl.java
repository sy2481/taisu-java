package com.ruoyi.timer.service.impl;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.enums.DataSourceType;
import com.ruoyi.timer.domain.TcInOutLog;
import com.ruoyi.timer.mapper.TcInOutLogMapper;
import com.ruoyi.timer.service.ITcInOutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DataSource(value = DataSourceType.IEMDG)
public class TcInOutLogServiceImpl implements ITcInOutLogService {
    @Autowired
    private TcInOutLogMapper tcInOutLogMapper;

    @Override
    public int insertTcInOutLog(TcInOutLog tcInOutLog){
        return tcInOutLogMapper.insertTcInOutLog(tcInOutLog);
    }


}
