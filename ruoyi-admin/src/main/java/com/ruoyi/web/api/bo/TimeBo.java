package com.ruoyi.web.api.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBo {

    /** 通道管制开始时间 */
    private String startTime;

    /** 通道管制结束时间 */
    private String endTime;
}
