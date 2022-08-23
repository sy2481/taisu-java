package com.ruoyi.web.api.bo;

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
public class SubscribeInfoBo implements Serializable {

    private Long time;
    private String type;
    private subscribeInfoDataBo data;
}
