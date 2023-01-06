package com.ruoyi.base.interact;

import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.mapper.EquipmentMapper;
import com.ruoyi.base.mapper.ManWorkMapper;
import com.ruoyi.base.service.IPlcEquipmentService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 兴跃
 */
@Component
public class UserJurisdiction {

    @Autowired
    private EquipmentMapper equipmentMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private IPlcEquipmentService plcEquipmentService;

    /**
     * 员工拿到厂区的权限
     * 05-26
     * 员工厂区权限分两种情况
     * 1：员工只选择厂区，权限为整个厂区的所有权限
     * 2：员工选择了厂区，同时也选择了通道，权限为通道PLC的所有权限
     *
     * @param sysUser
     * @return
     */
    public List<String> getCodeByUser(SysUser sysUser) {

        //查询到员工的厂区(多个)
        SysUser user = userMapper.selectUserById(sysUser.getUserId());
        if (StringUtils.isBlank(user.getFactoryId())) {
            return null;
        }
        List<Long> longList = new ArrayList<>();
        List<String> indexNos = new ArrayList<>();
        //通道不为空，权限根据通道下发
        if (StringUtils.isNotBlank(user.getPlc())) {
            String[] split = user.getPlc().split(",");
            for (String plc : split) {
                longList.add(new Long(plc));
            }
            List<PlcEquipment> plcEquipmentById = plcEquipmentService.getPlcEquipmentById(longList);
            for (PlcEquipment plcEquipment : plcEquipmentById) {
                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
                //修改查找plc下车辆设备
//                indexNos.addAll(equipmentMapper.listCarEquipmentCode(plcEquipment.getIp()));
            }
        }else {
            //通道为空，根据厂区权限
            String[] split = user.getFactoryId().split(",");
            //根据厂区拿PLC(in)
            for (String factory : split) {
                longList.add(new Long(factory));
            }
            List<PlcEquipment> plcEquipmentByDept = plcEquipmentService.getPlcEquipmentByDept(longList);
            for (PlcEquipment plcEquipment : plcEquipmentByDept) {
                //修改查找plc下车辆设备
//                indexNos.addAll(equipmentMapper.listCarEquipmentCode(plcEquipment.getIp()));
                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
            }
        }
        return indexNos;
    }

    public List<String> getCodeByUserNew(SysUser sysUser) {

        //查询到员工的厂区(多个)
        SysUser user = userMapper.selectUserById(sysUser.getUserId());
        if (StringUtils.isBlank(user.getFactoryId())) {
            return null;
        }
        List<Long> longList = new ArrayList<>();
        List<String> indexNos = new ArrayList<>();
        //通道不为空，权限根据通道下发
        if (StringUtils.isNotBlank(user.getPlc())) {
            String[] split = user.getPlc().split(",");
            for (String plc : split) {
                longList.add(new Long(plc));
            }
            List<PlcEquipment> plcEquipmentById = plcEquipmentService.getPlcEquipmentById(longList);
            for (PlcEquipment plcEquipment : plcEquipmentById) {
                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
                //修改查找plc下车辆设备
//                indexNos.addAll(equipmentMapper.listCarEquipmentCode(plcEquipment.getIp()));
            }
        }else {
            //通道为空，根据厂区权限
//            String[] split = user.getFactoryId().split(",");
//            //根据厂区拿PLC(in)
//            for (String factory : split) {
//                longList.add(new Long(factory));
//            }
//            List<PlcEquipment> plcEquipmentByDept = plcEquipmentService.getPlcEquipmentByDept(longList);
//            for (PlcEquipment plcEquipment : plcEquipmentByDept) {
//                //修改查找plc下车辆设备
////                indexNos.addAll(equipmentMapper.listCarEquipmentCode(plcEquipment.getIp()));
//                indexNos.addAll(equipmentMapper.listPersonEquipmentCode(plcEquipment.getIp()));
//            }
        }
        return indexNos;
    }
}
