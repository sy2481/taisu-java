package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcHik;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * HIK 海康设备Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-06
 */
public interface HikEquipmentMapper {
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
     * 删除HIK 海康设备
     *
     * @param id HIK 海康设备主键
     * @return 结果
     */
    public int deleteHikEquipmentById(Long id);

    /**
     * 批量删除HIK 海康设备
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHikEquipmentByIds(Long[] ids);

    List<PlcHik> listRelationEquip(Long plcId);

    public int deletePlcHikById(Long id);

    PlcHik getPlcHilRelation(Long id);

    //插入中间表
    void insertPlcHik(PlcHik plcHik);

    //更新中间表
    void updatePlcHik(PlcHik plcHik);

    //更新设备的 front IP 字段
    void updateFrontIpById(@Param("id") Long id, @Param("frontIp") String frontIp);



    List<HikEquipment> findByIp(@Param("ip") String ip);

    /**
     * 查询HIK 未綁定的海康設備
     *
     * @param hikEquipment 海康設備
     * @return HIK 海康設備
     */
    List<HikEquipment> selectHikEquipmentNotBindList(HikEquipment hikEquipment);
}
