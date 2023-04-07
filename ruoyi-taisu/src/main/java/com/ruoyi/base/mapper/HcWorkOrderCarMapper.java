package com.ruoyi.base.mapper;

import com.ruoyi.base.domain.HcWorkOrderCar;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 危化施工單車輛Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface HcWorkOrderCarMapper {
    /**
     * 查询危化施工單車輛
     *
     * @param id 危化施工單車輛主键
     * @return 危化施工單車輛
     */
    public HcWorkOrderCar selectHcWorkOrderCarById(Long id);

    /**
     * 查询危化施工單車輛列表
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 危化施工單車輛集合
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarList(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 新增危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    public int insertHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 修改危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    public int updateHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 删除危化施工單車輛
     *
     * @param id 危化施工單車輛主键
     * @return 结果
     */
    public int deleteHcWorkOrderCarById(Long id);

    /**
     * 批量删除危化施工單車輛
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderCarByIds(Long[] ids);

    /**
     * 查询危化施工單車輛
     *
     * @param idNo 危化施工單車牌號
     * @return 危化施工單車輛
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarByIdNo(String idNo);

    /**
     * 查询危化施工單車輛
     *
     * @param vhNos
     * @return
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarListByVhNos(@Param("vhNos") String[] vhNos);

    /**
     * 批量新增
     *
     * @param list 車
     * @return 结果
     */
    public int batchInsertHcWorkOrderCar(@Param("list") List<HcWorkOrderCar> list);

    /**
     * 批量修改
     *
     * @param list 車
     * @return 结果
     */
    public int batchUpdateHcWorkOrderCar(@Param("list") List<HcWorkOrderCar> list);

    /**
     * 根據工單號和車牌查找
     *
     * @param vhNo
     * @param idNo
     * @return
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarListByVhNoIdNo(@Param("vhNo") String vhNo, @Param("idNo") String idNo);

    /**
     * 根據工單號和車牌查找
     *
     * @param vhNo
     * @param idNo
     * @return
     */
    public HcWorkOrderCar selectHcWorkOrderCarByVhNoIdNo(@Param("vhNo") String vhNo, @Param("idNo") String idNo,@Param("ipltTm") Date ipltTm);

    /**
     * 需要移动到历史表的数据
     *
     * @return
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarListToHr(Date opltTm);

    /**
     * 修改司機信息
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    public int updateHcWorkOrderCarForDriver(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 查询危化施工單車輛列表
     *
     * @param ids 危化施工單車輛
     * @return 危化施工單車輛集合
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarListByIds(Long[] ids);

    /**
     * 查询危化施工單車輛
     *
     * @param idNo 危化施工單車牌號
     * @param beginSecOpltTm 當天00:00;00
     * @param endSecOpltTm 當天23:59:59
     * @return 危化施工單車輛
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarByIdNoEnable(@Param("idNo")String idNo,@Param("beginSecOpltTm")Date beginSecOpltTm,@Param("endSecOpltTm")Date endSecOpltTm);

}
