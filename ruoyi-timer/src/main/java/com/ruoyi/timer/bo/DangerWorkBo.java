package com.ruoyi.timer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DangerWorkBo implements Serializable {
    /**
     * 車牌
     */
    private String carId;

    /**
     * 工单号
     */
    private String workNumber;
    /**
     * 施工区域名称：2-7区
     */
    private String AreaNo;
    /**
     * 入出厂卡机之IP
     */
    private String ip;
    /**
     * 工程编号
     */
    private String egno;
    /**
     * 工程名称
     */
    private String egnm;
    /**
     * 作业类别
     */
    private String oprEnvt21;
    /**
     * 安全督导员 / 覆核人员 =Extender
     */
    private String inspector;
    /**
     * 洽公同意人员 ... only for IEM
     */
    private String inspector2;
    /**
     * 有效时间
     */
    private String datetime;

    /**
     * 预定入场时间
     */
    private String begtime;

    /**
     * 预定出场时间
     */
    private String endtime;


    /**
     * 厂商
     */
    List<DangerFactoryBo> dangerFactoryBoList;
    /**
     * 车辆
     */
    List<DangerCarBo> dangerCarBoList;
}
