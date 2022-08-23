package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.BasePassagewayOperation;

/**
 * 通道操作Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
public interface BasePassagewayOperationMapper 
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
     * 删除通道操作
     * 
     * @param operationId 通道操作主键
     * @return 结果
     */
    public int deleteBasePassagewayOperationByOperationId(Long operationId);

    /**
     * 批量删除通道操作
     * 
     * @param operationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBasePassagewayOperationByOperationIds(Long[] operationIds);
}
