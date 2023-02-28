package com.ruoyi.base.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.base.bo.V0NBRKX5Bo;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.enums.VndIdNoType;
import com.ruoyi.base.service.IHcUserService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.BatisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.slj.SljSyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.base.mapper.HcCarMapper;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.service.IHcCarService;

/**
 * 危化車輛Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-02
 */
@Service
public class HcCarServiceImpl implements IHcCarService
{
    @Autowired
    private HcCarMapper hcCarMapper;
    @Autowired
    private IHcUserService hcUserService;

    /**
     * 查询危化車輛
     *
     * @param id 危化車輛主键
     * @return 危化車輛
     */
    @Override
    public HcCar selectHcCarById(Long id)
    {
        return hcCarMapper.selectHcCarById(id);
    }

    /**
     * 查询危化車輛列表
     *
     * @param hcCar 危化車輛
     * @return 危化車輛
     */
    @Override
    public List<HcCar> selectHcCarList(HcCar hcCar)
    {
        return hcCarMapper.selectHcCarList(hcCar);
    }

    /**
     * 新增危化車輛
     *
     * @param hcCar 危化車輛
     * @return 结果
     */
    @Override
    public int insertHcCar(HcCar hcCar)
    {
        return hcCarMapper.insertHcCar(hcCar);
    }

    /**
     * 修改危化車輛
     *
     * @param hcCar 危化車輛
     * @return 结果
     */
    @Override
    public int updateHcCar(HcCar hcCar)
    {
        return hcCarMapper.updateHcCar(hcCar);
    }

    /**
     * 批量删除危化車輛
     *
     * @param ids 需要删除的危化車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcCarByIds(Long[] ids)
    {
        return hcCarMapper.deleteHcCarByIds(ids);
    }

    /**
     * 删除危化車輛信息
     *
     * @param id 危化車輛主键
     * @return 结果
     */
    @Override
    public int deleteHcCarById(Long id)
    {
        return hcCarMapper.deleteHcCarById(id);
    }

    /**
     * 构造参数
     *
     * @return 结果
     */
    @Override
    public void buildCar(HcWorkOrderCar hcWorkOrderCar)
    {
        HcCar hcCar = hcCarMapper.selectHcCarByIdNo(hcWorkOrderCar.getIdNo());
        if(hcCar!=null){
            hcCar.setNm(hcWorkOrderCar.getNm());
            hcCar.setIdNo(hcWorkOrderCar.getIdNo());
            hcCar.setCarPhoto(hcWorkOrderCar.getCarPhoto());
            hcCar.setCarColor(hcWorkOrderCar.getCarColor());
            hcCar.setCarType(hcWorkOrderCar.getCarType());
            hcCar.setCarTypeName(hcWorkOrderCar.getCarTypeName());
            hcCar.setVehicleLic(hcWorkOrderCar.getVehicleLic());
            hcCar.setHcTransportCertNo(hcWorkOrderCar.getHcTransportCertNo());
            hcCar.setEmisStandard(hcWorkOrderCar.getEmisStandard());
            hcCar.setEmisStandardName(hcWorkOrderCar.getEmisStandardName());
            hcCar.setEnvSign(hcWorkOrderCar.getEnvSign());
            hcCar.setUpdateBy(SecurityUtils.getUsernameDefaultSystem());
            hcCar.setUpdateTime(DateUtils.getNowDate());
            hcCarMapper.updateHcCar(hcCar);
        }else{
            hcCar = new HcCar();
            hcCar.setNm(hcWorkOrderCar.getNm());
            hcCar.setIdNo(hcWorkOrderCar.getIdNo());
            hcCar.setCarPhoto(hcWorkOrderCar.getCarPhoto());
            hcCar.setCarColor(hcWorkOrderCar.getCarColor());
            hcCar.setCarType(hcWorkOrderCar.getCarType());
            hcCar.setCarTypeName(hcWorkOrderCar.getCarTypeName());
            hcCar.setVehicleLic(hcWorkOrderCar.getVehicleLic());
            hcCar.setHcTransportCertNo(hcWorkOrderCar.getHcTransportCertNo());
            hcCar.setEmisStandard(hcWorkOrderCar.getEmisStandard());
            hcCar.setEmisStandardName(hcWorkOrderCar.getEmisStandardName());
            hcCar.setEnvSign(hcWorkOrderCar.getEnvSign());
            hcCar.setCreateBy(SecurityUtils.getUsernameDefaultSystem());
            hcCar.setCreateTime(DateUtils.getNowDate());
            hcCarMapper.insertHcCar(hcCar);
        }
    }

