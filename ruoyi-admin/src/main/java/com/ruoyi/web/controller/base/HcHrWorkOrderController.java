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
import com.ruoyi.base.domain.HcHrWorkOrder;
import com.ruoyi.base.service.IHcHrWorkOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 歷史危化施工單Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcHrWorkOrder")
public class HcHrWorkOrderController extends BaseController
{
    @Autowired
    private IHcHrWorkOrderService hcHrWorkOrderService;

    /**
     * 查询歷史危化施工單列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcHrWorkOrder hcHrWorkOrder)
    {
        startPage();
        List<HcHrWorkOrder> list = hcHrWorkOrderService.selectHcHrWorkOrderList(hcHrWorkOrder);
        return getDataTable(list);
    }

    /**
     * 导出歷史危化施工單列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:export')")
    @Log(title = "歷史危化施工單", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcHrWorkOrder hcHrWorkOrder)
    {
        List<HcHrWorkOrder> list = hcHrWorkOrderService.selectHcHrWorkOrderList(hcHrWorkOrder);
        ExcelUtil<HcHrWorkOrder> util = new ExcelUtil<HcHrWorkOrder>(HcHrWorkOrder.class);
        util.exportExcel(response, list, "歷史危化施工單数据");
    }

    /**
     * 获取歷史危化施工單详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcHrWorkOrderService.selectHcHrWorkOrderById(id));
    }

    /**
     * 新增歷史危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:add')")
    @Log(title = "歷史危化施工單", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcHrWorkOrder hcHrWorkOrder)
    {
        return toAjax(hcHrWorkOrderService.insertHcHrWorkOrder(hcHrWorkOrder));
    }

    /**
     * 修改歷史危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:edit')")
    @Log(title = "歷史危化施工單", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcHrWorkOrder hcHrWorkOrder)
    {
        return toAjax(hcHrWorkOrderService.updateHcHrWorkOrder(hcHrWorkOrder));
    }

    /**
     * 删除歷史危化施工單
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrder:remove')")
    @Log(title = "歷史危化施工單", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcHrWorkOrderService.deleteHcHrWorkOrderByIds(ids));
    }
}
