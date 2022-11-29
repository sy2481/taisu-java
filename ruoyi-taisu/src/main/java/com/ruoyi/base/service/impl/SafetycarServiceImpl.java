package com.ruoyi.base.service.impl;

import com.ruoyi.base.service.SafetycarService;
import com.ruoyi.base.domain.BaseSafetycar;
import com.ruoyi.base.mapper.SafetycarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author mi
* @description 针对表【base_safetyCar】的数据库操作Service实现
* @createDate 2022-08-29 09:39:08
*/
@Service
public class SafetycarServiceImpl implements SafetycarService {

    @Autowired
    private SafetycarMapper safetycarMapper;

    @Override
    public BaseSafetycar isExist(String ipltlic) {
        return safetycarMapper.isExist(ipltlic);
    }

    @Override
    public int insertCarlist(BaseSafetycar baseSafetycar) {
       return safetycarMapper.insertCarlist(baseSafetycar);
    }

    @Override
    public List<BaseSafetycar> getSafetycarByCarno(String carParam) {
        return safetycarMapper.getSafetycarByCarno(carParam);
    }





    @Override
    public List<BaseSafetycar> selectSafetyCarList(BaseSafetycar safetyCar) {
        return safetycarMapper.selectSafetyCarList(safetyCar);
    }

    @Override
    public int updateSafetyCar(BaseSafetycar safetyCar) {
        return safetycarMapper.updateSafetyCar(safetyCar);
    }

    /**
     * 查询车辆信息
     *
     * @param idno 车辆信息主键
     * @return 车辆信息
     */
    @Override
    public BaseSafetycar selectBaseSafetycarByIdno(String idno)
    {
        return safetycarMapper.selectBaseSafetycarByIdno(idno);
    }

    /**
     * 查询车辆信息列表
     *
     * @param baseSafetycar 车辆信息
     * @return 车辆信息
     */
    @Override
    public List<BaseSafetycar> selectBaseSafetycarList(BaseSafetycar baseSafetycar)
    {
        return safetycarMapper.selectBaseSafetycarList(baseSafetycar);
    }

    /**
     * 新增车辆信息
     *
     * @param baseSafetycar 车辆信息
     * @return 结果
     */
    @Override
    public int insertBaseSafetycar(BaseSafetycar baseSafetycar)
    {
        return safetycarMapper.insertBaseSafetycar(baseSafetycar);
    }

    /**
     * 修改车辆信息
     *
     * @param baseSafetycar 车辆信息
     * @return 结果
     */
    @Override
    public int updateBaseSafetycar(BaseSafetycar baseSafetycar)
    {
        return safetycarMapper.updateBaseSafetycar(baseSafetycar);
    }

    /**
     * 批量删除车辆信息
     *
     * @param idnos 需要删除的车辆信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseSafetycarByIdnos(String[] idnos)
    {
        return safetycarMapper.deleteBaseSafetycarByIdnos(idnos);
    }

    /**
     * 删除车辆信息信息
     *
     * @param idno 车辆信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseSafetycarByIdno(String idno)
    {
        return safetycarMapper.deleteBaseSafetycarByIdno(idno);
    }
}
