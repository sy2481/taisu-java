package com.ruoyi.base.service.impl;

import java.util.List;

import com.ruoyi.base.utils.UserUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.InOutLogPerilousMapper;
import com.ruoyi.base.domain.InOutLogPerilous;
import com.ruoyi.base.service.IInOutLogPerilousService;

/**
 * 危化进出记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-28
 */
@Service
public class InOutLogPerilousServiceImpl implements IInOutLogPerilousService
{
    @Autowired
    private InOutLogPerilousMapper inOutLogPerilousMapper;

    /**
     * 查询危化进出记录
     *
     * @param id 危化进出记录主键
     * @return 危化进出记录
     */
    @Override
    public InOutLogPerilous selectInOutLogPerilousById(Long id)
    {
        return inOutLogPerilousMapper.selectInOutLogPerilousById(id);
    }

    /**
     * 查询危化进出记录列表
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 危化进出记录
     */
    @Override
    public List<InOutLogPerilous> selectInOutLogPerilousList(InOutLogPerilous inOutLogPerilous)
    {
        //查询当前登录人是否有厂区ID(多个)
        List<Long> longList = UserUtils.getUserDept();
        if (longList!=null){
            inOutLogPerilous.setFactoryId(longList);
        }
        return inOutLogPerilousMapper.selectInOutLogPerilousList(inOutLogPerilous);
    }

    /**
     * 新增危化进出记录
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 结果
     */
    @Override
    public int insertInOutLogPerilous(InOutLogPerilous inOutLogPerilous)
    {
        inOutLogPerilous.setCreateTime(DateUtils.getNowDate());
        inOutLogPerilous.setOperationTime(DateUtils.getNowDate());
        return inOutLogPerilousMapper.insertInOutLogPerilous(inOutLogPerilous);
    }

    /**
     * 修改危化进出记录
     *
     * @param inOutLogPerilous 危化进出记录
     * @return 结果
     */
    @Override
    public int updateInOutLogPerilous(InOutLogPerilous inOutLogPerilous)
    {
        return inOutLogPerilousMapper.updateInOutLogPerilous(inOutLogPerilous);
    }

    /**
     * 批量删除危化进出记录
     *
     * @param ids 需要删除的危化进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogPerilousByIds(Long[] ids)
    {
        return inOutLogPerilousMapper.deleteInOutLogPerilousByIds(ids);
    }

    /**
     * 删除危化进出记录信息
     *
     * @param id 危化进出记录主键
     * @return 结果
     */
    @Override
    public int deleteInOutLogPerilousById(Long id)
    {
        return inOutLogPerilousMapper.deleteInOutLogPerilousById(id);
    }

    @Override
    public InOutLogPerilous getInOutLogGuestByIdCard(String idCard, Integer time,Long deptId) {
        return inOutLogPerilousMapper.getInOutLogGuestByIdCard(idCard,time,deptId);
    }

    @Override
    public int removeLog(Long id) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        return inOutLogPerilousMapper.removeLog(id,user.getNickName());
    }
}
