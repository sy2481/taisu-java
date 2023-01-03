package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.domain.EqPing;
import com.ruoyi.base.enums.EqDeviceStatus;
import com.ruoyi.base.mapper.EqPingMapper;
import com.ruoyi.base.service.IEqDeviceService;
import com.ruoyi.base.service.IEqPingService;
import com.ruoyi.base.service.IEqStateRecordService;
import com.ruoyi.base.taskHandler.PingState;
import com.ruoyi.common.utils.BatisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 設備檢測記錄Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-12
 */
@Service
public class EqPingServiceImpl implements IEqPingService {
    @Autowired
    private EqPingMapper eqPingMapper;
    @Autowired
    private IEqStateRecordService eqStateRecordService;
    @Autowired
    private IEqDeviceService eqDeviceService;

    /**
     * 查询設備檢測記錄
     *
     * @param id 設備檢測記錄主键
     * @return 設備檢測記錄
     */
    @Override
    public EqPing selectEqPingById(Long id) {
        return eqPingMapper.selectEqPingById(id);
    }

    /**
     * 查询設備檢測記錄列表
     *
     * @param eqPing 設備檢測記錄
     * @return 設備檢測記錄
     */
    @Override
    public List<EqPing> selectEqPingList(EqPing eqPing) {
        return eqPingMapper.selectEqPingList(eqPing);
    }

    /**
     * 新增設備檢測記錄
     *
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    @Override
    public int insertEqPing(EqPing eqPing) {
        return eqPingMapper.insertEqPing(eqPing);
    }

    /**
     * 修改設備檢測記錄
     *
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    @Override
    public int updateEqPing(EqPing eqPing) {
        return eqPingMapper.updateEqPing(eqPing);
    }

    /**
     * 批量删除設備檢測記錄
     *
     * @param ids 需要删除的設備檢測記錄主键
     * @return 结果
     */
    @Override
    public int deleteEqPingByIds(Long[] ids) {
        return eqPingMapper.deleteEqPingByIds(ids);
    }

    /**
     * 删除設備檢測記錄信息
     *
     * @param id 設備檢測記錄主键
     * @return 结果
     */
    @Override
    public int deleteEqPingById(Long id) {
        return eqPingMapper.deleteEqPingById(id);
    }


    /**
     * 根據設備獲取心跳數據
     *
     * @param eqDevice
     * @return
     */
    @Override
    public EqPing getEqPingByEqDevice(EqDevice eqDevice) {
        EqPing eqPing = eqPingMapper.selectEqPingByEqDevice(eqDevice.getEqType(), eqDevice.getEqId());
        if (eqPing == null) {
            eqPing = new EqPing();
            eqPing.setEqType(eqDevice.getEqType());
            eqPing.setEqId(eqDevice.getEqId());
            eqPing.setEqName(eqDevice.getEqName());
            eqPing.setIp(eqDevice.getIp());
            eqPing.setPort(eqDevice.getPort());
            eqPingMapper.insertEqPing(eqPing);
        }
        return eqPing;
    }


    /**
     * 心跳檢測
     *
     * @param id
     * @param pingTime
     * @param flag
     * @param pingState
     * @return
     */
    @Override
    @Transactional
    public int pinged(Long id, Date pingTime, boolean flag, PingState pingState) {
        int result = 0;
        //更新心跳數據
        result += this.updateLastPingTime(id, pingTime);

        //寫入在線記錄
        result += addEqStateRecord(flag, pingState, pingTime);

        //更新設備在線狀態


        return result;
    }

    /**
     * 更新心跳數據
     *
     * @param id
     * @param pingTime
     * @return
     */
    public int updateLastPingTime(Long id, Date pingTime) {
        EqPing eqPing = new EqPing();
        eqPing.setId(id);
        eqPing.setLastPingTime(pingTime);

        return eqPingMapper.updateEqPing(eqPing);
    }

    public int addEqStateRecord(boolean flag, PingState pingState, Date pingTime) {
        int result=0;
        Long lastStateRecordStatus = pingState.getLastStateRecordStatus();
        EqDevice eqDevice = pingState.getEqDevice();

        if (flag) {
            //ping通了
            if (lastStateRecordStatus == null || lastStateRecordStatus == 0l) {//上次狀態為離線，或者null，增加記錄
                result+=eqStateRecordService.addEqStateRecordVo(eqDevice, EqDeviceStatus.ON_LINE.getCode(), pingTime);
            }
            //當前狀態不在線，則更新
            if(!BatisUtils.longIsNvl(eqDevice.getStatus(),null).equals(EqDeviceStatus.ON_LINE.getCode())){
                result+=eqDeviceService.updateEqDeviceStatus(EqDeviceStatus.ON_LINE.getCode(),eqDevice.getEqType(),eqDevice.getEqId());
            }
        } else {
            if (lastStateRecordStatus == null || lastStateRecordStatus == 1l) {//上次狀態為在線，或者null，增加記錄
                result+=eqStateRecordService.addEqStateRecordVo(eqDevice, EqDeviceStatus.OFF_LINE.getCode(), pingTime);
            }
            //當前狀態不是離線，則更新
            if(!BatisUtils.longIsNvl(eqDevice.getStatus(),null).equals(EqDeviceStatus.OFF_LINE.getCode())){
                result+=eqDeviceService.updateEqDeviceStatus(EqDeviceStatus.OFF_LINE.getCode(),eqDevice.getEqType(),eqDevice.getEqId());
            }
        }
        return result;
    }


}
