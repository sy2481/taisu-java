package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 来宾卡进出记录对象 in_out_log_guest
 *
 * @author ruoyi
 * @date 2022-04-26
 */
public class InOutLogGuest extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 出入设备ip
     */
    private String ip;

    /**
     * 厂区名称
     */
    @Excel(name = "厂区名称")
    private String areaName;

    /**
     * 员工身份ID
     */
    @Excel(name = "员工身份ID")
    private String idCard;

    /**
     * 来宾卡号
     */
    @Excel(name = "来宾卡号")
    private String guestCard;

    /**
     * 日志类型，进/出（0/1）
     */
    @Excel(name = "日志类型，进/出", readConverterExp = "0=/1")
    private Integer logType;

    /**
     * 廠區ID
     */

    private Long deptId;

    /**
     * 通道
     */
    @Excel(name = "通道")
    private String pass;

    /**
     * 厂商
     */
    @Excel(name = "厂商")
    private String factoryName;
    /**
     * 借用人
     */
    @Excel(name = "借用人")
    private String userName;

    /**
     * 记录是否有效（0-有效，1-无效）
     */
    private Integer validType;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;
    /**
     * 操作人
     */
    private String operationName;

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setGuestCard(String guestCard) {
        this.guestCard = guestCard;
    }

    public String getGuestCard() {
        return guestCard;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ip", getIp())
                .append("areaName", getAreaName())
                .append("createTime", getCreateTime())
                .append("idCard", getIdCard())
                .append("guestCard", getGuestCard())
                .append("logType", getLogType())
                .append("deptId", getDeptId())
                .toString();
    }
}
