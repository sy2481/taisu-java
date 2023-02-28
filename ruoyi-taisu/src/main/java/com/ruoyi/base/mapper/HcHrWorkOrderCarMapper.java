package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HcHrWorkOrderCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歷史危化施工單車輛Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcHrWorkOrderCarMapper {
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
     * 删除歷史危化施工單車輛
     *
     * @param id 歷史危化施工單車輛主键
     * @return 结果
     */
    public int deleteHcHrWorkOrderCarById(Long id);

    /**
     * 批量删除歷史危化施工單車輛
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcHrWorkOrderCarByIds(Long[] ids);

    /**
     * 查询危化施工單車輛
     *
     * @param vhNos
     * @return
     */
    public List<HcHrWorkOrderCar> selectHcHrWorkOrderCarListByVhNos(@Param("vhNos") String[] vhNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcHrWorkOrderCar(@Param("list") List<HcHrWorkOrderCar> list);

}
