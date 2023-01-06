package com.ruoyi.base.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkCarBo {
    private String projectNo;

    private String workNo;

    private String lcensePlate;

    private String workTime;

    /**
     * 工单人员
     */
    private List<FactoryWorkBO> factoryWorkBOList;
}
