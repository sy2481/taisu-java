package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcHrWorkOrderUserMapper;
import com.ruoyi.base.domain.HcHrWorkOrderUser;
import com.ruoyi.base.service.IHcHrWorkOrderUserService;

/**
 * 歷史危化施工單明細Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcHrWorkOrderUserServiceImpl implements IHcHrWorkOrderUserService 
{
    @Autowired
    private HcHrWorkOrderUserMapper hcHrWorkOrderUserMapper;

    /**
     * 查询歷史危化施工單明細
     * 
     * @param id 歷史危化施工單明細主键
     * @return 歷史危化施工單明細
     */
    @Override
    public HcHrWorkOrderUser selectHcHrWorkOrderUserById(Long id)
    {
        return hcHrWorkOrderUserMapper.selectHcHrWorkOrderUserById(id);
    }

    /**
     * 查询歷史危化施工單明細列表
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 歷史危化施工單明細
     */
    @Override
    public List<HcHrWorkOrderUser> selectHcHrWorkOrderUserList(HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        return hcHrWorkOrderUserMapper.selectHcHrWorkOrderUserList(hcHrWorkOrderUser);
    }

    /**
     * 新增歷史危化施工單明細
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 结果
     */
    @Override
    public int insertHcHrWorkOrderUser(HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        hcHrWorkOrderUser.setCreateTime(DateUtils.getNowDate());
        return hcHrWorkOrderUserMapper.insertHcHrWorkOrderUser(hcHrWorkOrderUser);
    }

    /**
     * 修改歷史危化施工單明細
     * 
     * @param hcHrWorkOrderUser 歷史危化施工單明細
     * @return 结果
     */
    @Override
    public int updateHcHrWorkOrderUser(HcHrWorkOrderUser hcHrWorkOrderUser)
    {
        hcHrWorkOrderUser.setUpdateTime(DateUtils.getNowDate());
        return hcHrWorkOrderUserMapper.updateHcHrWorkOrderUser(hcHrWorkOrderUser);
    }

    /**
     * 批量删除歷史危化施工單明細
     * 
     * @param ids 需要删除的歷史危化施工單明細主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderUserByIds(Long[] ids)
    {
        return hcHrWorkOrderUserMapper.deleteHcHrWorkOrderUserByIds(ids);
    }

    /**
     * 删除歷史危化施工單明細信息
     * 
     * @param id 歷史危化施工單明細主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderUserById(Long id)
    {
        return hcHrWorkOrderUserMapper.deleteHcHrWorkOrderUserById(id);
    }
}
