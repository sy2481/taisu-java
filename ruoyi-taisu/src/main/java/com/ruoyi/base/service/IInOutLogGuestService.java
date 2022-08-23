package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.InOutLogGuest;

/**
 * 来宾卡进出记录Service接口
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public interface IInOutLogGuestService
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
     * 批量删除来宾卡进出记录
     *
     * @param ids 需要删除的来宾卡进出记录主键集合
     * @return 结果
     */
    public int deleteInOutLogGuestByIds(Long[] ids);

    /**
     * 删除来宾卡进出记录信息
     *
     * @param id 来宾卡进出记录主键
     * @return 结果
     */
    public int deleteInOutLogGuestById(Long id);

    /**
     * 作废
     * @param id
     * @return
     */
    public int removeLog(Long id);
    /**
     * 根据身份证号码查询
     */
    public  InOutLogGuest getInOutLogGuestByIdCard(String idCard,Integer time,Long deptId);
}
