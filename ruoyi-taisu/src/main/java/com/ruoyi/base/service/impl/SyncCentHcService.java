package com.ruoyi.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.domain.HcUser;
import com.ruoyi.base.domain.ManFactory;
import com.ruoyi.base.service.ISyncCentHcService;
import com.ruoyi.base.service.ISyncCentVndService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ZJFConverter;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.datasource.DynamicDataSourceContextHolder;
import com.ruoyi.system.bo.CentMemberBo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SyncCentHcService implements ISyncCentHcService {
    public static final Logger log = LoggerFactory.getLogger(SyncCentHcService.class);
    /**
     * 中心库地址
     */
    @Value("${cent.host}")
    private String centHost;

    /**
     * 向中心平台發送頭像
     */
    @Override
    public void sendToCent(HcUser hcUser) {
        HttpUtils.sendJsonPost(centHost + "/api/wechat/faceDataCent/saveFaceForHc",
                JSONObject.toJSONString(hcUser));
    }

    /**
     * 從中心庫獲取人員數據
     *
     * @return
     */
    @Override
    public Map<String, CentMemberBo> getListFromCent(String idCards) {
        String url = centHost + "/api/wechat/faceDataCent/getListByIdCardsForHc";
        log.info("getListFromCent的Url->>>>>"+url);
        JSONObject paramObj = new JSONObject();
        paramObj.put("noFaceIdCards", idCards);

        //結果
        Map<String, CentMemberBo> memberMap = new HashMap<String, CentMemberBo>();
        CentMemberBo centMemberBo = null;
        //開始獲取
        String result = HttpUtils.sendJsonPost(url,
                paramObj.toString());
        JSONObject resObj = !StringUtils.isEmpty(result)
                ? JSONObject.parseObject(result)
                : null;
        log.info("getListFromCent的resObj->>>>>"+resObj);
        if ("0".equals(resObj.getString("code"))) {
            Map<String, JSONObject> list = (Map<String, JSONObject>) resObj.get("data");
            for (Map.Entry<String, JSONObject> entry : list.entrySet()) {
                String key = entry.getKey();
                JSONObject item = entry.getValue();

                String idCard = item.getString("idCard");
                String name = ZJFConverter.SimToTra(StringUtils.nvl(item.getString("nm"), ""));
                String sex = item.getString("sex");
                String mobile = item.getString("phone");
                String address = ZJFConverter.SimToTra(StringUtils.nvl(item.getString("address"), ""));
                //String licensePlate=ZJFConverter.SimToTra(StringUtils.nvl(item.getString("lcensePlate"),""));
                String face = item.getString("face");
                String birtyday = item.getString("birthday");
                String company = ZJFConverter.SimToTra(StringUtils.nvl(item.getString("company"),""));

                String driverLicense = item.getString("driverLicense");
                String escortLicense = item.getString("escortLicense");

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
                    centMemberBo.setBirthday(birtyday);
                    centMemberBo.setCompany(company);
                    centMemberBo.setDriverLicense(driverLicense);
                    centMemberBo.setEscortLicense(escortLicense);
                    memberMap.put(key, centMemberBo);
                }
            }
            return memberMap;

        } else {
            return null;
        }

    }
}
