package com.ruoyi.web.api;

import com.ruoyi.base.domain.Device;
import com.ruoyi.base.mapper.DeviceMapper;
import com.ruoyi.base.service.IDeviceService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.web.api.basic.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/device")
public class ApiDeviceController {
    @Autowired
    private IDeviceService deviceService;

    @Autowired
    private DeviceMapper deviceMapper;

    @ApiOperation(value = "device")
    @ResponseBody
    @GetMapping("/getAll")
    public Response getAll() {
        try {

            //先拿到全部的 PLC 和下属设备的关系列表
            List<Device> result = deviceMapper.selectDeviceAllList();

            return Response.builder().code(0).data(result).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.error("查询出错！");
    }

    /**
     * 修改device
     */
    //@Log(title = "device", businessType = BusinessType.UPDATE)
    @ResponseBody
    @PostMapping("/updateStatus")
    public AjaxResult edit(@RequestBody Device device) {
        //deviceService.updateDeviceByIp(device);
        return AjaxResult.success(deviceService.updateDeviceByIp(device));
    }

}

