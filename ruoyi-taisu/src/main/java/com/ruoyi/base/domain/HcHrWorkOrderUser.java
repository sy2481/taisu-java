package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 歷史危化施工單明細对象 hc_hr_work_order_user
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public class HcHrWorkOrderUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 本單編號 */
    @Excel(name = "本單編號")
    @ApiModelProperty(value = "本單編號")
    private String vhNo;

    /** 人員姓名 */
    @Excel(name = "人員姓名")
    @ApiModelProperty(value = "人員姓名")
    private String nm;

    /** 合約卡號 */
    @Excel(name = "合約卡號")
    @ApiModelProperty(value = "合約卡號")
    private String ipltLic;

    /** 人員身份證 */
    @Excel(name = "人員身份證")
    @ApiModelProperty(value = "人員身份證")
    private String idNo;

    /** 人臉照片地址 */
    @Excel(name = "人臉照片地址")
    @ApiModelProperty(value = "人臉照片地址")
    private String face;

    /** 性別1男2女 */
    @Excel(name = "性別1男2女")
    @ApiModelProperty(value = "性別1男2女")
    private Long sex;

    /** 手機號 */
    @Excel(name = "手機號")
    @ApiModelProperty(value = "手機號")
    private String phone;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    @ApiModelProperty(value = "家庭住址")
    private String address;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生年月")
    private Date birthday;

    /** 所屬公司 */
    @Excel(name = "所屬公司")
    @ApiModelProperty(value = "所屬公司")
    private String company;

    /** 人員類型(1=司機,2=押運員) */
    @Excel(name = "人員類型(1=司機,2=押運員)")
    @ApiModelProperty(value = "人員類型(1=司機,2=押運員)")
    private Long userType;

    /** 人員類型名稱 */
    @Excel(name = "人員類型名稱")
    @ApiModelProperty(value = "人員類型名稱")
    private String userTypeName;

    /** 車牌 */
    @Excel(name = "車牌")
    @ApiModelProperty(value = "車牌")
    private String license;

    /** 車卡台塑碼 */
    @Excel(name = "車卡台塑碼")
    @ApiModelProperty(value = "車卡台塑碼")
    private String carTsNo;

    /** 廠門編號 */
    @Excel(name = "廠門編號")
    @ApiModelProperty(value = "廠門編號")
    private String facDorNm;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    @ApiModelProperty(value = "廠區名稱")
    private String facNm;

    /** 資料來源：MIS，Man */
    @Excel(name = "資料來源：MIS，Man")
    @ApiModelProperty(value = "資料來源：MIS，Man")
    private String dataFrom;

    /** 駕駛證照片 */
    @Excel(name = "駕駛證照片")
    @ApiModelProperty(value = "駕駛證照片")
    private String driverLicense;

    /** 押運員許可證 */
    @Excel(name = "押運員許可證")
    @ApiModelProperty(value = "押運員許可證")
    private String escortLicense;

    /** 一道門入廠時間 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "一道門入廠時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "一道門入廠時間")
    private Date ipltTm;

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
    public void setIpltLic(String ipltLic) 
    {
        this.ipltLic = ipltLic;
    }

    public String getIpltLic() 
    {
        return ipltLic;
    }
    public void setIdNo(String idNo) 
    {
        this.idNo = idNo;
    }

    public String getIdNo() 
    {
        return idNo;
    }
    public void setFace(String face) 
    {
        this.face = face;
    }

    public String getFace() 
    {
        return face;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public Date getBirthday() 
    {
        return birthday;
    }
    public void setCompany(String company) 
    {
        this.company = company;
    }

    public String getCompany() 
    {
        return company;
    }
    public void setUserType(Long userType) 
    {
        this.userType = userType;
    }

    public Long getUserType() 
    {
        return userType;
    }
    public void setUserTypeName(String userTypeName) 
    {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeName() 
    {
        return userTypeName;
    }
    public void setLicense(String license) 
    {
        this.license = license;
    }

    public String getLicense() 
    {
        return license;
    }
    public void setCarTsNo(String carTsNo) 
    {
        this.carTsNo = carTsNo;
    }

    public String getCarTsNo() 
    {
        return carTsNo;
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
    public void setDataFrom(String dataFrom) 
    {
        this.dataFrom = dataFrom;
    }

    public String getDataFrom() 
    {
        return dataFrom;
    }
    public void setDriverLicense(String driverLicense) 
    {
        this.driverLicense = driverLicense;
    }

    public String getDriverLicense() 
    {
        return driverLicense;
    }
    public void setEscortLicense(String escortLicense) 
    {
        this.escortLicense = escortLicense;
    }

    public String getEscortLicense() 
    {
        return escortLicense;
    }

    public Date getIpltTm() {
        return ipltTm;
    }

    public void setIpltTm(Date ipltTm) {
        this.ipltTm = ipltTm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vhNo", getVhNo())
            .append("nm", getNm())
            .append("ipltLic", getIpltLic())
            .append("idNo", getIdNo())
            .append("face", getFace())
            .append("sex", getSex())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("birthday", getBirthday())
            .append("company", getCompany())
            .append("userType", getUserType())
            .append("userTypeName", getUserTypeName())
            .append("license", getLicense())
            .append("carTsNo", getCarTsNo())
            .append("facDorNm", getFacDorNm())
            .append("facNm", getFacNm())
            .append("dataFrom", getDataFrom())
            .append("driverLicense", getDriverLicense())
            .append("escortLicense", getEscortLicense())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
