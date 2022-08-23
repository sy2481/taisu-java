package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 門對象 base_door
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public class BaseDoor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** doorId */
    private Long doorId;

    /** 門名稱 */
    @Excel(name = "門名稱")
    private String name;

    /** 類型（車道/人道） */
    @Excel(name = "類型", readConverterExp = "車道=/人道")
    private String type;

    /** 類型枚舉 */
    public enum typeEnum{車道,人道}

    /** 廠區id */
    private Long plantAreaId;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    private String plantAreaName;

    /** 創建人 */
    @Excel(name = "創建人")
    private String creator;

    /** 通道數量 */
    private Integer passagewayCount;

    public void setDoorId(Long doorId)
    {
        this.doorId = doorId;
    }

    public Long getDoorId()
    {
        return doorId;
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
    public void setPlantAreaId(Long plantAreaId) 
    {
        this.plantAreaId = plantAreaId;
    }

    public Long getPlantAreaId() 
    {
        return plantAreaId;
    }

    public String getPlantAreaName() {
        return plantAreaName;
    }

    public void setPlantAreaName(String plantAreaName) {
        this.plantAreaName = plantAreaName;
    }

    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }

    public Integer getPassagewayCount() {
        return passagewayCount;
    }

    public void setPassagewayCount(Integer passagewayCount) {
        this.passagewayCount = passagewayCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("doorId", getDoorId())
            .append("name", getName())
            .append("type", getType())
            .append("plantAreaId", getPlantAreaId())
            .append("getPlantAreaName", getPlantAreaName())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("updateTime", getUpdateTime())
            .append("passagewayCount", getPassagewayCount())
            .toString();
    }
}
