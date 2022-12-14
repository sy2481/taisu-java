package com.ruoyi.base.taskHandler;

import com.ruoyi.base.domain.EqDevice;

import java.util.Date;

public class PingState {
    private EqDevice eqDevice; //設備
    private Date pingTime; //檢測時間
    private Integer pingCnt; //檢測次數
    private Long lastStateRecordStatus; //最後一條記錄的狀態

    public EqDevice getEqDevice() {
        return eqDevice;
    }

    public void setEqDevice(EqDevice eqDevice) {
        this.eqDevice = eqDevice;
    }

    public Date getPingTime() {
        return pingTime;
    }

    public void setPingTime(Date pingTime) {
        this.pingTime = pingTime;
    }

    public Integer getPingCnt() {
        return pingCnt;
    }

    public void setPingCnt(Integer pingCnt) {
        this.pingCnt = pingCnt;
    }

    public Long getLastStateRecordStatus() {
        return lastStateRecordStatus;
    }

    public void setLastStateRecordStatus(Long lastStateRecordStatus) {
        this.lastStateRecordStatus = lastStateRecordStatus;
    }
}
