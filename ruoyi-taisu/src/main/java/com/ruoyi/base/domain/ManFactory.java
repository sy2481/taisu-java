package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 厂商对象 man_factory
 *
 * @author ruoyi
 * @date 2022-03-06
 */
public class ManFactory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 廠商人員ID
     */
    private Long factoryId;
    /**
     * 廠商名稱
     */
    @Excel(name = "廠商名稱")
    private String factoryName;
    /**
     * 當前工程編號
     */
    @Excel(name = "工程編號", prompt = "當前工程編號")
    private String thisNumber;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;
    /**
     * 性別1男2女
     */
    @Excel(name = "性別", readConverterExp = "1=男,2=女")
    private Long sex;
    /**
     * 身份証
     */
    @Excel(name = "身份証")
    private String idCard;
    /**
     * 出生年月
     */
    @Excel(name = "出生年月")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDay;
    /**
     * 手機號
     */
    @Excel(name = "手機號")
    private String phone;
    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String address;
    /**
     * 卡类型（0合约卡/1临时卡）
     */
    @Excel(name = "合約卡/臨時卡", readConverterExp = "0=合約卡,1=臨時卡")
    private Long cardType;
    /**
     * 車牌
     */
    @Excel(name = "車牌")
    private String lcensePlate;
    /**
     * 車卡
     */
    @Excel(name = "車卡")
    private String carCardNo;

    /**
     * 當前工程名稱
     */
    @Excel(name = "工程名稱", prompt = "當前工程名稱")
    private String egNm;

    /**
     * 入場證（定位卡編號）
     */
    private String ipLtLic;

    /**
     * 門禁管理員姓名
     */
    private String mngName;

    /**
     * 門禁管理員上傳時間
     */
    private String mngTime;

    /**
     * 類型：3人員，2司機
     */
    private Long type;
    /**
     * 人臉戰片地址
     */
    private String face;
    /**
     * 定位卡
     */
//    @Excel(name = "定位卡")
    private String locationCard;




    /**
     * Sn號碼
     */
    private String snNum;



    private String workNo;
    /**
     * 工单有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workTime;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 原工单号,用于保存的时候，更改关联工单
     */
    private String oldWorkNo;

    /**
     * 是否为负责人,负责人存的是XT
     */
    private String lead;

    /**
     * 人员是否已经进入，0-未进入，1-已进入
     */
    private int entered;

    /**
     * 人员类型，0-普通厂商人员，1-危化品人员
     */
    @Excel(name = "人员类型，0=厂商,1=危化品")
    private Integer dangerType;

    //sended 9下发失败 1 下发成功 0
    private Integer sended;

    private Date picInsertTime;


    public Date getPicInsertTime() {
        return picInsertTime;
    }

    public void setPicInsertTime(Date picInsertTime) {
        this.picInsertTime = picInsertTime;
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }
    public List<Long> getFactoryIdList() {
        return factoryIdList;
    }

    public void setFactoryIdList(List<Long> factoryIdList) {
        this.factoryIdList = factoryIdList;
    }

    private List<Long> factoryIdList;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    private Long deptId;


    private Integer today;
    private Integer existenceFace;
    /**
     * 有无车牌
     */
    private  Integer haveCarNum;
    public Integer getHaveCarNum() {
        return haveCarNum;
    }

    public void setHaveCarNum(Integer haveCarNum) {
        this.haveCarNum = haveCarNum;
    }

    private String carIdCard;
    private Long emisStandard;
    private String emisStandardName;
    private String envSign;

    /**
     * 車牌，用於模糊查詢
     */
    private String plateNo;




    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getExistenceFace() {
        return existenceFace;
    }

    public void setExistenceFace(Integer existenceFace) {
        this.existenceFace = existenceFace;
    }

    public Integer getDangerType() {
        return dangerType;
    }

    public void setDangerType(Integer dangerType) {
        this.dangerType = dangerType;
    }

    public Integer getSended() {
        return sended;
    }

    public void setSended(Integer sended) {
        this.sended = sended;
    }

    public int getEntered() {
        return entered;
    }

    public void setEntered(int entered) {
        this.entered = entered;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getOldWorkNo() {
        return oldWorkNo;
    }

    public void setOldWorkNo(String oldWorkNo) {
        this.oldWorkNo = oldWorkNo;
    }


    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setLcensePlate(String lcensePlate) {
        this.lcensePlate = lcensePlate;
    }

    public String getLcensePlate() {
        return lcensePlate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIpLtLic(String ipLtLic) {
        this.ipLtLic = ipLtLic;
    }

    public String getIpLtLic() {
        return ipLtLic;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setMngName(String mngName) {
        this.mngName = mngName;
    }

    public String getMngName() {
        return mngName;
    }

    public void setMngTime(String mngTime) {
        this.mngTime = mngTime;
    }

    public String getMngTime() {
        return mngTime;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getType() {
        return type;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getSex() {
        return sex;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setThisNumber(String thisNumber) {
        this.thisNumber = thisNumber;
    }

    public String getThisNumber() {
        return thisNumber;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setLocationCard(String locationCard) {
        this.locationCard = locationCard;
    }

    public String getLocationCard() {
        return locationCard;
    }

    public String getCarCardNo() {
        return carCardNo;
    }

    public void setCarCardNo(String carCardNo) {
        this.carCardNo = carCardNo;
    }

    public String getEgNm() {
        return egNm;
    }

    public void setEgNm(String egNm) {
        this.egNm = egNm;
    }

    public String getCarIdCard() {
        return carIdCard;
    }

    public void setCarIdCard(String carIdCard) {
        this.carIdCard = carIdCard;
    }

    public Long getEmisStandard() {
        return emisStandard;
    }

    public void setEmisStandard(Long emisStandard) {
        this.emisStandard = emisStandard;
    }

    public String getEmisStandardName() {
        return emisStandardName;
    }

    public void setEmisStandardName(String emisStandardName) {
        this.emisStandardName = emisStandardName;
    }

    public String getEnvSign() {
        return envSign;
    }

    public void setEnvSign(String envSign) {
        this.envSign = envSign;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("factoryId", getFactoryId())
                .append("idCard", getIdCard())
                .append("lcensePlate", getLcensePlate())
                .append("name", getName())
                .append("ipLtLic", getIpLtLic())
                .append("factoryName", getFactoryName())
                .append("mngName", getMngName())
                .append("mngTime", getMngTime())
                .append("type", getType())
                .append("sex", getSex())
                .append("face", getFace())
                .append("phone", getPhone())
                .append("address", getAddress())
                .append("thisNumber", getThisNumber())
                .append("birthDay", getBirthDay())
                .append("cardType", getCardType())
                .append("locationCard", getLocationCard())
                .append("workNo", getWorkNo())
                .append("lead", getLead())
                .append("egNm",getEgNm())
                .toString();
    }

    /**
     * 同步中心库比较字符
     * @return
     */
    public String toSyncCentCompareString(){
        return new ToStringBuilder(this,ToStringStyle.SIMPLE_STYLE)
                .append("name", getName())
                .append("sex", getSex())
                .append("phone", getPhone())
                .append("address", getAddress())
                .append("lcensePlate", getLcensePlate())
                .append("face",getFace())
                .toString();
    }

}
