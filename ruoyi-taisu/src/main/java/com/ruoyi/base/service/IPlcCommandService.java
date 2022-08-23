package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.PlcCommand;

/**
 * PLC指令Service接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface IPlcCommandService 
{
    /**
     * 查詢PLC指令
     * 
     * @param id PLC指令主鍵
     * @return PLC指令
     */
    public PlcCommand selectPlcCommandById(Long id);

    /**
     * 查詢PLC指令列表
     * 
     * @param plcCommand PLC指令
     * @return PLC指令集合
     */
    public List<PlcCommand> selectPlcCommandList(PlcCommand plcCommand);

    /**
     * 新增PLC指令
     * 
     * @param plcCommand PLC指令
     * @return 結果
     */
    public int insertPlcCommand(PlcCommand plcCommand);

    /**
     * 修改PLC指令
     * 
     * @param plcCommand PLC指令
     * @return 結果
     */
    public int updatePlcCommand(PlcCommand plcCommand);
    /**
     * 批量刪除PLC指令
     * 
     * @param ids 需要刪除的PLC指令主鍵集合
     * @return 結果
     */
    public int deletePlcCommandByIds(Long[] ids);

    /**
     * 刪除PLC指令信息
     * 
     * @param id PLC指令主鍵
     * @return 結果
     */
    public int deletePlcCommandById(Long id);
}
