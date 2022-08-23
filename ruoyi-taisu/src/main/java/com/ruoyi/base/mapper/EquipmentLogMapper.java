package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.EquipmentLog;

/**
 * 设备在线记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-27
 */
public interface EquipmentLogMapper
{
    /**
     * 查询设备在线记录
     *
     * @param id 设备在线记录主键
     * @return 设备在线记录
     */
    public EquipmentLog selectEquipmentLogById(Long id);

    /**
     * 查询设备在线记录列表
     *
     * @param equipmentLog 设备在线记录
     * @return 设备在线记录集合
     */
    public List<EquipmentLog> selectEquipmentLogList(EquipmentLog equipmentLog);

    /**
     * 新增设备在线记录
     *
     * @param equipmentLog 设备在线记录
     * @return 结果
     */
    public int insertEquipmentLog(EquipmentLog equipmentLog);

    /**
     * 修改设备在线记录
     *
     * @param equipmentLog 设备在线记录
     * @return 结果
     */
    public int updateEquipmentLog(EquipmentLog equipmentLog);

    /**
     * 删除设备在线记录
     *
     * @param id 设备在线记录主键
     * @return 结果
     */
    public int deleteEquipmentLogById(Long id);

    /**
     * 批量删除设备在线记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipmentLogByIds(Long[] ids);
}
