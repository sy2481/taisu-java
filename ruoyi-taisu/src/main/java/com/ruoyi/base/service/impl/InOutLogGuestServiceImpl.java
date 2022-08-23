package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.InOutLogGuestMapper;
import com.ruoyi.base.domain.InOutLogGuest;
import com.ruoyi.base.service.IInOutLogGuestService;

/**
 * 来宾卡进出记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-26
 */
@Service
public class InOutLogGuestServiceImpl implements IInOutLogGuestService
{
    @Autowired
    private InOutLogGuestMapper inOutLogGuestMapper;

    /**
     * 查询来宾卡进出记录
     *
     * @param id 来宾卡进出记录主键
     * @return 来宾卡进出记录
     */
    @Override
    public InOutLogGuest selectInOutLogGuestById(Long id)
    {
        return inOutLogGuestMapper.selectInOutLogGuestById(id);
    }

    /**
     * 查询来宾卡进出记录列表
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 来宾卡进出记录
     */
    @Override
    public List<InOutLogGuest> selectInOutLogGuestList(InOutLogGuest inOutLogGuest)
    {
        return inOutLogGuestMapper.selectInOutLogGuestList(inOutLogGuest);
    }

    /**
     * 新增来宾卡进出记录
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLogGuest(InOutLogGuest inOutLogGuest)
    {
        inOutLogGuest.setCreateTime(DateUtils.getNowDate());
        inOutLogGuest.setOperationTime(DateUtils.getNowDate());
        return inOutLogGuestMapper.insertInOutLogGuest(inOutLogGuest);
    }

    /**
     * 修改来宾卡进出记录
     *
     * @param inOutLogGuest 来宾卡进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLogGuest(InOutLogGuest inOutLogGuest)
    {
        return inOutLogGuestMapper.updateInOutLogGuest(inOutLogGuest);
    }

    /**
     * 批量删除来宾卡进出记录
     *
     * @param ids 需要删除的来宾卡进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogGuestByIds(Long[] ids)
    {
        return inOutLogGuestMapper.deleteInOutLogGuestByIds(ids);
    }

    /**
     * 删除来宾卡进出记录信息
     *
     * @param id 来宾卡进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogGuestById(Long id)
    {
        return inOutLogGuestMapper.deleteInOutLogGuestById(id);
    }

    @Override
    public int removeLog(Long id) {

        return inOutLogGuestMapper.removeLog(id,SecurityUtils.getLoginUser().getUser().getNickName());
    }

    @Override
    public InOutLogGuest getInOutLogGuestByIdCard(String idCard, Integer time,Long deptId) {
        return inOutLogGuestMapper.getInOutLogGuestByIdCard(idCard,time,deptId);
    }
}
