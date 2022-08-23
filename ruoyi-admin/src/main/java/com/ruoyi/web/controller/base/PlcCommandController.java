package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.ruoyi.base.domain.PlcCommand;
import com.ruoyi.base.service.IPlcCommandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * PLC指令Controller
 * 
 * @author ruoyi
 * @date 2022-07-16
 */
@Api(tags = "PLC指令")
@RestController
@RequestMapping("/base/command")
public class PlcCommandController extends BaseController
{
    @Autowired
    private IPlcCommandService plcCommandService;

    /**
     * 查詢PLC指令列表
     */
    @PreAuthorize("@ss.hasPermi('base:command:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlcCommand plcCommand)
    {
        startPage();
        List<PlcCommand> list = plcCommandService.selectPlcCommandList(plcCommand);
        return getDataTable(list);
    }

    /**
     * 導出PLC指令列表
     */
    @PreAuthorize("@ss.hasPermi('base:command:export')")
    @Log(title = "PLC指令", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlcCommand plcCommand)
    {
        List<PlcCommand> list = plcCommandService.selectPlcCommandList(plcCommand);
        ExcelUtil<PlcCommand> util = new ExcelUtil<PlcCommand>(PlcCommand.class);
        util.exportExcel(response, list, "PLC指令数据");
    }

    /**
     * 獲取PLC指令詳細信息
     */
    @PreAuthorize("@ss.hasPermi('base:command:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plcCommandService.selectPlcCommandById(id));
    }

    /**
     * 新增PLC指令
     */
    @PreAuthorize("@ss.hasPermi('base:command:add')")
    @Log(title = "PLC指令", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlcCommand plcCommand)
    {
        plcCommand.setCreator(getUsername());
        return toAjax(plcCommandService.insertPlcCommand(plcCommand));
    }

    /**
     * 修改PLC指令
     */
    @PreAuthorize("@ss.hasPermi('base:command:edit')")
    @Log(title = "PLC指令", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlcCommand plcCommand)
    {
        return toAjax(plcCommandService.updatePlcCommand(plcCommand));
    }

    /**
     * 刪除PLC指令
     */
    @PreAuthorize("@ss.hasPermi('base:command:remove')")
    @Log(title = "PLC指令", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(plcCommandService.deletePlcCommandByIds(ids));
    }
}
