package com.ruoyi.web.api.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class GuestBo implements Serializable {

    /**
     * 来宾卡号
     */
    @ApiModelProperty(value = "来宾卡号")
    private String guestCard;
    /**
     * 设备Ip
     */
    @ApiModelProperty(value = "设备IP")
    private String ip;
    /**
     * 进出类型
     */
    @ApiModelProperty(value = "进出类型(0-进，1-出)")
    private Integer inOutType;
}
