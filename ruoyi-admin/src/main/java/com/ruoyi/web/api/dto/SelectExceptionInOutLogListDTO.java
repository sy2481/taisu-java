package com.ruoyi.web.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SelectExceptionInOutLogListDTO {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
