package com.ruoyi.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 兴跃
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlJSONBo implements Serializable {
    /**
     * 人道
     */
    private String humane;
    /**
     * 车道
     */
    private String lane;
    /**
     * 危险品道
     */
    private String danger;

}
