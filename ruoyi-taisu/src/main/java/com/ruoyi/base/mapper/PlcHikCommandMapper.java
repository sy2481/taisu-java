package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.PlcHikCommand;
import org.apache.ibatis.annotations.Param;

/**
 * 設備所屬指令Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public interface PlcHikCommandMapper 
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
     * 删除設備所屬指令
     * 
     * @param id 設備所屬指令主键
     * @return 结果
     */
    public int deletePlcHikCommandById(Long id);

    /**
     * 批量删除設備所屬指令
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlcHikCommandByIds(Long[] ids);

    /**
     * 是否存在同設備同編號的指令
     * @param plcHikId
     * @param command
     * @return
     */
    public List<PlcHikCommand> existPlcHikCommand(@Param("plcHikId")Long plcHikId,@Param("code")String code);
}
