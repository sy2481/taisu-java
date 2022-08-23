package com.ruoyi.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class workCarBo {
    private String projectNo;

    private String workNo;

    private String lcensePlate;

    private String workTime;
}
