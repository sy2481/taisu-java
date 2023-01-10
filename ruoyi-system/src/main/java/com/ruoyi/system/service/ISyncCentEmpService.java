package com.ruoyi.system.service;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.bo.CentMemberBo;

import java.util.List;
import java.util.Map;

public interface ISyncCentEmpService {

    /**
     * 向中心平台發送頭像
     *
     */
    public void sendToCent(SysUser sysUser);

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    //public Map<String, CentMemberBo> getListFromCent(String idCards);

    //public Map<String, CentMemberBo> transToMap(JSONObject resObj);

    public List<CentMemberBo> getListFromCentByPage(int pageNum, int pageSize, String orderBy,String empNos);

}
