package com.ruoyi.base.service;

import com.ruoyi.base.domain.HCPassageway;
import com.ruoyi.base.domain.InOutLog;

import java.util.List;

/**
 * 进出记录Service接口
 * 
 */
public interface IHCPassagewayService
{

    /**
     * 开门的逻辑验证
     *
     * @param param 參數
     * @return 返回內容
     */
    public HCPassageway.HCPassagewayResultBody OpenDoorLogic(HCPassageway.HCPassagewayParamBody param);

}
