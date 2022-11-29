package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.DoorPlcManage;

import java.util.List;

public interface DoorPlcManageMapper {
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
     * 删除车道人道plc指令
     *
     * @param doorId 车道人道plc指令主键
     * @return 结果
     */
    public int deleteDoorPlcManageByDoorId(Long doorId);

    /**
     * 批量删除车道人道plc指令
     *
     * @param doorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDoorPlcManageByDoorIds(Long[] doorIds);
}
