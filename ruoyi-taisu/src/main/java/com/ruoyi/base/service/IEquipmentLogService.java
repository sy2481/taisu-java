package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.EquipmentLog;

/**
 * 设备在线记录Service接口
 *
 * @author ruoyi
 * @date 2022-04-27
 */
public interface IEquipmentLogService
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
     * 批量删除设备在线记录
     *
     * @param ids 需要删除的设备在线记录主键集合
     * @return 结果
     */
    public int deleteEquipmentLogByIds(Long[] ids);

    /**
     * 删除设备在线记录信息
     *
     * @param id 设备在线记录主键
     * @return 结果
     */
    public int deleteEquipmentLogById(Long id);



    public int addLogInfo(String ip, Integer type);
}
