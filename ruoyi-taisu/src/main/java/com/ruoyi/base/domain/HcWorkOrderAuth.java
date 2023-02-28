package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
/**
 * 危化工單組合認證对象 hc_work_order_auth
 *
 * @author ruoyi
 * @date 2023-02-07
 */
public class HcWorkOrderAuth
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 本單編號 */
    @Excel(name = "本單編號")
    @ApiModelProperty(value = "本單編號")
    private String vhNo;

    /** plc設備id */
    @Excel(name = "plc設備id")
    @ApiModelProperty(value = "plc設備id")
    private Long plcId;

    /** plc設備ip */
    @Excel(name = "plc設備ip")
    @ApiModelProperty(value = "plc設備ip")
    private String plcIp;

    /** 人臉設備id */
    @Excel(name = "人臉設備id")
    @ApiModelProperty(value = "人臉設備id")
    private Long personDeviceId;

    /** 人臉設備ip */
    @Excel(name = "人臉設備ip")
    @ApiModelProperty(value = "人臉設備ip")
    private String personDeviceIp;

    /** 車輛設備id */
    @Excel(name = "車輛設備id")
    @ApiModelProperty(value = "車輛設備id")
    private Long carDeviceId;

    /** 車輛設備ip */
    @Excel(name = "車輛設備ip")
    @ApiModelProperty(value = "車輛設備ip")
    private String carDeviceIp;

    /** 方向（進/出）由車輛設備的方向決定 */
    @Excel(name = "方向", readConverterExp = "進=/出")
    @ApiModelProperty(value = "方向")
    private String inOutMode;

    /** 车牌号 */
    @Excel(name = "车牌号")
    @ApiModelProperty(value = "车牌号")
    private String carNo;

    /** 历史车牌号(模糊匹配后会填充进来) */
    @Excel(name = "历史车牌号(模糊匹配后会填充进来)")
    @ApiModelProperty(value = "历史车牌号(模糊匹配后会填充进来)")
    private String carNoHistory;

    /** 司机身份ID */
    @Excel(name = "司机身份ID")
    @ApiModelProperty(value = "司机身份ID")
    private String driverIdCard;

    /** 司机姓名 */
    @Excel(name = "司机姓名")
    @ApiModelProperty(value = "司机姓名")
    private String driverName;

    /** 押运员身份ID */
    @Excel(name = "押运员身份ID")
    @ApiModelProperty(value = "押运员身份ID")
    private String escortIdCard;

    /** 押运员姓名 */
    @Excel(name = "押运员姓名")
    @ApiModelProperty(value = "押运员姓名")
    private String escortName;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最新更新时间")
    @ApiModelProperty(value = "最新更新时间")
    private Date updateTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setVhNo(String vhNo)
    {
        this.vhNo = vhNo;
    }

    public String getVhNo()
    {
        return vhNo;
    }
    public void setPlcId(Long plcId)
    {
        this.plcId = plcId;
    }

    public Long getPlcId()
    {
        return plcId;
    }
    public void setPlcIp(String plcIp)
    {
        this.plcIp = plcIp;
    }

    public String getPlcIp()
    {
        return plcIp;
    }
    public void setPersonDeviceId(Long personDeviceId)
    {
        this.personDeviceId = personDeviceId;
    }

    public Long getPersonDeviceId()
    {
        return personDeviceId;
    }
    public void setPersonDeviceIp(String personDeviceIp)
    {
        this.personDeviceIp = personDeviceIp;
    }

    public String getPersonDeviceIp()
    {
        return personDeviceIp;
    }
    public void setCarDeviceId(Long carDeviceId)
    {
        this.carDeviceId = carDeviceId;
    }

    public Long getCarDeviceId()
    {
        return carDeviceId;
    }
    public void setCarDeviceIp(String carDeviceIp)
    {
        this.carDeviceIp = carDeviceIp;
    }

    public String getCarDeviceIp()
    {
        return carDeviceIp;
    }
    public void setInOutMode(String inOutMode)
    {
        this.inOutMode = inOutMode;
    }

    public String getInOutMode()
    {
        return inOutMode;
    }
    public void setCarNo(String carNo)
    {
        this.carNo = carNo;
    }

    public String getCarNo()
    {
        return carNo;
    }
    public void setCarNoHistory(String carNoHistory)
    {
        this.carNoHistory = carNoHistory;
    }

    public String getCarNoHistory()
    {
        return carNoHistory;
    }
    public void setDriverIdCard(String driverIdCard)
    {
        this.driverIdCard = driverIdCard;
    }

    public String getDriverIdCard()
    {
        return driverIdCard;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getDriverName()
    {
        return driverName;
    }
    public void setEscortIdCard(String escortIdCard)
    {
        this.escortIdCard = escortIdCard;
    }

    public String getEscortIdCard()
    {
        return escortIdCard;
    }
    public void setEscortName(String escortName)
    {
        this.escortName = escortName;
    }

    public String getEscortName()
    {
        return escortName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vhNo", getVhNo())
                .append("plcId", getPlcId())
                .append("plcIp", getPlcIp())
                .append("personDeviceId", getPersonDeviceId())
                .append("personDeviceIp", getPersonDeviceIp())
                .append("carDeviceId", getCarDeviceId())
                .append("carDeviceIp", getCarDeviceIp())
                .append("inOutMode", getInOutMode())
                .append("carNo", getCarNo())
                .append("carNoHistory", getCarNoHistory())
                .append("driverIdCard", getDriverIdCard())
                .append("driverName", getDriverName())
                .append("escortIdCard", getEscortIdCard())
                .append("escortName", getEscortName())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}