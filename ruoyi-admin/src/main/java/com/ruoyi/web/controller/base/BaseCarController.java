package com.ruoyi.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
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
import com.ruoyi.base.domain.BaseCar;
import com.ruoyi.base.service.IBaseCarService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 車Controller
 *
 * @author ruoyi
 * @date 2022-12-08
 */
@Api(tags = "車輛基礎表")
@RestController
@RequestMapping("/base/BaseCar")
public class BaseCarController extends BaseController
{
    @Autowired
    private IBaseCarService baseCarService;

    /**
     * 查询車列表
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaseCar baseCar)
    {
        startPage();
        List<BaseCar> list = baseCarService.selectBaseCarList(baseCar);
        return getDataTable(list);
    }

    /**
     * 导出車列表
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:export')")
    @Log(title = "車", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaseCar baseCar)
    {
        List<BaseCar> list = baseCarService.selectBaseCarList(baseCar);
        ExcelUtil<BaseCar> util = new ExcelUtil<BaseCar>(BaseCar.class);
        util.exportExcel(response, list, "車数据");
    }

    /**
     * 获取車详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(baseCarService.selectBaseCarById(id));
    }

    /**
     * 新增車
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:add')")
    @Log(title = "車", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaseCar baseCar)
    {
        return toAjax(baseCarService.insertBaseCar(baseCar));
    }

    /**
     * 修改車
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:edit')")
    @Log(title = "車", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaseCar baseCar)
    {
        return toAjax(baseCarService.updateBaseCar(baseCar));
    }

    /**
     * 删除車
     */
    @PreAuthorize("@ss.hasPermi('base:BaseCar:remove')")
    @Log(title = "車", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baseCarService.deleteBaseCarByIds(ids));
    }


}
