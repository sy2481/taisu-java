package com.ruoyi.base.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.enums.EqDeviceStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.EqStateRecordMapper;
import com.ruoyi.base.domain.EqStateRecord;
import com.ruoyi.base.service.IEqStateRecordService;

/**
 * 設備在線記錄Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
@Service
public class EqStateRecordServiceImpl implements IEqStateRecordService 
{
    @Autowired
    private EqStateRecordMapper eqStateRecordMapper;

    /**
     * 查询設備在線記錄
     * 
     * @param id 設備在線記錄主键
     * @return 設備在線記錄
     */
    @Override
    public EqStateRecord selectEqStateRecordById(Long id)
    {
        return eqStateRecordMapper.selectEqStateRecordById(id);
    }

    /**
     * 查询設備在線記錄列表
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 設備在線記錄
     */
    @Override
    public List<EqStateRecord> selectEqStateRecordList(EqStateRecord eqStateRecord)
    {
        return eqStateRecordMapper.selectEqStateRecordList(eqStateRecord);
    }

    /**
     * 新增設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    @Override
    public int insertEqStateRecord(EqStateRecord eqStateRecord)
    {
        return eqStateRecordMapper.insertEqStateRecord(eqStateRecord);
    }

    /**
     * 修改設備在線記錄
     * 
     * @param eqStateRecord 設備在線記錄
     * @return 结果
     */
    @Override
    public int updateEqStateRecord(EqStateRecord eqStateRecord)
    {
        return eqStateRecordMapper.updateEqStateRecord(eqStateRecord);
    }

    /**
     * 批量删除設備在線記錄
     * 
     * @param ids 需要删除的設備在線記錄主键
     * @return 结果
     */
    @Override
    public int deleteEqStateRecordByIds(Long[] ids)
    {
        return eqStateRecordMapper.deleteEqStateRecordByIds(ids);
    }

    /**
     * 删除設備在線記錄信息
     * 
     * @param id 設備在線記錄主键
     * @return 结果
     */
    @Override
    public int deleteEqStateRecordById(Long id)
    {
        return eqStateRecordMapper.deleteEqStateRecordById(id);
    }

    /**
     * 獲取最後一條設備記錄
     * @param eqType
     * @param eqId
     * @return
     */
    @Override
    public EqStateRecord selectLastByEqDevice(String eqType,Long eqId){
        List<EqStateRecord> list= eqStateRecordMapper.selectEqStateRecordListDescByEqDevice(eqType,eqId);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


    /**
     * 新增記錄表
     * @param eqDevice
     * @param status
     * @param pingTime
     * @return
     */
    @Override
    public int addEqStateRecordVo(EqDevice eqDevice, Long status, Date pingTime){
        EqStateRecord eqStateRecord=new EqStateRecord();
        BeanUtils.copyProperties(eqDevice,eqStateRecord);
        eqStateRecord.setStatus(status);
        eqStateRecord.setStatusName(EqDeviceStatus.getNameByCode(status));
        eqStateRecord.setRecordTime(pingTime);
        eqStateRecord.setId(null);
        return eqStateRecordMapper.insertEqStateRecord(eqStateRecord);
    }
}
