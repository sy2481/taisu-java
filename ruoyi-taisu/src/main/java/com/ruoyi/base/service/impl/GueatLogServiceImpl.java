package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.GueatLogMapper;
import com.ruoyi.base.domain.GueatLog;
import com.ruoyi.base.service.IGueatLogService;

/**
 * 来宾卡操作记录Service业务层处理
 * 
 * @author xxy
 * @date 2022-06-05
 */
@Service
public class GueatLogServiceImpl implements IGueatLogService 
{
    @Autowired
    private GueatLogMapper gueatLogMapper;

    /**
     * 查询来宾卡操作记录
     * 
     * @param id 来宾卡操作记录主键
     * @return 来宾卡操作记录
     */
    @Override
    public GueatLog selectGueatLogById(Long id)
    {
        return gueatLogMapper.selectGueatLogById(id);
    }

    /**
     * 查询来宾卡操作记录列表
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 来宾卡操作记录
     */
    @Override
    public List<GueatLog> selectGueatLogList(GueatLog gueatLog)
    {
        return gueatLogMapper.selectGueatLogList(gueatLog);
    }

    /**
     * 新增来宾卡操作记录
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 结果
     */
    @Override
    public int insertGueatLog(GueatLog gueatLog)
    {
        gueatLog.setCreateTime(DateUtils.getNowDate());
        return gueatLogMapper.insertGueatLog(gueatLog);
    }

    /**
     * 修改来宾卡操作记录
     * 
     * @param gueatLog 来宾卡操作记录
     * @return 结果
     */
    @Override
    public int updateGueatLog(GueatLog gueatLog)
    {
        gueatLog.setUpdateTime(DateUtils.getNowDate());
        return gueatLogMapper.updateGueatLog(gueatLog);
    }

    /**
     * 批量删除来宾卡操作记录
     * 
     * @param ids 需要删除的来宾卡操作记录主键
     * @return 结果
     */
    @Override
    public int deleteGueatLogByIds(Long[] ids)
    {
        return gueatLogMapper.deleteGueatLogByIds(ids);
    }

    /**
     * 删除来宾卡操作记录信息
     * 
     * @param id 来宾卡操作记录主键
     * @return 结果
     */
    @Override
    public int deleteGueatLogById(Long id)
    {
        return gueatLogMapper.deleteGueatLogById(id);
    }
}
