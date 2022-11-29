package com.ruoyi.base.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName base_car_card_bind
 */
public class BaseCarCardBind implements Serializable {
    /**
     * 车卡编号
     */
    private String cardCarNo;

    /**
     * 车卡绑定人员
     */
    private String cardCarBind;

    /**
     * 车卡领用时间
     */
    private Date leadTime;

    private String cardType;

    private static final long serialVersionUID = 1L;


    /**
     * 车卡编号
     */
    public String getCardCarNo() {
        return cardCarNo;
    }

    /**
     * 车卡编号
     */
    public void setCardCarNo(String cardCarNo) {
        this.cardCarNo = cardCarNo;
    }

    /**
     * 车卡绑定人员
     */
    public String getCardCarBind() {
        return cardCarBind;
    }

    /**
     * 车卡绑定人员
     */
    public void setCardCarBind(String cardCarBind) {
        this.cardCarBind = cardCarBind;
    }

    /**
     * 车卡领用时间
     */
    public Date getLeadTime() {
        return leadTime;
    }

    /**
     * 车卡领用时间
     */
    public void setLeadTime(Date leadTime) {
        this.leadTime = leadTime;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cardCarNo=").append(cardCarNo);
        sb.append(", cardCarBind=").append(cardCarBind);
        sb.append(", leadTime=").append(leadTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}