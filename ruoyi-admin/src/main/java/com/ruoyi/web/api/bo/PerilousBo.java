package com.ruoyi.web.api.bo;

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
public class PerilousBo implements Serializable {

    @ApiModelProperty(value = "身份证号")
    private String idCard;
    /**
     * 设备Ip
     */
    @ApiModelProperty(value = "设备IP")
    private String ip;
    /**
     * 进出类型
     */
    @ApiModelProperty(value = "进出类型(1-进，2-出)")
    private Integer inOutType;

    @ApiModelProperty(value = "门禁验证方式")
    private String checkingType;

    @ApiModelProperty(value = "车牌号")
    private String carNo;
}
