package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * device对象 device
 *
 */
public class Device extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private Long type;

    /** $column.columnComment */

    private String ip;

    /** $column.columnComment */

    private String port;

    /** $column.columnComment */
    private String iscDeviceCode;

    /** $column.columnComment */
    private String status;

    /** $column.columnComment */
    private String plcCode;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setType(Long type)
    {
        this.type = type;
    }

    public Long getType()
    {
        return type;
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
    public void setIscDeviceCode(String iscDeviceCode)
    {
        this.iscDeviceCode = iscDeviceCode;
    }

    public String getIscDeviceCode()
    {
        return iscDeviceCode;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setPlcCode(String plcCode)
    {
        this.plcCode = plcCode;
    }

    public String getPlcCode()
    {
        return plcCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("ip", getIp())
                .append("port", getPort())
                .append("iscDeviceCode", getIscDeviceCode())
                .append("status", getStatus())
                .append("plcCode", getPlcCode())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}