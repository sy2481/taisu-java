package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 廠商黑名單記錄对象 man_black_info
 *
 * @author ruoyi
 * @date 2022-04-24
 */
public class ManBlackInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主鍵 */
    private Long id;

    /** 廠商名稱 */
    @Excel(name = "人员名称")
    private String factoryName;
    /**
     * 身份证
     */
    private  String idCard;
    /** 是否被拉黑(0-拉黑，1-解除) */
    @Excel(name = "是否被拉黑(0-拉黑，1-解除)")
    private Long blackType;

    /** 拉黑原因 */
    @Excel(name = "拉黑原因")
    private String blackInfo;

    /** 實際操作人 */
    @Excel(name = "實際操作人")
    private String blackAddName;

    /** 刪除標志（0代表存在 2代表删除） */
    private String delFlag;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFactoryName(String factoryName)
    {
        this.factoryName = factoryName;
    }

    public String getFactoryName()
    {
        return factoryName;
    }
    public void setBlackType(Long blackType)
    {
        this.blackType = blackType;
    }

    public Long getBlackType()
    {
        return blackType;
    }
    public void setBlackInfo(String blackInfo)
    {
        this.blackInfo = blackInfo;
    }

    public String getBlackInfo()
    {
        return blackInfo;
    }
    public void setBlackAddName(String blackAddName)
    {
        this.blackAddName = blackAddName;
    }

    public String getBlackAddName()
    {
        return blackAddName;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("factoryName", getFactoryName())
                .append("blackType", getBlackType())
                .append("blackInfo", getBlackInfo())
                .append("blackAddName", getBlackAddName())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
