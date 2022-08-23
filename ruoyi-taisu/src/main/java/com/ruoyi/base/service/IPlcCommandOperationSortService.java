package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.PlcCommandOperationSort;
import com.ruoyi.base.domain.PlcCommandOperationSortBatch;

/**
 * plc指令順序Service接口
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
public interface IPlcCommandOperationSortService 
{
    /**
     * 查询plc指令順序
     * 
     * @param sortId plc指令順序主键
     * @return plc指令順序
     */
    public PlcCommandOperationSort selectPlcCommandOperationSortBySortId(Long sortId);

    /**
     * 查询plc指令順序列表
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return plc指令順序集合
     */
    public List<PlcCommandOperationSort> selectPlcCommandOperationSortList(PlcCommandOperationSort plcCommandOperationSort);

    /**
     * 新增plc指令順序
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return 结果
     */
    public int insertPlcCommandOperationSort(PlcCommandOperationSort plcCommandOperationSort);

    /**
     * 修改plc指令順序
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return 结果
     */
    public int updatePlcCommandOperationSort(PlcCommandOperationSort plcCommandOperationSort);

    /**
     * 批量删除plc指令順序
     * 
     * @param sortIds 需要删除的plc指令順序主键集合
     * @return 结果
     */
    public int deletePlcCommandOperationSortBySortIds(Long[] sortIds);

    /**
     * 删除plc指令順序信息
     * 
     * @param sortId plc指令順序主键
     * @return 结果
     */
    public int deletePlcCommandOperationSortBySortId(Long sortId);

    /**
     * 批量新增plc指令順序
     *
     * @param plcCommandOperationSortBatch plc指令順序
     * @return 结果
     */
    public int insertPlcCommandOperationSortBatch(PlcCommandOperationSortBatch plcCommandOperationSortBatch);
}
