package com.ruoyi.base.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化人員对象 hc_user
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
public class HcUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 人員姓名/車輛名稱 */
    @Excel(name = "人員姓名/車輛名稱")
    @ApiModelProperty(value = "人員姓名/車輛名稱")
    private String nm;

    /** 人員身份證/車牌 */
    @Excel(name = "人員身份證/車牌")
    @ApiModelProperty(value = "人員身份證/車牌")
    private String idNo;

    /** 人臉照片地址 */
    @Excel(name = "人臉照片地址")
    @ApiModelProperty(value = "人臉照片地址")
    private String face;

    /** 中心平台人臉地址 */
    @Excel(name = "中心平台人臉地址")
    @ApiModelProperty(value = "中心平台人臉地址")
    private String centFace;

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

    /** 廠門編號 */
    @Excel(name = "廠門編號")
    @ApiModelProperty(value = "廠門編號")
    private String facDorNm;

    /** 廠區名稱 */
    @Excel(name = "廠區名稱")
    @ApiModelProperty(value = "廠區名稱")
    private String facNm;

    /** 駕駛證照片 */
    @Excel(name = "駕駛證照片")
    @ApiModelProperty(value = "駕駛證照片")
    private String driverLicense;

    /** 押運員許可證 */
    @Excel(name = "押運員許可證")
    @ApiModelProperty(value = "押運員許可證")
    private String escortLicense;

    /** 台塑碼 */
    @Excel(name = "台塑碼")
    private String tsNo;

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
    public void setFace(String face) 
    {
        this.face = face;
    }

    public String getFace() 
    {
        return face;
    }
    public void setCentFace(String centFace) 
    {
        this.centFace = centFace;
    }

    public String getCentFace() 
    {
        return centFace;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getEscortLicense() {
        return escortLicense;
    }

    public void setEscortLicense(String escortLicense) {
        this.escortLicense = escortLicense;
    }

    public String getTsNo() {
        return tsNo;
    }

    public void setTsNo(String tsNo) {
        this.tsNo = tsNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nm", getNm())
            .append("idNo", getIdNo())
            .append("face", getFace())
            .append("centFace", getCentFace())
            .append("sex", getSex())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("birthday", getBirthday())
            .append("company", getCompany())
            .append("facDorNm", getFacDorNm())
            .append("facNm", getFacNm())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("tsNo",getTsNo())
            .toString();
    }

    /**
     * 同步中心库比较字符
     * @return
     */
    public String toSyncCentCompareString(){
        return new ToStringBuilder(this,ToStringStyle.SIMPLE_STYLE)
                .append("name", getNm())
                .append("idNo", getIdNo())
                .append("sex", getSex())
                .append("phone", getPhone())
                .append("address", getAddress())
                //.append("lcensePlate", getLcensePlate())
                .append("face",getFace())
                .append("birthday",getBirthday())
                .append("company", getCompany())
                .append("driverLicense",getDriverLicense())
                .append("escortLicense",getEscortLicense())
                .append("tsNo",getTsNo())
                .toString();
    }
}
