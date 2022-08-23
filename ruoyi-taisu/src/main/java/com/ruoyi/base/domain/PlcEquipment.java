package com.ruoyi.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * PLC 道闸对象 plc_equipment
 * 
 * @author ruoyi
 * @date 2022-03-10
 */
public class PlcEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 设备ip */
    @Excel(name = "设备ip")
    private String ip;

    /** 端口 */
    @Excel(name = "端口")
    private Long port;

    /** 厂区id */
    @Excel(name = "厂区id")
    private Long plantAreaId;



    /** 厂区名称 */
    @Excel(name = "厂区名称")
    private String deptName;

    /** 是否为危化品通道 */
    @Excel(name = "危化品通道")
    private Long hazardousChemicals;
    /**
     * 门禁方式
     */
    private String  control;

    /** 通道管制开始时间 */

    private String startTime1;

    /** 通道管制结束时间 */

    private String endTime1;

    /** 通道管制开始时间 */

    private String startTime2;

    /** 通道管制结束时间 */

    private String endTime2;

    /** 通道管制开始时间 */

    private String startTime3;

    /** 通道管制结束时间 */

    private String endTime3;


    private String time1;
    private String time2;
    private String time3;

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getStartTime3() {
        return startTime3;
    }

    public void setStartTime3(String startTime3) {
        this.startTime3 = startTime3;
    }

    public String getEndTime3() {
        return endTime3;
    }

    public void setEndTime3(String endTime3) {
        this.endTime3 = endTime3;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }


    /** 通道管制是否开启，0-开启，1-关闭 */
    @Excel(name = "通道管制是否开启，0-开启，1-关闭")
    private Long controlType;




    public Long getControlType() {
        return controlType;
    }

    public void setControlType(Long controlType) {
        this.controlType = controlType;
    }

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
    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setPort(Long port) 
    {
        this.port = port;
    }

    public Long getPort() 
    {
        return port;
    }
    public void setPlantAreaId(Long plantAreaId) 
    {
        this.plantAreaId = plantAreaId;
    }

    public Long getPlantAreaId() 
    {
        return plantAreaId;
    }
    public void setHazardousChemicals(Long hazardousChemicals) 
    {
        this.hazardousChemicals = hazardousChemicals;
    }

    public Long getHazardousChemicals() 
    {
        return hazardousChemicals;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("ip", getIp())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("port", getPort())
            .append("plantAreaId", getPlantAreaId())
            .append("hazardousChemicals", getHazardousChemicals())
            .toString();
    }
}
