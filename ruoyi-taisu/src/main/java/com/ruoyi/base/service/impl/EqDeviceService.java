package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.EqDevice;
import com.ruoyi.base.domain.HikEquipment;
import com.ruoyi.base.domain.PlcEquipment;
import com.ruoyi.base.enums.EqDeviceStatus;
import com.ruoyi.base.enums.EqType;
import com.ruoyi.base.mapper.HikEquipmentMapper;
import com.ruoyi.base.mapper.PlcEquipmentMapper;
import com.ruoyi.base.service.IEqDeviceService;
import com.ruoyi.base.service.IHikEquipmentService;
import com.ruoyi.base.service.IPlcEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Service
public class EqDeviceService implements IEqDeviceService {

    @Autowired
    PlcEquipmentMapper plcEquipmentMapper;
    @Autowired
    HikEquipmentMapper hikEquipmentMapper;

    @Override
    public List<EqDevice> getList() {
        List<EqDevice> list = new ArrayList<>();

        List<PlcEquipment> plcList = plcEquipmentMapper.selectPlcEquipmentList(null);
        List<HikEquipment> hikList = hikEquipmentMapper.selectHikEquipmentList(null);

        list.addAll(this.plcToDevice(plcList));
        list.addAll(this.hikToDevice(hikList));

        return list;
    }

    /**
     * 获取门禁主机Ping连通性状态：true 为连通, false 为网络不通
     **/

    public boolean getPingStatus(String Ip) {
        boolean status = false;
        try {
            status = InetAddress.getByName(Ip)
                    .isReachable(1000);

        } catch (Exception e) {

        }
//        if (status) {
//            System.out.println("设备连通");
//        } else {
//            System.out.println("设备网络不通");
//        }
        return status;
    }

    //plc轉device
    public List<EqDevice> plcToDevice(List<PlcEquipment> plcList){
        List<EqDevice> list=new ArrayList<>();
        for(PlcEquipment item:plcList){
            EqDevice vo=new EqDevice();
            vo.setEqType(EqType.PLC.getName());
            vo.setEqId(item.getId());
            vo.setEqName(item.getName());
            vo.setIp(item.getIp());
            vo.setPort(null);
            list.add(vo);
        }
        return list;
    }

    //hik轉device
    public List<EqDevice> hikToDevice(List<HikEquipment> hikList){
        List<EqDevice> list=new ArrayList<>();
        for(HikEquipment item:hikList){
            EqDevice vo=new EqDevice();
            vo.setEqType(EqType.HIK.getName());
            vo.setEqId(item.getId());
            vo.setEqName(item.getName());
            vo.setIp(item.getIp());
            vo.setPort(null);
            list.add(vo);
        }
        return list;
    }
}
