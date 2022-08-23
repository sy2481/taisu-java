package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.BasePassagewayOperation;
import com.ruoyi.base.domain.SendPlcCommandParam;

/**
 * 通道操作Service接口
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
public interface IBasePassagewayOperationService 
{
    /**
     * 查询通道操作
     * 
     * @param operationId 通道操作主键
     * @return 通道操作
     */
    public BasePassagewayOperation selectBasePassagewayOperationByOperationId(Long operationId);

    /**
     * 查询通道操作列表
     * 
     * @param basePassagewayOperation 通道操作
     * @return 通道操作集合
     */
    public List<BasePassagewayOperation> selectBasePassagewayOperationList(BasePassagewayOperation basePassagewayOperation);

    /**
     * 新增通道操作
     * 
     * @param basePassagewayOperation 通道操作
     * @return 结果
     */
    public int insertBasePassagewayOperation(BasePassagewayOperation basePassagewayOperation);

    /**
     * 修改通道操作
     * 
     * @param basePassagewayOperation 通道操作
     * @return 结果
     */
    public int updateBasePassagewayOperation(BasePassagewayOperation basePassagewayOperation);

    /**
     * 批量删除通道操作
     * 
     * @param operationIds 需要删除的通道操作主键集合
     * @return 结果
     */
    public int deleteBasePassagewayOperationByOperationIds(Long[] operationIds);

    /**
     * 删除通道操作信息
     * 
     * @param operationId 通道操作主键
     * @return 结果
     */
    public int deleteBasePassagewayOperationByOperationId(Long operationId);

    /**
     * 发送PLC指令
     *
     * @param operationId 通道操作主键
     * @return 结果
     */
    public int sendPLCCommand(Long operationId);

    /**
     * 测试发送PLC指令
     *
     * @param sendPlcCommandParam 发送PLC指令参数
     * @return 结果
     */
    public int testSend(SendPlcCommandParam sendPlcCommandParam);
}
