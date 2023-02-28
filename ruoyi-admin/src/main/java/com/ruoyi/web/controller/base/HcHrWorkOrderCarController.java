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
import com.ruoyi.base.domain.HcHrWorkOrderCar;
import com.ruoyi.base.service.IHcHrWorkOrderCarService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 歷史危化施工單車輛Controller
 * 
 * @author ruoyi
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/base/HcHrWorkOrderCar")
public class HcHrWorkOrderCarController extends BaseController
{
    @Autowired
    private IHcHrWorkOrderCarService hcHrWorkOrderCarService;

    /**
     * 查询歷史危化施工單車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:list')")
    @GetMapping("/list")
    public TableDataInfo list(HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        startPage();
        List<HcHrWorkOrderCar> list = hcHrWorkOrderCarService.selectHcHrWorkOrderCarList(hcHrWorkOrderCar);
        return getDataTable(list);
    }

    /**
     * 导出歷史危化施工單車輛列表
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:export')")
    @Log(title = "歷史危化施工單車輛", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        List<HcHrWorkOrderCar> list = hcHrWorkOrderCarService.selectHcHrWorkOrderCarList(hcHrWorkOrderCar);
        ExcelUtil<HcHrWorkOrderCar> util = new ExcelUtil<HcHrWorkOrderCar>(HcHrWorkOrderCar.class);
        util.exportExcel(response, list, "歷史危化施工單車輛数据");
    }

    /**
     * 获取歷史危化施工單車輛详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(hcHrWorkOrderCarService.selectHcHrWorkOrderCarById(id));
    }

    /**
     * 新增歷史危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:add')")
    @Log(title = "歷史危化施工單車輛", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        return toAjax(hcHrWorkOrderCarService.insertHcHrWorkOrderCar(hcHrWorkOrderCar));
    }

    /**
     * 修改歷史危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:edit')")
    @Log(title = "歷史危化施工單車輛", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HcHrWorkOrderCar hcHrWorkOrderCar)
    {
        return toAjax(hcHrWorkOrderCarService.updateHcHrWorkOrderCar(hcHrWorkOrderCar));
    }

    /**
     * 删除歷史危化施工單車輛
     */
    @PreAuthorize("@ss.hasPermi('base:HcHrWorkOrderCar:remove')")
    @Log(title = "歷史危化施工單車輛", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(hcHrWorkOrderCarService.deleteHcHrWorkOrderCarByIds(ids));
    }
}
