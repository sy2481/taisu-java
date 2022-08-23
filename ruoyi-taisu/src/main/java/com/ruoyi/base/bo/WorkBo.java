package com.ruoyi.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkBo {
    /** 車牌  */
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
    private String IP;
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
    /** 厂门代号 */
    private String fctdornm;
    /**
     * 有效时间
     */
    private String datetime;
    /**
     * 厂商
     */
    List<FactoryBo> factoryBoList;
    /**
     * 车辆
     */
    List<CarBo> carBoList;

    /**
     * 预定入场时间
     */
    private String begtime;

    /**
     * 预定出场时间
     */
    private String endtime;

    /** 15延长工时-入厂施工时间 */
    private String intime;

    /** 延长工时-出厂施工时间 */
    private String outtime;

}
