package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.GueatLog;
import org.springframework.stereotype.Repository;

/**
 * 来宾卡操作记录Mapper接口
 * 
 * @author xxy
 * @date 2022-06-05
 */
@Repository
public interface GueatLogMapper 
{
    /**
     * 查询来宾卡操作记录
     * 
     * @param id 来宾卡操作记录主键
     * @return 来宾卡操作记录
     */
    public GueatLog selectGueatLogById(Long id);

    /**
     * 查询来宾卡操作记录列表
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 来宾卡操作记录集合
     */
    public List<GueatLog> selectGueatLogList(GueatLog gueatLog);

    /**
     * 新增来宾卡操作记录
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 结果
     */
    public int insertGueatLog(GueatLog gueatLog);

    /**
     * 修改来宾卡操作记录
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 结果
     */
    public int updateGueatLog(GueatLog gueatLog);

    /**
     * 删除来宾卡操作记录
     * 
     * @param id 来宾卡操作记录主键
     * @return 结果
     */
    public int deleteGueatLogById(Long id);

    /**
     * 批量删除来宾卡操作记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGueatLogByIds(Long[] ids);
}
