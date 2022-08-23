package com.ruoyi.base.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.base.utils.PlcRedisUtils;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.EquipmentLogMapper;
import com.ruoyi.base.domain.EquipmentLog;
import com.ruoyi.base.service.IEquipmentLogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备在线记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-27
 */
@Service
public class EquipmentLogServiceImpl implements IEquipmentLogService {
    @Autowired
    private EquipmentLogMapper equipmentLogMapper;
    @Autowired
    private IPlcEquipmentService plcEquipmentService;
    @Autowired
    private IHikEquipmentService hikEquipmentService;
    @Autowired
    private PlcRedisUtils plcRedisUtils;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询设备在线记录
     *
     * @param id 设备在线记录主键
     * @return 设备在线记录
     */
    @Override
    public EquipmentLog selectEquipmentLogById(Long id) {
        return equipmentLogMapper.selectEquipmentLogById(id);
    }

    /**
     * 查询设备在线记录列表
     *
     * @param equipmentLog 设备在线记录
     * @return 设备在线记录
     */
    @Override
    public List<EquipmentLog> selectEquipmentLogList(EquipmentLog equipmentLog) {

        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList!=null){
            equipmentLog.setFactoryId(longList);
        }
        return equipmentLogMapper.selectEquipmentLogList(equipmentLog);
    }

    /**
     * 新增设备在线记录
     *
     * @param equipmentLog 设备在线记录
     * @return 结果
     */
    @Override
    public int insertEquipmentLog(EquipmentLog equipmentLog) {
        equipmentLog.setCreateTime(DateUtils.getNowDate());
        return equipmentLogMapper.insertEquipmentLog(equipmentLog);
    }

    /**
     * 修改设备在线记录
     *
     * @param equipmentLog 设备在线记录
     * @return 结果
     */
    @Override
    public int updateEquipmentLog(EquipmentLog equipmentLog) {
        return equipmentLogMapper.updateEquipmentLog(equipmentLog);
    }

    /**
     * 批量删除设备在线记录
     *
     * @param ids 需要删除的设备在线记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentLogByIds(Long[] ids) {
        return equipmentLogMapper.deleteEquipmentLogByIds(ids);
    }

    /**
     * 删除设备在线记录信息
     *
     * @param id 设备在线记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentLogById(Long id) {
        return equipmentLogMapper.deleteEquipmentLogById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int addLogInfo(String ip, Integer type) {
        EquipmentLog equipmentLog = new EquipmentLog();
        equipmentLog.setIp(ip);
        equipmentLog.setUpDownType(type);
        equipmentLog.setCreateTime(new Date());
        //根据IP查询PLC
        PlcEquipment plcEquipment = plcRedisUtils.getPlcEquipment(ip);
        if (plcEquipment != null) {
            SysDept sysDept = sysDeptService.selectDeptById(plcEquipment.getPlantAreaId());
            equipmentLog.setDeptName(sysDept.getDeptName());
            equipmentLog.setDeptId(plcEquipment.getPlantAreaId());
            equipmentLog.setEquipment("PLC設備");
            equipmentLog.setEquipmentName(plcEquipment.getName());
        } else {
            List<HikEquipment> hikEquipmentList = hikEquipmentService.findByIp(ip);
            if (hikEquipmentList.size() > 0) {
                HikEquipment hikEquipment = hikEquipmentList.get(0);
                PlcEquipment equipment = plcEquipmentService.findByIp(hikEquipment.getFrontIp());
                equipmentLog.setDeptId(equipment.getPlantAreaId());
                SysDept sysDept = sysDeptService.selectDeptById(equipment.getPlantAreaId());
                equipmentLog.setDeptName(sysDept.getDeptName());
                equipmentLog.setEquipmentName(hikEquipment.getName());
                if (hikEquipment.getDeviceType() == 0) {
                    equipmentLog.setEquipment("人臉設備");
                } else if (hikEquipment.getDeviceType() == 1) {
                    equipmentLog.setEquipment("車輛設備");
                } else {
                    equipmentLog.setEquipment("定位卡設備");
                }
            }
        }
        return equipmentLogMapper.insertEquipmentLog(equipmentLog);
    }
}
