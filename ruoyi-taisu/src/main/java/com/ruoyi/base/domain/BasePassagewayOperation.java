package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 通道操作對象 base_passageway_operation
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
public class BasePassagewayOperation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 操作id */
    private Long operationId;

    /** 廠區id */
    private Long plantAreaId;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    private String plantAreaName;

    /** 門id */
    private Long doorId;

    /** 門名稱 */
    @Excel(name = "門名稱")
    private String doorName;

    /** 通道id */
    private Long passagewayId;

    /** 通道名稱 */
    @Excel(name = "通道名稱")
    private String passagewayName;

    /** 操作名稱 */
    @Excel(name = "操作名稱")
    private String name;

    /** 操作類型 */
    @Excel(name = "操作類型")
    private String type;

    /** 是否啟用 */
    @Excel(name = "是否啟用")
    private Long enable;

    /** 創建人 */
    @Excel(name = "創建人")
    private String creator;

    /** plc指令順序 */
    private List<PlcCommandOperationSort> plcCommandOperationSortList;

    public void setOperationId(Long operationId) 
    {
        this.operationId = operationId;
    }

    public Long getOperationId() 
    {
        return operationId;
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
    public void setDoorId(Long doorId)
    {
        this.doorId = doorId;
    }

    public Long getDoorId()
    {
        return doorId;
    }
    public void setDoorName(String doorName)
    {
        this.doorName = doorName;
    }

    public String getDoorName()
    {
        return doorName;
    }
    public void setPassagewayName(String passagewayName)
    {
        this.passagewayName = passagewayName;
    }

    public String getPassagewayName()
    {
        return passagewayName;
    }
    public void setPlcCommandOperationSortList(List<PlcCommandOperationSort> plcCommandOperationSortList){
        this.plcCommandOperationSortList = plcCommandOperationSortList;
    }
    public List<PlcCommandOperationSort> getPlcCommandOperationSortList()
    {
        return plcCommandOperationSortList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("operationId", getOperationId())
            .append("passagewayId", getPassagewayId())
            .append("name", getName())
            .append("type", getType())
            .append("enable", getEnable())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("updateTime", getUpdateTime())
            .append("plantAreaId", getPlantAreaId())
            .append("plantAreaName", getPlantAreaName())
            .append("doorId", getDoorId())
            .append("doorName", getDoorName())
            .append("passagewayName", getPassagewayName())
            .toString();
    }
}
