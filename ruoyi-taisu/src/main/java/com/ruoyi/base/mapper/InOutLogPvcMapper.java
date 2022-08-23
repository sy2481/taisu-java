package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.InOutLogPvc;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * pvc厂进出记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-11
 */
public interface InOutLogPvcMapper
{
    /**
     * 查询pvc厂进出记录
     *
     * @param workTime pvc厂进出记录主键
     * @return pvc厂进出记录
     */
    public InOutLogPvc selectInOutLogPvcByWorkTime(String workTime);


    public Integer selectInOutLogPvcByAid(Integer aid);

    /**
     * 查询pvc厂进出记录列表
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return pvc厂进出记录集合
     */
    public List<InOutLogPvc> selectInOutLogPvcList(InOutLogPvc inOutLogPvc);

    /**
     * 新增pvc厂进出记录
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return 结果
     */
    public int insertInOutLogPvc(InOutLogPvc inOutLogPvc);

    /**
     * 修改pvc厂进出记录
     *
     * @param inOutLogPvc pvc厂进出记录
     * @return 结果
     */
    public int updateInOutLogPvc(InOutLogPvc inOutLogPvc);

    /**
     * 删除pvc厂进出记录
     *
     * @param workTime pvc厂进出记录主键
     * @return 结果
     */
    public int deleteInOutLogPvcByWorkTime(String workTime);

    /**
     * 批量删除pvc厂进出记录
     *
     * @param workTimes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInOutLogPvcByWorkTimes(String[] workTimes);

    List<InOutLogPvc> getInOutLogPvcByAid(@Param("today") Date today, @Param("nextDay") Date nextDay);
}
