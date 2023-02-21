package com.ruoyi.base.service;


import com.ruoyi.base.domain.BaseSafetycar;

import java.util.List;

/**
 * @author mi
 * @description 针对表【base_safetyCar】的数据库操作Service
 * @createDate 2022-08-29 09:39:08
 */
public interface SafetycarService {
    /**
     * 判断是否已存在
     */
    BaseSafetycar isExist(String ipltlic);

    /**
     * 插入当日车辆信息
     */
    int insertCarlist(BaseSafetycar baseSafetycar);

    /**
     * 获取拉取的车辆数据
     */
    List<BaseSafetycar> getSafetycarByCarno(String carParam);

    List<BaseSafetycar> getSafetycarByNoCarType();


//    /**
//     * 插入拉取的车辆数据
//     */
//    void insertCarlist(List<Safetyman> list);


    /**
     * 查询当日车辆信息
     */
    List<BaseSafetycar> selectSafetyCarList(BaseSafetycar safetyCar);

    /**
     * 更新当日车辆信息
     */
    int updateSafetyCar(BaseSafetycar safetyCar);

    int updateSafetyCarType(BaseSafetycar safetyCar);

    /**
     * 查询车辆信息
     *
     * @param idno 车辆信息主键
     * @return 车辆信息
     */
    public BaseSafetycar selectBaseSafetycarByIdno(String idno);

    /**
     * 查询车辆信息列表
     *
     * @param baseSafetycar 车辆信息
     * @return 车辆信息集合
     */
    public List<BaseSafetycar> selectBaseSafetycarList(BaseSafetycar baseSafetycar);

    /**
     * 新增车辆信息
     *
     * @param baseSafetycar 车辆信息
     * @return 结果
     */
    public int insertBaseSafetycar(BaseSafetycar baseSafetycar);

    /**
     * 修改车辆信息
     *
     * @param baseSafetycar 车辆信息
     * @return 结果
     */
    public int updateBaseSafetycar(BaseSafetycar baseSafetycar);

    /**
     * 批量删除车辆信息
     *
     * @param idnos 需要删除的车辆信息主键集合
     * @return 结果
     */
    public int deleteBaseSafetycarByIdnos(String[] idnos);

    /**
     * 删除车辆信息信息
     *
     * @param idno 车辆信息主键
     * @return 结果
     */
    public int deleteBaseSafetycarByIdno(String idno);
}
