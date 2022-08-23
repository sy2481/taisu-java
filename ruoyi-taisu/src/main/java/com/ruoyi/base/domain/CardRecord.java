package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 卡片記錄对象 base_card_record
 * 
 * @author ZZF
 * @date 2022-03-07
 */
@Data
public class CardRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主鍵 */
    private Long cardRecordId;

    /** 卡類型(0=車卡，1=定位卡) */
    @Excel(name = "卡類型(0=車卡，1=定位卡)")
    private String cardType;

    /** 卡ID */
    @Excel(name = "卡ID")
    private Long cardId;

    /** 卡編號 */
    @Excel(name = "卡編號")
    private String cardNo;

    /** 操作方式(0=領用，1=歸還) */
    @Excel(name = "操作方式(0=領用，1=歸還)")
    private String cardRecordOperate;

    /** 操作用途 */
    @Excel(name = "操作用途")
    private String cardRecordUse;

    /** 操作對象（綁定對象） */
    @Excel(name = "操作對象")
    private String cardRecordObject;

    /** 操作時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "操作時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm")
    private Date cardRecordTime;

    /** 實際操作人 */
    @Excel(name = "實際操作人")
    private String cardRecordName;

    /** 刪除標志（0代表存在 2代表删除） */
    private String delFlag;

}
