package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.InOutLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 进出记录Mapper接口
 * 
 * @author ruoyi
 * @date 2022-03-07
 */
public interface InOutLogMapper 
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
     * 删除进出记录
     * 
     * @param id 进出记录主键
     * @return 结果
     */
    public int deleteInOutLogById(Long id);

    /**
     * 批量删除进出记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInOutLogByIds(Long[] ids);



    /**
     * 根据身份证号码查询
     */
    public InOutLog getInOutLogGuestByIdCard(@Param("idCard") String idCard,
                                             @Param("time") Integer time,
                                             @Param("personType")String personType,@Param("deptId") Long deptId);


    int removeLog(@Param("id") Long id,@Param("operationName")String operationName);

}
