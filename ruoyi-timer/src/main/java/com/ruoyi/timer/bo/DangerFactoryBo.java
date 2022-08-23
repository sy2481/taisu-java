package com.ruoyi.timer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 兴跃
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DangerFactoryBo implements Serializable {
    /**
     * 工单号
     */
    private String workNumber;
    /** 身份証 */
    private String idCard;

    /** 姓名 */
    private String name;

    /** 入場證（定位卡編號） */
    private String ipltlic;
    /**
     * 发证厂区编号
     */
    private String factoryNo;

    /** 廠商名稱 */
    private String factoryName;

    /** 門禁管理員姓名 */
    private String mngName;

    /** 門禁管理員上傳時間 */
    private String mngTime;

    /** 類型：3人員，2司機 */
    private String type;

    /** 當前工程編號 */
    private String thisNumber;
    /**
     * 有效时间
     */
    private String datetime;

    /** 专业别代号，工安(头头)：XT*；其余的是普通作业人员 */
    private String profsid;
}
