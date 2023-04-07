package com.ruoyi.base.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 大門对象 base_gate
 *
 * @author ruoyi
 * @date 2022-11-10
 */
public class BaseGate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long gateId;

    /**
     * 大门名稱
     */
    @ApiModelProperty("大门名稱")
    @Excel(name = "大门名稱")
    private String name;

    /**
     * 廠區id
     */
    @ApiModelProperty("廠區id")
    @Excel(name = "廠區id")
    private Long plantAreaId;

    /**
     * 廠區名稱
     */
    @Excel(name = "廠區名稱")
    @ApiModelProperty(value = "廠區名稱")
    private String plantAreaName;

    /**
     * 大门编号
     */
    @ApiModelProperty("大门编号")
    @Excel(name = "大门编号")
    private String gateNo;

    /**
     * y轴
     */
    @ApiModelProperty("y轴")
    @Excel(name = "y轴")
    private String yAxis;

    /**
     * x轴
     */
    @ApiModelProperty("x轴")
    @Excel(name = "x轴")
    private String xAxis;

    private List<BaseDoor> baseDoorList;

    /**
     * 门状态 0 正常 1 开启 2 异常 3 警报
     */
    private Integer status;

    /** 经度 */
    @ApiModelProperty("经度")
    @Excel(name = "经度")
    private String longitude;

    /** 纬度 */
    @ApiModelProperty("纬度")
    @Excel(name = "纬度")
    private String latitude;


    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPlantAreaId(Long plantAreaId) {
        this.plantAreaId = plantAreaId;
    }

    public Long getPlantAreaId() {
        return plantAreaId;
    }

    public String getPlantAreaName() {
        return plantAreaName;
    }

    public void setPlantAreaName(String plantAreaName) {
        this.plantAreaName = plantAreaName;
    }

    public void setGateNo(String gateNo) {
        this.gateNo = gateNo;
    }

    public String getGateNo() {
        return gateNo;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getxAxis() {
        return xAxis;
    }

    public List<BaseDoor> getBaseDoorList() {
        return baseDoorList;
    }

    public void setBaseDoorList(List<BaseDoor> baseDoorList) {
        this.baseDoorList = baseDoorList;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getLongitude()
    {
        return longitude;
    }
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("gateId", getGateId())
                .append("name", getName())
                .append("plantAreaId", getPlantAreaId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("gateNo", getGateNo())
                .append("yAxis", getyAxis())
                .append("xAxis", getxAxis())
                .append("plantAreaName", getPlantAreaName())
                .append("longitude", getLongitude())
                .append("latitude", getLatitude())
                .toString();
    }
}
