package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
//    @Excel(name = "員工卡號", cellType = ColumnType.NUMERIC, prompt = "員工卡號")
    private Long userId;


    /**
     * 員工編號
     */
    @Excel(name = "員工編號")
    private String empNo;

    /**
     * 部门ID
     */
    //@Excel(name = "部門編號", type = Excel.Type.IMPORT)
    private Long deptId;


    @Excel(name = "部門名稱")
    private String deptName;

    /**
     * 用户昵称
     */
    @Excel(name = "員工姓名")
    private String nickName;

    /**
     * 用户性别
     */
    @Excel(name = "性別", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /**
     * 身份證
     */
    @Excel(name = "身份證號", cellType = ColumnType.STRING, prompt = "身份證號")
    private String idCard;

    /**
     * 手机号码
     */
    @Excel(name = "手機號碼")
    private String phonenumber;
    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址", cellType = ColumnType.STRING, prompt = "家庭住址")
    private String familyAddress;

    /**
     * 車卡
     */
    @Excel(name = "車卡")
    private String carCard;

    /**
     * 車牌
     */
    @Excel(name = "車牌")
    private String carId;


    /**
     * 用户账号
     */
    private String userName;


    /**
     * 用户邮箱
     */
//    @Excel(name = "員工郵箱")
    private String email;


    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
//    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
//    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;

    /**
     * 最后登录时间
     */
//    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;

    /**
     * 部门对象
     */
//    @Excels({
//        @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
//        @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
//    })
    private SysDept dept;

    /**
     * 角色对象
     */
    private List<SysRole> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;

    /**
     * 岗位组
     */
    private Long[] postIds;

    /**
     * 角色ID
     */
    private Long roleId;

    //是否已经下发到设备
    private Long sended;

    /************smallbeans -- 同步所需字段 *************/
    /**
     * 是否隐藏 0 代表显示 2 代表隐藏
     */
    private String displayStatus;

//    private Integer inOrOutFlag;
//
//    public Integer getInOrOutFlag() {
//        return inOrOutFlag;
//    }
//
//    public void setInOrOutFlag(Integer inOrOutFlag) {
//        this.inOrOutFlag = inOrOutFlag;
//    }
//    /** AE厂同步状态 0 代表同步 2 代表未同步*/
//    private String aeSync;

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

//    public String getAeSync() {
//        return aeSync;
//    }
//
//    public void setAeSync(String aeSync) {
//        this.aeSync = aeSync;
//    }

    /************根据原台塑原DB增加的字段*************/

    public Long getSended() {
        return sended;
    }

    public void setSended(Long sended) {
        this.sended = sended;
    }

    /**
     * 公司代號
     */
    private String companyId;

    /**
     * 生日
     */
//    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private String birthday;
    /**
     * 定位卡編號
     */
    //@Excel(name = "定位卡編號", cellType = ColumnType.STRING, prompt = "定位卡編號")
    private String positionCardNo;
    /**
     * 入職日期
     */
    private String joinDate;
    /**
     * 海康卡號
     */
    private String hikCard;
    /**
     * 職稱
     */
    private String title;
    /**
     * 管理員注記
     */
    private String mngMk;
    /**
     * 廠門代號
     */
    private String fctDorNm;
    /**
     * 廠區代號
     */
    private String factoryId;
    private String factoryName;

    private Long[] factoryIdArray;

    /**
     * PLC通道
     */
    private Long[] plcInfo;

    /**
     * 廠區信息
     */
    private SysDept factory;
    /**
     * 靜脈ID
     */
    private String fvid;
    /**
     * 海康personId
     */
    private String peisonId;
    /**
     * 年齡
     */
    private String age;
    /**
     * 人臉圖片
     */
    private String face;
    /**
     * 海康人臉id
     */
    private String faceId;


    /**
     * plc信息
     */
    private String plc;
    /**
     * sn號碼
     */
    private String snNum;

    private Long emisStandard;//排放標準 國5 國6
    private String emisStandardName;
    private String envSign; //環保標誌

    private String[] empNos;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }


    public String getPlc() {
        return plc;
    }

    public void setPlc(String plc) {
        this.plc = plc;
    }


    /*****************页面查询字段**********************/
    private String factoryStr;
    private String deptfStr;

    private Integer existenceFace;


    public Long[] getPlcInfo() {
        return plcInfo;
    }

    public void setPlcInfo(Long[] plcInfo) {
        this.plcInfo = plcInfo;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public Long[] getFactoryIdArray() {
        return factoryIdArray;
    }

    public void setFactoryIdArray(Long[] factoryIdArray) {
        this.factoryIdArray = factoryIdArray;
    }

    public Integer getExistenceFace() {
        return existenceFace;
    }

    public void setExistenceFace(Integer existenceFace) {
        this.existenceFace = existenceFace;
    }

    public String getCarCard() {
        return carCard;
    }

    public void setCarCard(String carCard) {
        this.carCard = carCard;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public SysDept getFactory() {
        return factory;
    }

    public String getFactoryStr() {
        return factoryStr;
    }

    public void setFactoryStr(String factoryStr) {
        this.factoryStr = factoryStr;
    }

    public String getDeptfStr() {
        return deptfStr;
    }

    public void setDeptfStr(String deptfStr) {
        this.deptfStr = deptfStr;
    }

    public void setFactory(SysDept factory) {
        this.factory = factory;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPositionCardNo() {
        return positionCardNo;
    }

    public void setPositionCardNo(String positionCardNo) {
        this.positionCardNo = positionCardNo;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getHikCard() {
        return hikCard;
    }

    public void setHikCard(String hikCard) {
        this.hikCard = hikCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMngMk() {
        return mngMk;
    }

    public void setMngMk(String mngMk) {
        this.mngMk = mngMk;
    }

    public String getFctDorNm() {
        return fctDorNm;
    }

    public void setFctDorNm(String fctDorNm) {
        this.fctDorNm = fctDorNm;
    }

    public String getFvid() {
        return fvid;
    }

    public void setFvid(String fvid) {
        this.fvid = fvid;
    }

    public String getPeisonId() {
        return peisonId;
    }

    public void setPeisonId(String peisonId) {
        this.peisonId = peisonId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
//    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

    public String[] getEmpNos() {
        return empNos;
    }

    public void setEmpNos(String[] empNos) {
        this.empNos = empNos;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("salt", getSalt())
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
                .append("title", getTitle())
                .append("mngMk", getMngMk())
                .append("fctDorNm", getFctDorNm())
                .append("fvid", getFvid())
                .append("peisonId", getPeisonId())
                .append("age", getAge())
                .append("face", getFace())
                .append("faceId", getFaceId())
                .append("empNo", getEmpNo())
                .append("factoryStr", getFactoryStr())
                .append("deptfStr", getDeptfStr())
                .append("carId", getCarId())
                .append("carCard", getCarCard())
                .toString();
    }

    /**
     * 同步中心库比较字符
     *
     * @return
     */
    public String toSyncCentCompareString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
                .append("empNo", getEmpNo())
                .append("idCard", getIdCard())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("sex", getSex())
                .append("phonenumber", getPhonenumber())
                .append("familyAddress", getFamilyAddress())
                //.append("carId",getCarId())
                .append("face", getFace())
                .toString();
    }
}
