package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.domain.SendPlcCommandParam;
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
import com.ruoyi.base.domain.BasePassagewayOperation;
import com.ruoyi.base.service.IBasePassagewayOperationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 通道操作Controller
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
@Api(tags = "通道操作")
@RestController
@RequestMapping("/base/operation")
public class BasePassagewayOperationController extends BaseController
{
    @Autowired
    private IBasePassagewayOperationService basePassagewayOperationService;

    /**
     * 查詢通道操作列表
     */
    @PreAuthorize("@ss.hasPermi('base:operation:list')")
    @GetMapping("/list")
    public TableDataInfo list(BasePassagewayOperation basePassagewayOperation)
    {
        startPage();
        List<BasePassagewayOperation> list = basePassagewayOperationService.selectBasePassagewayOperationList(basePassagewayOperation);
        return getDataTable(list);
    }

    /**
     * 導出通道操作列表
     */
    @PreAuthorize("@ss.hasPermi('base:operation:export')")
    @Log(title = "通道操作", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BasePassagewayOperation basePassagewayOperation)
    {
        List<BasePassagewayOperation> list = basePassagewayOperationService.selectBasePassagewayOperationList(basePassagewayOperation);
        ExcelUtil<BasePassagewayOperation> util = new ExcelUtil<BasePassagewayOperation>(BasePassagewayOperation.class);
        util.exportExcel(response, list, "通道操作數據");
    }

    /**
     * 獲取通道操作詳細信息
     */
    @PreAuthorize("@ss.hasPermi('base:operation:query')")
    @GetMapping(value = "/{operationId}")
    public AjaxResult getInfo(@PathVariable("operationId") Long operationId)
    {
        return AjaxResult.success(basePassagewayOperationService.selectBasePassagewayOperationByOperationId(operationId));
    }

    /**
     * 新增通道操作
     */
    @PreAuthorize("@ss.hasPermi('base:operation:add')")
    @Log(title = "通道操作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BasePassagewayOperation basePassagewayOperation)
    {
        basePassagewayOperation.setCreator(getUsername());
        return toAjax(basePassagewayOperationService.insertBasePassagewayOperation(basePassagewayOperation));
    }

    /**
     * 修改通道操作
     */
    @PreAuthorize("@ss.hasPermi('base:operation:edit')")
    @Log(title = "通道操作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BasePassagewayOperation basePassagewayOperation)
    {
        return toAjax(basePassagewayOperationService.updateBasePassagewayOperation(basePassagewayOperation));
    }

    /**
     * 删除通道操作
     */
    @PreAuthorize("@ss.hasPermi('base:operation:remove')")
    @Log(title = "通道操作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{operationIds}")
    public AjaxResult remove(@PathVariable Long[] operationIds)
    {
        return toAjax(basePassagewayOperationService.deleteBasePassagewayOperationByOperationIds(operationIds));
    }

    /**
     * 发送PLC指令
     */
    @PreAuthorize("@ss.hasPermi('base:operation:send')")
    @GetMapping("/send/{operationId}")
    public AjaxResult sendPLCCommand(@PathVariable("operationId") Long operationId)
    {
        return toAjax(basePassagewayOperationService.sendPLCCommand(operationId));
    }

    /**
     * 发送PLC指令
     */
    @GetMapping("/testSend")
    public AjaxResult testSend(SendPlcCommandParam sendPlcCommandParam)
    {
        return toAjax(basePassagewayOperationService.testSend(sendPlcCommandParam));
    }
}
