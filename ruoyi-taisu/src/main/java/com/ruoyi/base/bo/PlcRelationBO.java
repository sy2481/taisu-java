package com.ruoyi.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shiva   2022/3/12 21:55
 */
@Data
public class PlcRelationBO implements Serializable {

    private String plcId;

    private String plcName;

    private String plcIp;

    private String plcPort;

    private Long personId;

    private Long carDeviceId;

    private String plcCommand;
    /**
     * 门禁方式
     */
    private String  control;

    /** 时间1 */
    private String time1;

    /**
     * 时间2
     */
    private String time2;

    private String time3;

    /** 通道管制是否开启，0-开启，1-关闭 */
    private Long controlType;
}
