package com.ruoyi.base.service;


import com.ruoyi.base.domain.PeakInfo;

import java.util.List;

/**
 * 尖峰數據Service接口
 *
 * @author ruoyi
 * @date 2023-03-27
 */
public interface PeakInfoService
{
    /**
     * 查询尖峰數據
     *
     * @param id 尖峰數據主键
     * @return 尖峰數據
     */
    public PeakInfo selectPeakInfoById(Long id);

    /**
     * 查询尖峰數據列表
     *
     * @param peakInfo 尖峰數據
     * @return 尖峰數據集合
     */
    public List<PeakInfo> selectPeakInfoList(PeakInfo peakInfo);

    /**
     * 新增尖峰數據
     *
     * @param peakInfo 尖峰數據
     * @return 结果
     */
    public int insertPeakInfo(PeakInfo peakInfo);

    /**
     * 修改尖峰數據
     *
     * @param peakInfo 尖峰數據
     * @return 结果
     */
    public int updatePeakInfo(PeakInfo peakInfo);

    public int clearPeakInfo();

    /**
     * 批量删除尖峰數據
     *
     * @param ids 需要删除的尖峰數據主键集合
     * @return 结果
     */
    public int deletePeakInfoByIds(Long[] ids);

    /**
     * 删除尖峰數據信息
     *
     * @param id 尖峰數據主键
     * @return 结果
     */
    public int deletePeakInfoById(Long id);
}
