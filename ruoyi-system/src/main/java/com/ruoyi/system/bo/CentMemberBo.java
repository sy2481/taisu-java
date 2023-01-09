package com.ruoyi.system.bo;

import ch.qos.logback.classic.pattern.SyslogStartConverter;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.io.Serializable;

/**
 * 從中心庫獲取的人員類
 */
public class CentMemberBo implements Serializable {

    private String empNo;
    private String idCard;
    private String userName;
    private String name;
    private String sex;
    private String mobile;
    private String address;
    private String licensePlate;
    private String face;
    //項目編號
    private String projectNo;
    //施工單號
    private String workOrderNo;


    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    /**
     * 轉為用戶
     * @param memberBo
     * @param sysUser
     * @return
     */
    public static SysUser transToSysUser(CentMemberBo memberBo,SysUser sysUser){
        sysUser.setEmpNo(memberBo.getEmpNo());
        sysUser.setIdCard(memberBo.getIdCard());
        sysUser.setUserName(memberBo.getUserName());
        sysUser.setNickName(memberBo.getName());
        sysUser.setSex(memberBo.getSex());
        sysUser.setPhonenumber(memberBo.getMobile());
        sysUser.setFamilyAddress(memberBo.getAddress());
        sysUser.setCarId(memberBo.getLicensePlate());
        sysUser.setFace(memberBo.getFace());
        return sysUser;
    }

    /**
     * 轉為用戶
     * @param memberBo
     * @param sysUser
     * @return
     */
    public static CentMemberBo transFromSysUser(CentMemberBo memberBo,SysUser sysUser){
        memberBo.setEmpNo(sysUser.getEmpNo());
        memberBo.setIdCard(sysUser.getIdCard());
        memberBo.setUserName(sysUser.getUserName());
        memberBo.setName(sysUser.getNickName());
        memberBo.setSex(sysUser.getSex());
        memberBo.setMobile(sysUser.getPhonenumber());
        memberBo.setAddress(sysUser.getFamilyAddress());
        memberBo.setLicensePlate(sysUser.getCarId());
        memberBo.setFace(sysUser.getFace());
        return memberBo;
    }


}
