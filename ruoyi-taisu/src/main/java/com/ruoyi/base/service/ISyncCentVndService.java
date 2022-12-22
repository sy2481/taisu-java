package com.ruoyi.base.service;

import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.bo.CentMemberBo;

import java.util.Map;

public interface ISyncCentVndService {

    /**
     * 向中心平台發送頭像
     *
     */
    public void sendToCent(ManFactory manFactory);

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    public Map<String, CentMemberBo> getListFromCent(String idCards);

}
