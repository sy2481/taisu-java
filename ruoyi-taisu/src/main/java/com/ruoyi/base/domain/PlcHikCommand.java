package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 設備所屬指令对象 plc_hik_command
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
public class PlcHikCommand extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 設備ID */
    @Excel(name = "設備ID")
    private Long plcHikId;

    /** 指令代碼 */
    @Excel(name = "指令代碼")
    private String code;

    /** 指令名稱 */
    @Excel(name = "指令名稱")
    private String name;

    /** 指令 */
    @Excel(name = "指令")
    private String command;

    /** 順序 */
    @Excel(name = "順序")
    private Long sort;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlcHikId(Long plcHikId) 
    {
        this.plcHikId = plcHikId;
    }

    public Long getPlcHikId() 
    {
        return plcHikId;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCommand(String command) 
    {
        this.command = command;
    }

    public String getCommand() 
    {
        return command;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("plcHikId", getPlcHikId())
            .append("code", getCode())
            .append("name", getName())
            .append("command", getCommand())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
