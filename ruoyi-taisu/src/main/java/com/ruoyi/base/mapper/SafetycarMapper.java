package com.ruoyi.base.mapper;


import com.ruoyi.base.domain.BaseSafetycar;

import java.util.List;

/**
* @author mi
* @description 针对表【base_safetyCar】的数据库操作Mapper
* @createDate 2022-08-29 09:39:08
* @Entity generator.domain.BaseSafetycar
*/
public interface SafetycarMapper {


    int insertCarlist(BaseSafetycar baseSafetycar);

    BaseSafetycar isExist(String ipltlic);

    List<BaseSafetycar> getSafetycarByCarno(String carParam);




    List<BaseSafetycar> selectSafetyCarList(BaseSafetycar safetyCar);

    int updateSafetyCar(BaseSafetycar safetyCar);

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
     * 删除车辆信息
     *
     * @param idno 车辆信息主键
     * @return 结果
     */
    public int deleteBaseSafetycarByIdno(String idno);

    /**
     * 批量删除车辆信息
     *
     * @param ipLtLics 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaseSafetycarByIdnos(String[] ipLtLics);
}
