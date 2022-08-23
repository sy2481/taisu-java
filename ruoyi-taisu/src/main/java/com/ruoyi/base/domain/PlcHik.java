package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 中间-道闸与设备对象 plc_hik
 *
 * @author ruoyi
 * @date 2022-03-10
 */
public class PlcHik extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long plcId;

    /**
     * 人脸设备id
     */
    @Excel(name = "人脸设备id")
    private Long personDeviceId;

    /**
     * 车辆设备id
     */
    @Excel(name = "车辆设备id")
    private Long carDeviceId;

    /**
     * 0-人道设备关系, 1-车道设备关系
     */
    @Excel(name = "类型")
    private Long deviceType;

    /**
     * plc指令
     */
    @Excel(name = "plc指令")
    private String plcCommand;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String deviceName;

    /**
     * 车辆设备
     */
    private HikEquipment carEquip;

    /**
     * 人脸设备
     */
    private HikEquipment personEquip;

    public HikEquipment getCarEquip() {
        return carEquip;
    }

    public void setCarEquip(HikEquipment carEquip) {
        this.carEquip = carEquip;
    }

    public HikEquipment getPersonEquip() {
        return personEquip;
    }

    public void setPersonEquip(HikEquipment personEquip) {
        this.personEquip = personEquip;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPlcId(Long plcId) {
        this.plcId = plcId;
    }

    public Long getPlcId() {
        return plcId;
    }

    public void setPersonDeviceId(Long personDeviceId) {
        this.personDeviceId = personDeviceId;
    }

    public Long getPersonDeviceId() {
        return personDeviceId;
    }

    public void setCarDeviceId(Long carDeviceId) {
        this.carDeviceId = carDeviceId;
    }

    public Long getCarDeviceId() {
        return carDeviceId;
    }

    public void setDeviceType(Long deviceType) {
        this.deviceType = deviceType;
    }

    public Long getDeviceType() {
        return deviceType;
    }

    public void setPlcCommand(String plcCommand) {
        this.plcCommand = plcCommand;
    }

    public String getPlcCommand() {
        return plcCommand;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("plcId", getPlcId())
                .append("personDeviceId", getPersonDeviceId())
                .append("carDeviceId", getCarDeviceId())
                .append("deviceType", getDeviceType())
                .append("plcCommand", getPlcCommand())
                .append("deviceName", getDeviceName())
                .append("createTime", getCreateTime())
                .toString();
    }
}
