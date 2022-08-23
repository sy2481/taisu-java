package com.ruoyi.web.api;

import com.ruoyi.base.service.impl.ApiService;
import com.ruoyi.web.api.basic.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 进出日志插入接口
 * @author shiva   2022/3/7 14:18
 */
@Api(tags = "TIMER")
@RestController
@RequestMapping("/api/log")
public class ApiInOutLogController {

    @Autowired
    private ApiService apiService;


    /**
     * 进出记录接⼝
     * 定位卡进出记录，由我们来提供接⼝，他们调⽤插⼊
     * @param logType 0-入场，1-离场
     * @param idCardNo 身份证号
     * @param locationCardNo 定位卡编号
     * @param equipmentIp 设备IP
     * @param carParam 車牌號或車卡
     */
    @ResponseBody
    @GetMapping("/inOutLogInsert")
    public Response inOutLogInsert(String idCardNo, String locationCardNo, String equipmentIp, String logType, String carParam) {
        try {
            apiService.inOutLogInsert(idCardNo, locationCardNo, equipmentIp, logType, carParam);
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("插入出錯，請稍後再試！");
    }

    /**
     * TODO 来宾卡进出记录日志
     */
    @ResponseBody
    @GetMapping("/guestLogInsert")
    public Response guestLogInsert(String cardNo,String equipmentIp, String logType) {
        try {
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("插入出錯，請稍後再試！");
    }

}
