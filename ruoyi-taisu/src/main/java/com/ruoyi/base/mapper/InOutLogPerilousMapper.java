package com.ruoyi.base.mapper;

import java.util.List;

import com.ruoyi.base.domain.InOutLog;
import com.ruoyi.base.domain.InOutLogPerilous;
import org.apache.ibatis.annotations.Param;

/**
 * 危化进出记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-28
 */
public interface InOutLogPerilousMapper
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
    public int updateInOutLogPerilous( InOutLogPerilous inOutLogPerilous);

    /**
     * 删除危化进出记录
     *
     * @param id 危化进出记录主键
     * @return 结果
     */
    public int deleteInOutLogPerilousById(Long id);

    /**
     * 批量删除危化进出记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInOutLogPerilousByIds(Long[] ids);
    /**
     * 根据身份证号码查询
     */
    public InOutLogPerilous getInOutLogGuestByIdCard(@Param("idCard") String idCard,
                                                     @Param("time") Integer time,
                                                     @Param("deptId")Long deptId);


    int removeLog(@Param("id") Long id ,@Param("operationName") String operationName);
}
