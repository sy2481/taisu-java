package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ZJFConverter;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.bo.CentMemberBo;
import com.ruoyi.system.service.ISyncCentEmpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SyncCentEmpService implements ISyncCentEmpService {

    /**
     * 中心库地址
     */
    @Value("${cent.host}")
    private String centHost;

    /**
     * 向中心平台發送頭像
     *
     */
    @Override
    public void sendToCent(SysUser sysUser){
        HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForEmployee",
                JSONObject.toJSONString(sysUser));
    }

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    @Override
    public Map<String, CentMemberBo> getListFromCent(String idCards){
        String url = centHost + "/api/wechat/faceDataCent/getListByIdCardsForEmployee";
        JSONObject paramObj = new JSONObject();
        paramObj.put("noFaceIdCards", idCards);

        //結果
        Map<String, CentMemberBo> memberMap = new HashMap<>();
        CentMemberBo centMemberBo = null;
        //開始獲取
        String result = HttpUtils.sendJsonPost(url,
                paramObj.toString());
        JSONObject resObj = !StringUtils.isEmpty(result)
                ? JSONObject.parseObject(result)
                : null;
        if ("0".equals(resObj.getString("code"))) {
            Map<String, JSONObject> list =  (Map<String, JSONObject>) resObj.get("data");
            for(Map.Entry<String,JSONObject> entry:list.entrySet()){
                String key=entry.getKey();
                JSONObject item=entry.getValue();

                String idCard = item.getString("idCard");
                String name = ZJFConverter.SimToTra(StringUtils.nvl(item.getString("nickName"),""));
                String sex= item.getString("sex");
                String mobile=item.getString("phonenumber");
                String address=ZJFConverter.SimToTra(StringUtils.nvl(item.getString("familyAddress"),""));
                //String licensePlate=ZJFConverter.SimToTra(StringUtils.nvl(item.getString("carId"),""));
                String face=item.getString("face");

                if (StringUtils.isEmpty(key)) {
                    continue;
                }
                if (!memberMap.containsKey(key)) {
                    centMemberBo = new CentMemberBo();
                    centMemberBo.setIdCard(key);
                    centMemberBo.setName(name);

                    centMemberBo.setSex(sex);
                    centMemberBo.setMobile(mobile);
                    centMemberBo.setAddress(address);
                    //centMemberBo.setLicensePlate(licensePlate);
                    centMemberBo.setFace(face);
                    memberMap.put(key, centMemberBo);
                }
            }
            return memberMap;

        } else {
            return null;
        }

    }
}
