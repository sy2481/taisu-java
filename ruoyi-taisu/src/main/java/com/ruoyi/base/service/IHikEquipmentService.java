package com.ruoyi.base.service;

import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcHik;

import java.util.List;

/**
 * HIK 海康设备Service接口
 * 
 * @author ruoyi
 * @date 2022-04-06
 */
public interface IHikEquipmentService 
{
    /**
     * 查询HIK 海康设备
     * 
     * @param id HIK 海康设备主键
     * @return HIK 海康设备
     */
    public HikEquipment selectHikEquipmentById(Long id);

    /**
     * 查询HIK 海康设备列表
     * 
     * @param hikEquipment HIK 海康设备
     * @return HIK 海康设备集合
     */
    public List<HikEquipment> selectHikEquipmentList(HikEquipment hikEquipment);

    /**
     * 新增HIK 海康设备
     * 
     * @param hikEquipment HIK 海康设备
     * @return 结果
     */
    public int insertHikEquipment(HikEquipment hikEquipment);

    /**
     * 修改HIK 海康设备
     * 
     * @param hikEquipment HIK 海康设备
     * @return 结果
     */
    public int updateHikEquipment(HikEquipment hikEquipment);

    /**
     * 批量删除HIK 海康设备
     * 
     * @param ids 需要删除的HIK 海康设备主键集合
     * @return 结果
     */
    public int deleteHikEquipmentByIds(Long[] ids);

    /**
     * 删除HIK 海康设备信息
     * 
     * @param id HIK 海康设备主键
     * @return 结果
     */
    public int deleteHikEquipmentById(Long id);

    /**
     * 根據PLCid,獲取中間表的設備關係
     */
    List<PlcHik> listRelationEquip(Long plcId);


    /**
     * 根据ID获取对象
     */
    PlcHik getPlcHilRelation(Long id);

    /**
     * 新增中間關係表
     */
    int insertPlcHik(PlcHik plcHik);

    /**
     * 删除中间表
     */
    int deletePlcHikById(Long id);

    /**
     * 更新中间表信息
     */
    int updatePlcHik(PlcHik plcHik);

    List<HikEquipment> findByIp(String ip);

    /**
     * 查询HIK 未綁定的海康設備
     *
     * @param hikEquipment HIK 海康設備
     * @return HIK 海康設備
     */
    List<HikEquipment> selectHikEquipmentNotBindList(HikEquipment hikEquipment);
}
