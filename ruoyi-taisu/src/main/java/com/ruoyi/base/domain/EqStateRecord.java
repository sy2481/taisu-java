package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 設備在線記錄对象 eq_state_record
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public class EqStateRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 設備類型：hik_equipment、plc_equipme */
    @ApiModelProperty("設備類型：hik_equipment、plc_equipme")
    @Excel(name = "設備類型：hik_equipment、plc_equipme")
    private String eqType;

    /** 設備表ID */
    @ApiModelProperty("設備表ID")
    @Excel(name = "設備表ID")
    private Long eqId;

    /** 設備名稱 */
    @ApiModelProperty("設備名稱")
    @Excel(name = "設備名稱")
    private String eqName;

    /** 設備IP */
    @ApiModelProperty("設備IP")
    @Excel(name = "設備IP")
    private String ip;

    /** 設備端口 */
    @ApiModelProperty("設備端口")
    @Excel(name = "設備端口")
    private String port;

    /** 在線狀態(1=在線;0=離線) */
    @ApiModelProperty("在線狀態(1=在線;0=離線)")
    @Excel(name = "在線狀態(1=在線;0=離線)")
    private Long status;

    /** 在線狀態名稱 */
    @ApiModelProperty("在線狀態名稱")
    @Excel(name = "在線狀態名稱")
    private String statusName;

    /** 更新時間 */
    @ApiModelProperty("更新時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "更新時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEqType(String eqType) 
    {
        this.eqType = eqType;
    }

    public String getEqType() 
    {
        return eqType;
    }
    public void setEqId(Long eqId) 
    {
        this.eqId = eqId;
    }

    public Long getEqId() 
    {
        return eqId;
    }
    public void setEqName(String eqName) 
    {
        this.eqName = eqName;
    }

    public String getEqName() 
    {
        return eqName;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setPort(String port) 
    {
        this.port = port;
    }

    public String getPort() 
    {
        return port;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setStatusName(String statusName) 
    {
        this.statusName = statusName;
    }

    public String getStatusName() 
    {
        return statusName;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("eqType", getEqType())
            .append("eqId", getEqId())
            .append("eqName", getEqName())
            .append("ip", getIp())
            .append("port", getPort())
            .append("status", getStatus())
            .append("statusName", getStatusName())
            .append("recordTime", getRecordTime())
            .toString();
    }
}
