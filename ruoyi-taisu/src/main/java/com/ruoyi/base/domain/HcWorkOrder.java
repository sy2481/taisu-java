package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化施工單对象 hc_work_order
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public class HcWorkOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 本單編號 */
    @Excel(name = "本單編號")
    @ApiModelProperty(value = "本單編號")
    private String vhNo;

    /** 資料來源：MIS，Man */
    @Excel(name = "資料來源：MIS，Man")
    @ApiModelProperty(value = "資料來源：MIS，Man")
    private String dataFrom;

    /** 廠門編號 */
    @Excel(name = "廠門編號")
    @ApiModelProperty(value = "廠門編號")
    private String facDorNm;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    @ApiModelProperty(value = "廠區名稱")
    private String facNm;

    /** 工單日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "工單日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "工單日期")
    private Date vhTime;

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
    public void setDataFrom(String dataFrom) 
    {
        this.dataFrom = dataFrom;
    }

    public String getDataFrom() 
    {
        return dataFrom;
    }
    public void setFacDorNm(String facDorNm) 
    {
        this.facDorNm = facDorNm;
    }

    public String getFacDorNm() 
    {
        return facDorNm;
    }
    public void setFacNm(String facNm) 
    {
        this.facNm = facNm;
    }

    public String getFacNm() 
    {
        return facNm;
    }
    public void setVhTime(Date vhTime) 
    {
        this.vhTime = vhTime;
    }

    public Date getVhTime() 
    {
        return vhTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vhNo", getVhNo())
            .append("dataFrom", getDataFrom())
            .append("facDorNm", getFacDorNm())
            .append("facNm", getFacNm())
            .append("vhTime", getVhTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
