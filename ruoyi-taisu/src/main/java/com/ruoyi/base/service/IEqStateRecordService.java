package com.ruoyi.base.service;

import java.util.Date;
import java.util.List;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.domain.EqStateRecord;

/**
 * 設備在線記錄Service接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface IEqStateRecordService 
{
    /**
     * 查询設備在線記錄
     * 
     * @param id 設備在線記錄主键
     * @return 設備在線記錄
     */
    public EqStateRecord selectEqStateRecordById(Long id);

    /**
     * 查询設備在線記錄列表
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 設備在線記錄集合
     */
    public List<EqStateRecord> selectEqStateRecordList(EqStateRecord eqStateRecord);

    /**
     * 新增設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    public int insertEqStateRecord(EqStateRecord eqStateRecord);

    /**
     * 修改設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    public int updateEqStateRecord(EqStateRecord eqStateRecord);

    /**
     * 批量删除設備在線記錄
     * 
     * @param ids 需要删除的設備在線記錄主键集合
     * @return 结果
     */
    public int deleteEqStateRecordByIds(Long[] ids);

    /**
     * 删除設備在線記錄信息
     * 
     * @param id 設備在線記錄主键
     * @return 结果
     */
    public int deleteEqStateRecordById(Long id);

    /**
     * 獲取最後一條設備記錄
     * @param eqType
     * @param eqId
     * @return
     */
    public EqStateRecord selectLastByEqDevice(String eqType,Long eqId);

    /**
     * 新增記錄表
     * @param eqDevice
     * @param status
     * @param pingTime
     * @return
     */
    public int addEqStateRecordVo(EqDevice eqDevice, Long status, Date pingTime);
}
