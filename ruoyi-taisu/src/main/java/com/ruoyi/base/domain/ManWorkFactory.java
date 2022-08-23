package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 工单对象 man_work
 *
 * @author ruoyi
 * @date 2022-03-06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManWorkFactory extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 工單ID
     */
    private Long workId;
    /**
     * 廠商人員ID
     */
    private Long factoryId;
    /**
     * 有效時間
     */
    private String effectiveTime;

    /**
     * 廠商名稱
     */
    private String factoryName;
    /**
     * 當前工程編號
     */
    private String thisNumber;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性別1男2女
     */
    private Long sex;
    /**
     * 身份証
     */
    private String idCard;
    /**
     * 出生年月
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDay;
    /**
     * 手機號
     */
    private String phone;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 卡类型（0合约卡/1临时卡）
     */
    private Long cardType;

    /**
     * 車牌
     */
    private String lcensePlate;
    /**
     * 車卡
     */
    private String carCardNo;

    /**
     * 入場證（定位卡編號）
     */
    private String ipLtLic;

    /**
     * 門禁管理員姓名
     */
    private String mngName;

    /**
     * 門禁管理員上傳時間
     */
    private String mngTime;

    /**
     * 類型：3人員，2司機
     */
    private Long type;
    /**
     * 人臉戰片地址
     */
    private String face;
    /**
     * 定位卡
     */
//    @Excel(name = "定位卡")
    private String locationCard;

    /**
     * 工程編號
     */
    private String projectNo;

    private String ip;

    private String factoryDoorName;
    /**
     * 發張廠區編號
     */
    private String workNo;

    /**
     * 工单有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workTime;

    /**
     * 車牌
     */
    private String carId;

    /**
     * 項目名稱
     */
    private String projectName;

    /**
     * 作業類別
     */
    private String jobCategory;

    /**
     * 安全督導員/覆核人員=Extender
     */
    private String inspector;

    /**
     * 洽公同意人員… only for IEM
     */
    private String inspector2;
    /***    車卡-*****/
    private String carCard;

    /***   XT人员已进人员数量   **/
    private Integer xtInNum;
    /***   普通厂商人员已数量   **/
    private Integer comInNum;

    //工单类型，0-普通厂商工单，1-危化品工单
    private Integer workType;

    //工单类型，0-正常，9-异常
    private Integer workStatus;

    private Long deptId;

    private Date startTime;
    private Date endTime;
    private Date extendStartTime;
    private Date extendEndTime;



}
