package com.ruoyi.system.mapper;



import java.util.List;
import com.ruoyi.system.domain.SysJobLog;
import org.springframework.stereotype.Repository;

/**
 * 定时任务调度日志Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-26
 */
@Repository
public interface SysJobLogMapper
{
    /**
     * 查询定时任务调度日志
     *
     * @param jobLogId 定时任务调度日志主键
     * @return 定时任务调度日志
     */
    public SysJobLog selectSysJobLogByJobLogId(Long jobLogId);

    /**
     * 查询定时任务调度日志列表
     *
     * @param sysJobLog 定时任务调度日志
     * @return 定时任务调度日志集合
     */
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog);

}
