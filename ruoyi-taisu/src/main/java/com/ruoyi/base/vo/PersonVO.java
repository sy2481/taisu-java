package com.ruoyi.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenyuren
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonVO implements Serializable {
    /** 是否下发海康所有设备权限；内部员工填true，厂商人员 false */
    private boolean authIsAll;
    /** 下发海康设备权限 **/
    private List<String> deviceNos;
    /** 人脸base64字符串 **/
    private String faceBase64Str;
    /** 工号 **/
    private String jobNo;
    /** 厂商员工必填，厂商员工工单号 填工单号 **/
    private String orderSn;
    /** 海康人员唯一标识，必传填身份证号 **/
    private String personId;
    /** 人员名称 **/
    private String personName;
    /** 人员类型 0-内部员工、1-厂商员工 **/
    private Integer personType;
    /** 手机号 **/
    private String phoneNo;

    /****************特殊權限*************/
    /**
     * 人道特殊权限-脸
     */
    private String faceSpecialPersonRoad;
    /**
     * 车道特殊权限-脸
     */
    private String faceSpecialCarRoad;
    /**
     * 人道特殊权限-卡
     */
    private String cardSpecialPersonRoad;
    /**
     * 车道特殊权限-卡
     */
    private String cardSpecialCarRoad;
}
