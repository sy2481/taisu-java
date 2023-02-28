package com.ruoyi.base.service;

import com.ruoyi.base.domain.HcUser;
import com.ruoyi.system.bo.CentMemberBo;

import java.util.Map;

public interface ISyncCentHcService {

    /**
     * 向中心平台發送頭像
     *
     */
    public void sendToCent(HcUser hcUser);

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    public Map<String, CentMemberBo> getListFromCent(String idCards);

}
