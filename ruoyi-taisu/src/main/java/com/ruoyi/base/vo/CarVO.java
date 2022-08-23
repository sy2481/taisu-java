package com.ruoyi.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2022/3/11 18:41
 */
@Data
public class CarVO implements Serializable {
    //是否下发海康所有设备权限， carType为1时，返回 true；否则为 false
    private boolean authIsAll;
    //车辆权限，厂商车辆返回对应工单下的PLC，对应下属的全部车辆设备的设备号
    private List<String> auths;
    //车牌号
    private String carNumber;
    //当为内部车辆的时候传输人员唯一标识（IDcard）， 当为厂商车辆的时候传输工单号
    private String carSn;
    //员工-0，厂商-1
    private Integer carType;
}
