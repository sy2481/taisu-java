package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 危化进出记录对象 in_out_log_perilous
 *
 * @author ruoyi
 * @date 2022-04-28
 */
public class InOutLogPerilous extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 出入设备ip */
    @Excel(name = "出入设备ip")
    private String ip;

    /** 厂区名称 */
    @Excel(name = "厂区名称")
    private String areaName;

    /** 员工身份ID */
    @Excel(name = "身份证号")
    private String idCard;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNo;

    /** 日志类型，进/出（0/1） */
    @Excel(name = "操作类型" , dictType = "sign_type")
    private Integer logType;

    /** 廠區ID */

    private Long deptId;

    /** 门禁验证方式 */
    @Excel(name = "门禁验证方式")
    private String checkType;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /**
     * 廠區ID虛擬
     */
    private List<Long> factoryId;


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

    public List<Long> getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(List<Long> factoryId) {
        this.factoryId = factoryId;
    }


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaName()
    {
        return areaName;
    }
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getIdCard()
    {
        return idCard;
    }
    public void setCarNo(String carNo)
    {
        this.carNo = carNo;
    }

    public String getCarNo()
    {
        return carNo;
    }
    public void setLogType(Integer logType)
    {
        this.logType = logType;
    }

    public Integer getLogType()
    {
        return logType;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setCheckType(String checkType)
    {
        this.checkType = checkType;
    }

    public String getCheckType()
    {
        return checkType;
    }
    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("ip", getIp())
                .append("areaName", getAreaName())
                .append("createTime", getCreateTime())
                .append("idCard", getIdCard())
                .append("carNo", getCarNo())
                .append("logType", getLogType())
                .append("deptId", getDeptId())
                .append("checkType", getCheckType())
                .append("deviceName", getDeviceName())
                .toString();
    }
}
