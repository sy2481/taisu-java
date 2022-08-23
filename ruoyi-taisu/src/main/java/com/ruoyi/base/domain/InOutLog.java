package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 进出记录对象 in_out_log
 *
 * @author ruoyi
 * @date 2022-03-07
 */
public class InOutLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 工程编号
     */
    @Excel(name = "工程編號")
    private String projectNo;
    /**
     * 工程名称
     */
    @Excel(name = "工程名稱")
    private String projectName;
    /**
     * 厂商名称
     */
    @Excel(name = "廠商名稱")
    private String factoryName;
    /**
     * 工单编号
     */
    @Excel(name = "工單號")
    private String workNo;

    /**
     * 出入设备ip
     */
    @Excel(name = "出入設備IP")
    private String ip;

    /**
     * 道路类型-人道、车道
     */
    @Excel(name = "道路类型",dictType = "load_type")
    private String loadType;
    /**
     * 厂区名称
     */
    @Excel(name = "廠區名稱")
    private String areaName;

    /**
     * 员工id，如果是员工出入记得插入
     */
    private Long userId;


    /**
     * 出入人员姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 出入人员身份证号
     */
    @Excel(name = "身份證號")
    private String idCard;
    @Excel(name = "定位卡編號")
    private String locationCardNo;
    @Excel(name = "日志類型",dictType = "log_type")
    private String logType;

    @Excel(name = "車牌號")
    private String plateNo;

    //0-員工，1-廠商
    @Excel(name = "人員類型",readConverterExp = "0=員工,1=廠商")
    private String personType;
    @Excel(name = "大門")
    private String gate;
    /**
     * 進出時間
     */
    @Excel(name = "進出時間",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date inOutTime;

    /**
     * 廠區ID
     */
    private Long deptId;
    private List<Long> factoryIdList;


    /**
     * 记录是否有效（0-有效，1-无效）
     */
    private Integer validType;
    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;
    /**
     * 操作人
     */
    private String operationName;

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public List<Long> getFactoryIdList() {
        return factoryIdList;
    }

    public void setFactoryIdList(List<Long> factoryIdList) {
        this.factoryIdList = factoryIdList;
    }


    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }




    /**
     * 厂商id，如果是厂商人员进入记得插入
     */
    private Long factoryId;







    /**
     * 日志类型，人员入/出厂，车辆出/入厂
     */
    // 第一位，出入，0-入场，1-离场
    // 第二位，0-員工、1-廠商、2-車輛


    private String userNo;

    private String userDeptName;

    private String endTime;

    public Date getInOutTime() {
        return inOutTime;
    }

    public void setInOutTime(Date inOutTime) {
        this.inOutTime = inOutTime;

    }
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    private String startTime;


    public String getGate(){
        return gate;
    }
    public void setGate(String gate){
        this.gate=gate;
    }
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getLocationCardNo() {
        return locationCardNo;
    }

    public void setLocationCardNo(String locationCardNo) {
        this.locationCardNo = locationCardNo;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogType() {
        return logType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectNo", getProjectNo())
                .append("workNo", getWorkNo())
                .append("ip", getIp())
                .append("areaName", getAreaName())
                .append("projectName", getProjectName())
                .append("factoryName", getFactoryName())
                .append("createTime", getCreateTime())
                .append("loadType", getLoadType())
                .append("name", getName())
                .append("idCard", getIdCard())
                .append("userId", getUserId())
                .append("factoryId", getFactoryId())
                .append("logType", getLogType())
                .toString();
    }
}
