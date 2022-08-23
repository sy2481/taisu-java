package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 来宾卡对象 base_guest_card
 *
 * @author ruoyi
 * @date 2022-03-26
 */
public class GuestCard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String delFlag;

    /**
     * 卡编号
     */
    @Excel(name = "卡编号")
    private String no;

    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;


    /**
     * 创建者
     */
    @Excel(name = "创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 厂商名称
     */
    private String factoryName;
    /**
     * 借用人名称
     */
    private String userName;

    @Excel(name = "借用时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date borrowDate;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 厂区权限
     */
    private String area;
    /**
     * 通道权限
     */
    private String pass;

    /**
     * 厂区
     */
    private Long[] factoryIdArray;



    @Excel(name = "有效期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validTime;
    /**
     * PLC通道
     */
    private Long[] plcInfo;


    public Date getValidTime() {
        return validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }
    public Long[] getFactoryIdArray() {
        return factoryIdArray;
    }

    public void setFactoryIdArray(Long[] factoryIdArray) {
        this.factoryIdArray = factoryIdArray;
    }

    public Long[] getPlcInfo() {
        return plcInfo;
    }

    public void setPlcInfo(Long[] plcInfo) {
        this.plcInfo = plcInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
