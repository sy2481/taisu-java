package com.ruoyi.base.service;


import com.ruoyi.base.domain.DoorPlcManage;

import javax.sound.sampled.Port;
import java.util.List;


/**
 * 车道人道plc指令Service接口
 *
 * @author ruoyi
 * @date 2022-10-25
 */
public interface DoorPlcManageService
{
    /**
     * 查询车道人道plc指令
     *
     * @param doorId 车道人道plc指令主键
     * @return 车道人道plc指令
     */
    public DoorPlcManage selectDoorPlcManageByDoorId(Long doorId);

    /**
     * 查询车道人道plc指令列表
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 车道人道plc指令集合
     */
    //public List<DoorPlcManage> selectDoorPlcManageList(DoorPlcManage doorPlcManage);

    /**
     * 新增车道人道plc指令
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 结果
     */
    public int insertDoorPlcManage(DoorPlcManage doorPlcManage);

    /**
     * 修改车道人道plc指令
     *
     * @param doorPlcManage 车道人道plc指令
     * @return 结果
     */
    public int updateDoorPlcManage(DoorPlcManage doorPlcManage);

    /**
     * 批量删除车道人道plc指令
     *
     * @param doorIds 需要删除的车道人道plc指令主键集合
     * @return 结果
     */
    public int deleteDoorPlcManageByDoorIds(Long[] doorIds);

    /**
     * 删除车道人道plc指令信息
     *
     * @param doorId 车道人道plc指令主键
     * @return 结果
     */
    public int deleteDoorPlcManageByDoorId(Long doorId);

   void executePlcSendCommand(String ip, String port,String command);
}
