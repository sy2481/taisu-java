package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.mapper.*;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.base.vo.CardBindVO;
import com.ruoyi.system.mapper.SysUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author shiva   2022-04-02 16:50
 */
@Log4j2
@Service
public class LocationCardSendService {
    @Value("${plchlk.host}")
    private String host;

    /**
     * 绑定人员和定位卡;
     * 后台员工绑定定位卡触发
     */
    public void bindCardRequest(CardBindVO cardBindVO) {
        String json = JSONObject.toJSONString(cardBindVO);
        String resultStr = HttpUtils.sendJsonPost(host + "/hik/person/bind/card", json);
        JSONObject bindCardResultObj = JSONObject.parseObject(resultStr);
        Integer code = (Integer) bindCardResultObj.get("code");
        if (code == 200) {
            log.info("定位卡绑定成功");
        } else {
            log.error("定位卡绑定失败,错误代码：" + code);
        }
    }

    /**
     * 下发解绑定位卡，
     */
    public void downSendUnbindLocationCard(String locationCardNo) {
        HttpUtils.sendPost(host + "/hik/person/untie/card/" + locationCardNo, null);
    }

}
