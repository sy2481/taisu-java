package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.InOutLog;
import com.ruoyi.base.mapper.InOutLogMapper;
import com.ruoyi.base.service.IInOutLogService;
import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 进出记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-03-07
 */
@Service
public class InOutLogServiceImpl implements IInOutLogService 
{
    @Autowired
    private InOutLogMapper inOutLogMapper;

    /**
     * 查询进出记录
     * 
     * @param id 进出记录主键
     * @return 进出记录
     */
    @Override
    public InOutLog selectInOutLogById(Long id)
    {
        return inOutLogMapper.selectInOutLogById(id);
    }

    /**
     * 查询进出记录列表
     * 
     * @param inOutLog 进出记录
     * @return 进出记录
     */
    @Override
    public List<InOutLog> selectInOutLogList(InOutLog inOutLog)
    {

        /**
         * 根據登錄人從廠區查詢
         */
        //查询当前登录人是否擁有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList!=null){
            inOutLog.setFactoryIdList(longList);
        }
        List<InOutLog> inOutLogs = inOutLogMapper.selectInOutLogList(inOutLog);
        inOutLogs.forEach(inOutLogItem ->{
           if ( inOutLogItem.getCreateTime()!=null){
               inOutLogItem.setInOutTime(inOutLogItem.getCreateTime());
           }
        } );

        return inOutLogs;
    }

    /**
     * 新增进出记录
     * 
     * @param inOutLog 进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLog(InOutLog inOutLog)
    {
        inOutLog.setCreateTime(DateUtils.getNowDate());
        inOutLog.setOperationTime(DateUtils.getNowDate());
        return inOutLogMapper.insertInOutLog(inOutLog);
    }

    /**
     * 修改进出记录
     * 
     * @param inOutLog 进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLog(InOutLog inOutLog)
    {
        return inOutLogMapper.updateInOutLog(inOutLog);
    }

    /**
     * 批量删除进出记录
     * 
     * @param ids 需要删除的进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogByIds(Long[] ids)
    {
        return inOutLogMapper.deleteInOutLogByIds(ids);
    }

    /**
     * 删除进出记录信息
     * 
     * @param id 进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogById(Long id)
    {
        return inOutLogMapper.deleteInOutLogById(id);
    }

    @Override
    public InOutLog getInOutLogGuestByIdCard(String idCard, Integer time, String personType,Long deptId) {
        return inOutLogMapper.getInOutLogGuestByIdCard(idCard,time ,personType, deptId);
    }

    @Override
    public int removeLog(Long id) {
        return inOutLogMapper.removeLog(id, SecurityUtils.getLoginUser().getUser().getNickName());
    }
}
