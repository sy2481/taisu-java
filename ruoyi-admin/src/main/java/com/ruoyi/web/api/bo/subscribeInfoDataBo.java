package com.ruoyi.web.api.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class subscribeInfoDataBo implements Serializable {
    private String socialCreditCode;
    private String id;
    private Long time;
    private String type;
    private String alarmInfo;
    private String code;
    private String alarmType;
    private String alarmLocation;
    private List<SubscribeDataRailsBo> rails;
}
