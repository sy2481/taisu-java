package com.ruoyi.base.service;

import java.util.List;
import com.ruoyi.base.domain.HcHrWorkOrderCar;

/**
 * 歷史危化施工單車輛Service接口
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcHrWorkOrderCarService 
{
    /**
     * 查询歷史危化施工單車輛
     * 
     * @param id 歷史危化施工單車輛主键
     * @return 歷史危化施工單車輛
     */
    public HcHrWorkOrderCar selectHcHrWorkOrderCarById(Long id);

    /**
     * 查询歷史危化施工單車輛列表
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 歷史危化施工單車輛集合
     */
    public List<HcHrWorkOrderCar> selectHcHrWorkOrderCarList(HcHrWorkOrderCar hcHrWorkOrderCar);

    /**
     * 新增歷史危化施工單車輛
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 结果
     */
    public int insertHcHrWorkOrderCar(HcHrWorkOrderCar hcHrWorkOrderCar);

    /**
     * 修改歷史危化施工單車輛
     * 
     * @param hcHrWorkOrderCar 歷史危化施工單車輛
     * @return 结果
     */
    public int updateHcHrWorkOrderCar(HcHrWorkOrderCar hcHrWorkOrderCar);

    /**
     * 批量删除歷史危化施工單車輛
     * 
     * @param ids 需要删除的歷史危化施工單車輛主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderCarByIds(Long[] ids);

    /**
     * 删除歷史危化施工單車輛信息
     * 
     * @param id 歷史危化施工單車輛主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderCarById(Long id);
}
