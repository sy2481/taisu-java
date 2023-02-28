package com.ruoyi.base.service;

import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.system.bo.CentMemberBo;

import java.util.Map;

public interface ISyncCentHcCarService {

    /**
     * 向中心平台同步車輛
     *
     */
    public void sendToCent(HcCar hcCar);

    /**
     * 從中心庫獲取車輛
     *
     * @return
     */
    public Map<String, CentMemberBo> getListFromCent(String idNos);

}
