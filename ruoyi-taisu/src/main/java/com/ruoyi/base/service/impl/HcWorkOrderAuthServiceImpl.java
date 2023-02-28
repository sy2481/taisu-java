package com.ruoyi.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.ruoyi.base.bo.HcWorkOrderCarForPermitBo;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcWorkOrderAuth;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderUser;
import com.ruoyi.base.enums.VndIdNoType;
import com.ruoyi.base.interact.PersonSendService;
import com.ruoyi.base.interact.PlateSendService;
import com.ruoyi.base.mapper.HcCarMapper;
import com.ruoyi.base.mapper.HcWorkOrderAuthMapper;
import com.ruoyi.base.mapper.HcWorkOrderCarMapper;
import com.ruoyi.base.mapper.HcWorkOrderUserMapper;
import com.ruoyi.base.service.IHcCarService;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.base.service.IHcWorkOrderAuthService;
import com.ruoyi.base.service.IHcWorkOrderCarService;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.vo.CarVO;
import com.ruoyi.base.vo.PersonVO;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
/**
 * 危化工單組合認證Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-07
 */
@Service
public class HcWorkOrderAuthServiceImpl implements IHcWorkOrderAuthService
{
    @Autowired
    private HcWorkOrderAuthMapper hcWorkOrderAuthMapper;

    /**
     * 查询危化工單組合認證
     *
     * @param id 危化工單組合認證主键
     * @return 危化工單組合認證
     */
    @Override
    public HcWorkOrderAuth selectHcWorkOrderAuthById(Long id)
    {
        return hcWorkOrderAuthMapper.selectHcWorkOrderAuthById(id);
    }

    /**
     * 查询危化工單組合認證列表
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 危化工單組合認證
     */
    @Override
    public List<HcWorkOrderAuth> selectHcWorkOrderAuthList(HcWorkOrderAuth hcWorkOrderAuth)
    {
        return hcWorkOrderAuthMapper.selectHcWorkOrderAuthList(hcWorkOrderAuth);
    }

    /**
     * 新增危化工單組合認證
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 结果
     */
    @Override
    public int insertHcWorkOrderAuth(HcWorkOrderAuth hcWorkOrderAuth)
    {
        return hcWorkOrderAuthMapper.insertHcWorkOrderAuth(hcWorkOrderAuth);
    }

    /**
     * 修改危化工單組合認證
     *
     * @param hcWorkOrderAuth 危化工單組合認證
     * @return 结果
     */
    @Override
    public int updateHcWorkOrderAuth(HcWorkOrderAuth hcWorkOrderAuth)
    {
        hcWorkOrderAuth.setUpdateTime(DateUtils.getNowDate());
        return hcWorkOrderAuthMapper.updateHcWorkOrderAuth(hcWorkOrderAuth);
    }

    /**
     * 批量删除危化工單組合認證
     *
     * @param ids 需要删除的危化工單組合認證主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderAuthByIds(Long[] ids)
    {
        return hcWorkOrderAuthMapper.deleteHcWorkOrderAuthByIds(ids);
    }

    /**
     * 删除危化工單組合認證信息
     *
     * @param id 危化工單組合認證主键
     * @return 结果
     */
    @Override
    public int deleteHcWorkOrderAuthById(Long id)
    {
        return hcWorkOrderAuthMapper.deleteHcWorkOrderAuthById(id);
    }
}