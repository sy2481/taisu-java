package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.InOutLogPerilous;
import org.apache.ibatis.annotations.Param;

/**
 * 危化进出记录Service接口
 *
 * @author ruoyi
 * @date 2022-04-28
 */
public interface IInOutLogPerilousService
{
    /**
     * 查询危化进出记录
     *
     * @param id 危化进出记录主键
     * @return 危化进出记录
     */
    public InOutLogPerilous selectInOutLogPerilousById(Long id);

    /**
     * 查询危化进出记录列表
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 危化进出记录集合
     */
    public List<InOutLogPerilous> selectInOutLogPerilousList(InOutLogPerilous inOutLogPerilous);

    /**
     * 新增危化进出记录
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 结果
     */
    public int insertInOutLogPerilous(InOutLogPerilous inOutLogPerilous);

    /**
     * 修改危化进出记录
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 结果
     */
    public int updateInOutLogPerilous(InOutLogPerilous inOutLogPerilous);

    /**
     * 批量删除危化进出记录
     *
     * @param ids 需要删除的危化进出记录主键集合
     * @return 结果
     */
    public int deleteInOutLogPerilousByIds(Long[] ids);

    /**
     * 删除危化进出记录信息
     *
     * @param id 危化进出记录主键
     * @return 结果
     */
    public int deleteInOutLogPerilousById(Long id);


    public InOutLogPerilous getInOutLogGuestByIdCard( String idCard,Integer time,Long deptId);


    int removeLog(Long id);
}
