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
public class SubscribeDataRailsBo implements Serializable {

    private String railId;
    private String railName;
    private String floorNo;
    private List<SubscribeDataPointsBo> points;
}
