package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 设备在线记录对象 equipment_log
 *
 * @author ruoyi
 * @date 2022-04-27
 */
public class EquipmentLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**ID */
    private Long id;

    /** 设备ip */
    @Excel(name = "设备ip")
    private String ip;

    /** 上下线 */
    @Excel(name = "上下线")
    private Integer upDownType;

    /** 廠區ID */
    @Excel(name = "廠區ID")
    private Long deptId;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipment;
    /**
     * 設備名稱
     */
    private String equipmentName;
    /**
     * 廠區名稱
     */
    private String deptName;

    /**
     * 廠區ID虛擬
     */
    private List<Long> factoryId;
    public List<Long> getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(List<Long> factoryId) {
        this.factoryId = factoryId;
    }



    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setUpDownType(Integer upDownType)
    {
        this.upDownType = upDownType;
    }

    public Integer getUpDownType()
    {
        return upDownType;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setEquipment(String equipment)
    {
        this.equipment = equipment;
    }

    public String getEquipment()
    {
        return equipment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ip", getIp())
                .append("upDownType", getUpDownType())
                .append("createTime", getCreateTime())
                .append("deptId", getDeptId())
                .append("equipment", getEquipment())
                .toString();
    }
}
