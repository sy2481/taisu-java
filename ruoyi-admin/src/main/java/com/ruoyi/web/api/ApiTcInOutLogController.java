package com.ruoyi.web.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.timer.domain.TcInOutLog;
import com.ruoyi.timer.service.ITcInOutLogService;
import com.ruoyi.timer.service.ITimInOutLogService;
import com.ruoyi.web.api.basic.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author sunlj
 */
@Api(tags = "TIMER")
@RestController
@RequestMapping(value = "/api/inOutLog")
public class ApiTcInOutLogController extends BaseController {
    @Autowired
    private ITcInOutLogService tcInOutLogService;
    @Autowired
    private ITimInOutLogService timInOutLogService;

    @ResponseBody
    @PostMapping(value = "setOutLog")
    public Response setOutLog(@RequestBody TcInOutLog tcInOutLog) {
        try {
            System.out.println(tcInOutLog);
            tcInOutLogService.insertTcInOutLog(tcInOutLog);
            return Response.success("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return Response.error(e.getMessage());
        }

    }


}
