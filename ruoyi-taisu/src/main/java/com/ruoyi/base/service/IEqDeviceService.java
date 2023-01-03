package com.ruoyi.base.service;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.enums.EqDeviceStatus;

import java.util.Date;
import java.util.List;

public interface IEqDeviceService {

    public List<EqDevice> getList();

    public boolean getPingStatus(String Ip);

    /**
     * 獲取設備更新時間
     * @param eqType
     * @param eqId
     * @return
     */
    public Date lastUpdateTime(String eqType, Long eqId);

    /**
     * 獲取設備
     * @return
     */
    public EqDevice getEqDevice(String eqType, Long eqId);

    /**
     * 更新設備狀態
     * @param status
     * @param eqType
     * @param eqId
     * @return
     */
    public int updateEqDeviceStatus(Long status,String eqType, Long eqId);

}
