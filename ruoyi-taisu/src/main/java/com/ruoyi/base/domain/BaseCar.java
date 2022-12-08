package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 車对象 base_car
 *
 * @author ruoyi
 * @date 2022-12-08
 */
public class BaseCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 車牌 */
    @Excel(name = "車牌")
    private String idCard;

    /** 車備註 */
    @Excel(name = "車備註")
    private String name;

    /** 排放標準 */
    @Excel(name = "排放標準")
    private Long emisStandard;

    /** 排放標準名稱 */
    @Excel(name = "排放標準名稱")
    private String emisStandardName;

    /** 環保標誌圖片路徑 */
    @Excel(name = "環保標誌圖片路徑")
    private String envSign;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getIdCard()
    {
        return idCard;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setEmisStandard(Long emisStandard)
    {
        this.emisStandard = emisStandard;
    }

    public Long getEmisStandard()
    {
        return emisStandard;
    }
    public void setEmisStandardName(String emisStandardName)
    {
        this.emisStandardName = emisStandardName;
    }

    public String getEmisStandardName()
    {
        return emisStandardName;
    }
    public void setEnvSign(String envSign)
    {
        this.envSign = envSign;
    }

    public String getEnvSign()
    {
        return envSign;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("idCard", getIdCard())
                .append("name", getName())
                .append("emisStandard", getEmisStandard())
                .append("emisStandardName", getEmisStandardName())
                .append("envSign", getEnvSign())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
