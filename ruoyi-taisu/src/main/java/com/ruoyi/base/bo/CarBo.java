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
public class CarBo {
    /** 車牌 */
    private String lcensePlate;

    private String name;
    /** 廠商名稱 */
    private String factoryName;

    /**
     * 发证厂区编号
     */
    private String factoryNo;
    /** 入場證（定位卡編號） */
    private String ipLtLic;
    /**
     * 有效时间
     */
    private String datetime;
}
