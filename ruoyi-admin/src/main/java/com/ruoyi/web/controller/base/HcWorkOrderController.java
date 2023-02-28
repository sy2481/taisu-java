package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.web.controller.timer.TaskSyncErp;
import io.swagger.annotations.ApiOperation;
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
import com.ruoyi.base.domain.HcWorkOrder;
import com.ruoyi.base.service.IHcWorkOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 危化施工單Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcWorkOrder")
public class HcWorkOrderController extends BaseController
{
    @Autowired
    private IHcWorkOrderService hcWorkOrderService;
    @Autowired
    private TaskSyncErp taskSyncErp;

    /**
     * 查询危化施工單列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcWorkOrder hcWorkOrder)
    {
        startPage();
        List<HcWorkOrder> list = hcWorkOrderService.selectHcWorkOrderList(hcWorkOrder);
        return getDataTable(list);
    }

    /**
     * 导出危化施工單列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:export')")
    @Log(title = "危化施工單", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcWorkOrder hcWorkOrder)
    {
        List<HcWorkOrder> list = hcWorkOrderService.selectHcWorkOrderList(hcWorkOrder);
        ExcelUtil<HcWorkOrder> util = new ExcelUtil<HcWorkOrder>(HcWorkOrder.class);
        util.exportExcel(response, list, "危化施工單数据");
    }

    /**
     * 获取危化施工單详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcWorkOrderService.selectHcWorkOrderById(id));
    }

    /**
     * 新增危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:add')")
    @Log(title = "危化施工單", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcWorkOrder hcWorkOrder)
    {
        return toAjax(hcWorkOrderService.insertHcWorkOrder(hcWorkOrder));
    }

    /**
     * 修改危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:edit')")
    @Log(title = "危化施工單", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcWorkOrder hcWorkOrder)
    {
        return toAjax(hcWorkOrderService.updateHcWorkOrder(hcWorkOrder));
    }

    /**
     * 删除危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:remove')")
    @Log(title = "危化施工單", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcWorkOrderService.deleteHcWorkOrderByIds(ids));
    }

    /**
     * 從EPR手動同步
     */
    @ApiOperation("危化信息獲取(ERP)")
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:syncErp')")
    @Log(title = "危化信息獲取(ERP)")
    @GetMapping("/syncErp")
    public AjaxResult syncErp() {
        taskSyncErp.syncHcFromErp();
        return AjaxResult.success();
    }

    /**
     * 從EPR手動同步
     */
    @ApiOperation("移動到歷史表")
    @PreAuthorize("@ss.hasPermi('base:HcWorkOrder:toHr')")
    @Log(title = "移動到歷史表")
    @GetMapping("/toHr")
    public AjaxResult toHr() {
        taskSyncErp.hcToHr();
        return AjaxResult.success();
    }
}
