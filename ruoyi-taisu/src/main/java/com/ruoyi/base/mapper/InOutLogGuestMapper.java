package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.InOutLogGuest;
import org.apache.ibatis.annotations.Param;

/**
 * 来宾卡进出记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public interface InOutLogGuestMapper
{
    /**
     * 查询来宾卡进出记录
     *
     * @param id 来宾卡进出记录主键
     * @return 来宾卡进出记录
     */
    public InOutLogGuest selectInOutLogGuestById(Long id);

    /**
     * 查询来宾卡进出记录列表
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 来宾卡进出记录集合
     */
    public List<InOutLogGuest> selectInOutLogGuestList(InOutLogGuest inOutLogGuest);

    /**
     * 新增来宾卡进出记录
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 结果
     */
    public int insertInOutLogGuest(InOutLogGuest inOutLogGuest);

    /**
     * 修改来宾卡进出记录
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 结果
     */
    public int updateInOutLogGuest(InOutLogGuest inOutLogGuest);

    /**
     * 删除来宾卡进出记录
     *
     * @param id 来宾卡进出记录主键
     * @return 结果
     */
    public int deleteInOutLogGuestById(Long id);

    /**
     * 批量删除来宾卡进出记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInOutLogGuestByIds(Long[] ids);

    /**
     * 作废进出记录
     * @param id
     * @return
     */
    public int removeLog(@Param("id") Long id,@Param("operationName")String operationName);


    /**
     * 根据身份证号码查询
     */
    public  InOutLogGuest getInOutLogGuestByIdCard(@Param("idCard") String idCard,
                                                   @Param("time") Integer time,
                                                   @Param("deptId")Long deptId);
}
