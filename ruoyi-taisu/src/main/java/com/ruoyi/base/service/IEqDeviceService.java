package com.ruoyi.base.service;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.enums.EqDeviceStatus;

import java.util.List;

public interface IEqDeviceService {

    public List<EqDevice> getList();

    public boolean getPingStatus(String Ip);

}
