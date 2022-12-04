package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.PlcHikCommand;

/**
 * 設備所屬指令Service接口
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public interface IPlcHikCommandService 
{
    /**
     * 查询設備所屬指令
     * 
     * @param id 設備所屬指令主键
     * @return 設備所屬指令
     */
    public PlcHikCommand selectPlcHikCommandById(Long id);

    /**
     * 查询設備所屬指令列表
     * 
     * @param plcHikCommand 設備所屬指令
     * @return 設備所屬指令集合
     */
    public List<PlcHikCommand> selectPlcHikCommandList(PlcHikCommand plcHikCommand);

    /**
     * 新增設備所屬指令
     * 
     * @param plcHikCommand 設備所屬指令
     * @return 结果
     */
    public int insertPlcHikCommand(PlcHikCommand plcHikCommand);

    /**
     * 修改設備所屬指令
     * 
     * @param plcHikCommand 設備所屬指令
     * @return 结果
     */
    public int updatePlcHikCommand(PlcHikCommand plcHikCommand);

    /**
     * 批量删除設備所屬指令
     * 
     * @param ids 需要删除的設備所屬指令主键集合
     * @return 结果
     */
    public int deletePlcHikCommandByIds(Long[] ids);

    /**
     * 删除設備所屬指令信息
     * 
     * @param id 設備所屬指令主键
     * @return 结果
     */
    public int deletePlcHikCommandById(Long id);
}
