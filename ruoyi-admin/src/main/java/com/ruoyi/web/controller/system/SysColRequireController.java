package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.SysColRequire;
import com.ruoyi.system.service.ISysColRequireService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 字段必填配置Controller
 * 
 * @author ruoyi
 * @date 2023-02-20
 */
@RestController
@RequestMapping("/system/SysColRequire")
public class SysColRequireController extends BaseController
{
    @Autowired
    private ISysColRequireService sysColRequireService;

    /**
     * 查询字段必填配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysColRequire sysColRequire)
    {
        startPage();
        List<SysColRequire> list = sysColRequireService.selectSysColRequireList(sysColRequire);
        return getDataTable(list);
    }

    /**
     * 导出字段必填配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:export')")
    @Log(title = "字段必填配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysColRequire sysColRequire)
    {
        List<SysColRequire> list = sysColRequireService.selectSysColRequireList(sysColRequire);
        ExcelUtil<SysColRequire> util = new ExcelUtil<SysColRequire>(SysColRequire.class);
        util.exportExcel(response, list, "字段必填配置数据");
    }

    /**
     * 获取字段必填配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysColRequireService.selectSysColRequireById(id));
    }

    /**
     * 新增字段必填配置
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:add')")
    @Log(title = "字段必填配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysColRequire sysColRequire)
    {
        return toAjax(sysColRequireService.insertSysColRequire(sysColRequire));
    }

    /**
     * 修改字段必填配置
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:edit')")
    @Log(title = "字段必填配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysColRequire sysColRequire)
    {
        return toAjax(sysColRequireService.updateSysColRequire(sysColRequire));
    }

    /**
     * 删除字段必填配置
     */
    @PreAuthorize("@ss.hasPermi('system:SysColRequire:remove')")
    @Log(title = "字段必填配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysColRequireService.deleteSysColRequireByIds(ids));
    }

}
