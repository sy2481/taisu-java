package com.ruoyi.timer.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 危化信息对象 V0NBRKX5
 * 
 * @author ruoyi
 * @date 2022-10-21
 */
public class V0NBRKX5 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 本單編號 */
    @ApiModelProperty("本單編號")
    @Excel(name = "本單編號")
    private String vhno;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String nm;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ipltlic;

    /** $column.columnComment */
    private String idno;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dpid;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ipltcarno;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ipltwgt;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String opltwgt;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String iplttm;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String oplttm;

    /** $column.columnComment */
    @ApiModelProperty("${comment}")
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String kdnm;

    public void setVhno(String vhno) 
    {
        this.vhno = vhno;
    }

    public String getVhno() 
    {
        return vhno;
    }
    public void setNm(String nm) 
    {
        this.nm = nm;
    }

    public String getNm() 
    {
        return nm;
    }
    public void setIpltlic(String ipltlic) 
    {
        this.ipltlic = ipltlic;
    }

    public String getIpltlic() 
    {
        return ipltlic;
    }
    public void setIdno(String idno) 
    {
        this.idno = idno;
    }

    public String getIdno() 
    {
        return idno;
    }
    public void setDpid(String dpid) 
    {
        this.dpid = dpid;
    }

    public String getDpid() 
    {
        return dpid;
    }
    public void setIpltcarno(String ipltcarno) 
    {
        this.ipltcarno = ipltcarno;
    }

    public String getIpltcarno() 
    {
        return ipltcarno;
    }
    public void setIpltwgt(String ipltwgt) 
    {
        this.ipltwgt = ipltwgt;
    }

    public String getIpltwgt() 
    {
        return ipltwgt;
    }
    public void setOpltwgt(String opltwgt) 
    {
        this.opltwgt = opltwgt;
    }

    public String getOpltwgt() 
    {
        return opltwgt;
    }
    public void setIplttm(String iplttm) 
    {
        this.iplttm = iplttm;
    }

    public String getIplttm() 
    {
        return iplttm;
    }
    public void setOplttm(String oplttm) 
    {
        this.oplttm = oplttm;
    }

    public String getOplttm() 
    {
        return oplttm;
    }
    public void setKdnm(String kdnm) 
    {
        this.kdnm = kdnm;
    }

    public String getKdnm() 
    {
        return kdnm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("vhno", getVhno())
            .append("nm", getNm())
            .append("ipltlic", getIpltlic())
            .append("idno", getIdno())
            .append("dpid", getDpid())
            .append("ipltcarno", getIpltcarno())
            .append("ipltwgt", getIpltwgt())
            .append("opltwgt", getOpltwgt())
            .append("iplttm", getIplttm())
            .append("oplttm", getOplttm())
            .append("kdnm", getKdnm())
            .toString();
    }
}
