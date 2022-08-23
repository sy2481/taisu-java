package com.ruoyi.web.api;

import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定位卡绑定解绑相关接口
 * @author shiva   2022/3/7 14:19
 */
@RestController
@RequestMapping("/api/location")
public class ApiLocationController {

    @Autowired
    private ApiService apiService;

    /**
     * 绑定
     * @param idCardNo 身份证号
     * @param locationCardNo 定位卡编号
     */
    @ResponseBody
    @GetMapping("/bind")
    public Response bind(String idCardNo, String locationCardNo) {
        try {
            if (StringUtils.isBlank(idCardNo)){
                return Response.error("身份证号不允许为空");
            }
            if (StringUtils.isBlank(locationCardNo)){
                return Response.error("定位卡编号不允许为空");
            }
            apiService.bind(idCardNo, locationCardNo);
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }
    }

    /**
     * 解绑
     * @param locationCardNo 定位卡编号
     */
    @ResponseBody
    @GetMapping("/unbind")
    public Response unbind(String locationCardNo) {
        try {
            if (StringUtils.isBlank(locationCardNo)){
                return Response.error("定位卡编号不允许为空");
            }
            int data = apiService.unbind(locationCardNo);
            return Response.builder().code(0).data(data).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查詢出錯，請稍後再試！");
    }

}
