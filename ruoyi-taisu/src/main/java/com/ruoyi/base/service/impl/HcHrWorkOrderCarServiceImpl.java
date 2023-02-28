package com.ruoyi.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcHrWorkOrderCarMapper;
import com.ruoyi.base.domain.HcHrWorkOrderCar;
import com.ruoyi.base.service.IHcHrWorkOrderCarService;

/**
 * 歷史危化施工單車輛Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcHrWorkOrderCarServiceImpl implements IHcHrWorkOrderCarService 
{
    @Autowired
    private HcHrWorkOrderCarMapper hcHrWorkOrderCarMapper;

    /**
     * 查询歷史危化施工單車輛
     * 
     * @param id 歷史危化施工單車輛主键
     * @return 歷史危化施工單車輛
     */
    @Override
    public HcHrWorkOrderCar selectHcHrWorkOrderCarById(Long id)
    {
        return hcHrWorkOrderCarMapper.selectHcHrWorkOrderCarById(id);
    }

    /**
     * 查询歷史危化施工單車輛列表
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 歷史危化施工單車輛
     */
    @Override
    public List<HcHrWorkOrderCar> selectHcHrWorkOrderCarList(HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        return hcHrWorkOrderCarMapper.selectHcHrWorkOrderCarList(hcHrWorkOrderCar);
    }

    /**
     * 新增歷史危化施工單車輛
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 结果
     */
    @Override
    public int insertHcHrWorkOrderCar(HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        hcHrWorkOrderCar.setCreateTime(DateUtils.getNowDate());
        return hcHrWorkOrderCarMapper.insertHcHrWorkOrderCar(hcHrWorkOrderCar);
    }

    /**
     * 修改歷史危化施工單車輛
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 结果
     */
    @Override
    public int updateHcHrWorkOrderCar(HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        hcHrWorkOrderCar.setUpdateTime(DateUtils.getNowDate());
        return hcHrWorkOrderCarMapper.updateHcHrWorkOrderCar(hcHrWorkOrderCar);
    }

    /**
     * 批量删除歷史危化施工單車輛
     * 
     * @param ids 需要删除的歷史危化施工單車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderCarByIds(Long[] ids)
    {
        return hcHrWorkOrderCarMapper.deleteHcHrWorkOrderCarByIds(ids);
    }

    /**
     * 删除歷史危化施工單車輛信息
     * 
     * @param id 歷史危化施工單車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcHrWorkOrderCarById(Long id)
    {
        return hcHrWorkOrderCarMapper.deleteHcHrWorkOrderCarById(id);
    }
}
