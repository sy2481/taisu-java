package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * plc指令順序對象 plc_command_operation_sort
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
public class PlcCommandOperationSort extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 指令順序id */
    private Long sortId;

    /** 通道操作id */
    private Long operationId;

    /** plc指令id */
    private Long commandId;

    /** plc的id */
    private Long plcId;

    /** plc名稱 */
    private String plcName;

    /** plc指令類型 */
    private String commandOpenCloseMode;

    /** 指令順序 */
    @Excel(name = "指令順序")
    private Long sort;

    public void setSortId(Long sortId) 
    {
        this.sortId = sortId;
    }

    public Long getSortId() 
    {
        return sortId;
    }
    public void setOperationId(Long operationId) 
    {
        this.operationId = operationId;
    }

    public Long getOperationId() 
    {
        return operationId;
    }
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
    public void setPlcName(String plcName)
    {
        this.plcName = plcName;
    }

    public String getPlcName()
    {
        return plcName;
    }
    public void setCommandOpenCloseMode(String commandOpenCloseMode)
    {
        this.commandOpenCloseMode = commandOpenCloseMode;
    }

    public String getCommandOpenCloseMode()
    {
        return commandOpenCloseMode;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sortId", getSortId())
            .append("operationId", getOperationId())
            .append("commandId", getCommandId())
            .append("commandOpenCloseMode", getCommandOpenCloseMode())
            .append("sort", getSort())
            .toString();
    }
}
