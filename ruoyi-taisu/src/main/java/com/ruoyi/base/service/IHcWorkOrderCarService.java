package com.ruoyi.base.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.bo.HcWorkOrderCarForPermitBo;
import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.domain.HcWorkOrderUser;
import com.ruoyi.common.core.domain.entity.SysDept;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 危化施工單車輛Service接口
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public interface IHcWorkOrderCarService {
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
     * 批量删除危化施工單車輛
     *
     * @param ids 需要删除的危化施工單車輛主键集合
     * @return 结果
     */
    public int deleteHcWorkOrderCarByIds(Long[] ids);

    /**
     * 删除危化施工單車輛信息
     *
     * @param id 危化施工單車輛主键
     * @return 结果
     */
    public int deleteHcWorkOrderCarById(Long id);

    /**
     * 查询危化施工單車輛
     *
     * @param idNo 危化施工單車牌號
     * @return 危化施工單車輛
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarByIdNo(String idNo);

    /**
     * 從ERP獲取數據
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant,String addOrUpdate);

    /**
     * 獲取實體map
     *
     * @return
     */
    public Map<String, HcWorkOrderCar> getEntityMap(List<String> vhNoList);


    /**
     * 下發
     * @param hcWorkOrderCarForPermitBo
     * @return
     */
    public int permit(HcWorkOrderCarForPermitBo hcWorkOrderCarForPermitBo);

    /**
     * 設置是否下發成功
     * @param hcWorkOrderCar
     * @return
     */
    public int updateIssued(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 從HcCar獲取數據
     * @return
     */
    public void syncFromHcCar();

    /**
     * 查询危化施工單車輛
     *
     * @return 危化施工單車輛
     */
    public List<HcWorkOrderCar> selectHcWorkOrderCarListByVhNoIdNo(String vhNo,String idNo);


    /**
     * 修改危化施工單車輛
     *
     * @param hcWorkOrderCar 危化施工單車輛
     * @return 结果
     */
    public int saveHcWorkOrderCarForH5(HcWorkOrderCar hcWorkOrderCar);


    /**
     * 新增危化施工單押运员
     *
     * @param hcWorkOrderUser 危化施工單押运员
     * @return 结果
     */
    public int updateHcWorkOrderUserForH5(HcWorkOrderUser hcWorkOrderUser);

    /**
     * 危化查询详情
     *
     * @param id 危化车辆主键
     * @return 结果
     */
    public JSONObject hcQueryDetails(Long id);

    /**
     * 保存車輛數據
     * @param hcWorkOrderCar
     * @return
     */
    public int saveHcWorkOrderCar(HcWorkOrderCar hcWorkOrderCar);

    /**
     * 查询危化施工單車輛
     *
     * @param idNo 危化施工單車牌號
     * @return 危化施工單車輛
     */
    public JSONObject hcQueryDetailByIdNo(String idNo);
}
