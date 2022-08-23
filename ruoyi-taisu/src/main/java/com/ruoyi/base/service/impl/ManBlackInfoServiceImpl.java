package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.ManBlackInfoMapper;
import com.ruoyi.base.domain.ManBlackInfo;
import com.ruoyi.base.service.IManBlackInfoService;

/**
 * 廠商黑名單記錄Service业务层处理
 *
 * @author ruoyi
 * @date 2022-04-24
 */
@Service
public class ManBlackInfoServiceImpl implements IManBlackInfoService
{
    @Autowired
    private ManBlackInfoMapper manBlackInfoMapper;

    /**
     * 查询廠商黑名單記錄
     *
     * @param id 廠商黑名單記錄主键
     * @return 廠商黑名單記錄
     */
    @Override
    public ManBlackInfo selectManBlackInfoById(Long id)
    {
        return manBlackInfoMapper.selectManBlackInfoById(id);
    }

    /**
     * 查询廠商黑名單記錄列表
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 廠商黑名單記錄
     */
    @Override
    public List<ManBlackInfo> selectManBlackInfoList(ManBlackInfo manBlackInfo)
    {
        return manBlackInfoMapper.selectManBlackInfoList(manBlackInfo);
    }

    /**
     * 新增廠商黑名單記錄
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 结果
     */
    @Override
    public int insertManBlackInfo(ManBlackInfo manBlackInfo)
    {
        String username = SecurityUtils.getUsername();
        manBlackInfo.setBlackAddName(username);
        manBlackInfo.setCreateTime(DateUtils.getNowDate());
        return manBlackInfoMapper.insertManBlackInfo(manBlackInfo);
    }

    /**
     * 修改廠商黑名單記錄
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 结果
     */
    @Override
    public int updateManBlackInfo(ManBlackInfo manBlackInfo)
    {
        String username = SecurityUtils.getUsername();
        manBlackInfo.setBlackAddName(username);
        manBlackInfo.setUpdateTime(DateUtils.getNowDate());
        return manBlackInfoMapper.updateManBlackInfo(manBlackInfo);
    }

    /**
     * 批量删除廠商黑名單記錄
     *
     * @param ids 需要删除的廠商黑名單記錄主键
     * @return 结果
     */
    @Override
    public int deleteManBlackInfoByIds(Long[] ids)
    {
        return manBlackInfoMapper.deleteManBlackInfoByIds(ids);
    }

    /**
     * 删除廠商黑名單記錄信息
     *
     * @param id 廠商黑名單記錄主键
     * @return 结果
     */
    @Override
    public int deleteManBlackInfoById(Long id)
    {
        return manBlackInfoMapper.deleteManBlackInfoById(id);
    }

    @Override
    public ManBlackInfo getBlackInfo(String factoryName) {
        return manBlackInfoMapper.getBlackInfo(factoryName);
    }

    @Override
    public ManBlackInfo getBlackInfoByCard(String idCard) {
        return manBlackInfoMapper.getBlackInfoByCard(idCard);
    }
}
