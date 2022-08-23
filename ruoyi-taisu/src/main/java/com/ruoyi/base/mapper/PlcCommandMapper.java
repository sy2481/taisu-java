package com.ruoyi.base.mapper;

import java.util.List;

import com.ruoyi.base.domain.CommandType;
import com.ruoyi.base.domain.PlcCommand;

/**
 * PLC信道Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
public interface PlcCommandMapper 
{
    /**
     * 查询PLC信道
     * 
     * @param id PLC信道主键
     * @return PLC信道
     */
    public PlcCommand selectPlcCommandById(Long id);

    /**
     * 查询PLC信道列表
     * 
     * @param plcCommand PLC信道
     * @return PLC信道集合
     */
    public List<PlcCommand> selectPlcCommandList(PlcCommand plcCommand);

    /**
     * 新增PLC信道
     * 
     * @param plcCommand PLC信道
     * @return 结果
     */
    public int insertPlcCommand(PlcCommand plcCommand);

    /**
     * 修改PLC信道
     * 
     * @param plcCommand PLC信道
     * @return 结果
     */
    public int updatePlcCommand(PlcCommand plcCommand);

    /**
     * 删除PLC信道
     * 
     * @param id PLC信道主键
     * @return 结果
     */
    public int deletePlcCommandById(Long id);

    /**
     * 批量删除PLC信道
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePlcCommandByIds(Long[] ids);

    /**
     * 根据信道id返回对应指令及模式
     *
     * @param passageway_id 需要删除的数据主键集合
     * @return 结果
     */
    public List<CommandType> selectPlcCommandByPassagewayId(Long passageway_id);
}
