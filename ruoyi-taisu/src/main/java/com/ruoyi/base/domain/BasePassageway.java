package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 通道對象 base_passageway
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public class BasePassageway extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long passagewayId;

    /** 通道名稱 */
    @Excel(name = "通道名稱")
    private String name;

    /** 通道類型（車道/人道） */
    @Excel(name = "通道類型", readConverterExp = "車道=/人道")
    private String type;

    /** 模式（PLC控制/設備控制） */
    @Excel(name = "模式", readConverterExp = "PLC控制/設備控制")
    private String mode;

    /** 模式枚舉 */
    public enum modeEnum{PLC控制,設備控制}

    /** 是否可用 */
    private Long enable;

    /** 創建人 */
    @Excel(name = "創建人")
    private String creator;

    /** 門id */
    private Long doorId;

    /** 門名稱 */
    private String doorName;

    /** 方向（入/出） */
    @Excel(name = "方向")
    private String inOutMode;

    /** 方向枚舉 */
    public enum inOutModeEnum{入,出}

    /** 廠區id */
    private Long plantAreaId;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    private String plantAreaName;

    /** 海康設備數量 */
    private Integer hikEquipmentCount;

    /** 海康設備名稱 */
    private List<String> hikEquipmentNameList;

    /** 海康設備列表 */
    private List<HikEquipment> hikEquipmentList;

    /** 通道操作數量 */
    private Integer operationCount;

    /** 通道操作列表 */
    private List<BasePassagewayOperation> basePassagewayOperationList;

    public List<String> getHikEquipmentNameList() {
        return hikEquipmentNameList;
    }

    public void setHikEquipmentNameList(List<String> hikEquipmentNameList) {
        this.hikEquipmentNameList = hikEquipmentNameList;
    }

    public List<HikEquipment> getHikEquipmentList(){ return hikEquipmentList; }

    public void setHikEquipmentList(List<HikEquipment> hikEquipmentList){
        this.hikEquipmentList = hikEquipmentList;
    }
    public List<BasePassagewayOperation> getBasePassagewayOperationList(){ return basePassagewayOperationList; }

    public void setBasePassagewayOperationList(List<BasePassagewayOperation> basePassagewayOperationList){
        this.basePassagewayOperationList = basePassagewayOperationList;
    }

    public void setPassagewayId(Long passagewayId)
    {
        this.passagewayId = passagewayId;
    }

    public Long getPassagewayId()
    {
        return passagewayId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setMode(String mode) 
    {
        this.mode = mode;
    }

    public String getMode() 
    {
        return mode;
    }
    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setDoorId(Long doorId) 
    {
        this.doorId = doorId;
    }

    public Long getDoorId() 
    {
        return doorId;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public void setInOutMode(String inOutMode)
    {
        this.inOutMode = inOutMode;
    }

    public String getInOutMode() 
    {
        return inOutMode;
    }
    public void setPlantAreaId(Long plantAreaId)
    {
        this.plantAreaId = plantAreaId;
    }

    public Long getPlantAreaId()
    {
        return plantAreaId;
    }
    public void setPlantAreaName(String plantAreaName) 
    {
        this.plantAreaName = plantAreaName;
    }

    public String getPlantAreaName() 
    {
        return plantAreaName;
    }

    public Integer getHikEquipmentCount() {
        return hikEquipmentCount;
    }

    public void setHikEquipmentCount(Integer hikEquipmentCount) {
        this.hikEquipmentCount = hikEquipmentCount;
    }

    public Integer getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(Integer operationCount) {
        this.operationCount = operationCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("passagewayId", getPassagewayId())
            .append("name", getName())
            .append("type", getType())
            .append("mode", getMode())
            .append("enable", getEnable())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("updateTime", getUpdateTime())
            .append("doorId", getDoorId())
            .append("doorName", getDoorName())
            .append("inOutMode", getInOutMode())
            .append("plantAreaId", getPlantAreaId())
            .append("plantAreaName", getPlantAreaName())
            .append("hikEquipmentCount", getHikEquipmentCount())
            .toString();
    }
}
