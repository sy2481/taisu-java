package com.ruoyi.base.domain;

import com.ruoyi.common.annotation.Excel;

import java.util.List;

public class PlcCommandOperationSortCommand {
    /** plc指令Id */
    private Long commandId;

    /** 指令順序 */
    @Excel(name = "指令順序")
    private Long sort;

    public void setCommandId(Long commandId)
    {
        this.commandId = commandId;
    }

    public Long getCommandId()
    {
        return commandId;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
}
