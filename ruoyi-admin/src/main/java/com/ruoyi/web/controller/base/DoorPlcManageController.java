package com.ruoyi.web.controller.base;


import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.domain.DoorPlcManage;
import com.ruoyi.base.service.DoorPlcManageService;
import com.ruoyi.web.api.dto.PlcCommandDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车道人道plc指令Controller
 *
 * @author ruoyi
 * @date 2022-10-25
 */
@RestController
@RequestMapping("/system/manage")
public class DoorPlcManageController extends BaseController
{
    @Autowired
    private DoorPlcManageService doorPlcManageService;

    /**
     * 查询车道人道plc指令列表
     */
//    @PreAuthorize("@ss.hasPermi('system:manage:list')")
//    @GetMapping("/list")
//    public TableDataInfo list(DoorPlcManage doorPlcManage)
//    {
//        startPage();
//        List<DoorPlcManage> list = doorPlcManageService.selectDoorPlcManageList(doorPlcManage);
//        return getDataTable(list);
//    }

    /**
     * 导出车道人道plc指令列表
     */
//    @PreAuthorize("@ss.hasPermi('system:manage:export')")
//    @Log(title = "车道人道plc指令", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, DoorPlcManage doorPlcManage)
//    {
//        List<DoorPlcManage> list = doorPlcManageService.selectDoorPlcManageList(doorPlcManage);
//        ExcelUtil<DoorPlcManage> util = new ExcelUtil<DoorPlcManage>(DoorPlcManage.class);
//        util.exportExcel(response, list, "车道人道plc指令数据");
//    }

    /**
     * 获取车道人道plc指令详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:manage:query')")
    @GetMapping(value = "/{doorId}")
    public AjaxResult getInfo(@PathVariable("doorId") Long doorId)
    {
        return AjaxResult.success(doorPlcManageService.selectDoorPlcManageByDoorId(doorId));
    }

    /**
     * 新增车道人道plc指令
     */
    @PreAuthorize("@ss.hasPermi('system:manage:add')")
    @Log(title = "车道人道plc指令", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DoorPlcManage doorPlcManage)
    {
        return toAjax(doorPlcManageService.insertDoorPlcManage(doorPlcManage));
    }

    /**
     * 修改车道人道plc指令
     */
    @PreAuthorize("@ss.hasPermi('system:manage:edit')")
    @Log(title = "车道人道plc指令", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DoorPlcManage doorPlcManage)
    {
        return toAjax(doorPlcManageService.updateDoorPlcManage(doorPlcManage));
    }

    /**
     * 删除车道人道plc指令
     */
    @PreAuthorize("@ss.hasPermi('system:manage:remove')")
    @Log(title = "车道人道plc指令", businessType = BusinessType.DELETE)
    @DeleteMapping("/{doorIds}")
    public AjaxResult remove(@PathVariable Long[] doorIds)
    {
        return toAjax(doorPlcManageService.deleteDoorPlcManageByDoorIds(doorIds));
    }

    @PreAuthorize("@ss.hasPermi('system:manage:export')")
    @PostMapping("/sendPlc")
    public void export(@RequestBody PlcCommandDTO plcCommandDTO)
    {
        doorPlcManageService.executePlcSendCommand(plcCommandDTO.getIp(),plcCommandDTO.getPort(),plcCommandDTO.getCommand());
    }

}
