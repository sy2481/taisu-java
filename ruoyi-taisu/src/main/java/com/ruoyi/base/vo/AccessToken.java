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
public class AccessToken {

    private String refreshToken;

    private String token;

    private Integer expiresIn;

    private Long startTime;
}
