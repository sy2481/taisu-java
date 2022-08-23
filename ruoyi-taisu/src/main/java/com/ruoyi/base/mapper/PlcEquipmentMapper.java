package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.PlcEquipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PLC 道闸Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-05
 */
public interface PlcEquipmentMapper {
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
     * 删除PLC 道闸
     *
     * @param id PLC 道闸主键
     * @return 结果
     */
    public int deletePlcEquipmentById(Long id);

    /**
     * 批量删除PLC 道闸
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlcEquipmentByIds(Long[] ids);

    /**
     * 根據IP查詢
     */
    public  PlcEquipment findByIp(@Param("ip") String ip);

    public List<PlcEquipment> getPlcEquipmentByDept(@Param("factoryIdList") List<Long> factoryIdList);

    /**
     * 根据id查询
     * @param factoryIdList
     * @return
     */
    public List<PlcEquipment> getPlcEquipmentById(@Param("factoryIdList") List<Long> factoryIdList);
    /**
     * 根据厂区ID非危险
     */
    List<PlcEquipment> getPlcEquipmentByDeptId(@Param("plantAreaId")Long plantAreaId);


}
