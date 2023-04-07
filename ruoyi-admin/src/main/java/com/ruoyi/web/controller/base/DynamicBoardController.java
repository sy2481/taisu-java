package com.ruoyi.web.controller.base;

import com.ruoyi.base.service.DynamicBoardService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicBoardController extends BaseController {

    @Autowired
    DynamicBoardService dynamicBoardService;

    @RequestMapping(value = "/base/board/getDayInAndOutInfo", method = {RequestMethod.GET})
    public AjaxResult getDayInAndOutInfo() {
        return AjaxResult.success(dynamicBoardService.getDayInAndOutInfo());

    }

    @RequestMapping(value = "/base/board/getDayInAndOutInfoByArea", method = {RequestMethod.GET})
    public AjaxResult getDayInAndOutInfoByArea(@RequestParam String area) {
        return AjaxResult.success(dynamicBoardService.getDayInAndOutInfoByArea(area));

    }

    @RequestMapping(value = "/base/board/getAllPersonAndCarDayInAndOutInfo", method = {RequestMethod.GET})
    public AjaxResult getAllPersonAndCarDayInAndOutInfo() {
        return AjaxResult.success(dynamicBoardService.getAllPersonAndCarDayInAndOutInfo());

    }

    @RequestMapping(value = "/base/board/getAllDayInAndOutInfo", method = {RequestMethod.GET})
    public AjaxResult getAllDayInAndOutInfo() {
        return AjaxResult.success(dynamicBoardService.getAllDayInAndOutInfo());
    }

}