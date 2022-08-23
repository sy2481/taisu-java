package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.ManBlackInfo;

/**
 * 廠商黑名單記錄Service接口
 *
 * @author ruoyi
 * @date 2022-04-24
 */
public interface IManBlackInfoService
{
    /**
     * 查询廠商黑名單記錄
     *
     * @param id 廠商黑名單記錄主键
     * @return 廠商黑名單記錄
     */
    public ManBlackInfo selectManBlackInfoById(Long id);

    /**
     * 查询廠商黑名單記錄列表
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 廠商黑名單記錄集合
     */
    public List<ManBlackInfo> selectManBlackInfoList(ManBlackInfo manBlackInfo);

    /**
     * 新增廠商黑名單記錄
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 结果
     */
    public int insertManBlackInfo(ManBlackInfo manBlackInfo);

    /**
     * 修改廠商黑名單記錄
     *
     * @param manBlackInfo 廠商黑名單記錄
     * @return 结果
     */
    public int updateManBlackInfo(ManBlackInfo manBlackInfo);

    /**
     * 批量删除廠商黑名單記錄
     *
     * @param ids 需要删除的廠商黑名單記錄主键集合
     * @return 结果
     */
    public int deleteManBlackInfoByIds(Long[] ids);

    /**
     * 删除廠商黑名單記錄信息
     *
     * @param id 廠商黑名單記錄主键
     * @return 结果
     */
    public int deleteManBlackInfoById(Long id);

    public ManBlackInfo getBlackInfo(String factoryName);

    public ManBlackInfo getBlackInfoByCard(String idCard);
}
