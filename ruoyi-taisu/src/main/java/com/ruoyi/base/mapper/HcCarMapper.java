package com.ruoyi.base.mapper;

import java.util.List;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcUser;
import org.apache.ibatis.annotations.Param;

/**
 * 危化車輛Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcCarMapper
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
     * 删除危化車輛
     *
     * @param id 危化車輛主键
     * @return 结果
     */
    public int deleteHcCarById(Long id);

    /**
     * 批量删除危化車輛
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcCarByIds(Long[] ids);

    /**
     * 查询危化車輛
     *
     * @param idNo 危化車輛主键
     * @return 危化車輛
     */
    public HcCar selectHcCarByIdNo(String idNo);

    /**
     * 根據身份證獲取數據
     * @param idNos
     * @return
     */
    public List<HcCar> selectHcCarListByIdNos(String[] idNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcCar(@Param("list") List<HcCar> list);

    /**
     * 批量修改
     *
     * @param list 車
     * @return 结果
     */
    public int batchUpdateHcCar(@Param("list") List<HcCar> list);
}
