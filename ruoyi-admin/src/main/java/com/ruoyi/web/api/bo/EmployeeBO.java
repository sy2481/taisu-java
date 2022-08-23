package com.ruoyi.web.api.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author shiva   2022/3/6 19:02
 */
@Data
public class EmployeeBO implements Serializable {

    private Long userId;
    private String nickName;
    private String phonenumber;
    private String sex;
    private String idCard;
    private String face;
    private Long sended;


}
