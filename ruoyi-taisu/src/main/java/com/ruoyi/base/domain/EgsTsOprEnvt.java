package com.ruoyi.base.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class EgsTsOprEnvt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("編號")
    private Long oprId;

    /**
     * 作業類別代碼
     */
    @ApiModelProperty("作業類別代碼")
    @Excel(name = "作業類別代碼")
    private String oprCd;

    /**
     * 作業類別名稱
     */
    @ApiModelProperty("作業類別名稱")
    @Excel(name = "作業類別名稱")
    private String oprNm;

    /**
     * 作業類別顯示名稱
     */
    @ApiModelProperty("作業類別顯示名稱")
    @Excel(name = "作業類別顯示名稱")
    private String oprDisplay;

    /**
     * 作業類別顏色
     */
    @ApiModelProperty("作業類別顏色")
    @Excel(name = "作業類別顏色")
    private String oprColor;

    /** 作業證照 */
    @Excel(name = "作業證照")
    private String oprCerts;

    /**
     * 所属证照
     */
    @ApiModelProperty("所属证照Ids")
    private List<String> certIds;

    public void setOprId(Long oprId) {
        this.oprId = oprId;
    }

    public Long getOprId() {
        return oprId;
    }

    public void setOprCd(String oprCd) {
        this.oprCd = oprCd;
    }

    public String getOprCd() {
        return oprCd;
    }

    public void setOprNm(String oprNm) {
        this.oprNm = oprNm;
    }

    public String getOprNm() {
        return oprNm;
    }

    public void setOprDisplay(String oprDisplay) {
        this.oprDisplay = oprDisplay;
    }

    public String getOprDisplay() {
        return oprDisplay;
    }

    public void setOprColor(String oprColor) {
        this.oprColor = oprColor;
    }

    public String getOprColor() {
        return oprColor;
    }

    public String getOprCerts() {
        return oprCerts;
    }

    public void setOprCerts(String oprCerts) {
        this.oprCerts = oprCerts;
    }

    public List<String> getCertIds() {
        return certIds;
    }

    public void setCertIds(List<String> certIds) {
        this.certIds = certIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("oprId", getOprId())
                .append("oprCd", getOprCd())
                .append("oprNm", getOprNm())
                .append("oprDisplay", getOprDisplay())
                .append("oprColor", getOprColor())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
