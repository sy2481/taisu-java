package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 定位卡对象 base_locate_card
 *
 * @author ZZF
 * @date 2022-03-06
 */
public class LocateCard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 定位卡ID
     */
    private Long cardLocateId;

    /**
     * 定位卡編號
     */
    @Excel(name = "定位卡編號")
    private String cardLocateNo;

    /**
     * 定位卡狀態(0=未綁定，1=已綁定)
     */
    @Excel(name = "定位卡狀態(0=未綁定，1=已綁定)")
    private String cardLocateStatus;

    /**
     * 定位卡用途
     */
    @Excel(name = "定位卡用途")
    private String cardLocateUse;

    /**
     * 當前綁定人編號
     */
    @Excel(name = "當前綁定人編號")
    private String bindUserCode;

    /**
     * 當前綁定人名稱
     */
    @Excel(name = "當前綁定人名稱")
    private String bindUserName;

    /**
     * 領用人名稱
     */
    @Excel(name = "領用人名稱")
    private String leadName;

    /**
     * 領用時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "領用時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leadTime;

    /**
     * 歸還人名稱
     */
    @Excel(name = "歸還人名稱")
    private String returnName;

    /**
     * 歸還時間
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "歸還時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /**
     * 刪除標志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 當前綁定人身份證
     */
    @Excel(name = "當前綁定人身份證")
    private String bindUserIdCard;

    @Excel(name = "SN號")
    private String snNum;

    @Excel(name = "电量")
    private Integer battery;

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }



    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }


    public void setCardLocateId(Long cardLocateId) {
        this.cardLocateId = cardLocateId;
    }

    public Long getCardLocateId() {
        return cardLocateId;
    }

    public void setCardLocateNo(String cardLocateNo) {
        this.cardLocateNo = cardLocateNo;
    }

    public String getCardLocateNo() {
        return cardLocateNo;
    }

    public void setCardLocateStatus(String cardLocateStatus) {
        this.cardLocateStatus = cardLocateStatus;
    }

    public String getCardLocateStatus() {
        return cardLocateStatus;
    }

    public void setCardLocateUse(String cardLocateUse) {
        this.cardLocateUse = cardLocateUse;
    }

    public String getCardLocateUse() {
        return cardLocateUse;
    }

    public void setBindUserCode(String bindUserCode) {
        this.bindUserCode = bindUserCode;
    }

    public String getBindUserCode() {
        return bindUserCode;
    }

    public void setBindUserName(String bindUserName) {
        this.bindUserName = bindUserName;
    }

    public String getBindUserName() {
        return bindUserName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public Date getLeadTime() {
        return leadTime;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setBindUserIdCard(String bindUserIdCard) {
        this.bindUserIdCard = bindUserIdCard;
    }

    public String getBindUserIdCard() {
        return bindUserIdCard;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cardLocateId", getCardLocateId())
                .append("cardLocateNo", getCardLocateNo())
                .append("snNum", getSnNum())
                .append("cardLocateStatus", getCardLocateStatus())
                .append("cardLocateUse", getCardLocateUse())
                .append("bindUserCode", getBindUserCode())
                .append("bindUserName", getBindUserName())
                .append("leadName", getLeadName())
                .append("leadTime", getLeadTime())
                .append("returnName", getReturnName())
                .append("returnTime", getReturnTime())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("bindUserIdCard", getBindUserIdCard())
                .toString();
    }
}
