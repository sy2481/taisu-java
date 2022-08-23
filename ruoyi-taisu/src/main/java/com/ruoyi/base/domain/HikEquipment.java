package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * HIK 海康设备对象 hik_equipment
 *
 * @author ruoyi
 * @date 2022-03-10
 */
public class HikEquipment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    @Excel(name = "设备名称")
    private String name;

    @Excel(name = "ip")
    private String ip;

    /**
     * 1-进 2-出
     */
    @Excel(name = "1-进 2-出")
    private Long sign;

    /**
     * 设备号
     */
    @Excel(name = "设备号")
    private String indexCode;

    /**
     * 前端设备ip
     */
    @Excel(name = "前端设备ip")
    private String frontIp;

    @Excel(name = "当类型为0的时候代表人脸设备，1的时候代表车辆设备，默认为0")
    private Long deviceType;

    /**
     * 字幕机Ip
     */
    @Excel(name = "字幕机Ip")
    private String subtitleMachineIp;

    /**
     * 车道LED编号
     */
    private String ledCode;

    /**
     *  门状态
     */
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLedCode() {
        return ledCode;
    }

    public void setLedCode(String ledCode) {
        this.ledCode = ledCode;
    }


    // 0-普通设备、1-绑定定位卡设备、2-车道绑定的人脸设备
    private String equipmentUseType;

    /**
     * 通道id
     */
    private Long passagewayId;

    public String getEquipmentUseType() {
        return equipmentUseType;
    }

    public void setEquipmentUseType(String equipmentUseType) {
        this.equipmentUseType = equipmentUseType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public Long getSign() {
        return sign;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setFrontIp(String frontIp) {
        this.frontIp = frontIp;
    }

    public String getFrontIp() {
        return frontIp;
    }

    public void setDeviceType(Long deviceType) {
        this.deviceType = deviceType;
    }

    public Long getDeviceType() {
        return deviceType;
    }

    public void setSubtitleMachineIp(String subtitleMachineIp) {
        this.subtitleMachineIp = subtitleMachineIp;
    }

    public String getSubtitleMachineIp() {
        return subtitleMachineIp;
    }

    public void setPassagewayId(Long passagewayId) {
        this.passagewayId = passagewayId;
    }

    public Long getPassagewayId() {
        return passagewayId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("ip", getIp())
                .append("sign", getSign())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("indexCode", getIndexCode())
                .append("frontIp", getFrontIp())
                .append("deviceType", getDeviceType())
                .append("subtitleMachineIp", getSubtitleMachineIp())
                .append("passagewayId", getPassagewayId())
                .toString();
    }
}
