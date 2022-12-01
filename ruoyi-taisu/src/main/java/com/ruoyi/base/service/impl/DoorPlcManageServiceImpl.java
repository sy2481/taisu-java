package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.domain.DoorPlcManage;
import com.ruoyi.base.mapper.DoorPlcManageMapper;
import com.ruoyi.base.service.DoorPlcManageService;
import com.ruoyi.common.utils.plc.PlcSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoorPlcManageServiceImpl implements DoorPlcManageService {
    @Autowired
    private DoorPlcManageMapper doorPlcManageMapper;

    /**
     * 查询车道人道plc指令
     *
     * @param doorId 车道人道plc指令主键
     * @return 车道人道plc指令
     */
    @Override
    public DoorPlcManage selectDoorPlcManageByDoorId(Long doorId) {
        return doorPlcManageMapper.selectDoorPlcManageByDoorId(doorId);
    }

    /**
     * 查询车道人道plc指令列表
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 车道人道plc指令
     */
    @Override
    public List<DoorPlcManage> selectDoorPlcManageList(DoorPlcManage doorPlcManage) {
        return doorPlcManageMapper.selectDoorPlcManageList(doorPlcManage);
    }

    /**
     * 新增车道人道plc指令
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 结果
     */
    @Override
    public int insertDoorPlcManage(DoorPlcManage doorPlcManage) {
        return doorPlcManageMapper.insertDoorPlcManage(doorPlcManage);
    }

    /**
     * 修改车道人道plc指令
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 结果
     */
    @Override
    public int updateDoorPlcManage(DoorPlcManage doorPlcManage) {
        return doorPlcManageMapper.updateDoorPlcManage(doorPlcManage);
    }

    /**
     * 批量删除车道人道plc指令
     *
     * @param doorIds 需要删除的车道人道plc指令主键
     * @return 结果
     */
    @Override
    public int deleteDoorPlcManageByDoorIds(Long[] doorIds) {
        return doorPlcManageMapper.deleteDoorPlcManageByDoorIds(doorIds);
    }

    /**
     * 删除车道人道plc指令信息
     *
     * @param doorId 车道人道plc指令主键
     * @return 结果
     */
    @Override
    public int deleteDoorPlcManageByDoorId(Long doorId) {
        return doorPlcManageMapper.deleteDoorPlcManageByDoorId(doorId);
    }

    @Override
    public void executePlcSendCommand(String ip, String port, String command) {
        PlcSocket.sentMessage(ip, port, command);
        return;
    }
}