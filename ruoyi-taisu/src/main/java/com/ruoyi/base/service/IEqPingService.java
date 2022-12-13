package com.ruoyi.base.service;

import java.util.Date;
import java.util.List;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.domain.EqPing;
import com.ruoyi.base.taskHandler.PingState;

/**
 * 設備檢測記錄Service接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface IEqPingService 
{
    /**
     * 查询設備檢測記錄
     * 
     * @param id 設備檢測記錄主键
     * @return 設備檢測記錄
     */
    public EqPing selectEqPingById(Long id);

    /**
     * 查询設備檢測記錄列表
     * 
     * @param eqPing 設備檢測記錄
     * @return 設備檢測記錄集合
     */
    public List<EqPing> selectEqPingList(EqPing eqPing);

    /**
     * 新增設備檢測記錄
     * 
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    public int insertEqPing(EqPing eqPing);

    /**
     * 修改設備檢測記錄
     * 
     * @param eqPing 設備檢測記錄
     * @return 结果
     */
    public int updateEqPing(EqPing eqPing);

    /**
     * 批量删除設備檢測記錄
     * 
     * @param ids 需要删除的設備檢測記錄主键集合
     * @return 结果
     */
    public int deleteEqPingByIds(Long[] ids);

    /**
     * 删除設備檢測記錄信息
     * 
     * @param id 設備檢測記錄主键
     * @return 结果
     */
    public int deleteEqPingById(Long id);

    /**
     * 根據設備獲取心跳數據
     * @param eqDevice
     * @return
     */
    public EqPing getEqPingByEqDevice(EqDevice eqDevice);

    /**
     * 心跳檢測
     * @param id
     * @param pingTime
     * @param flag
     * @param pingState
     * @return
     */
    public int pinged(Long id, Date pingTime, boolean flag, PingState pingState);
}
