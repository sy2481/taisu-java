package com.ruoyi.web.api.bo;

import com.ruoyi.base.bo.ControlJSONBo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2022/3/12 21:56
 */
@Data
public class EquipmentBO implements Serializable {

    //plc指令
    private String plcCommand;

    //plc设备ip
    private String plcIp;

    //plc设备名称
    private String plcName;

    //plc设备端口
    private String plcPort;

    /////////////////////////////////////////////////////////////////////

    //车道和人道绑定的设备例如：
    // 1、如果本记录是车道设备，那个这个字段为绑定的人脸设备号
    // 2、如果本记录是被车道设备绑定的人脸设备，那这个字段为绑定的车道设备号
    private String bindIndexCode;

    // 0-普通设备、1-绑定定位卡设备、2-车道绑定的人脸设备
    private String deviceAttribute;

    //当类型为0的时候代表人脸设备，1的时候代表车辆设备，默认为0
    private Long deviceType;

    //设备号
    private String indexCode;

    //设备名称
    private String name;

    //1-进 2-出
    private Long sign;

    //LED

    private String ledCode;

    //字幕机Ip
    private String subtitleMachineIp;

    //设备IP
    private String ip;
    /**
     * PLC门禁方式
     */
    private ControlJSONBo controlJSONBo;

    /**
     * 进出有效时间
     */
    private List<TimeBo> timeList;




    /** 通道管制是否开启，0-开启，1-关闭 */
    private Long controlType;

}