    /**
     * 從ERP獲取數據
     *
     * @param boList
     * @return
     */
    public int syncErp(List<V0NBRKX5Bo> boList, SysDept plant) {
        int result = 0;
        List<HcCar> addList = new ArrayList<>();
        List<HcCar> updateList = new ArrayList<>();

        if(boList.size()==0){
            return result;
        }

        //只導入車輛
        List<V0NBRKX5Bo> tempBoList = new ArrayList<>();
        for (V0NBRKX5Bo bo : boList) {
            VndIdNoType vndIdNoType = hcUserService.checkIdNoType(bo.getIdno());
            if (!VndIdNoType.MAN.equals(vndIdNoType)) {
                tempBoList.add(bo);
            }
        }
        boList = tempBoList;

        //開始導入
        List<String> idNoList = boList.stream().map(V0NBRKX5Bo::getIdno).distinct().collect(Collectors.toList());
        Map<String, HcCar> entityMap = this.getEntityMap(idNoList);

        List<HcCar> newList = new ArrayList<>();
        HcCar vo = null;
        for (V0NBRKX5Bo item : boList) {
            vo = item.toHcCar();
            newList.add(vo);
        }

        List<HcCar> oldList = entityMap.values().stream().collect(Collectors.toList());
        List<String> keyFieldNames = Arrays.asList("idNo");
        String primaryFieldName = "id";
        List<String> syncFieldNames = Arrays.asList("idNo", "nm");

        Map<String, List<HcCar>> oprMap = SljSyncUtils.getOprMap(newList, oldList, keyFieldNames, primaryFieldName, syncFieldNames,
                null,
                null,
                null);
        addList = oprMap.get("addList");
        updateList = oprMap.get("updateList");

        //批量新增
        if (addList.size() > 0) {
            List<List<HcCar>> lists = BatisUtils.splitList(addList, 40);
            for (List<HcCar> ls : lists) {
                result += hcCarMapper.batchInsertHcCar(ls);
            }
        }
        //批量修改
        if (updateList.size() > 0) {
            List<List<HcCar>> lists = BatisUtils.splitList(updateList, 40);
            for (List<HcCar> ls : lists) {
                result += hcCarMapper.batchUpdateHcCar(ls);
            }
        }

        return result;

    }

    /**
     * 獲取實體map
     *
     * @return
     */
    @Override
    public Map<String, HcCar> getEntityMap(List<String> idNoList) {
        Map<String, HcCar> result = new HashMap<>();
        List<HcCar> list = new ArrayList<>();
        if (idNoList == null) {
            list = hcCarMapper.selectHcCarList(new HcCar());
        } else {
            if (idNoList.size() > 0) {
                list = hcCarMapper.selectHcCarListByIdNos(idNoList.toArray(new String[0]));
            }
        }

        for (HcCar item : list) {
            result.put(item.getIdNo(), item);
        }
        return result;
    }

    /**
     * 查询危化車輛
     *
     * @param idNo 車牌
     * @return 危化車輛
     */
    @Override
    public HcCar selectHcCarByIdNo(String idNo){
        return hcCarMapper.selectHcCarByIdNo(idNo);
    }

}
