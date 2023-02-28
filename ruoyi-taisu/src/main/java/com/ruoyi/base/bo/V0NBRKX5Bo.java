package com.ruoyi.base.bo;

import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.HcWorkOrder;
import com.ruoyi.base.domain.HcWorkOrderCar;
import com.ruoyi.base.enums.DataFrom;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ZJFConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * 危化信息对象 V0NBRKX5
 * 
 * @author ruoyi
 * @date 2022-10-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class V0NBRKX5Bo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 本單編號 */
    private String vhno;

    /** 姓名 */
    private String nm;

    /** 合約卡 */
    private String ipltlic;

    /** 身份證 */
    private String idno;

    /** 部門 */
    private String dpid;

    /** 車尾車牌號 */
    private String ipltcarno;

    /** 車輛入廠重量 */
    private String ipltwgt;

    /** 車輛出廠重量 */
    private String opltwgt;

    /** 車輛入廠時間 */
    private String iplttm;

    /** 車輛出廠時間 */
    private String oplttm;

    /** 廠區標識碼 */
    private String kdnm;

    public HcWorkOrder toHcWorkOrder(){
        HcWorkOrder entity=new HcWorkOrder();
        entity.setVhNo(getVhno());
        entity.setDataFrom(DataFrom.MIS.getName());
        try {
            entity.setVhTime(DateUtils.parseDate(getIplttm(), "yyyyMMddHHmmss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public HcWorkOrderCar toHcWorkOrderCar(){
        HcWorkOrderCar entity=new HcWorkOrderCar();
        entity.setVhNo(getVhno());
        entity.setNm(ZJFConverter.SimToTra(getNm()));
        entity.setIdNo(ZJFConverter.SimToTra(getIdno()));
        entity.setIpltLic(getIpltlic());
        entity.setDpId(getDpid());
        entity.setIpltCarNo(getIpltcarno());
        entity.setDataFrom(DataFrom.MIS.getName());
        try {
            entity.setIpltTm(DateUtils.parseDate(getIplttm(), "yyyyMMddHHmmss"));
            if(!StringUtils.isEmpty(getOplttm()) && !"*".equals(getOplttm())) {
                entity.setOpltTm(DateUtils.parseDate(getOplttm(), "yyyyMMddHHmmss"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public HcUser toHcUser(){
        HcUser entity=new HcUser();
        entity.setNm(ZJFConverter.SimToTra(getNm()));
        entity.setIdNo(getIdno());
        entity.setTsNo(getIpltlic());
        return entity;
    }

    public HcCar toHcCar(){
        HcCar entity=new HcCar();
        entity.setNm(ZJFConverter.SimToTra(getNm()));
        entity.setIdNo(ZJFConverter.SimToTra(getIdno()));
        return entity;
    }



}
