package com.ruoyi.base.interact;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.base.utils.HttpUtils;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 * ---------中心同步
 */
public class SyncCenterService {
    /**
     * 中心-host
     */
    @Value("${cent.host}")
    private String centHost;

    public void centerUserUpdate(SysUser user) {
        //判断是否为员工,厂商转员工不做同步
        String empNo = user.getEmpNo();
        if (!empNo.toLowerCase().startsWith("PP")) {
            return;
        }
        //身份证、照片不为空就做中心同步
        if (StringUtils.isBlank(user.getFace()) || StringUtils.isBlank(user.getIdCard())) {
            return;
        }
        String userJson = JSONObject.toJSONString(user);
        String httpStr = centHost + "/center/user/updateUser";
        System.out.println("同步 --->  中心 http : " + centHost);
        HttpUtils.sendJsonPost(centHost + "/center/user/updateUser", userJson);
    }

}
