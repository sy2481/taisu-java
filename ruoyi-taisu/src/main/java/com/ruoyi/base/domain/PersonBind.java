package com.ruoyi.base.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonBind implements Serializable {

    private Long id;
    /**
     * 0-内部员工，1-厂商人员
     */
    private Integer personType;
    /**
     * 定位卡SN号
     */
    private String sn;
    /**
     * 身份证号（台胞号），可能18位或10位，字段唯一
     */
    private String idCard;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 0-男，1-女，2-未知
     */
    private String sex;
    /**
     * 人脸照片url地址
     */
    private String face;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 车牌号，简体编码；多个用“,”拼接
     */
    private String plateNo;
    /**
     * 可进入厂区，厂区缩写如AE,SAP；多个用“,”拼接
     */
    private String factoryArea;
    /**
     * 员工-编号
     */
    private String empNo;
    /**
     * 员工-入职日期
     */
    private String joinDate;
    /**
     * 员工-归属部门名称
     */
    private String deptName;
    /**
     * 员工岗位
     */
    private String userPost;
    /**
     * 厂商-工单号
     */
    private String workNo;
    /**
     * 厂商-工程编号
     */
    private String projectNo;
    /**
     * 厂商-供应商公司名称
     */
    private String factoryName;
    /**
     * 数据创建时间
     */
    private Date createTime;
    /**
     * 数据更新时间
     */
    private Date updateTime;
    /**
     * 0-绑卡，1-解绑（解绑后会将SN置空）
     */
    private Integer eventType;

}
