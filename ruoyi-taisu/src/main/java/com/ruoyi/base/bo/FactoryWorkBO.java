package com.ruoyi.base.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shiva   2022/3/6 13:47
 */
@Data
public class FactoryWorkBO implements Serializable {

    //工单号
    public String workNo;

    //工程编号
    public String projectNo;

    //厂商人员id
    public String factoryId;

    //厂商人员姓名
    public String name;

    //身份证号
    public String idCard;

    //工单有效时间
    public String workTime;
    //手機號
    public String phone;
    //家庭住址
    public String address;
    //人脸照片
    public String face;

    public Integer sended;


    public Integer sex;
    //车牌
    private String lcensePlate;

    /**
     * 工单车辆
     */
    private List<workCarBo> workCarList;
}
