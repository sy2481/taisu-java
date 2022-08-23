package com.ruoyi.web.api;

import com.ruoyi.base.service.IEquipmentLogService;
import com.ruoyi.web.api.basic.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 兴跃
 */
@Api(tags = "设备上下线记录")
@RestController
@RequestMapping(value = "/api/equipmentLog")
public class ApiEquipmentLogController {
    @Autowired
    private IEquipmentLogService equipmentLogService;

    @ApiOperation(value = "上下线记录添加")
    @ResponseBody
    @GetMapping("/info")
    public Response equipmentLogInfo(String ip, Integer type) {
        try {
            equipmentLogService.addLogInfo(ip, type);
            return Response.builder().code(0).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(e.getMessage());
        }

    }
}
