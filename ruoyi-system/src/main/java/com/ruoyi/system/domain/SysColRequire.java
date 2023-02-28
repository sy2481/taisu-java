package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 字段必填配置对象 sys_col_require
 * 
 * @author ruoyi
 * @date 2023-02-20
 */
public class SysColRequire extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 表名 */
    @Excel(name = "表名")
    private String tableName;

    /** 字段名 */
    @Excel(name = "字段名")
    private String colName;

    /** 字段名称 */
    @Excel(name = "字段名称")
    private String colDisplay;

    /** 是否必填 */
    @Excel(name = "是否必填")
    private String isRequire;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTableName(String tableName) 
    {
        this.tableName = tableName;
    }

    public String getTableName() 
    {
        return tableName;
    }
    public void setColName(String colName) 
    {
        this.colName = colName;
    }

    public String getColName() 
    {
        return colName;
    }

    public void setColDisplay(String colDisplay)
    {
        this.colDisplay = colDisplay;
    }

    public String getColDisplay()
    {
        return colDisplay;
    }

    public void setIsRequire(String isRequire) 
    {
        this.isRequire = isRequire;
    }

    public String getIsRequire() 
    {
        return isRequire;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tableName", getTableName())
            .append("colName", getColName())
            .append("colDisplay", getColDisplay())
            .append("isRequire", getIsRequire())
            .toString();
    }
}
