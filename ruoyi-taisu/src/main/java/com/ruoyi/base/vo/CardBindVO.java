package com.ruoyi.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author chenyuren
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardBindVO implements Serializable {
    /** 人员卡号 */
    private String cardNumber;
    /** 海康人员ID **/
    private String personId;
}
