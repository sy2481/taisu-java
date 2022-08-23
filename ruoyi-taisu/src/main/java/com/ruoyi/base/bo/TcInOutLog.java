package com.ruoyi.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcInOutLog {
    /**
     * 刷卡机IP
     */
    private String ip;
    /**
     * 刷卡时间
     */
    private String dateTime;
    /**
     * 刷卡类别：1--人员入厂；2--人员出厂；3--车辆入厂；4--车辆出厂
     */
    private String type;
    /**
     * 身份证号
     */
    private String idNo;
    /**
     * 0未上傳
     */
    private int uploaded;
    /**
     * 厂门代号
     */
    private String fctDorNm;

}
