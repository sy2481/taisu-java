package com.ruoyi.base.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * pvc厂进出记录对象 in_out_log_pvc
 *
 * @author ruoyi
 * @date 2022-07-11
 */
public class InOutLogPvc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String workTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String areaNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ip;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String vhNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String egNo;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String egName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String oprEnvt;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String idno;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String ipltLic;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String pz;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String tkVnd;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String beginTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String endTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String inTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String outTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String inspector;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String inspector2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String mark;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String profsId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String opltTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String doutTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fvId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String fctDoorNm;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer aid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date insertTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer isProcess;

    /** 刪除標志（0代表存在 2代表删除） */
    private String delFlag;

    public void setWorkTime(String workTime)
    {
        this.workTime = workTime;
    }

    public String getWorkTime()
    {
        return workTime;
    }
    public void setAreaNo(String areaNo)
    {
        this.areaNo = areaNo;
    }

    public String getAreaNo()
    {
        return areaNo;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }
    public void setVhNo(String vhNo)
    {
        this.vhNo = vhNo;
    }

    public String getVhNo()
    {
        return vhNo;
    }
    public void setEgNo(String egNo)
    {
        this.egNo = egNo;
    }

    public String getEgNo()
    {
        return egNo;
    }
    public void setEgName(String egName)
    {
        this.egName = egName;
    }

    public String getEgName()
    {
        return egName;
    }
    public void setOprEnvt(String oprEnvt)
    {
        this.oprEnvt = oprEnvt;
    }

    public String getOprEnvt()
    {
        return oprEnvt;
    }
    public void setIdno(String idno)
    {
        this.idno = idno;
    }

    public String getIdno()
    {
        return idno;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setIpltLic(String ipltLic)
    {
        this.ipltLic = ipltLic;
    }

    public String getIpltLic()
    {
        return ipltLic;
    }
    public void setPz(String pz)
    {
        this.pz = pz;
    }

    public String getPz()
    {
        return pz;
    }
    public void setTkVnd(String tkVnd)
    {
        this.tkVnd = tkVnd;
    }

    public String getTkVnd()
    {
        return tkVnd;
    }
    public void setBeginTime(String beginTime)
    {
        this.beginTime = beginTime;
    }

    public String getBeginTime()
    {
        return beginTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public String getEndTime()
    {
        return endTime;
    }
    public void setInTime(String inTime)
    {
        this.inTime = inTime;
    }

    public String getInTime()
    {
        return inTime;
    }
    public void setOutTime(String outTime)
    {
        this.outTime = outTime;
    }

    public String getOutTime()
    {
        return outTime;
    }
    public void setInspector(String inspector)
    {
        this.inspector = inspector;
    }

    public String getInspector()
    {
        return inspector;
    }
    public void setInspector2(String inspector2)
    {
        this.inspector2 = inspector2;
    }

    public String getInspector2()
    {
        return inspector2;
    }
    public void setMark(String mark)
    {
        this.mark = mark;
    }

    public String getMark()
    {
        return mark;
    }
    public void setProfsId(String profsId)
    {
        this.profsId = profsId;
    }

    public String getProfsId()
    {
        return profsId;
    }
    public void setOpltTime(String opltTime)
    {
        this.opltTime = opltTime;
    }

    public String getOpltTime()
    {
        return opltTime;
    }
    public void setDoutTime(String doutTime)
    {
        this.doutTime = doutTime;
    }

    public String getDoutTime()
    {
        return doutTime;
    }
    public void setFvId(String fvId)
    {
        this.fvId = fvId;
    }

    public String getFvId()
    {
        return fvId;
    }
    public void setFctDoorNm(String fctDoorNm)
    {
        this.fctDoorNm = fctDoorNm;
    }

    public String getFctDoorNm()
    {
        return fctDoorNm;
    }
    public void setAid(Integer aid)
    {
        this.aid = aid;
    }

    public Integer getAid()
    {
        return aid;
    }
    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }
    public void setIsProcess(Integer isProcess)
    {
        this.isProcess = isProcess;
    }

    public Integer getIsProcess()
    {
        return isProcess;
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
                .append("workTime", getWorkTime())
                .append("areaNo", getAreaNo())
                .append("ip", getIp())
                .append("vhNo", getVhNo())
                .append("egNo", getEgNo())
                .append("egName", getEgName())
                .append("oprEnvt", getOprEnvt())
                .append("idno", getIdno())
                .append("name", getName())
                .append("ipltLic", getIpltLic())
                .append("pz", getPz())
                .append("tkVnd", getTkVnd())
                .append("beginTime", getBeginTime())
                .append("endTime", getEndTime())
                .append("inTime", getInTime())
                .append("outTime", getOutTime())
                .append("inspector", getInspector())
                .append("inspector2", getInspector2())
                .append("mark", getMark())
                .append("profsId", getProfsId())
                .append("opltTime", getOpltTime())
                .append("doutTime", getDoutTime())
                .append("fvId", getFvId())
                .append("fctDoorNm", getFctDoorNm())
                .append("aid", getAid())
                .append("insertTime", getInsertTime())
                .append("isProcess", getIsProcess())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
