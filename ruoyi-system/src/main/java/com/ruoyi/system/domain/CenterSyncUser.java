package com.ruoyi.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户信息对象 sys_user
 *
 * @author ruoyi
 * @date 2022-10-11
 */
public class CenterSyncUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 員工ID
     */
    private Long userId;

    /**
     * 部門ID
     */
    @Excel(name = "部門ID")
    private Long deptId;

    /**
     * 員工賬號
     */
    @Excel(name = "員工賬號")
    private String userName;

    /**
     * 員工姓名
     */
    @Excel(name = "員工姓名")
    private String nickName;

    /**
     * 用戶類型（00管理員）
     */
    @Excel(name = "用戶類型", readConverterExp = "0=0管理員")
    private String userType;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phonenumber;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 头像地址
     */
    @Excel(name = "头像地址")
    private String avatar;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

    /**
     * 公司代號
     */
    @Excel(name = "公司代號")
    private String companyId;

    /**
     * 身份證
     */
    @Excel(name = "身份證")
    private String idCard;

    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String familyAddress;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 定位卡編號
     */
    @Excel(name = "定位卡編號")
    private String positionCardNo;

    /**
     * 入職日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入職日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date joinDate;

    /**
     * 海康卡號
     */
    @Excel(name = "海康卡號")
    private String hikCard;

    /**
     * 廠區代號
     */
    @Excel(name = "廠區代號")
    private String factoryId;

    /**
     * 職稱
     */
    @Excel(name = "職稱")
    private String title;

    /**
     * 管理員注記
     */
    @Excel(name = "管理員注記")
    private String mngMk;

    /**
     * 廠門代號
     */
    @Excel(name = "廠門代號")
    private String fctDorNm;

    /**
     * 靜脈ID
     */
    @Excel(name = "靜脈ID")
    private String fvid;

    /**
     * 海康personId
     */
    @Excel(name = "海康personId")
    private String peisonId;

    /**
     * 年齡
     */
    @Excel(name = "年齡")
    private Long age;

    /**
     * 人臉圖片
     */
    @Excel(name = "人臉圖片")
    private String face;

    /**
     * 海康人臉id
     */
    @Excel(name = "海康人臉id")
    private String faceId;

    /**
     * 員工編號
     */
    @Excel(name = "員工編號")
    private String empNo;

    /**
     * 車牌
     */
    @Excel(name = "車牌")
    private String carId;

    /**
     * 車卡
     */
    @Excel(name = "車卡")
    private String carCard;

    /**
     * 下发状态
     */
    @Excel(name = "下发状态")
    private Long sended;

    /**
     * plc通道
     */
    @Excel(name = "plc通道")
    private String plc;

    /**
     * sn号码
     */
    @Excel(name = "sn号码")
    private String snNum;

    /**
     * 员工显示状态
     */
    @Excel(name = "sn号码")
    private String displayStatus;

    /**
     * AE员工同步状态(0 同步过 2 未同步)
     */
    @Excel(name = "AE员工同步状态(0 同步过 2 未同步)")
    private String aeSync;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }
    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setPositionCardNo(String positionCardNo) {
        this.positionCardNo = positionCardNo;
    }

    public String getPositionCardNo() {
        return positionCardNo;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setHikCard(String hikCard) {
        this.hikCard = hikCard;
    }

    public String getHikCard() {
        return hikCard;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setMngMk(String mngMk) {
        this.mngMk = mngMk;
    }

    public String getMngMk() {
        return mngMk;
    }

    public void setFctDorNm(String fctDorNm) {
        this.fctDorNm = fctDorNm;
    }

    public String getFctDorNm() {
        return fctDorNm;
    }

    public void setFvid(String fvid) {
        this.fvid = fvid;
    }

    public String getFvid() {
        return fvid;
    }

    public void setPeisonId(String peisonId) {
        this.peisonId = peisonId;
    }

    public String getPeisonId() {
        return peisonId;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getAge() {
        return age;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarCard(String carCard) {
        this.carCard = carCard;
    }

    public String getCarCard() {
        return carCard;
    }

    public void setSended(Long sended) {
        this.sended = sended;
    }

    public Long getSended() {
        return sended;
    }

    public void setPlc(String plc) {
        this.plc = plc;
    }

    public String getPlc() {
        return plc;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }

    public String getSnNum() {
        return snNum;
    }

    public void setAeSync(String aeSync) {
        this.aeSync = aeSync;
    }

    public String getAeSync() {
        return aeSync;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("userType", getUserType())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("companyId", getCompanyId())
                .append("idCard", getIdCard())
                .append("familyAddress", getFamilyAddress())
                .append("birthday", getBirthday())
                .append("positionCardNo", getPositionCardNo())
                .append("joinDate", getJoinDate())
                .append("hikCard", getHikCard())
                .append("factoryId", getFactoryId())
                .append("title", getTitle())
                .append("mngMk", getMngMk())
                .append("fctDorNm", getFctDorNm())
                .append("fvid", getFvid())
                .append("peisonId", getPeisonId())
                .append("age", getAge())
                .append("face", getFace())
                .append("faceId", getFaceId())
                .append("empNo", getEmpNo())
                .append("carId", getCarId())
                .append("carCard", getCarCard())
                .append("sended", getSended())
                .append("plc", getPlc())
                .append("snNum", getSnNum())
                .append("displayStatus", getDisplayStatus())
                .append("aeSync", getAeSync())
                .toString();
    }
}
