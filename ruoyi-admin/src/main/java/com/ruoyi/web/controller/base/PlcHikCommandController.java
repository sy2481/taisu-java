package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.base.domain.PlcHikCommand;
import com.ruoyi.base.service.IPlcHikCommandService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 設備所屬指令Controller
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
@RestController
@RequestMapping("/base/PlcHikCommand")
public class PlcHikCommandController extends BaseController
{
    @Autowired
    private IPlcHikCommandService plcHikCommandService;

    /**
     * 查询設備所屬指令列表
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlcHikCommand plcHikCommand)
    {
        startPage();
        List<PlcHikCommand> list = plcHikCommandService.selectPlcHikCommandList(plcHikCommand);
        return getDataTable(list);
    }

    /**
     * 导出設備所屬指令列表
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:export')")
    @Log(title = "設備所屬指令", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlcHikCommand plcHikCommand)
    {
        List<PlcHikCommand> list = plcHikCommandService.selectPlcHikCommandList(plcHikCommand);
        ExcelUtil<PlcHikCommand> util = new ExcelUtil<PlcHikCommand>(PlcHikCommand.class);
        util.exportExcel(response, list, "設備所屬指令数据");
    }

    /**
     * 获取設備所屬指令详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(plcHikCommandService.selectPlcHikCommandById(id));
    }

    /**
     * 新增設備所屬指令
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:add')")
    @Log(title = "設備所屬指令", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlcHikCommand plcHikCommand)
    {
        return toAjax(plcHikCommandService.insertPlcHikCommand(plcHikCommand));
    }

    /**
     * 修改設備所屬指令
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:edit')")
    @Log(title = "設備所屬指令", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlcHikCommand plcHikCommand)
    {
        return toAjax(plcHikCommandService.updatePlcHikCommand(plcHikCommand));
    }

    /**
     * 删除設備所屬指令
     */
    @PreAuthorize("@ss.hasPermi('base:PlcHikCommand:remove')")
    @Log(title = "設備所屬指令", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(plcHikCommandService.deletePlcHikCommandByIds(ids));
    }
}
