package com.ruoyi.web.controller.base;

import com.ruoyi.base.domain.HCPassageway;
import com.ruoyi.base.domain.InOutLog;
import com.ruoyi.base.service.IHCPassagewayService;
import com.ruoyi.base.service.IInOutLogService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.web.api.basic.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 危化开门的逻辑验证Controller
 * 
 */
@RestController
@RequestMapping("/api/hCPassageway")
public class HCPassagewayController extends BaseController
{
    @Autowired
    private IHCPassagewayService hCPassagewayService;
    @Autowired
    private IInOutLogService inOutLogService;

    /**
     * 危化开门的逻辑验证
     */
    @ResponseBody
    @PostMapping(value = "openDoorLogic")
    public Response openDoorLogic(@RequestBody HCPassageway.HCPassagewayParamBody paramBody)
    {
        HCPassageway.HCPassagewayResultBody result =new HCPassageway.HCPassagewayResultBody();
        try {
            result = hCPassagewayService.OpenDoorLogic(paramBody);
            return Response.success(result.getMsg(),result);
        }catch (Exception e) {
            result.setMsg("物流通道邏輯驗證異常");
            return Response.success(result.getMsg(),result);
        }
    }


//    /**
//     * 生成/更新进出记录
//     */
//    @ResponseBody
//    @PostMapping(value = "createOrUpdateInOutLog")
//    public Response createOrUpdateInOutLog(InOutLog inOutLog)
//    {
//        try {
//
//            int result = inOutLogService.insertInOutLog(inOutLog);
//            if(result>0)
//
//            return Response.success(result.getMsg(),result);
//        }catch (Exception e) {
//            return Response.error("危化通道邏輯驗證異常");
//        }
//    }

}
