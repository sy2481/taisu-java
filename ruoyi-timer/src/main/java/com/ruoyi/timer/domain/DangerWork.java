package com.ruoyi.timer.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shiva   2022-03-27 21:22
 */
@Data
public class DangerWork implements Serializable {
    public static final String TABLE_NAME="V0NBRKX5";


    private String vhno;
    private String nm;
    private String ipltlic;
    private String idno;
    private String dpid;
    private String ipltcarno;
    private String ipltwgt;
    private String opltwgt;
    private String iplttm;
    private String oplttm;
    private String kdnm;

}
