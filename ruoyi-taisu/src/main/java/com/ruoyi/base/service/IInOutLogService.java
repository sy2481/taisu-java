package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.InOutLog;
import org.apache.ibatis.annotations.Param;

/**
 * 进出记录Service接口
 * 
 * @author ruoyi
 * @date 2022-03-07
 */
public interface IInOutLogService 
{
    /**
     * 查询进出记录
     * 
     * @param id 进出记录主键
     * @return 进出记录
     */
    public InOutLog selectInOutLogById(Long id);

    /**
     * 查询进出记录列表
     * 
     * @param inOutLog 进出记录
     * @return 进出记录集合
     */
    public List<InOutLog> selectInOutLogList(InOutLog inOutLog);

    /**
     * 新增进出记录
     * 
     * @param inOutLog 进出记录
     * @return 结果
     */
    public int insertInOutLog(InOutLog inOutLog);

    /**
     * 修改进出记录
     * 
     * @param inOutLog 进出记录
     * @return 结果
     */
    public int updateInOutLog(InOutLog inOutLog);

    /**
     * 批量删除进出记录
     * 
     * @param ids 需要删除的进出记录主键集合
     * @return 结果
     */
    public int deleteInOutLogByIds(Long[] ids);

    /**
     * 删除进出记录信息
     * 
     * @param id 进出记录主键
     * @return 结果
     */
    public int deleteInOutLogById(Long id);


    /**
     * 根据身份证号码查询
     */
    public InOutLog getInOutLogGuestByIdCard( String idCard,Integer time,String personType,Long deptId);

    int removeLog(Long id);
}
