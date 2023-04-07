package com.ruoyi.base.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 动态看板警报
 */
public class BaseBoardWarn {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 警报地址
     */
    private String address;
    /**
     * 警报内容
     */
    private String msg;
    /**
     * 警报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
    /**
     * 警报状态
     */
    private String status;
    /**
     * 类型 1 设备 2 定位卡警报
     */
    private Long type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


