package com.ruoyi.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacSuccessVo {

    private String mac;
    private Integer battery;
    private Integer workStatus;
    private Boolean online;
    private String errorMsg;
}
