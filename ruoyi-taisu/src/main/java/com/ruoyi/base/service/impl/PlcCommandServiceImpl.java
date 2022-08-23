package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.domain.BaseDoor;
import com.ruoyi.base.domain.BasePassageway;
import com.ruoyi.base.domain.PlcCommandOperationSort;
import com.ruoyi.base.mapper.BasePassagewayMapper;
import com.ruoyi.base.mapper.PlcCommandOperationSortMapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.PlcCommandMapper;
import com.ruoyi.base.domain.PlcCommand;
import com.ruoyi.base.service.IPlcCommandService;

/**
 * PLC指令Service業務層處理
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Service
public class PlcCommandServiceImpl implements IPlcCommandService 
{
    @Autowired
    private PlcCommandMapper plcCommandMapper;

    @Autowired
    private PlcCommandOperationSortMapper plcCommandOperationSortMapper;

    /**
     * 查詢PLC指令
     * 
     * @param id PLC指令主鍵
     * @return PLC指令
     */
    @Override
    public PlcCommand selectPlcCommandById(Long id)
    {
        return plcCommandMapper.selectPlcCommandById(id);
    }

    /**
     * 查詢PLC指令列表
     * 
     * @param plcCommand PLC指令
     * @return PLC指令
     */
    @Override
    public List<PlcCommand> selectPlcCommandList(PlcCommand plcCommand)
    {
        return plcCommandMapper.selectPlcCommandList(plcCommand);
    }

    /**
     * 新增PLC指令
     * 
     * @param plcCommand PLC指令
     * @return 結果
     */
    @Override
    public int insertPlcCommand(PlcCommand plcCommand)
    {
        int result = 0;
        plcCommand.setCreateTime(DateUtils.getNowDate());
        result = plcCommandMapper.insertPlcCommand(plcCommand);
        return result;
    }

    /**
     * 修改PLC指令
     * 
     * @param plcCommand PLC指令
     * @return 結果
     */
    @Override
    public int updatePlcCommand(PlcCommand plcCommand)
    {
        int result = 0;
        plcCommand.setUpdateTime(DateUtils.getNowDate());
        result = plcCommandMapper.updatePlcCommand(plcCommand);
        return result;
    }

    /**
     * 批量刪除PLC指令
     * 
     * @param ids 需要刪除的PLC指令主鍵
     * @return 結果
     */
    @Override
    public int deletePlcCommandByIds(Long[] ids)
    {
        int result = 0;
        for(Long id : ids){
            result += deletePlcCommandById(id);
        }
        return result;
    }

    /**
     * 刪除PLC指令信息
     * 
     * @param id PLC指令主鍵
     * @return 結果
     */
    @Override
    public int deletePlcCommandById(Long id)
    {
        PlcCommandOperationSort plcCommandOperationSortParam = new PlcCommandOperationSort();
        plcCommandOperationSortParam.setCommandId(id);
        List<PlcCommandOperationSort> plcCommandOperationSortList = plcCommandOperationSortMapper.selectPlcCommandOperationSortList(plcCommandOperationSortParam);
        for(PlcCommandOperationSort plcCommandOperationSort : plcCommandOperationSortList){
            plcCommandOperationSortMapper.deletePlcCommandOperationSortBySortId(plcCommandOperationSort.getSortId());
        }
        return plcCommandMapper.deletePlcCommandById(id);
    }
}
