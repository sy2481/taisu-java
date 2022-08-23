package com.ruoyi.base.mapper;

import com.ruoyi.base.bo.PlcRelationBO;
import com.ruoyi.base.domain.HikEquipment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备权限相关资料
 * @author shiva   2022/3/9 22:07
 */
public interface EquipmentMapper {

    /**
     * 根据PLC ip，查询全部下属的 车道设备号
     */
    List<String> listCarEquipmentCode(@Param("ip") String ip);

    /**
     * 根据PLC ip，查询全部下属的 人道设备号
     */
    List<String> listPersonEquipmentCode(@Param("ip") String ip);


    List<PlcRelationBO> listPlcRelation();

    HikEquipment getById(@Param("id") Long id);

    List<HikEquipment> findByFrontIp(@Param("ip") String ip);

    List<String> findLocationEquipList();

    HikEquipment findByIp(@Param("ip") String ip);

    List<HikEquipment> listLocationEquip();

    /**
     * 根据类型获取设备列表
     */
    List<HikEquipment> listEquipByType(Integer type);
}
