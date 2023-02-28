package com.ruoyi.base.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化施工單車輛对象 hc_work_order_car
 *
 * @author ruoyi
 * @date 2023-02-02
 */
public class HcWorkOrderCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 本單編號 */
    @Excel(name = "本單編號")
    @ApiModelProperty(value = "本單編號")
    private String vhNo;

    /** 車輛名稱 */
    @Excel(name = "車輛名稱")
    @ApiModelProperty(value = "車輛名稱")
    private String nm;

    /** 車牌 */
    @Excel(name = "車牌")
    @ApiModelProperty(value = "車牌")
    private String idNo;

    /** 合約卡號 */
    @Excel(name = "合約卡號")
    @ApiModelProperty(value = "合約卡號")
    private String ipltLic;

    /** 部門ID */
    @Excel(name = "部門ID")
    @ApiModelProperty(value = "部門ID")
    private String dpId;

    /** 車尾車牌號 */
    @Excel(name = "車尾車牌號")
    @ApiModelProperty(value = "車尾車牌號")
    private String ipltCarNo;

    /** 廠門編號 */
    @Excel(name = "廠門編號")
    @ApiModelProperty(value = "廠門編號")
    private String facDorNm;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    @ApiModelProperty(value = "廠區名稱")
    private String facNm;

    /** 司機身份證 */
    @Excel(name = "司機身份證")
    @ApiModelProperty(value = "司機身份證")
    private String dirverIdNo;

    /** 司機姓名 */
    @Excel(name = "司機姓名")
    @ApiModelProperty(value = "司機姓名")
    private String driverNm;

    /** 車輛照片 */
    @Excel(name = "車輛照片")
    @ApiModelProperty(value = "車輛照片")
    private String carPhoto;

    /** 車輛顏色 */
    @Excel(name = "車輛顏色")
    @ApiModelProperty(value = "車輛顏色")
    private String carColor;

    /** 車輛類型 */
    @Excel(name = "車輛類型")
    @ApiModelProperty(value = "車輛類型")
    private Long carType;

    /** 車輛類型名稱 */
    @Excel(name = "車輛類型名稱")
    @ApiModelProperty(value = "車輛類型名稱")
    private String carTypeName;

    /** 行駛證照片 */
    @Excel(name = "行駛證照片")
    @ApiModelProperty(value = "行駛證照片")
    private String vehicleLic;

    /** 危化品運輸證編號 */
    @Excel(name = "危化品運輸證編號")
    @ApiModelProperty(value = "危化品運輸證編號")
    private String hcTransportCertNo;

    /** 排放標準（國5、國6） */
    @Excel(name = "排放標準", readConverterExp = "國=5、國6")
    @ApiModelProperty(value = "排放標準")
    private Long emisStandard;

    /** 排放標準名稱 */
    @Excel(name = "排放標準名稱")
    @ApiModelProperty(value = "排放標準名稱")
    private String emisStandardName;

    /** 車輛環保標誌 */
    @Excel(name = "車輛環保標誌")
    @ApiModelProperty(value = "車輛環保標誌")
    private String envSign;

    /** 一道門入廠時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "一道門入廠時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "一道門入廠時間")
    private Date ipltTm;

    /** 一道門出廠時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "一道門出廠時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "一道門出廠時間")
    private Date opltTm;

    /** 二道門入廠時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "二道門入廠時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "二道門入廠時間")
    private Date secIpltTm;

    /** 二道門出廠時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "二道門出廠時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "二道門出廠時間")
    private Date secOpltTm;

    /** 資料來源：MIS，Man */
    @Excel(name = "資料來源")
    @ApiModelProperty(value = "資料來源：MIS，Man")
    private String dataFrom;

    /** 是否下發：Y=是，N=否 */
    @Excel(name = "是否下發")
    @ApiModelProperty(value = "是否下發：Y=是，N=否")
    private String issued;

    /** 工單日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "工單日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "工單日期")
    private Date vhTime;

    /**
     * 車輛下的危化人員
     */
    List<HcWorkOrderUser> hcWorkOrderUsers;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setVhNo(String vhNo)
    {
        this.vhNo = vhNo;
    }

    public String getVhNo()
    {
        return vhNo;
    }
    public void setNm(String nm)
    {
        this.nm = nm;
    }

    public String getNm()
    {
        return nm;
    }
    public void setIdNo(String idNo)
    {
        this.idNo = idNo;
    }

    public String getIdNo()
    {
        return idNo;
    }
    public void setIpltLic(String ipltLic)
    {
        this.ipltLic = ipltLic;
    }

    public String getIpltLic()
    {
        return ipltLic;
    }
    public void setDpId(String dpId)
    {
        this.dpId = dpId;
    }

    public String getDpId()
    {
        return dpId;
    }
    public void setIpltCarNo(String ipltCarNo)
    {
        this.ipltCarNo = ipltCarNo;
    }

    public String getIpltCarNo()
    {
        return ipltCarNo;
    }
    public void setFacDorNm(String facDorNm)
    {
        this.facDorNm = facDorNm;
    }

    public String getFacDorNm()
    {
        return facDorNm;
    }
    public void setFacNm(String facNm)
    {
        this.facNm = facNm;
    }

    public String getFacNm()
    {
        return facNm;
    }
    public void setDirverIdNo(String dirverIdNo)
    {
        this.dirverIdNo = dirverIdNo;
    }

    public String getDirverIdNo()
    {
        return dirverIdNo;
    }
    public void setDriverNm(String driverNm)
    {
        this.driverNm = driverNm;
    }

    public String getDriverNm()
    {
        return driverNm;
    }
    public void setCarPhoto(String carPhoto)
    {
        this.carPhoto = carPhoto;
    }

    public String getCarPhoto()
    {
        return carPhoto;
    }
    public void setCarColor(String carColor)
    {
        this.carColor = carColor;
    }

    public String getCarColor()
    {
        return carColor;
    }
    public void setCarType(Long carType)
    {
        this.carType = carType;
    }

    public Long getCarType()
    {
        return carType;
    }
    public void setCarTypeName(String carTypeName)
    {
        this.carTypeName = carTypeName;
    }

    public String getCarTypeName()
    {
        return carTypeName;
    }
    public void setVehicleLic(String vehicleLic)
    {
        this.vehicleLic = vehicleLic;
    }

    public String getVehicleLic()
    {
        return vehicleLic;
    }
    public void setHcTransportCertNo(String hcTransportCertNo)
    {
        this.hcTransportCertNo = hcTransportCertNo;
    }

    public String getHcTransportCertNo()
    {
        return hcTransportCertNo;
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
    public void setIpltTm(Date ipltTm)
    {
        this.ipltTm = ipltTm;
    }

    public Date getIpltTm()
    {
        return ipltTm;
    }

    public Date getOpltTm() {
        return opltTm;
    }

    public void setOpltTm(Date opltTm) {
        this.opltTm = opltTm;
    }

    public void setSecIpltTm(Date secIpltTm)
    {
        this.secIpltTm = secIpltTm;
    }

    public Date getSecIpltTm()
    {
        return secIpltTm;
    }
    public void setSecOpltTm(Date secOpltTm)
    {
        this.secOpltTm = secOpltTm;
    }

    public Date getSecOpltTm()
    {
        return secOpltTm;
    }
    public void setDataFrom(String dataFrom)
    {
        this.dataFrom = dataFrom;
    }

    public String getDataFrom()
    {
        return dataFrom;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public void setVhTime(Date vhTime)
    {
        this.vhTime = vhTime;
    }

    public Date getVhTime()
    {
        return vhTime;
    }

    public List<HcWorkOrderUser> getHcWorkOrderUsers() {
        return hcWorkOrderUsers;
    }

    public void setHcWorkOrderUsers(List<HcWorkOrderUser> hcWorkOrderUsers) {
        this.hcWorkOrderUsers = hcWorkOrderUsers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vhNo", getVhNo())
            .append("nm", getNm())
            .append("idNo", getIdNo())
            .append("ipltLic", getIpltLic())
            .append("dpId", getDpId())
            .append("ipltCarNo", getIpltCarNo())
            .append("facDorNm", getFacDorNm())
            .append("facNm", getFacNm())
            .append("dirverIdNo", getDirverIdNo())
            .append("driverNm", getDriverNm())
            .append("carPhoto", getCarPhoto())
            .append("carColor", getCarColor())
            .append("carType", getCarType())
            .append("carTypeName", getCarTypeName())
            .append("vehicleLic", getVehicleLic())
            .append("hcTransportCertNo", getHcTransportCertNo())
            .append("emisStandard", getEmisStandard())
            .append("emisStandardName", getEmisStandardName())
            .append("envSign", getEnvSign())
            .append("ipltTm", getIpltTm())
            .append("secIpltTm", getSecIpltTm())
            .append("secOpltTm", getSecOpltTm())
            .append("dataFrom", getDataFrom())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("issued",getIssued())
            .toString();
    }
}
