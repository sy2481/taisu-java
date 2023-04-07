package com.ruoyi.base.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 進出記錄对象 base_inout_log
 *
 * @author ruoyi
 * @date 2022-08-31
 */

public class BaseInoutLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    /**
     * 本單編號
     */
    @ApiModelProperty("本單編號")
    @Excel(name = "本單編號")
    private String vhNo;

    /**
     * 工單類型(1=廠商工單.2=危化工單)
     */
    @ApiModelProperty("工單類型(1=廠商工單.2=危化工單)")
    @Excel(name = "工單類型(1=廠商工單.2=危化工單)")
    private Long vhType;

    /**
     * 工單類型名稱
     */
    @ApiModelProperty("工單類型名稱")
    @Excel(name = "工單類型名稱")
    private String vhTypeName;

    /**
     * 施工區域
     */
    @ApiModelProperty("施工區域")
    @Excel(name = "施工區域")
    private String cnstrArea;
    /**
     * 施工區域IDS
     */
    @ApiModelProperty("施工區域IDS")
    @Excel(name = "施工區域IDS")
    private String cnstrAreaIds;

    /**
     * 門id
     */
    @ApiModelProperty("門id")
    @Excel(name = "門id")
    private Long doorId;

    /**
     * 身份證/車牌
     */
    @ApiModelProperty("身份證/車牌")
    @Excel(name = "身份證/車牌")
    private String idNo;

    /**
     * 姓名/車備註
     */
    @ApiModelProperty("姓名/車備註")
    @Excel(name = "姓名/車備註")
    private String name;

    /**
     * IC卡號
     */
    @ApiModelProperty("IC卡號")
    @Excel(name = "IC卡號")
    private String icNo;

    /**
     * 司機身份證
     */
    @ApiModelProperty("司機身份證")
    @Excel(name = "司機身份證")
    private String carUserIdNo;

    /**
     * 司機姓名
     */
    @ApiModelProperty("司機姓名")
    @Excel(name = "司機姓名")
    private String carUserName;

    /**
     * 門禁密碼
     */
    @ApiModelProperty("門禁密碼")
    @Excel(name = "門禁密碼")
    private String doorPwd;

    /**
     * 開始有效期
     */
    @ApiModelProperty("開始有效期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "開始有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginTime;

    /**
     * 結束有效期
     */
    @ApiModelProperty("結束有效期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "結束有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 廠門代號
     */
    @ApiModelProperty("廠門代號")
    @Excel(name = "廠門代號")
    private String facDorNm;

    /**
     * 門禁管理員卡號
     */
    @ApiModelProperty("門禁管理員卡號")
    @Excel(name = "門禁管理員卡號")
    private String mngIcNo;

    /**
     * 門禁管理員刷卡時間
     */
    @ApiModelProperty("門禁管理員刷卡時間")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "門禁管理員刷卡時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date mngTime;

    /**
     * 狀態 （Y 有效 N 失效）
     */
    @ApiModelProperty("狀態 ")
    @Excel(name = "狀態 ", readConverterExp = "Y=,有=效,N=,失=效")
    private String status;

    /**
     * 卡類型（1 員工卡 2 合約卡 3 臨時卡 4 來賓卡 5
     */
    @ApiModelProperty("卡類型")
    @Excel(name = "卡類型", readConverterExp = "卡類型（1 員工卡 2 合約卡 3 臨時卡 4 來賓卡 5 ")
    private Long cardType;

    /**
     * 卡類型名稱
     */
    @ApiModelProperty("卡類型名稱")
    @Excel(name = "卡類型名稱")
    private String cardTypeName;

    /**
     * 卡類別（人/車）
     */
    @ApiModelProperty("卡類別")
    @Excel(name = "卡類別", readConverterExp = "人=/車")
    private Long cardKind;

    /**
     * 卡類別名稱
     */
    @ApiModelProperty("卡類別名稱")
    @Excel(name = "卡類別名稱")
    private String cardKindName;

    /**
     * 車輛種類（皮卡車、叉車、公務車、吊車、消防車、其他）
     */
    @ApiModelProperty("車輛種類")
    @Excel(name = "車輛種類", readConverterExp = "皮=卡車、叉車、公務車、吊車、消防車、其他")
    private Long carKind;

    /**
     * 車輛種類名稱
     */
    @ApiModelProperty("車輛種類名稱")
    @Excel(name = "車輛種類名稱")
    private String carKindName;

    /**
     * 車輛類型（1 大 2小）
     */
    @ApiModelProperty("車輛類型")
    @Excel(name = "車輛類型", readConverterExp = "1=,大=,2=小")
    private Long carType;

    /**
     * 車輛類型名稱
     */
    @ApiModelProperty("車輛類型名稱")
    @Excel(name = "車輛類型名稱")
    private String carTypeName;

    /**
     * 定位卡用戶類型（內部員工、廠商人員、外來訪客）
     */
    @ApiModelProperty("定位卡用戶類型")
    @Excel(name = "定位卡用戶類型", readConverterExp = "內=部員工、廠商人員、外來訪客")
    private Long locateUserType;

    /**
     * 定位卡用戶類型名稱
     */
    @ApiModelProperty("定位卡用戶類型名稱")
    @Excel(name = "定位卡用戶類型名稱")
    private String locateUserTypeName;

//    /** 車備註 */
//    @ApiModelProperty("車備註")
//    @Excel(name = "車備註")
//    private String carRemark;

    /**
     * 通道id
     */
    @ApiModelProperty("通道id")
    @Excel(name = "通道id")
    private Long passagewayId;

    /**
     * 通道名稱
     */
    @ApiModelProperty("通道名稱")
    @Excel(name = "通道名稱")
    private String passagewayName;

    /**
     * 通道類型（車道/人道）
     */
    @ApiModelProperty("通道類型")
    @Excel(name = "通道類型", readConverterExp = "車=道/人道")
    private String passagewayType;

    /**
     * 認證方式
     */
    @ApiModelProperty("認證方式")
    @Excel(name = "認證方式")
    private String identityWay;

    /**
     * 方向（入/出）
     */
    @ApiModelProperty("方向")
    @Excel(name = "方向", readConverterExp = "入=/出")
    private String inOutMode;

    /**
     * 開門時間
     */
    @ApiModelProperty("開門時間")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "開門時間", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date passagewayOpenTime;

    /**
     * 是否使用了車牌匹配機制
     */
    @ApiModelProperty("是否使用了車牌匹配機制")
    @Excel(name = "是否使用了車牌匹配機制")
    private String useMatchingCarpalte;

    /**
     * 歷史身份證/車牌號
     */
    @ApiModelProperty("歷史身份證/車牌號")
    @Excel(name = "歷史身份證/車牌號")
    private String historyIdNo;

    /**
     * 模式（PLC控制/設備控制）
     */
    @ApiModelProperty("模式")
    @Excel(name = "模式", readConverterExp = "P=LC控制/設備控制")
    private String passagewayMode;

    /**
     * 職稱名稱
     */
    @ApiModelProperty("職稱名稱")
    @Excel(name = "職稱名稱")
    private String positionName;

    /**
     * 工程編號
     */
    @ApiModelProperty("工程編號")
    @Excel(name = "工程編號")
    private String egNo;

    /**
     * 工程名稱
     */
    @ApiModelProperty("工程名稱")
    @Excel(name = "工程名稱")
    private String egNm;

    /**
     * 廠商編號
     */
    @ApiModelProperty("廠商編號")
    @Excel(name = "廠商編號")
    private String vndNo;

    /**
     * 廠商名稱
     */
    @ApiModelProperty("廠商名稱")
    @Excel(name = "廠商名稱")
    private String vndAbr;

    /**
     * 作業項目
     */
    @ApiModelProperty("作業項目")
    @Excel(name = "作業項目")
    private String oprEnvt21;

    /**
     * 監工人員及電話
     */
    @ApiModelProperty("監工人員及電話")
    @Excel(name = "監工人員及電話")
    private String taskMaster22;

    /**
     * 廠區名稱
     */
    @ApiModelProperty("廠區名稱")
    private String facNm;

    @ApiModelProperty("門名稱")
    private String doorName;

    /**
     * 作業項目名稱
     */
    @ApiModelProperty("作業項目名稱")
    @Excel(name = "作業項目名稱")
    private String oprEnvtNm;

    /**
     * 台塑碼
     */
    @ApiModelProperty("台塑碼")
    @Excel(name = "台塑碼")
    private String tsNo;

    /**
     * 異常狀態 正常/未入廠/未出廠
     */
    @ApiModelProperty("正常/未入廠/未出廠")
    @Excel(name = "正常/未入廠/未出廠")
    private String abnormalState;

    /**
     * 補錄狀態 正常/已補錄/未補錄
     */
    @ApiModelProperty("正常/已補錄/未補錄")
    @Excel(name = "正常/已補錄/未補錄")
    private String supplementaryStatus;

    /**
     * 數量
     */
    private Integer cnt;
    /**
     * 身份標識1.員工 2.廠商人員 3.廠商轉員工
     */
    @ApiModelProperty("身份標識1.員工 2.廠商人員 3.廠商轉員工")
    @Excel(name = "身份標識1.員工 2.廠商人員 3.廠商轉員工")
    private Integer identification;

    /** 補錄管理員用戶編號 */
    @ApiModelProperty("補錄管理員用戶編號")
    @Excel(name = "補錄管理員用戶編號")
    private String supplyManagerNo;

    /** 補錄管理員姓名 */
    @ApiModelProperty("補錄管理員姓名")
    @Excel(name = "補錄管理員姓名")
    private String supplyManagerNm;

    /** 創建時間 */
    @ApiModelProperty("創建時間")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "創建時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date supplyTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setVhNo(String vhNo) {
        this.vhNo = vhNo;
    }

    public String getVhNo() {
        return vhNo;
    }

    public void setCnstrArea(String cnstrArea) {
        this.cnstrArea = cnstrArea;
    }

    public String getCnstrArea() {
        return cnstrArea;
    }

    public void setDoorId(Long doorId) {
        this.doorId = doorId;
    }

    public Long getDoorId() {
        return doorId;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setCarUserIdNo(String carUserIdNo) {
        this.carUserIdNo = carUserIdNo;
    }

    public String getCarUserIdNo() {
        return carUserIdNo;
    }

    public void setCarUserName(String carUserName) {
        this.carUserName = carUserName;
    }

    public String getCarUserName() {
        return carUserName;
    }

    public void setDoorPwd(String doorPwd) {
        this.doorPwd = doorPwd;
    }

    public String getDoorPwd() {
        return doorPwd;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setFacDorNm(String facDorNm) {
        this.facDorNm = facDorNm;
    }

    public String getFacDorNm() {
        return facDorNm;
    }

    public void setMngIcNo(String mngIcNo) {
        this.mngIcNo = mngIcNo;
    }

    public String getMngIcNo() {
        return mngIcNo;
    }

    public void setMngTime(Date mngTime) {
        this.mngTime = mngTime;
    }

    public Date getMngTime() {
        return mngTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardKind(Long cardKind) {
        this.cardKind = cardKind;
    }

    public Long getCardKind() {
        return cardKind;
    }

    public void setCardKindName(String cardKindName) {
        this.cardKindName = cardKindName;
    }

    public String getCardKindName() {
        return cardKindName;
    }

    public void setCarKind(Long carKind) {
        this.carKind = carKind;
    }

    public Long getCarKind() {
        return carKind;
    }

    public void setCarKindName(String carKindName) {
        this.carKindName = carKindName;
    }

    public String getCarKindName() {
        return carKindName;
    }

    public void setCarType(Long carType) {
        this.carType = carType;
    }

    public Long getCarType() {
        return carType;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setLocateUserType(Long locateUserType) {
        this.locateUserType = locateUserType;
    }

    public Long getLocateUserType() {
        return locateUserType;
    }

    public void setLocateUserTypeName(String locateUserTypeName) {
        this.locateUserTypeName = locateUserTypeName;
    }

    public String getLocateUserTypeName() {
        return locateUserTypeName;
    }

    //    public void setCarRemark(String carRemark)
//    {
//        this.carRemark = carRemark;
//    }
//
//    public String getCarRemark()
//    {
//        return carRemark;
//    }
    public void setPassagewayId(Long passagewayId) {
        this.passagewayId = passagewayId;
    }

    public Long getPassagewayId() {
        return passagewayId;
    }

    public void setPassagewayName(String passagewayName) {
        this.passagewayName = passagewayName;
    }

    public String getPassagewayName() {
        return passagewayName;
    }

    public void setPassagewayType(String passagewayType) {
        this.passagewayType = passagewayType;
    }

    public String getPassagewayType() {
        return passagewayType;
    }

    public void setIdentityWay(String identityWay) {
        this.identityWay = identityWay;
    }

    public String getIdentityWay() {
        return identityWay;
    }

    public void setInOutMode(String inOutMode) {
        this.inOutMode = inOutMode;
    }

    public String getInOutMode() {
        return inOutMode;
    }

    public void setPassagewayOpenTime(Date passagewayOpenTime) {
        this.passagewayOpenTime = passagewayOpenTime;
    }

    public Date getPassagewayOpenTime() {
        return passagewayOpenTime;
    }

    public void setUseMatchingCarpalte(String useMatchingCarpalte) {
        this.useMatchingCarpalte = useMatchingCarpalte;
    }

    public String getUseMatchingCarpalte() {
        return useMatchingCarpalte;
    }

    public void setHistoryIdNo(String historyIdNo) {
        this.historyIdNo = historyIdNo;
    }

    public String getHistoryIdNo() {
        return historyIdNo;
    }




    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vhNo", getVhNo())
                .append("cnstrArea", getCnstrArea())
                .append("doorId", getDoorId())
                .append("idNo", getIdNo())
                .append("name", getName())
                .append("icNo", getIcNo())
                .append("carUserIdNo", getCarUserIdNo())
                .append("carUserName", getCarUserName())
                .append("doorPwd", getDoorPwd())
                .append("beginTime", getBeginTime())
                .append("endTime", getEndTime())
                .append("facDorNm", getFacDorNm())
                .append("mngIcNo", getMngIcNo())
                .append("mngTime", getMngTime())
                .append("status", getStatus())
                .append("cardType", getCardType())
                .append("cardTypeName", getCardTypeName())
                .append("cardKind", getCardKind())
                .append("cardKindName", getCardKindName())
                .append("carKind", getCarKind())
                .append("carKindName", getCarKindName())
                .append("carType", getCarType())
                .append("carTypeName", getCarTypeName())
                .append("locateUserType", getLocateUserType())
                .append("locateUserTypeName", getLocateUserTypeName())
//                .append("carRemark", getCarRemark())
                .append("passagewayId", getPassagewayId())
                .append("passagewayName", getPassagewayName())
                .append("passagewayType", getPassagewayType())
                .append("identityWay", getIdentityWay())
                .append("inOutMode", getInOutMode())
                .append("passagewayOpenTime", getPassagewayOpenTime())
                .append("useMatchingCarpalte", getUseMatchingCarpalte())
                .append("historyIdNo", getHistoryIdNo())
                .append("egNo", getEgNo())
                .append("egNm", getEgNm())
                .append("vndNo", getVndNo())
                .append("vndAbr", getVndAbr())
                .append("oprEnvt21", getOprEnvt21())
                .toString();
    }

    public String getPassagewayMode() {
        return passagewayMode;
    }

    public void setPassagewayMode(String passagewayMode) {
        this.passagewayMode = passagewayMode;
    }

    public String getCnstrAreaIds() {
        return cnstrAreaIds;
    }

    public void setCnstrAreaIds(String cnstrAreaIds) {
        this.cnstrAreaIds = cnstrAreaIds;
    }

    public Long getVhType() {
        return vhType;
    }

    public void setVhType(Long vhType) {
        this.vhType = vhType;
    }

    public String getVhTypeName() {
        return vhTypeName;
    }

    public void setVhTypeName(String vhTypeName) {
        this.vhTypeName = vhTypeName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setEgNo(String egNo) {
        this.egNo = egNo;
    }

    public String getEgNo() {
        return egNo;
    }

    public void setEgNm(String egNm) {
        this.egNm = egNm;
    }

    public String getEgNm() {
        return egNm;
    }

    public void setVndNo(String vndNo) {
        this.vndNo = vndNo;
    }

    public String getVndNo() {
        return vndNo;
    }

    public void setVndAbr(String vndAbr) {
        this.vndAbr = vndAbr;
    }

    public String getVndAbr() {
        return vndAbr;
    }

    public void setOprEnvt21(String oprEnvt21) {
        this.oprEnvt21 = oprEnvt21;
    }

    public String getOprEnvt21() {
        return oprEnvt21;
    }

    public String getTaskMaster22() {
        return taskMaster22;
    }

    public void setTaskMaster22(String taskMaster22) {
        this.taskMaster22 = taskMaster22;
    }

    public String getFacNm() {
        return facNm;
    }

    public void setFacNm(String facNm) {
        this.facNm = facNm;
    }

    public String getDoorName() {
        return doorName;
    }

    public void setDoorName(String doorName) {
        this.doorName = doorName;
    }

    public String getOprEnvtNm() {
        return oprEnvtNm;
    }

    public void setOprEnvtNm(String oprEnvtNm) {
        this.oprEnvtNm = oprEnvtNm;
    }

    public String getTsNo() {
        return tsNo;
    }

    public void setTsNo(String tsNo) {
        this.tsNo = tsNo;
    }

    public String getAbnormalState() {
        return abnormalState;
    }

    public void setAbnormalState(String abnormalState) {
        this.abnormalState = abnormalState;
    }

    public String getSupplementaryStatus() {
        return supplementaryStatus;
    }

    public void setSupplementaryStatus(String supplementaryStatus) {
        this.supplementaryStatus = supplementaryStatus;
    }

    public Integer getIdentification() {
        return identification;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public void setIdentification(Integer identification) {
        this.identification = identification;
    }

    public String getSupplyManagerNo() {
        return supplyManagerNo;
    }

    public void setSupplyManagerNo(String supplyManagerNo) {
        this.supplyManagerNo = supplyManagerNo;
    }

    public String getSupplyManagerNm() {
        return supplyManagerNm;
    }

    public void setSupplyManagerNm(String supplyManagerNm) {
        this.supplyManagerNm = supplyManagerNm;
    }

    public Date getSupplyTime() {
        return supplyTime;
    }

    public void setSupplyTime(Date supplyTime) {
        this.supplyTime = supplyTime;
    }
}
