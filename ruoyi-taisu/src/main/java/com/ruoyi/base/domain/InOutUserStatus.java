package com.ruoyi.base.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 內部人員進出对象 in_out_user_status
 *
 * @author ruoyi
 * @date 2023-03-22
 */
public class InOutUserStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    private String idNo;


    private String username;

    private String deptName;

    private Date inTime;


    private Date outTime;



    private Integer inOrOutFlag;

    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    public String getIdNo()
    {
        return idNo;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setInTime(Date inTime)
    {
        this.inTime = inTime;
    }

    public Date getInTime()
    {
        return inTime;
    }
    public void setOutTime(Date outTime)
    {
        this.outTime = outTime;
    }

    public Date getOutTime()
    {
        return outTime;
    }
    public void setInOrOutFlag(Integer inOrOutFlag)
    {
        this.inOrOutFlag = inOrOutFlag;
    }

    public Integer getInOrOutFlag()
    {
        return inOrOutFlag;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
