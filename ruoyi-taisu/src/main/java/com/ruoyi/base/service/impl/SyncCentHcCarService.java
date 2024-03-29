package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.HcCar;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.service.ISyncCentHcCarService;
import com.ruoyi.base.service.ISyncCentHcService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ZJFConverter;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.system.bo.CentMemberBo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SyncCentHcCarService implements ISyncCentHcCarService {

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
    public void sendToCent(HcCar hcCar){
        HttpUtils.sendJsonPost(centHost+"/api/wechat/faceDataCent/saveFaceForHc",
                JSONObject.toJSONString(hcCar));
    }

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    @Override
    public Map<String, CentMemberBo> getListFromCent(String idNos){
        String url = centHost + "/api/wechat/faceDataCent/getListByIdCardsForHc";
        JSONObject paramObj = new JSONObject();
        paramObj.put("noFaceIdCards", idNos);

        //結果
        Map<String, CentMemberBo> memberMap = new HashMap<String, CentMemberBo>();
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
                String name = ZJFConverter.SimToTra(StringUtils.nvl(item.getString("name"),""));
                String sex= item.getString("sex");
                String mobile=item.getString("phone");
                String address=ZJFConverter.SimToTra(StringUtils.nvl(item.getString("address"),""));
                //String licensePlate=ZJFConverter.SimToTra(StringUtils.nvl(item.getString("lcensePlate"),""));
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
