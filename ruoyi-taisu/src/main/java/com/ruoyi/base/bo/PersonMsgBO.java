package com.ruoyi.base.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shiva   2022/3/7 14:43
 */
@Data
public class PersonMsgBO implements Serializable {

    // 0-内部员工，1-厂商人员
    private String personType;

    // 身份证号
    private String idCard;

    // 姓名
    private String name;

    // 手机号
    private String mobileNo;

    // 人脸照片
    private String face;

    // 定位卡编号
    private String locationCardNo;

    // 车牌号
    private String plateNo;

    // 车卡编号
    private String carCardNo;

}
