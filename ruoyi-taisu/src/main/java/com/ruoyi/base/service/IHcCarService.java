package com.ruoyi.base.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * 危化車輛Service接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcCarService
{
    /**
     * 查询危化車輛
     *
     * @param id 危化車輛主键
     * @return 危化車輛
     */
    public HcCar selectHcCarById(Long id);

    /**
     * 查询危化車輛列表
     *
     * @param hcCar 危化車輛
     * @return 危化車輛集合
     */
    public List<HcCar> selectHcCarList(HcCar hcCar);

    /**
     * 新增危化車輛
     *
     * @param hcCar 危化車輛
     * @return 结果
     */
    public int insertHcCar(HcCar hcCar);

    /**
     * 修改危化車輛
     *
     * @param hcCar 危化車輛
     * @return 结果
     */
    public int updateHcCar(HcCar hcCar);

    /**
     * 批量删除危化車輛
     *
     * @param ids 需要删除的危化車輛主键集合
     * @return 结果
     */
    public int deleteHcCarByIds(Long[] ids);

    /**
     * 删除危化車輛信息
     *
     * @param id 危化車輛主键
     * @return 结果
     */
    public int deleteHcCarById(Long id);

    /**
     * 构造参数
     * @param hcWorkOrderCar
     * @return
     */
    public void buildCar(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 從ERP獲取數據
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant);

    /**
     * 獲取實體map
     *
     * @return
     */
    public Map<String, HcCar> getEntityMap(List<String> idNoList);

    /**
     * 查询危化車輛
     *
     * @param idNo 車牌
     * @return 危化車輛
     */
    public HcCar selectHcCarByIdNo(String idNo);
}
