package com.ruoyi.base.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 尖峰數據对象 peak_info
 *
 * @author ruoyi
 * @date 2023-03-27
 */
public class PeakInfo implements Serializable
{


    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private String peakType;

    /** $column.columnComment */

    private Integer peakCount;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPeakType(String peakType)
    {
        this.peakType = peakType;
    }

    public String getPeakType()
    {
        return peakType;
    }
    public void setPeakCount(Integer peakCount)
    {
        this.peakCount = peakCount;
    }

    public Integer getPeakCount()
    {
        return peakCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("peakType", getPeakType())
                .append("peakCount", getPeakCount())
                .toString();
    }
}
