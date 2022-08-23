package com.ruoyi.base.service;

import com.ruoyi.base.domain.PlcEquipment;

import java.util.List;

/**
 * PLC 道闸Service接口
 *
 * @author ruoyi
 * @date 2022-04-05
 */
public interface IPlcEquipmentService {
    /**
     * 查询PLC 道闸
     *
     * @param id PLC 道闸主键
     * @return PLC 道闸
     */
    public PlcEquipment selectPlcEquipmentById(Long id);

    /**
     * 查询PLC 道闸列表
     *
     * @param plcEquipment PLC 道闸
     * @return PLC 道闸集合
     */
    public List<PlcEquipment> selectPlcEquipmentList(PlcEquipment plcEquipment);

    /**
     * 新增PLC 道闸
     *
     * @param plcEquipment PLC 道闸
     * @return 结果
     */
    public int insertPlcEquipment(PlcEquipment plcEquipment);

    /**
     * 修改PLC 道闸
     *
     * @param plcEquipment PLC 道闸
     * @return 结果
     */
    public int updatePlcEquipment(PlcEquipment plcEquipment);

    /**
     * 批量删除PLC 道闸
     *
     * @param ids 需要删除的PLC 道闸主键集合
     * @return 结果
     */
    public int deletePlcEquipmentByIds(Long[] ids);

    /**
     * 删除PLC 道闸信息
     *
     * @param id PLC 道闸主键
     * @return 结果
     */
    public int deletePlcEquipmentById(Long id);

    /**
     * 根據IP查詢
     */
    public  PlcEquipment findByIp( String ip);

    /**
     * 根据厂区查询（多个）
     * @param factoryIdList
     * @return
     */
    public List<PlcEquipment> getPlcEquipmentByDept(List<Long> factoryIdList);

    /**
     * 根据ID（in）
     * @param factoryIdList
     * @return
     */
    public List<PlcEquipment> getPlcEquipmentById( List<Long> factoryIdList);


    List<PlcEquipment> getPlcEquipmentByDeptId(Long plantAreaId);

}
