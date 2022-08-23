package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 车牌近似规则配置对象 basics_matching_rules
 * 
 * @author ruoyi
 * @date 2022-07-15
 */
public class BasicsMatchingRules extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long configId;

    /** 替换值 */
    @Excel(name = "替换值")
    private String configName;

    /** 替換值 */
    @Excel(name = "替換值")
    private String configDisplace;



    public void setConfigId(Long configId) 
    {
        this.configId = configId;
    }

    public Long getConfigId() 
    {
        return configId;
    }
    public void setConfigName(String configName) 
    {
        this.configName = configName;
    }

    public String getConfigName() 
    {
        return configName;
    }
    public void setConfigDisplace(String configDisplace) 
    {
        this.configDisplace = configDisplace;
    }

    public String getConfigDisplace() 
    {
        return configDisplace;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("configName", getConfigName())
            .append("configDisplace", getConfigDisplace())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .toString();
    }
}
