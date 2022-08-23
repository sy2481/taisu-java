package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.base.domain.PlcCommandOperationSortBatch;
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
import com.ruoyi.base.domain.PlcCommandOperationSort;
import com.ruoyi.base.service.IPlcCommandOperationSortService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * plc指令順序Controller
 * 
 * @author ruoyi
 * @date 2022-07-21
 */
@Api(tags = "plc指令順序")
@RestController
@RequestMapping("/base/sort")
public class PlcCommandOperationSortController extends BaseController
{
    @Autowired
    private IPlcCommandOperationSortService plcCommandOperationSortService;

    /**
     * 查詢plc指令順序列表
     */
    @PreAuthorize("@ss.hasPermi('base:sort:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlcCommandOperationSort plcCommandOperationSort)
    {
        startPage();
        List<PlcCommandOperationSort> list = plcCommandOperationSortService.selectPlcCommandOperationSortList(plcCommandOperationSort);
        return getDataTable(list);
    }

    /**
     * 導出plc指令順序列表
     */
    @PreAuthorize("@ss.hasPermi('base:sort:export')")
    @Log(title = "plc指令順序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PlcCommandOperationSort plcCommandOperationSort)
    {
        List<PlcCommandOperationSort> list = plcCommandOperationSortService.selectPlcCommandOperationSortList(plcCommandOperationSort);
        ExcelUtil<PlcCommandOperationSort> util = new ExcelUtil<PlcCommandOperationSort>(PlcCommandOperationSort.class);
        util.exportExcel(response, list, "plc指令順序数据");
    }

    /**
     * 獲取plc指令順序詳細信息
     */
    @PreAuthorize("@ss.hasPermi('base:sort:query')")
    @GetMapping(value = "/{sortId}")
    public AjaxResult getInfo(@PathVariable("sortId") Long sortId)
    {
        return AjaxResult.success(plcCommandOperationSortService.selectPlcCommandOperationSortBySortId(sortId));
    }

    /**
     * 新增plc指令順序
     */
    @PreAuthorize("@ss.hasPermi('base:sort:add')")
    @Log(title = "plc指令順序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PlcCommandOperationSort plcCommandOperationSort)
    {
        return toAjax(plcCommandOperationSortService.insertPlcCommandOperationSort(plcCommandOperationSort));
    }

    /**
     * 批量新增plc指令順序
     */
    @PreAuthorize("@ss.hasPermi('base:sort:batchAdd')")
    @Log(title = "plc指令順序", businessType = BusinessType.INSERT)
    @PostMapping("/batchAdd")
    public AjaxResult batchAdd(@RequestBody PlcCommandOperationSortBatch plcCommandOperationSortBatch)
    {
        return toAjax(plcCommandOperationSortService.insertPlcCommandOperationSortBatch(plcCommandOperationSortBatch));
    }

    /**
     * 修改plc指令順序
     */
    @PreAuthorize("@ss.hasPermi('base:sort:edit')")
    @Log(title = "plc指令順序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PlcCommandOperationSort plcCommandOperationSort)
    {
        return toAjax(plcCommandOperationSortService.updatePlcCommandOperationSort(plcCommandOperationSort));
    }

    /**
     * 删除plc指令順序
     */
    @PreAuthorize("@ss.hasPermi('base:sort:remove')")
    @Log(title = "plc指令順序", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sortIds}")
    public AjaxResult remove(@PathVariable Long[] sortIds)
    {
        return toAjax(plcCommandOperationSortService.deletePlcCommandOperationSortBySortIds(sortIds));
    }
}
