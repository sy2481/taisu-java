package com.ruoyi.base.service.impl;

import com.ruoyi.base.domain.PeakInfo;
import com.ruoyi.base.mapper.PeakInfoMapper;
import com.ruoyi.base.service.PeakInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeakInfoServiceImpl implements PeakInfoService {
    @Autowired
    private PeakInfoMapper peakInfoMapper;

    /**
     * 查询尖峰數據
     *
     * @param id 尖峰數據主键
     * @return 尖峰數據
     */
    @Override
    public PeakInfo selectPeakInfoById(Long id) {
        return peakInfoMapper.selectPeakInfoById(id);
    }

    /**
     * 查询尖峰數據列表
     *
     * @param peakInfo 尖峰數據
     * @return 尖峰數據
     */
    @Override
    public List<PeakInfo> selectPeakInfoList(PeakInfo peakInfo) {
        return peakInfoMapper.selectPeakInfoList(peakInfo);
    }

    /**
     * 新增尖峰數據
     *
     * @param peakInfo 尖峰數據
     * @return 结果
     */
    @Override
    public int insertPeakInfo(PeakInfo peakInfo) {
        return peakInfoMapper.insertPeakInfo(peakInfo);
    }

    /**
     * 修改尖峰數據
     *
     * @param peakInfo 尖峰數據
     * @return 结果
     */
    @Override
    public int updatePeakInfo(PeakInfo peakInfo) {
        return peakInfoMapper.updatePeakInfo(peakInfo);
    }

    @Override
    public int clearPeakInfo() {
        return peakInfoMapper.clearPeakInfo();
    }

    /**
     * 批量删除尖峰數據
     *
     * @param ids 需要删除的尖峰數據主键
     * @return 结果
     */
    @Override
    public int deletePeakInfoByIds(Long[] ids) {
        return peakInfoMapper.deletePeakInfoByIds(ids);
    }

    /**
     * 删除尖峰數據信息
     *
     * @param id 尖峰數據主键
     * @return 结果
     */
    @Override
    public int deletePeakInfoById(Long id) {
        return peakInfoMapper.deletePeakInfoById(id);
    }
}