package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 車卡对象 base_car_card
 * 
 * @author ZZF
 * @date 2022-03-06
 */
@Data
public class CarCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 車卡ID */
    private Long cardCarId;

    /** 車卡編號 */
    @Excel(name = "車卡編號")
    private String cardCarNo;

    /** 車卡狀態(0=未綁定，1=已綁定) */
    @Excel(name = "車卡狀態(0=未綁定，1=已綁定)")
    private String cardCarStatus;

    /** 車卡用途 */
    @Excel(name = "車卡用途")
    private String cardCarUse;

    /** 當前綁定車輛 */
    @Excel(name = "當前綁定車輛")
    private String bindPlateNo;

    /** 領用人名稱 */
    @Excel(name = "領用人名稱")
    private String leadName;

    /** 領用時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "領用時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date leadTime;

    /** 歸還人名稱 */
    @Excel(name = "歸還人名稱")
    private String returnName;

    /** 歸還時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "歸還時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date returnTime;

    /** 刪除標志（0代表存在 2代表删除） */
    private String delFlag;

    /** 虛擬字段（0=領用，1=歸還） */
    private String type;

}
