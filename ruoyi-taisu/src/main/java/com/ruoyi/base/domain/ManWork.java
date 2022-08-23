package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 工单对象 man_work
 *
 * @author ruoyi
 * @date 2022-03-06
 */
public class ManWork extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 工單號
     */
    private Long workId;

    /**
     * 工程編號
     */
    @Excel(name = "工程編號")
    private String projectNo;
    @Excel(name = "IP")
    private String ip;

    private String factoryDoorName;
    /**
     * 發張廠區編號
     */
    @Excel(name = "發張廠區編號")
    private String workNo;

    /**
     * 門禁管理員姓名
     */
    @Excel(name = "門禁管理員姓名")
    private String mngName;

    /**
     * 門將管理員上傳時間
     */
    @Excel(name = "門將管理員上傳時間")
    private String mngTime;

    /**
     * 工单有效期
     */
    @Excel(name = "工单有效期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String workTime;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 車牌
     */
    private String carId;

    /**
     * 項目名稱
     */
    private String projectName;

    /**
     * 作業類別
     */
    private String jobCategory;

    /**
     * 安全督導員/覆核人員=Extender
     */
    private String inspector;

    /**
     * 洽公同意人員… only for IEM
     */
    private String inspector2;
    /***    車卡-*****/
    private String carCard;

    /***   XT人员已进人员数量   **/
    private Integer xtInNum;
    /***   普通厂商人员已数量   **/
    private Integer comInNum;

    //工单类型，0-普通厂商工单，1-危化品工单
    private Integer workType;

    //工单类型，0-正常，9-异常
    private Integer workStatus;

    private Long deptId;

    private Date startTime;
    private Date endTime;
    private Date extendStartTime;
    private Date extendEndTime;


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getExtendStartTime() {
        return extendStartTime;
    }

    public void setExtendStartTime(Date extendStartTime) {
        this.extendStartTime = extendStartTime;
    }

    public Date getExtendEndTime() {
        return extendEndTime;
    }

    public void setExtendEndTime(Date extendEndTime) {
        this.extendEndTime = extendEndTime;
    }



    public List<Long> getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(List<Long> factoryId) {
        this.factoryId = factoryId;
    }

    private List<Long> factoryId;




    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /** 部门名称 */
    private String deptName;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getXtInNum() {
        return xtInNum;
    }

    public void setXtInNum(Integer xtInNum) {
        this.xtInNum = xtInNum;
    }

    public Integer getComInNum() {
        return comInNum;
    }

    public void setComInNum(Integer comInNum) {
        this.comInNum = comInNum;
    }

    public String getCarCard() {
        return carCard;
    }

    public void setCarCard(String carCard) {
        this.carCard = carCard;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(String jobCategory) {
        this.jobCategory = jobCategory;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspector2() {
        return inspector2;
    }

    public void setInspector2(String inspector2) {
        this.inspector2 = inspector2;
    }

    // 虛擬字段
    private boolean historyQuery;

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public Long getWorkId() {
        return workId;
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

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public boolean isHistoryQuery() {
        return historyQuery;
    }

    public void setHistoryQuery(boolean historyQuery) {
        this.historyQuery = historyQuery;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getFactoryDoorName() {
        return factoryDoorName;
    }

    public void setFactoryDoorName(String factoryDoorName) {
        this.factoryDoorName = factoryDoorName;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workId", getWorkId())
                .append("projectNo", getProjectNo())
                .append("workNo", getWorkNo())
                .append("mngName", getMngName())
                .append("mngTime", getMngTime())
                .append("workTime", getWorkTime())
                .append("carId", getCarId())
                .append("projectName", getProjectName())
                .append("jobCategory", getJobCategory())
                .append("inspector", getInspector())
                .append("inspector2", getInspector2())
                .append("carCard", getCarCard())
                .append("xtInNum", getXtInNum())
                .append("comInNum", getComInNum())
                .toString();
    }
}
