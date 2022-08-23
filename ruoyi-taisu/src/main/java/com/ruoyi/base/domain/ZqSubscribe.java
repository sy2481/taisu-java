package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 报预紧信息对象 zq_subscribe
 *
 * @author ruoyi
 * @date 2022-05-07
 */
public class ZqSubscribe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private String delFlag;

    /** 發送時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "發送時間",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /** 數據類型 */
    @Excel(name = "數據類型")
    private String dataType;

    /** 企業信用代碼 */
    @Excel(name = "企業信用代碼")
    private String socialCreditCode;

    /** 報警ID */
    @Excel(name = "報警ID")
    private String warnId;

    /** 報警時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "報警時間" ,dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date warnTime;

    /** alarm 代表报警、alarm:handle 代表报警已处 */
    @Excel(name = "alarm 代表报警、alarm:handle 代表报警已处")
    private String warnType;

    /** 報警信息 */
    @Excel(name = "報警信息")
    private String alarmInfo;

    /** 工號 */
    @Excel(name = "工號")
    private String cede;

    /** 報警類型 */
    @Excel(name = "報警類型")
    private String alarmType;

    /** 報警位置 */
    @Excel(name = "報警位置")
    private String alarmLocation;
    /**
     * 厂区ID
     */
    private Long deptId;
    /**
     * 厂区名称
     */
    private String deptName;

    private List<Long> deptIds;

    public List<Long> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Long> deptIds) {
        this.deptIds = deptIds;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }



    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public Date getSendTime()
    {
        return sendTime;
    }
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
    public void setSocialCreditCode(String socialCreditCode)
    {
        this.socialCreditCode = socialCreditCode;
    }

    public String getSocialCreditCode()
    {
        return socialCreditCode;
    }
    public void setWarnId(String warnId)
    {
        this.warnId = warnId;
    }

    public String getWarnId()
    {
        return warnId;
    }
    public void setWarnTime(Date warnTime)
    {
        this.warnTime = warnTime;
    }

    public Date getWarnTime()
    {
        return warnTime;
    }
    public void setWarnType(String warnType)
    {
        this.warnType = warnType;
    }

    public String getWarnType()
    {
        return warnType;
    }
    public void setAlarmInfo(String alarmInfo)
    {
        this.alarmInfo = alarmInfo;
    }

    public String getAlarmInfo()
    {
        return alarmInfo;
    }
    public void setCede(String cede)
    {
        this.cede = cede;
    }

    public String getCede()
    {
        return cede;
    }
    public void setAlarmType(String alarmType)
    {
        this.alarmType = alarmType;
    }

    public String getAlarmType()
    {
        return alarmType;
    }
    public void setAlarmLocation(String alarmLocation)
    {
        this.alarmLocation = alarmLocation;
    }

    public String getAlarmLocation()
    {
        return alarmLocation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("sendTime", getSendTime())
                .append("dataType", getDataType())
                .append("socialCreditCode", getSocialCreditCode())
                .append("warnId", getWarnId())
                .append("warnTime", getWarnTime())
                .append("warnType", getWarnType())
                .append("alarmInfo", getAlarmInfo())
                .append("cede", getCede())
                .append("alarmType", getAlarmType())
                .append("alarmLocation", getAlarmLocation())
                .toString();
    }
}
