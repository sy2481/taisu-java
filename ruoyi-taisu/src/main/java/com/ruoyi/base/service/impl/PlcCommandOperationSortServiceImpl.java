package com.ruoyi.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.base.domain.*;
import com.ruoyi.base.mapper.BasePassagewayOperationMapper;
import com.ruoyi.base.mapper.PlcCommandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.PlcCommandOperationSortMapper;
import com.ruoyi.base.service.IPlcCommandOperationSortService;

/**
 * plc指令順序Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
@Service
public class PlcCommandOperationSortServiceImpl implements IPlcCommandOperationSortService 
{
    @Autowired
    private PlcCommandOperationSortMapper plcCommandOperationSortMapper;

    @Autowired
    private BasePassagewayOperationMapper basePassagewayOperationMapper;

    @Autowired
    private PlcCommandMapper plcCommandMapper;

    /**
     * 查询plc指令順序
     * 
     * @param sortId plc指令順序主键
     * @return plc指令順序
     */
    @Override
    public PlcCommandOperationSort selectPlcCommandOperationSortBySortId(Long sortId)
    {
        return plcCommandOperationSortMapper.selectPlcCommandOperationSortBySortId(sortId);
    }

    /**
     * 查询plc指令順序列表
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return plc指令順序
     */
    @Override
    public List<PlcCommandOperationSort> selectPlcCommandOperationSortList(PlcCommandOperationSort plcCommandOperationSort)
    {
        return plcCommandOperationSortMapper.selectPlcCommandOperationSortList(plcCommandOperationSort);
    }

    /**
     * 新增plc指令順序
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return 结果
     */
    @Override
    public int insertPlcCommandOperationSort(PlcCommandOperationSort plcCommandOperationSort)
    {
        return plcCommandOperationSortMapper.insertPlcCommandOperationSort(plcCommandOperationSort);
    }

    /**
     * 批量新增plc指令順序
     *
     * @param plcCommandOperationSortBatch plc指令順序
     * @return 结果
     */
    @Override
    public int insertPlcCommandOperationSortBatch(PlcCommandOperationSortBatch plcCommandOperationSortBatch)
    {
        int result = 0;

        BasePassagewayOperation basePassagewayOperation = basePassagewayOperationMapper.selectBasePassagewayOperationByOperationId(plcCommandOperationSortBatch.getOperationId());

        PlcCommandOperationSort plcCommandOperationSort = new PlcCommandOperationSort();
        plcCommandOperationSort.setOperationId(basePassagewayOperation.getOperationId());
        List<PlcCommandOperationSort> plcCommandOperationSortList = plcCommandOperationSortMapper.selectPlcCommandOperationSortList(plcCommandOperationSort);
        for(PlcCommandOperationSort commandOperationSort : plcCommandOperationSortList){
            result += plcCommandOperationSortMapper.deletePlcCommandOperationSortBySortId(commandOperationSort.getSortId());
        }

        if(plcCommandOperationSortBatch.getPlcCommandOperationSortCommandList().size()>0){
            for(PlcCommandOperationSortCommand plcCommandOperationSortCommand : plcCommandOperationSortBatch.getPlcCommandOperationSortCommandList()){
                PlcCommand plcCommand = plcCommandMapper.selectPlcCommandById(plcCommandOperationSortCommand.getCommandId());

                PlcCommandOperationSort newPlcCommandOperationSort = new PlcCommandOperationSort();
                newPlcCommandOperationSort.setOperationId(basePassagewayOperation.getOperationId());
                newPlcCommandOperationSort.setCommandId(plcCommand.getCommandId());
                newPlcCommandOperationSort.setSort(plcCommandOperationSortCommand.getSort());

                result += plcCommandOperationSortMapper.insertPlcCommandOperationSort(newPlcCommandOperationSort);
            }
        }
        else{
            result = 1;
            return result;
        }

        return result;
    }

    /**
     * 修改plc指令順序
     * 
     * @param plcCommandOperationSort plc指令順序
     * @return 结果
     */
    @Override
    public int updatePlcCommandOperationSort(PlcCommandOperationSort plcCommandOperationSort)
    {
        return plcCommandOperationSortMapper.updatePlcCommandOperationSort(plcCommandOperationSort);
    }

    /**
     * 批量删除plc指令順序
     * 
     * @param sortIds 需要删除的plc指令順序主键
     * @return 结果
     */
    @Override
    public int deletePlcCommandOperationSortBySortIds(Long[] sortIds)
    {
        return plcCommandOperationSortMapper.deletePlcCommandOperationSortBySortIds(sortIds);
    }

    /**
     * 删除plc指令順序信息
     * 
     * @param sortId plc指令順序主键
     * @return 结果
     */
    @Override
    public int deletePlcCommandOperationSortBySortId(Long sortId)
    {
        return plcCommandOperationSortMapper.deletePlcCommandOperationSortBySortId(sortId);
    }
}
