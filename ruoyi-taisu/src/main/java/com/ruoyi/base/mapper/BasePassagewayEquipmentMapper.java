package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.BasePassagewayEquipment;

import java.util.List;

/**
 * 通道設備Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface BasePassagewayEquipmentMapper
{
    /**
     * 查询通道設備
     *
     * @param passagewayId 通道主鍵
     * @return 通道設備
     */
    public BasePassagewayEquipment selectBasePassagewayEquipmentByPassagewayId(Long passagewayId);

    /**
     * 查询通道設備
     *
     * @param hikEquipmentId 設備主鍵
     * @return 通道設備
     */
    public BasePassagewayEquipment selectBasePassagewayEquipmentByHikEquipmentId(Long hikEquipmentId);

    /**
     * 查询通道設備列表
     *
     * @param basePassagewayEquipment 通道設備
     * @return 通道設備集合
     */
    public List<BasePassagewayEquipment> selectBasePassagewayEquipmentList(BasePassagewayEquipment basePassagewayEquipment);

    /**
     * 新增通道設備
     *
     * @param basePassagewayEquipment 通道設備
     * @return 結果
     */
    public int insertBasePassagewayEquipment(BasePassagewayEquipment basePassagewayEquipment);

    /**
     * 批量新增通道設備
     *
     * @param basePassagewayEquipmentList 通道設備列表
     * @return 結果
     */
    public int batchInsertBasePassagewayEquipment(List<BasePassagewayEquipment> basePassagewayEquipmentList);

    /**
     * 修改通道設備
     *
     * @param basePassagewayEquipment 通道設備
     * @return 結果
     */
    public int updateBasePassagewayEquipment(BasePassagewayEquipment basePassagewayEquipment);

    /**
     * 删除通道設備
     *
     * @param passagewayId 通道主鍵
     * @return 結果
     */
    public int deleteBasePassagewayEquipmentById(Long passagewayId);

    /**
     * 批量删除通道設備
     *
     * @param ids 需要删除的数据主鍵集合
     * @return 結果
     */
    public int deleteBasePassagewayEquipmentByIds(Long[] ids);
}
