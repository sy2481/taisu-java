package com.ruoyi.base.domain;

import java.util.List;

public class PlcCommandOperationSortBatch {
    /** 通道操作id */
    private Long operationId;

    /** 指令及順序 */
    private List<PlcCommandOperationSortCommand> plcCommandOperationSortCommandList;

    public void setOperationId(Long operationId)
    {
        this.operationId = operationId;
    }

    public Long getOperationId()
    {
        return operationId;
    }

    public void setPlcCommandOperationSortCommandList(List<PlcCommandOperationSortCommand> plcCommandOperationSortCommandList)
    {
        this.plcCommandOperationSortCommandList = plcCommandOperationSortCommandList;
    }

    public List<PlcCommandOperationSortCommand> getPlcCommandOperationSortCommandList()
    {
        return plcCommandOperationSortCommandList;
    }
}
