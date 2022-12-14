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
import com.ruoyi.base.domain.EqPing;
import com.ruoyi.base.service.IEqPingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 設備檢測記錄Controller
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
@RestController
@RequestMapping("/base/EqPing")
public class EqPingController extends BaseController
{
    @Autowired
    private IEqPingService eqPingService;

    /**
     * 查询設備檢測記錄列表
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:list')")
    @GetMapping("/list")
    public TableDataInfo list(EqPing eqPing)
    {
        startPage();
        List<EqPing> list = eqPingService.selectEqPingList(eqPing);
        return getDataTable(list);
    }

    /**
     * 导出設備檢測記錄列表
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:export')")
    @Log(title = "設備檢測記錄", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EqPing eqPing)
    {
        List<EqPing> list = eqPingService.selectEqPingList(eqPing);
        ExcelUtil<EqPing> util = new ExcelUtil<EqPing>(EqPing.class);
        util.exportExcel(response, list, "設備檢測記錄数据");
    }

    /**
     * 获取設備檢測記錄详细信息
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(eqPingService.selectEqPingById(id));
    }

    /**
     * 新增設備檢測記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:add')")
    @Log(title = "設備檢測記錄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqPing eqPing)
    {
        return toAjax(eqPingService.insertEqPing(eqPing));
    }

    /**
     * 修改設備檢測記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:edit')")
    @Log(title = "設備檢測記錄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqPing eqPing)
    {
        return toAjax(eqPingService.updateEqPing(eqPing));
    }

    /**
     * 删除設備檢測記錄
     */
    @PreAuthorize("@ss.hasPermi('base:EqPing:remove')")
    @Log(title = "設備檢測記錄", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(eqPingService.deleteEqPingByIds(ids));
    }
}
