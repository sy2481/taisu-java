package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * PLC指令对象 plc_command
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public class PlcCommand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** commandId */
    private Long commandId;

    /** plc的id */
    private Long plcId;

    /** plc名稱 */
    private String plcName;

    /** 指令名稱 */
    @Excel(name = "指令名稱")
    private String name;

    /** 指令值 */
    @Excel(name = "指令值")
    private String command;

    /** 創建人 */
    @Excel(name = "創建人")
    private String creator;

    /** 是否啓用 */
    @Excel(name = "是否啓用")
    private Long enable;

    /** 開關模式 */
    @Excel(name = "開關模式")
    private String openCloseMode;

    /** 門名稱 */
    private String doorName;

    /** 廠區名稱 */
    private String plandAreaName;

    public void setCommandId(Long commandId)
    {
        this.commandId = commandId;
    }

    public Long getCommandId()
    {
        return commandId;
    }
    public void setPlcId(Long plcId) 
    {
        this.plcId = plcId;
    }

    public Long getPlcId() 
    {
        return plcId;
    }

    public String getPlcName() {
        return plcName;
    }

    public void setPlcName(String plcName) {
        this.plcName = plcName;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCommand(String command) 
    {
        this.command = command;
    }

    public String getCommand() 
    {
        return command;
    }
    public void setCreator(String creator) 
    {
        this.creator = creator;
    }

    public String getCreator() 
    {
        return creator;
    }
    public void setEnable(Long enable) 
    {
        this.enable = enable;
    }

    public Long getEnable() 
    {
        return enable;
    }
    public void setOpenCloseMode(String openCloseMode) 
    {
        this.openCloseMode = openCloseMode;
    }

    public String getOpenCloseMode() 
    {
        return openCloseMode;
    }

    public void setDoorName(String doorName)
    {
        this.doorName = doorName;
    }

    public String getDoorName()
    {
        return doorName;
    }

    public void setPlandAreaName(String plandAreaName)
    {
        this.plandAreaName = plandAreaName;
    }

    public String getPlandAreaName()
    {
        return plandAreaName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("commandId", getCommandId())
            .append("plcId", getPlcId())
            .append("plcName", getPlcName())
            .append("name", getName())
            .append("command", getCommand())
            .append("createTime", getCreateTime())
            .append("creator", getCreator())
            .append("updateTime", getUpdateTime())
            .append("enable", getEnable())
            .append("openCloseMode", getOpenCloseMode())
            .append("doorName", getDoorName())
            .append("plandAreaName", getPlandAreaName())
            .toString();
    }
}
