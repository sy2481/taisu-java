package com.ruoyi.base.domain;

import com.ruoyi.common.utils.ZJFConverter;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化車輛对象 hc_car
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public class HcCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 車輛名稱 */
    @Excel(name = "車輛名稱")
    @ApiModelProperty(value = "車輛名稱")
    private String nm;

    /** 車牌 */
    @Excel(name = "車牌")
    @ApiModelProperty(value = "車牌")
    private String idNo;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nm", getNm())
            .append("idNo", getIdNo())
            .append("carPhoto", getCarPhoto())
            .append("carColor", getCarColor())
            .append("carType", getCarType())
            .append("carTypeName", getCarTypeName())
            .append("vehicleLic", getVehicleLic())
            .append("hcTransportCertNo", getHcTransportCertNo())
            .append("emisStandard", getEmisStandard())
            .append("emisStandardName", getEmisStandardName())
            .append("envSign", getEnvSign())
            .toString();
    }

    public HcWorkOrderCar toHcWorkOrderCar(){
        HcWorkOrderCar entity=new HcWorkOrderCar();
        entity.setNm(ZJFConverter.SimToTra(getNm()));
        entity.setIdNo(ZJFConverter.SimToTra(getIdNo()));
        entity.setCarPhoto(getCarPhoto());
        entity.setCarColor(getCarColor());
        entity.setCarType(getCarType());
        entity.setCarTypeName(getCarTypeName());
        entity.setVehicleLic(getVehicleLic());
        entity.setHcTransportCertNo(getHcTransportCertNo());
        entity.setEmisStandard(getEmisStandard());
        entity.setEmisStandardName(getEmisStandardName());
        entity.setEnvSign(getEnvSign());
        return entity;
    }
}
