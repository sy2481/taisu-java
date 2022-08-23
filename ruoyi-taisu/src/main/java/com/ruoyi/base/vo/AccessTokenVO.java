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
public class AccessTokenVO {
    private Object errorMsg;

    private Integer errorCode;

    private AccessToken accessToken;
}
